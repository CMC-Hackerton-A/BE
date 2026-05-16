package com.example.neodinary_hackaton.domain.Track.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long artistId;

    private Long albumId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 10)
    private String trackType;

    private Integer trackOrder;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
