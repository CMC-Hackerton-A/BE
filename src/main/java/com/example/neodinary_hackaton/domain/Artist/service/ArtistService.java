package com.example.neodinary_hackaton.domain.Artist.service;

import com.example.neodinary_hackaton.domain.Artist.client.ArtistExternalClient;
import com.example.neodinary_hackaton.domain.Artist.converter.ArtistConverter;
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
        ArtistRequestDto.ExternalRequest externalRequest =
                artistExternalClient.fetchArtistExternalInfo(name);

        return List.of(ArtistConverter.toSearchResponse(externalRequest));
    }

    public ArtistResponseDto.SearchResponse createArtist(ArtistRequestDto.SaveRequest request) {
        return artistRepository.findByMbid(request.getMbid())
                .map(ArtistConverter::toSearchResponse)
                .orElseGet(() -> {
                    Artist artist = ArtistConverter.toArtist(request);
                    Artist savedArtist = artistRepository.save(artist);
                    return ArtistConverter.toSearchResponse(savedArtist);
                });
    }
}
