package com.ia.legislative.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "law_keywords",
            joinColumns = @JoinColumn(name = "law_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id")
    )
    @JsonIgnoreProperties("laws")
    private Set<Keyword> keywords;
}
