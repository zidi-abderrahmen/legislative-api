package com.ia.legislative.dtos;

import java.util.Set;

public record LawRequestDTO(
    Set<Long> keywordIds,
    String title,
    String description
) {}