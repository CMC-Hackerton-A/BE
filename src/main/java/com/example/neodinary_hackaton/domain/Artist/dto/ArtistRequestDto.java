package com.example.neodinary_hackaton.domain.Artist.dto;

import lombok.*;

public class ArtistRequestDto {

    @Getter
    @Builder
    public static class ExternalRequest {

        // MusicBrainz artist.id
        private String mbid;

        // MusicBrainz artist.name 또는 Spotify artist.name
        private String name;

        // MusicBrainz life-span.begin에서 연도만 추출
        private Integer beginYear;

        // MusicBrainz life-span.end에서 연도만 추출
        private Integer endYear;

        // MusicBrainz artist.country
        private String country;

        // Spotify genres[0] 우선, 없으면 MusicBrainz tags/genres
        private String genre;

        // 외부 API 값 아님. 우리 서비스 내부 값. 최초 저장 시 0
        private Integer starCount;

        // Spotify images[0].url
        private String artistImageUrl;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SaveRequest {

        private String mbid;

        private String name;

        private Integer beginYear;

        private Integer endYear;

        private String country;

        private String genre;

        private Integer starCount;

        private String artistImageUrl;
    }
}
