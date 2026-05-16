package com.example.neodinary_hackaton.domain.Album.converter;

import com.example.neodinary_hackaton.domain.Album.dto.AlbumResponseDto;
import com.example.neodinary_hackaton.domain.Album.entity.Album;
import com.example.neodinary_hackaton.domain.Track.entity.Track;
import java.time.format.DateTimeFormatter;

public class AlbumConverter {

    public static AlbumResponseDto.LastAlbumAndTrackResponse toLastAlbumAndTrackResponse(Album album, Track track) {
        return AlbumResponseDto.LastAlbumAndTrackResponse.builder()
                .lastAlbum(toLastAlbumInfo(album))
                .lastTrack(toLastTrackInfo(track, album))
                .build();
    }

    private static AlbumResponseDto.LastAlbumInfo toLastAlbumInfo(Album album) {
        String formattedDate = album.getReleaseDate() != null ? 
                album.getReleaseDate().format(DateTimeFormatter.ofPattern("yyyy.MM")) : null;

        return AlbumResponseDto.LastAlbumInfo.builder()
                .id(album.getId())
                .name(album.getName())
                .coverUrl(album.getCoverUrl())
                .releaseDate(formattedDate)
                .build();
    }

    private static AlbumResponseDto.LastTrackInfo toLastTrackInfo(Track track, Album album) {
        if (track == null) return null;

        String formattedDate = album.getReleaseDate() != null ? 
                album.getReleaseDate().format(DateTimeFormatter.ofPattern("yyyy.MM")) : null;

        return AlbumResponseDto.LastTrackInfo.builder()
                .id(track.getId())
                .name(track.getName())
                .coverUrl(album.getCoverUrl())
                .releaseDate(formattedDate)
                .build();
    }
}
