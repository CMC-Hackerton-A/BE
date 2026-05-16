package com.example.neodinary_hackaton.domain.track.repository;

import com.example.neodinary_hackaton.domain.track.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
}
