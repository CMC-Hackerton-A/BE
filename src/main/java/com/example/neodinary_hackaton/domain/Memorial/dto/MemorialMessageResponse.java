package com.example.neodinary_hackaton.domain.Memorial.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class MemorialMessageResponse {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateMemorialMessageResponse {

        private Long id;

        @JsonProperty("artist_id")
        private Long artistId;

        private String content;

        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemorialMessageInfo {

        private Long id;

        @JsonProperty("artist_id")
        private Long artistId;

        private String content;

        private LocalDateTime createdAt;
    }
}
