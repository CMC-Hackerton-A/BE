package com.example.neodinary_hackaton.domain.Artist.service;

import com.example.neodinary_hackaton.domain.Artist.Converter.ArtistConverter;
import com.example.neodinary_hackaton.domain.Artist.dto.ArtistRequestDto;
import com.example.neodinary_hackaton.domain.Artist.dto.ArtistResponseDto;
import com.example.neodinary_hackaton.domain.Artist.entity.Artist;
import com.example.neodinary_hackaton.domain.Artist.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArtistService {
    private final ArtistRepository artistRepository;

    public List<ArtistResponseDto.SearchResponse> searchArtists(String name) {
        List<Artist> artists = artistRepository.findTop10ByNameStartingWith(
                name,
                PageRequest.of(0, 10)
        );

        return artists.stream()
                .map(ArtistConverter::toSearchResponse)
                .toList();
    }

    public ArtistResponseDto.SearchResponse saveExternalArtist(
            ArtistRequestDto.ExternalRequest request
    ) {
        Artist artist = ArtistConverter.toArtist(request);
        Artist savedArtist = artistRepository.save(artist);

        return ArtistConverter.toSearchResponse(savedArtist);
    }
}
