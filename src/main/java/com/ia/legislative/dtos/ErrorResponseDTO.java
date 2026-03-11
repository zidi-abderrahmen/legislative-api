package com.ia.legislative.dtos;

public record ErrorResponseDTO(
        int status,
        String message,
        long timestamp
) {}