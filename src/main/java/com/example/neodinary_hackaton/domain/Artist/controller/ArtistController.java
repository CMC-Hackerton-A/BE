package com.example.neodinary_hackaton.domain.Artist.controller;

import com.example.neodinary_hackaton.domain.Artist.dto.ArtistRequestDto;
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
            @RequestParam("q") String q
    ) {
        List<ArtistResponseDto.SearchResponse> result = artistService.searchArtists(q);
        return ApiResponse.onSuccessResponse(GeneralSuccessCode.GET_SUCCESS, result);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ArtistResponseDto.SearchResponse>> createArtist(
            @RequestBody ArtistRequestDto.SaveRequest request
    ) {
        ArtistResponseDto.SearchResponse result = artistService.createArtist(request);
        return ApiResponse.onSuccessResponse(GeneralSuccessCode.POST_SUCCESS, result);
    }
}
