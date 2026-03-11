package com.ia.legislative.mappers;

import com.ia.legislative.dtos.LawDTO;
import com.ia.legislative.entities.Law;
import org.springframework.stereotype.Component;

@Component
public class LawMapper {
    public LawDTO toDTO(Law law) {
        return new LawDTO(
                law.getId(),
                law.getTitle(),
                law.getDescription()
        );
    }
}
