package com.ia.legislative.mappers;

import com.ia.legislative.dtos.KeywordResponseDTO;
import com.ia.legislative.entities.Keyword;
import org.springframework.stereotype.Component;

@Component
public class KeywordMapper {
    public KeywordResponseDTO toDTO(Keyword keyword) {
        return new KeywordResponseDTO(
                keyword.getId(),
                keyword.getText()
        );
    }
}
