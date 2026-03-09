package com.ia.legislative.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "searched_keywords",
        indexes = @Index(name = "idx_keyword_text", columnList = "text"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchedKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String text;
}
