package com.example.neodinary_hackaton.domain.Artist.converter;

import com.example.neodinary_hackaton.domain.Artist.dto.ArtistRequestDto;
import com.example.neodinary_hackaton.domain.Artist.dto.ArtistResponseDto;
import com.example.neodinary_hackaton.domain.Artist.entity.Artist;

import java.time.LocalDateTime;

public class ArtistConverter {

    public static ArtistResponseDto.SearchResponse toSearchResponse(Artist artist) {
        return ArtistResponseDto.SearchResponse.builder()
                .id(artist.getId())
                .mbid(artist.getMbid())
                .name(artist.getName())
                .beginYear(artist.getBeginYear())
                .endYear(artist.getEndYear())
                .starCount(artist.getStarCount())
                .artistImageUrl(artist.getArtistImageUrl())
                .build();
    }

    public static ArtistResponseDto.SearchResponse toSearchResponse(ArtistRequestDto.ExternalRequest request) {
        return ArtistResponseDto.SearchResponse.builder()
                .id(null)
                .mbid(request.getMbid())
                .name(request.getName())
                .beginYear(request.getBeginYear())
                .endYear(request.getEndYear())
                .starCount(request.getStarCount())
                .artistImageUrl(request.getArtistImageUrl())
                .build();
    }

    public static Artist toArtist(ArtistRequestDto.ExternalRequest request) {
        LocalDateTime now = LocalDateTime.now();

        return Artist.builder()
                .mbid(request.getMbid())
                .name(request.getName())
                .beginYear(request.getBeginYear())
                .endYear(request.getEndYear())
                .country(request.getCountry())
                .genre(request.getGenre())
                .starCount(request.getStarCount() != null ? request.getStarCount() : 0)
                .artistImageUrl(request.getArtistImageUrl())
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public static Artist toArtist(ArtistRequestDto.SaveRequest request) {
        LocalDateTime now = LocalDateTime.now();

        return Artist.builder()
                .mbid(request.getMbid())
                .name(request.getName())
                .beginYear(request.getBeginYear())
                .endYear(request.getEndYear())
                .country(request.getCountry())
                .genre(request.getGenre())
                .starCount(request.getStarCount() != null ? request.getStarCount() : 0)
                .artistImageUrl(request.getArtistImageUrl())
                .createdAt(now)
                .updatedAt(now)
                .build();
    }
}
