package com.ia.legislative.entities;

import jakarta.persistence.*;
import lombok.*;

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
}
