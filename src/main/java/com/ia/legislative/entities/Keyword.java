package com.ia.legislative.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "keywords",
        indexes = @Index(name = "idx_keyword_text", columnList = "text"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, unique = true, length = 50)
    private String text;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "keywords")
    private Set<Law> laws = new HashSet<>();
}