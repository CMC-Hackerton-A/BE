package com.example.neodinary_hackaton.domain.Album.dto;

import lombok.Builder;
import lombok.Getter;

public class AlbumResponseDto {

    @Getter
    @Builder
    public static class LastAlbumAndTrackResponse {
        private LastAlbumInfo lastAlbum;
        private LastTrackInfo lastTrack;
    }

    @Getter
    @Builder
    public static class LastAlbumInfo {
        private Long id;
        private String name;
        private String coverUrl;
        private String releaseDate; // "1985.08" 포맷팅을 위해 String 사용
    }

    @Getter
    @Builder
    public static class LastTrackInfo {
        private Long id;
        private String name;
        private String coverUrl; // Album에서 가져옴
        private String releaseDate; // Album에서 가져옴
    }
}
