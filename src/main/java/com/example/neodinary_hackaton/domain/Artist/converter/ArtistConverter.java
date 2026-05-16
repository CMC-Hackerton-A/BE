package com.example.neodinary_hackaton.domain.Artist.converter;

import com.example.neodinary_hackaton.domain.Artist.dto.ArtistRequestDto;
import com.example.neodinary_hackaton.domain.Artist.dto.ArtistResponseDto;
import com.example.neodinary_hackaton.domain.Artist.entity.Artist;

import java.time.LocalDateTime;

public class ArtistConverter {

    public static ArtistResponseDto.SearchResponse toSearchResponse(Artist artist) {
        return ArtistResponseDto.SearchResponse.builder()
                .artistName(artist.getName())
                .imageUrl(artist.getArtistImageUrl())
                .activityPeriod(formatActivityPeriod(artist.getBeginYear(), artist.getEndYear()))
                .activityYears(formatActivityYears(artist.getBeginYear(), artist.getEndYear()))
                .country(artist.getCountry())
                .genre(artist.getGenre())
                .build();
    }

    public static ArtistResponseDto.SearchResponse toSearchResponse(ArtistRequestDto.ExternalRequest request) {
        return ArtistResponseDto.SearchResponse.builder()
                .artistName(request.getName())
                .imageUrl(request.getArtistImageUrl())
                .activityPeriod(formatActivityPeriod(request.getBeginYear(), request.getEndYear()))
                .activityYears(formatActivityYears(request.getBeginYear(), request.getEndYear()))
                .country(request.getCountry())
                .genre(request.getGenre())
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

    private static String formatActivityPeriod(Integer beginYear, Integer endYear) {
        if (beginYear == null && endYear == null) {
            return null;
        }

        String begin = beginYear != null ? beginYear.toString() : "";
        String end = endYear != null ? endYear.toString() : "";

        if (begin.isEmpty()) {
            return " - " + end;
        }

        if (end.isEmpty()) {
            return begin + " - ";
        }

        return begin + " - " + end;
    }

    private static String formatActivityYears(Integer beginYear, Integer endYear) {
        if (beginYear == null || endYear == null || endYear < beginYear) {
            return null;
        }

        return String.valueOf(endYear - beginYear + 1);
    }
}
