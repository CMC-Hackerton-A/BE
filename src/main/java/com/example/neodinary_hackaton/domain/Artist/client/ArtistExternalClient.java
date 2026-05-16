package com.example.neodinary_hackaton.domain.Artist.client;

import com.example.neodinary_hackaton.domain.Artist.dto.ArtistRequestDto;
import com.example.neodinary_hackaton.domain.Artist.dto.external.MusicBrainzArtistSearchResponse;
import com.example.neodinary_hackaton.domain.Artist.dto.external.SpotifyArtistSearchResponse;
import com.example.neodinary_hackaton.domain.Artist.dto.external.SpotifyTokenResponse;
import com.example.neodinary_hackaton.global.api.GeneralErrorCode;
import com.example.neodinary_hackaton.global.api.ProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

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
        MusicBrainzArtistSearchResponse.MusicBrainzArtist musicBrainzArtist =
                fetchMusicBrainzArtist(name);

        SpotifyArtistSearchResponse.SpotifyArtist spotifyArtist = null;

        try {
            spotifyArtist = fetchSpotifyArtist(musicBrainzArtist.getName());
        } catch (Exception e) {
            // Spotify 호출 실패 시에도 전체 검색 API는 실패시키지 않음.
            // 현재 Spotify Premium 제한 등으로 403이 날 수 있으므로 이미지/Spotify 장르는 null 처리.
            spotifyArtist = null;
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

        MusicBrainzArtistSearchResponse response = restClient.get()
                .uri(uri)
                .header(HttpHeaders.USER_AGENT, musicBrainzUserAgent)
                .retrieve()
                .body(MusicBrainzArtistSearchResponse.class);

        if (response == null || response.getArtists() == null || response.getArtists().isEmpty()) {
            throw new ProjectException(GeneralErrorCode.ARTIST_NOT_FOUND);
        }

        return response.getArtists().get(0);
    }

    private SpotifyArtistSearchResponse.SpotifyArtist fetchSpotifyArtist(String name) {
        String accessToken = getSpotifyAccessToken();

        URI uri = UriComponentsBuilder
                .fromUriString("https://api.spotify.com/v1/search")
                .queryParam("q", name)
                .queryParam("type", "artist")
                .queryParam("limit", 1)
                .build()
                .encode()
                .toUri();

        SpotifyArtistSearchResponse response = restClient.get()
                .uri(uri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .body(SpotifyArtistSearchResponse.class);

        if (response == null
                || response.getArtists() == null
                || response.getArtists().getItems() == null
                || response.getArtists().getItems().isEmpty()) {
            return null;
        }

        return response.getArtists().getItems().get(0);
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
            throw new ProjectException(GeneralErrorCode.EXTERNAL_API_ERROR);
        }

        return response.getAccessToken();
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