package com.example.neodinary_hackaton.domain.Memorial.service;

import com.example.neodinary_hackaton.domain.Artist.entity.Artist;
import com.example.neodinary_hackaton.domain.Artist.repository.ArtistRepository;
import com.example.neodinary_hackaton.domain.Memorial.converter.MemorialConverter;
import com.example.neodinary_hackaton.domain.Memorial.dto.MemorialMessageRequest;
import com.example.neodinary_hackaton.domain.Memorial.dto.MemorialMessageResponse;
import com.example.neodinary_hackaton.domain.Memorial.dto.MemorialPageResponse;
import com.example.neodinary_hackaton.domain.Memorial.entity.MemorialMessage;
import com.example.neodinary_hackaton.domain.Memorial.repository.MemorialMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemorialService {

    private final MemorialMessageRepository memorialMessageRepository;
    private final ArtistRepository artistRepository;

    public MemorialPageResponse.MemorialMessagesResponse getMemorialMessages() {
        List<MemorialMessage> messages =
                memorialMessageRepository.findAllByOrderByCreatedAtDesc();

        return MemorialConverter.toMemorialMessagesResponse(messages);
    }

    @Transactional
    public MemorialMessageResponse.CreateMemorialMessageResponse createMemorialMessage(
            MemorialMessageRequest.CreateMemorialMessageRequest request
    ) {
        Artist artist = artistRepository.findById(request.getArtistId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아티스트입니다."));

        MemorialMessage memorialMessage =
                MemorialConverter.toMemorialMessage(request, artist);

        MemorialMessage savedMessage =
                memorialMessageRepository.save(memorialMessage);

        return MemorialConverter.toCreateMemorialMessageResponse(savedMessage);
    }
}