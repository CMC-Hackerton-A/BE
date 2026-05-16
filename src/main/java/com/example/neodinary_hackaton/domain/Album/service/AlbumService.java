package com.example.neodinary_hackaton.domain.Album.service;

import com.example.neodinary_hackaton.domain.Album.converter.AlbumConverter;
import com.example.neodinary_hackaton.domain.Album.dto.AlbumResponseDto;
import com.example.neodinary_hackaton.domain.Album.entity.Album;
import com.example.neodinary_hackaton.domain.Album.repository.AlbumRepository;
import com.example.neodinary_hackaton.domain.Track.entity.Track;
import com.example.neodinary_hackaton.domain.Track.repository.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.neodinary_hackaton.global.api.GeneralErrorCode;
import com.example.neodinary_hackaton.global.api.ProjectException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final TrackRepository trackRepository;

    public AlbumResponseDto.LastAlbumAndTrackResponse getLastAlbumAndTrack(Long artistId) {
        Album lastAlbum = albumRepository.findByArtistIdAndIsLastTrue(artistId)
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.LAST_ALBUM_NOT_FOUND));

        Track lastTrack = trackRepository.findByAlbumIdAndTrackType(lastAlbum.getId(), "LAST")
                .orElse(null);

        return AlbumConverter.toLastAlbumAndTrackResponse(lastAlbum, lastTrack);
    }
}
