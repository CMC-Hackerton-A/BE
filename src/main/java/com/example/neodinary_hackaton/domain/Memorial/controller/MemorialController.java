package com.example.neodinary_hackaton.domain.Memorial.controller;

import com.example.neodinary_hackaton.domain.Memorial.dto.MemorialMessageRequest;
import com.example.neodinary_hackaton.domain.Memorial.dto.MemorialMessageResponse;
import com.example.neodinary_hackaton.domain.Memorial.dto.MemorialPageResponse;
import com.example.neodinary_hackaton.domain.Memorial.service.MemorialService;
import com.example.neodinary_hackaton.global.api.ApiResponse;
import com.example.neodinary_hackaton.global.api.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memorials")
public class MemorialController {
    private final MemorialService memorialService;

    @GetMapping("/memorial/messages")
    public ResponseEntity<ApiResponse<MemorialPageResponse.MemorialMessagesResponse>> getMemorialMessages(

    ) {
        MemorialPageResponse.MemorialMessagesResponse result = memorialService.getMemorialMessages();
        return ApiResponse.onSuccessResponse(GeneralSuccessCode.GET_SUCCESS, result);
    }

    @PostMapping("/memorial/messages")
    public ResponseEntity<ApiResponse<MemorialMessageResponse.CreateMemorialMessageResponse>> createMemorialMessage(
            @RequestBody MemorialMessageRequest.CreateMemorialMessageRequest request
    ) {
        MemorialMessageResponse.CreateMemorialMessageResponse result =
                memorialService.createMemorialMessage(request);

        return ApiResponse.onSuccessResponse(GeneralSuccessCode.POST_SUCCESS, result);
    }
}
