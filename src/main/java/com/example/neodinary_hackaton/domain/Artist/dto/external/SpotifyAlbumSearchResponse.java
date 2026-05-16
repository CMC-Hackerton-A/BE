package com.example.neodinary_hackaton.domain.Artist.dto.external;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SpotifyAlbumSearchResponse {

    private Albums albums;

    @Getter
    @NoArgsConstructor
    public static class Albums {
        private List<AlbumItem> items;
    }

    @Getter
    @NoArgsConstructor
    public static class AlbumItem {
        private String name;
        private List<AlbumArtist> artists;
    }

    @Getter
    @NoArgsConstructor
    public static class AlbumArtist {
        private String id;
        private String name;
    }
}