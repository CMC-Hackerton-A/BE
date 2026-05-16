package com.example.neodinary_hackaton.domain.Artist.service;

import com.example.neodinary_hackaton.domain.Artist.client.ArtistExternalClient;
import com.example.neodinary_hackaton.domain.Artist.Converter.ArtistConverter;
import com.example.neodinary_hackaton.domain.Artist.dto.ArtistRequestDto;
import com.example.neodinary_hackaton.domain.Artist.dto.ArtistResponseDto;
import com.example.neodinary_hackaton.domain.Artist.entity.Artist;
import com.example.neodinary_hackaton.domain.Artist.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistExternalClient artistExternalClient;

    public List<ArtistResponseDto.SearchResponse> searchArtists(String name) {
        List<Artist> artists = artistRepository.findTop10ByNameStartingWith(name);

        if (artists.isEmpty()) {
            ArtistRequestDto.ExternalRequest externalRequest =
                    artistExternalClient.fetchArtistExternalInfo(name);

            if (!artistRepository.existsByMbid(externalRequest.getMbid())) {
                Artist artist = ArtistConverter.toArtist(externalRequest);
                artistRepository.save(artist);
            }

            artists = artistRepository.findTop10ByNameStartingWith(name);
        }

        return artists.stream()
                .map(ArtistConverter::toSearchResponse)
                .toList();
    }

    @Transactional
    public ArtistResponseDto.StarIncreaseResponse increaseStarCount(Long artistId) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아티스트를 찾을 수 없습니다."));

        artist.increaseStarCount();

        return ArtistConverter.toStarIncreaseResponse(artist);
    }
}
