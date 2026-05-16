package com.example.neodinary_hackaton.domain.Artist.dto;

import lombok.Builder;
import lombok.Getter;

public class ArtistRequestDto {

    @Getter
    @Builder
    public static class ExternalRequest {

        /**
         * MusicBrainz artist.id
         */
        private String mbid;

        /**
         * MusicBrainz artist.name 또는 Spotify artist.name
         */
        private String name;

        /**
         * MusicBrainz life-span.begin에서 연도만 추출
         */
        private Integer beginYear;

        /**
         * MusicBrainz life-span.end에서 연도만 추출
         */
        private Integer endYear;

        /**
         * MusicBrainz country
         */
        private String country;

        /**
         * MusicBrainz genres/tags 또는 Spotify genres
         */
        private String genre;

        /**
         * 외부 API 값 아님.
         * 우리 서비스 내부 누적 카운터.
         * 기본값 0.
         */
        private Integer starCount;

        /**
         * Spotify images[0].url
         */
        private String artistImageUrl;
    }
}