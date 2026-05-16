package com.example.neodinary_hackaton.domain.Artist.repository;

import com.example.neodinary_hackaton.domain.Artist.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository 
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    @Query("""
            SELECT a
            FROM Artist a
            WHERE LOWER(a.name) LIKE LOWER(CONCAT(:name, '%'))
            ORDER BY
                CASE WHEN a.starCount IS NULL THEN 1 ELSE 0 END ASC,
                a.starCount DESC,
                a.id ASC
            """)
    List<Artist> findTop10ByNameStartingWith(
            @Param("name") String name,
            Pageable pageable
    );
}
