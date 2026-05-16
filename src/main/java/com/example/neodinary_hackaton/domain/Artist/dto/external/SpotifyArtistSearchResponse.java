package com.example.neodinary_hackaton.domain.Artist.dto.external;

import lombok.Getter;

import java.util.List;

@Getter
public class SpotifyArtistSearchResponse {

    private Artists artists;

    @Getter
    public static class Artists {

        private List<SpotifyArtist> items;
    }

    @Getter
    public static class SpotifyArtist {

        private String id;

        private String name;

        private List<String> genres;

        private List<Image> images;
    }

    @Getter
    public static class Image {

        private String url;

        private Integer height;

        private Integer width;
    }
}
