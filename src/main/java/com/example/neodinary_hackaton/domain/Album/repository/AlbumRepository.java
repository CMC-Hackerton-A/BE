package com.example.neodinary_hackaton.domain.Album.repository;

import com.example.neodinary_hackaton.domain.Album.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    Optional<Album> findByArtistIdAndIsLastTrue(Long artistId);
}
