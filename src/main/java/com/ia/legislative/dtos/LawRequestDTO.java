package com.ia.legislative.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record LawRequestDTO(
    Set<Long> keywordIds,

    @NotBlank
    @Size(max = 200)
    String title,

    @NotBlank
    String description
) {}