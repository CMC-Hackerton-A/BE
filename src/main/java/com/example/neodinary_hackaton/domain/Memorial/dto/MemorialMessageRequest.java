package com.example.neodinary_hackaton.domain.Memorial.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemorialMessageRequest {

    @Getter
    @NoArgsConstructor
    public static class CreateMemorialMessageRequest {

        @JsonProperty("artist_id")
        private Long artistId;

        private String content;
    }
}
