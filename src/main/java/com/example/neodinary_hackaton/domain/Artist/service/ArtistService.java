package com.example.neodinary_hackaton.domain.Artist.service;

import com.example.neodinary_hackaton.domain.Artist.converter.ArtistConverter;
import com.example.neodinary_hackaton.domain.Artist.dto.ArtistRequestDto;
import com.example.neodinary_hackaton.domain.Artist.dto.ArtistResponseDto;
import com.example.neodinary_hackaton.domain.Artist.entity.Artist;
import com.example.neodinary_hackaton.domain.Artist.repository.ArtistRepository;
import com.example.neodinary_hackaton.global.api.GeneralErrorCode;
import com.example.neodinary_hackaton.global.api.ProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;

    public List<ArtistResponseDto.SearchResponse> searchArtists(String q) {
        String keyword = q == null ? "" : q.trim();

        if (!StringUtils.hasText(keyword)) {
            return List.of();
        }

        List<ArtistResponseDto.SearchResponse> localResults = artistRepository
                .searchTop10ByNameContaining(keyword)
                .stream()
                .map(ArtistConverter::toSearchResponse)
                .toList();

        return localResults;
    }

    public List<ArtistResponseDto.TopArtistResponse> getTopArtistsByStarCount() {
        return artistRepository.findTop10OrderByStarCountDesc()
                .stream()
                .map(ArtistConverter::toTopArtistResponse)
                .toList();
    }

    public ArtistResponseDto.DetailResponse getArtistDetail(Long artistId) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.NOT_FOUND));

        return ArtistConverter.toDetailResponse(artist);
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
