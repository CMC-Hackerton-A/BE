package com.example.neodinary_hackaton.domain.Artist.dto;

import lombok.Builder;
import lombok.Getter;

public class ArtistResponseDto {

    @Getter
    @Builder
    public static class SearchResponse {

        private Long id;

        private String mbid;

        private String name;

        private Integer beginYear;

        private Integer endYear;

        private Integer starCount;

        private String artistImageUrl;
    }
}
