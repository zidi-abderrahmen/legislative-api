package com.ia.legislative.dtos;

import java.util.Set;

public record KeywordRequestDTO(
        Set<Long> lawIds,
        String text
) {}