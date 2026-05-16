package com.example.neodinary_hackaton.domain.album.repository;

import com.example.neodinary_hackaton.domain.album.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
}
