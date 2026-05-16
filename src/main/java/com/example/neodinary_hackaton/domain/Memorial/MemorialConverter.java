package com.example.neodinary_hackaton.domain.Memorial;

import com.example.neodinary_hackaton.domain.Artist.entity.Artist;
import com.example.neodinary_hackaton.domain.Memorial.dto.MemorialMessageRequest;
import com.example.neodinary_hackaton.domain.Memorial.dto.MemorialMessageResponse;
import com.example.neodinary_hackaton.domain.Memorial.dto.MemorialPageResponse;
import com.example.neodinary_hackaton.domain.Memorial.entity.MemorialMessage;

import java.time.LocalDateTime;
import java.util.List;

public class MemorialConverter {

    public static MemorialMessage toMemorialMessage(
            MemorialMessageRequest.CreateMemorialMessageRequest request,
            Artist artist
    ) {
        return MemorialMessage.builder()
                .artist(artist)
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static MemorialMessageResponse.CreateMemorialMessageResponse toCreateMemorialMessageResponse(
            MemorialMessage memorialMessage
    ) {
        return MemorialMessageResponse.CreateMemorialMessageResponse.builder()
                .id(memorialMessage.getId())
                .artistId(memorialMessage.getArtist().getId())
                .content(memorialMessage.getContent())
                .createdAt(memorialMessage.getCreatedAt())
                .build();
    }

    public static MemorialMessageResponse.MemorialMessageInfo toMemorialMessageInfo(
            MemorialMessage memorialMessage
    ) {
        return MemorialMessageResponse.MemorialMessageInfo.builder()
                .id(memorialMessage.getId())
                .artistId(memorialMessage.getArtist().getId())
                .content(memorialMessage.getContent())
                .createdAt(memorialMessage.getCreatedAt())
                .build();
    }

    public static MemorialPageResponse.MemorialMessagesResponse toMemorialMessagesResponse(
            List<MemorialMessage> messages
    ) {
        return MemorialPageResponse.MemorialMessagesResponse.builder()
                .messages(
                        messages.stream()
                                .map(MemorialConverter::toMemorialMessageInfo)
                                .toList()
                )
                .build();
    }
}