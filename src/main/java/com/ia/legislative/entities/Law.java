package com.ia.legislative.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "laws")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Law {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 200)
    @Column(nullable = false, length = 200)
    private String title;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "law_keywords",
            joinColumns = @JoinColumn(name = "law_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id"),
            indexes = {
                    @Index(name = "idx_law_keyword_law", columnList = "law_id"),
                    @Index(name = "idx_law_keyword_keyword", columnList = "keyword_id")
            }
    )
    private Set<Keyword> keywords = new HashSet<>();
}