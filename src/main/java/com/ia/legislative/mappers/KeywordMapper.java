package com.ia.legislative.mappers;

import com.ia.legislative.dtos.KeywordDTO;
import com.ia.legislative.entities.Keyword;
import org.springframework.stereotype.Component;

@Component
public class KeywordMapper {
    public KeywordDTO toDTO(Keyword keyword) {
        return new KeywordDTO(
                keyword.getId(),
                keyword.getText()
        );
    }
}
