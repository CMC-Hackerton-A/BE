package com.example.neodinary_hackaton.domain.Artist.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import com.example.neodinary_hackaton.domain.Album.entity.Album;
import com.example.neodinary_hackaton.domain.Memorial.entity.MemorialMessage;
import java.util.List;
import java.util.ArrayList;

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

    @Column(length = 500)
    private String artistImageUrl;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder.Default
    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemorialMessage> messages = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Album> albums = new ArrayList<>();

    public void increaseStarCount() {
        if (this.starCount == null) {
            this.starCount = 0;
        }
        this.starCount += 1;
    }
}
