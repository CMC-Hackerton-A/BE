package com.example.neodinary_hackaton.domain.Memorial.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class MemorialPageResponse {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemorialMessagesResponse {

        private List<MemorialMessageResponse.MemorialMessageInfo> messages;
    }
}
