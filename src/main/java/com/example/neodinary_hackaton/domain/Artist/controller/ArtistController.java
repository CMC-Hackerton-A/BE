package com.example.neodinary_hackaton.domain.Artist.controller;

import com.example.neodinary_hackaton.domain.Artist.dto.ArtistResponseDto;
import com.example.neodinary_hackaton.domain.Artist.service.ArtistService;
import com.example.neodinary_hackaton.global.api.ApiResponse;
import com.example.neodinary_hackaton.global.api.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ArtistResponseDto.SearchResponse>>> searchArtists(
            @RequestParam String name
    ) {
        List<ArtistResponseDto.SearchResponse> result = artistService.searchArtists(name);
        return ApiResponse.onSuccessResponse(GeneralSuccessCode.GET_SUCCESS, result);
    }

    @PatchMapping("/{artist_id}/stars")
    public ResponseEntity<ApiResponse<ArtistResponseDto.StarIncreaseResponse>> increaseStarCount(
            @PathVariable(name = "artist_id") Long artistId
    ) {
        ArtistResponseDto.StarIncreaseResponse result = artistService.increaseStarCount(artistId);
        return ApiResponse.onSuccessResponse(GeneralSuccessCode.GET_SUCCESS, result);
    }
}
