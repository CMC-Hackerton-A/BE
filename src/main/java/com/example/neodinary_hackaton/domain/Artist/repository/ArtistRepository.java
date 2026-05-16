package com.example.neodinary_hackaton.domain.Artist.repository;

import com.example.neodinary_hackaton.domain.Artist.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    @Query(value = """
            SELECT *
            FROM artist
            WHERE LOWER(name) LIKE LOWER(CONCAT('%', :q, '%'))
            ORDER BY
                CASE WHEN star_count IS NULL THEN 1 ELSE 0 END ASC,
                star_count DESC,
                id ASC
            LIMIT 10
            """, nativeQuery = true)
    List<Artist> searchTop10ByNameContaining(@Param("q") String q);

    boolean existsByMbid(String mbid);

    Optional<Artist> findByMbid(String mbid);
}
