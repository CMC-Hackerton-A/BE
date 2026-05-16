package com.example.neodinary_hackaton.domain.Track.repository;

import com.example.neodinary_hackaton.domain.Track.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
}
