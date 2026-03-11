package com.ia.legislative.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record KeywordRequestDTO(
        Set<Long> lawIds,

        @NotBlank
        @Size(max = 50)
        String text
) {}