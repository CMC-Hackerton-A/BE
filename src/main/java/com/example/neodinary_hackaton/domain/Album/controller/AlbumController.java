package com.example.neodinary_hackaton.domain.Album.controller;

import com.example.neodinary_hackaton.domain.Album.dto.AlbumResponseDto;
import com.example.neodinary_hackaton.domain.Album.service.AlbumService;
import com.example.neodinary_hackaton.global.api.ApiResponse;
import com.example.neodinary_hackaton.global.api.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/albums")
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping("/last")
    public ResponseEntity<ApiResponse<AlbumResponseDto.LastAlbumAndTrackResponse>> getLastAlbumAndTrack(
            @RequestParam(name = "artist_id") Long artistId
    ) {
        AlbumResponseDto.LastAlbumAndTrackResponse result = albumService.getLastAlbumAndTrack(artistId);
        return ApiResponse.onSuccessResponse(GeneralSuccessCode.GET_SUCCESS, result);
    }
}
