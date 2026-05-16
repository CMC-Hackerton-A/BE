package com.example.neodinary_hackaton.domain.artist.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 36)
    private String mbid;

    @Column(nullable = false)
    private String name;

    private Integer beginYear;
    private Integer endYear;

    @Column(length = 10)
    private String country;

    @Column(length = 100)
    private String genre;

    @Column(nullable = false)
    private Integer starCount;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
