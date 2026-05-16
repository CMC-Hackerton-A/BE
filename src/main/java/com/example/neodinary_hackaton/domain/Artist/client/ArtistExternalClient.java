package com.example.neodinary_hackaton.domain.Artist.client;

import com.example.neodinary_hackaton.domain.Artist.dto.ArtistRequestDto;
import com.example.neodinary_hackaton.domain.Artist.dto.external.MusicBrainzArtistSearchResponse;
import com.example.neodinary_hackaton.domain.Artist.dto.external.SpotifyAlbumSearchResponse;
import com.example.neodinary_hackaton.domain.Artist.dto.external.SpotifyArtistSearchResponse;
import com.example.neodinary_hackaton.domain.Artist.dto.external.SpotifyTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArtistExternalClient {

    private final RestClient restClient;

    @Value("${external.spotify.client-id}")
    private String spotifyClientId;

    @Value("${external.spotify.client-secret}")
    private String spotifyClientSecret;

    @Value("${external.musicbrainz.user-agent}")
    private String musicBrainzUserAgent;

    public ArtistRequestDto.ExternalRequest fetchArtistExternalInfo(String name) {
        log.info("[Artist] fetchArtistExternalInfo 시작, name={}", name);

        MusicBrainzArtistSearchResponse.MusicBrainzArtist musicBrainzArtist =
                fetchMusicBrainzArtist(name);

        log.info("[MusicBrainz] mbid={}, name={}, country={}, genres={}, tags={}",
                musicBrainzArtist.getId(),
                musicBrainzArtist.getName(),
                musicBrainzArtist.getCountry(),
                musicBrainzArtist.getGenres(),
                musicBrainzArtist.getTags());

        SpotifyArtistSearchResponse.SpotifyArtist spotifyArtist = null;

        try {
            spotifyArtist = fetchSpotifyArtist(musicBrainzArtist.getName());
        } catch (Exception e) {
            // Spotify 호출 실패 시에도 전체 검색 API는 실패시키지 않음.
            log.warn("[Spotify] fetchSpotifyArtist 실패, name={}, message={}",
                    musicBrainzArtist.getName(), e.getMessage(), e);
            spotifyArtist = null;
        }

        if (spotifyArtist == null) {
            log.warn("[Spotify] 최종 spotifyArtist == null (이미지/장르 null 처리됨)");
        } else {
            log.info("[Spotify] 최종 artist id={}, name={}, genres={}, images={}",
                    spotifyArtist.getId(),
                    spotifyArtist.getName(),
                    spotifyArtist.getGenres(),
                    spotifyArtist.getImages());
        }

        return ArtistRequestDto.ExternalRequest.builder()
                .mbid(musicBrainzArtist.getId())
                .name(musicBrainzArtist.getName())
                .beginYear(extractYear(
                        musicBrainzArtist.getLifeSpan() != null
                                ? musicBrainzArtist.getLifeSpan().getBegin()
                                : null
                ))
                .endYear(extractYear(
                        musicBrainzArtist.getLifeSpan() != null
                                ? musicBrainzArtist.getLifeSpan().getEnd()
                                : null
                ))
                .country(musicBrainzArtist.getCountry())
                .genre(resolveGenre(musicBrainzArtist, spotifyArtist))
                .starCount(0)
                .artistImageUrl(resolveArtistImageUrl(spotifyArtist))
                .build();
    }

    private MusicBrainzArtistSearchResponse.MusicBrainzArtist fetchMusicBrainzArtist(String name) {

        URI uri = UriComponentsBuilder
                .fromUriString("https://musicbrainz.org/ws/2/artist")
                .queryParam("query", "artist:" + name)
                .queryParam("fmt", "json")
                .queryParam("limit", 1)
                .queryParam("inc", "genres+tags")
                .build()
                .encode()
                .toUri();

        log.info("[MusicBrainz] artist search 요청 uri={}", uri);

        MusicBrainzArtistSearchResponse response = restClient.get()
                .uri(uri)
                .header(HttpHeaders.USER_AGENT, musicBrainzUserAgent)
                .retrieve()
                .body(MusicBrainzArtistSearchResponse.class);

        if (response == null || response.getArtists() == null || response.getArtists().isEmpty()) {
            log.warn("[MusicBrainz] artist search 응답 비어있음, response={}", response);
            throw new IllegalArgumentException("MusicBrainz에서 해당 아티스트를 찾을 수 없습니다.");
        }

        return response.getArtists().get(0);
    }

    private SpotifyArtistSearchResponse.SpotifyArtist fetchSpotifyArtist(String name) {
        String accessToken = getSpotifyAccessToken();

        // 1) album 검색으로 artist id 후보 찾기
        String artistId = findSpotifyArtistIdViaAlbumSearch(name, accessToken);
        if (artistId == null) {
            log.warn("[Spotify] album search 결과에서 매칭되는 artistId를 찾지 못함, name={}", name);
            return null;
        }

        // 2) artist id로 상세 조회 (images, genres 포함)
        return fetchSpotifyArtistById(artistId, accessToken);
    }

    private String findSpotifyArtistIdViaAlbumSearch(String name, String accessToken) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://api.spotify.com/v1/search")
                .queryParam("q", name)
                .queryParam("type", "album")
                .queryParam("limit", 20)
                .build()
                .encode()
                .toUri();

        log.info("[Spotify] album search 요청 uri={}", uri);

        SpotifyAlbumSearchResponse response = restClient.get()
                .uri(uri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .body(SpotifyAlbumSearchResponse.class);

        if (response == null
                || response.getAlbums() == null
                || response.getAlbums().getItems() == null) {
            log.warn("[Spotify] album search 응답 비어있음, response={}", response);
            return null;
        }

        // 디버깅: 어떤 앨범/아티스트들이 검색됐는지 전부 찍기
        response.getAlbums().getItems().forEach(album -> {
            if (album.getArtists() == null) return;
            album.getArtists().forEach(a ->
                    log.info("[Spotify] album 후보 album.name='{}' → artist.id={}, artist.name='{}'",
                            album.getName(), a.getId(), a.getName())
            );
        });

        String matchedId = response.getAlbums().getItems().stream()
                .filter(album -> album.getArtists() != null)
                .flatMap(album -> album.getArtists().stream())
                .filter(a -> a.getName() != null && a.getName().equalsIgnoreCase(name))
                .map(SpotifyAlbumSearchResponse.AlbumArtist::getId)
                .findFirst()
                .orElse(null);

        log.info("[Spotify] album search 매칭 결과, 입력 name='{}', matched artistId={}",
                name, matchedId);

        return matchedId;
    }

    private SpotifyArtistSearchResponse.SpotifyArtist fetchSpotifyArtistById(
            String artistId, String accessToken
    ) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://api.spotify.com/v1/artists/" + artistId)
                .build()
                .encode()
                .toUri();

        log.info("[Spotify] artist detail 요청 uri={}", uri);

        SpotifyArtistSearchResponse.SpotifyArtist artist = restClient.get()
                .uri(uri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .body(SpotifyArtistSearchResponse.SpotifyArtist.class);

        if (artist == null) {
            log.warn("[Spotify] artist detail 응답이 null, artistId={}", artistId);
        } else {
            log.info("[Spotify] artist detail 응답 id={}, name={}, genres={}, images.size={}, images={}",
                    artist.getId(),
                    artist.getName(),
                    artist.getGenres(),
                    artist.getImages() == null ? null : artist.getImages().size(),
                    artist.getImages());
        }

        return artist;
    }

    private String getSpotifyAccessToken() {
        String credentials = spotifyClientId + ":" + spotifyClientSecret;
        String encodedCredentials = Base64.getEncoder()
                .encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        SpotifyTokenResponse response = restClient.post()
                .uri("https://accounts.spotify.com/api/token")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedCredentials)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(body)
                .retrieve()
                .body(SpotifyTokenResponse.class);

        if (response == null || response.getAccessToken() == null) {
            log.error("[Spotify] access token 발급 실패, response={}", response);
            throw new IllegalArgumentException("Spotify access token 발급에 실패했습니다.");
        }

        String token = response.getAccessToken();
        String tokenPrefix = token.substring(0, Math.min(10, token.length())) + "...";
        log.info("[Spotify] access token 발급 성공, tokenType={}, expiresIn={}, tokenPrefix={}",
                response.getTokenType(),
                response.getExpiresIn(),
                tokenPrefix);

        return token;
    }

    private Integer extractYear(String date) {
        if (date == null || date.isBlank()) {
            return null;
        }

        if (date.length() < 4) {
            return null;
        }

        try {
            return Integer.parseInt(date.substring(0, 4));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String resolveGenre(
            MusicBrainzArtistSearchResponse.MusicBrainzArtist musicBrainzArtist,
            SpotifyArtistSearchResponse.SpotifyArtist spotifyArtist
    ) {
        if (spotifyArtist != null
                && spotifyArtist.getGenres() != null
                && !spotifyArtist.getGenres().isEmpty()) {
            return spotifyArtist.getGenres().get(0);
        }

        if (musicBrainzArtist.getGenres() != null
                && !musicBrainzArtist.getGenres().isEmpty()) {
            return musicBrainzArtist.getGenres().get(0).getName();
        }

        if (musicBrainzArtist.getTags() != null
                && !musicBrainzArtist.getTags().isEmpty()) {
            return musicBrainzArtist.getTags().get(0).getName();
        }

        return null;
    }

    private String resolveArtistImageUrl(SpotifyArtistSearchResponse.SpotifyArtist spotifyArtist) {
        if (spotifyArtist == null
                || spotifyArtist.getImages() == null
                || spotifyArtist.getImages().isEmpty()) {
            return null;
        }

        return spotifyArtist.getImages().get(0).getUrl();
    }
}