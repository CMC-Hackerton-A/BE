package com.example.neodinary_hackaton.domain.Artist.repository;

import com.example.neodinary_hackaton.domain.Artist.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    @Query(value = """
            SELECT *
            FROM artist
            WHERE LOWER(name) LIKE LOWER(CONCAT(:name, '%'))
            ORDER BY
                CASE WHEN star_count IS NULL THEN 1 ELSE 0 END ASC,
                star_count DESC,
                id ASC
            LIMIT 10
            """, nativeQuery = true)
    List<Artist> findTop10ByNameStartingWith(@Param("name") String name);

    boolean existsByMbid(String mbid);
}
