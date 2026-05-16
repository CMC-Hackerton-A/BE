package com.example.neodinary_hackaton.domain.Artist.repository;

import com.example.neodinary_hackaton.domain.Artist.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
