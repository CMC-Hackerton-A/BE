package com.example.neodinary_hackaton.domain.Track.repository;

import com.example.neodinary_hackaton.domain.Track.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TrackRepository extends JpaRepository<Track, Long> {
    Optional<Track> findByAlbumIdAndTrackType(Long albumId, String trackType);
}
