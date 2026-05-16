package com.example.neodinary_hackaton.domain.Memorial.service;

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

    public MemorialPageResponse.MemorialMessagesResponse getMemorialMessages() {
        List<MemorialMessage> messages = memorialMessageRepository.findAllByOrderByCreatedAtDesc();

        return MemorialConverter.toMemorialMessagesResponse(messages);
    }

    @Transactional
    public MemorialMessageResponse.CreateMemorialMessageResponse createMemorialMessage(
            MemorialMessageRequest.CreateMemorialMessageRequest request
    ) {
        MemorialMessage memorialMessage = MemorialConverter.toMemorialMessage(request);

        MemorialMessage savedMessage = memorialMessageRepository.save(memorialMessage);

        return MemorialConverter.toCreateMemorialMessageResponse(savedMessage);
    }
}