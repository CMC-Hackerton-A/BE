package com.example.neodinary_hackaton.domain.album.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 36)
    private String mbid;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String coverUrl;

    private LocalDate releaseDate;

    @Column(nullable = false)
    private Boolean isLast;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Long artistId;
}
