package com.example.neodinary_hackaton.domain.Track.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import com.example.neodinary_hackaton.domain.Album.entity.Album;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 10)
    private String trackType;

    private Integer trackOrder;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
