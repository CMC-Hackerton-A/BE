package com.example.neodinary_hackaton.domain.Artist.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class MusicBrainzArtistSearchResponse {

    private List<MusicBrainzArtist> artists;

    @Getter
    public static class MusicBrainzArtist {

        private String id;

        private String name;

        private String country;

        @JsonProperty("life-span")
        private LifeSpan lifeSpan;

        private List<Tag> tags;

        private List<Genre> genres;
    }

    @Getter
    public static class LifeSpan {

        private String begin;

        private String end;

        private Boolean ended;
    }

    @Getter
    public static class Tag {

        private String name;

        private Integer count;
    }

    @Getter
    public static class Genre {

        private String name;

        private Integer count;
    }
}
