package com.ia.legislative.mappers;

import com.ia.legislative.dtos.LawResponseDTO;
import com.ia.legislative.entities.Law;
import org.springframework.stereotype.Component;

@Component
public class LawMapper {
    public LawResponseDTO toDTO(Law law) {
        return new LawResponseDTO(
                law.getId(),
                law.getTitle(),
                law.getDescription()
        );
    }
}
