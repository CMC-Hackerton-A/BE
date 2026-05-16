package com.example.neodinary_hackaton.domain.Artist.service;

import com.example.neodinary_hackaton.domain.Artist.client.ArtistExternalClient;
import com.example.neodinary_hackaton.domain.Artist.Converter.ArtistConverter;
import com.example.neodinary_hackaton.domain.Artist.dto.ArtistRequestDto;
import com.example.neodinary_hackaton.domain.Artist.dto.ArtistResponseDto;
import com.example.neodinary_hackaton.domain.Artist.entity.Artist;
import com.example.neodinary_hackaton.domain.Artist.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
