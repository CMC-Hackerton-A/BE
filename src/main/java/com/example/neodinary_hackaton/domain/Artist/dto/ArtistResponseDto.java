package com.example.neodinary_hackaton.domain.Artist.dto;

import lombok.Builder;
import lombok.Getter;

public class ArtistResponseDto {

    @Getter
    @Builder
    public static class SearchResponse {

        private Long artistId;

        private String artistName;

        private String imageUrl;

        private String activityPeriod;

        private String activityYears;

        private String country;

        private String genre;
    }

    @Getter
    @Builder
    public static class TopArtistResponse {

        private Long artistId;

        private String artistName;

        private String imageUrl;

        private String activityPeriod;

        private String activityYears;

        private Integer starCount;
    }

    @Getter
    @Builder
    public static class DetailResponse {

        private Long artistId;

        private String artistName;

        private String imageUrl;

        private String genre;

        private String country;

        private String activityPeriod;

        private String activityYears;

        private Integer starCount;
    }
}
