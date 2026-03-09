package com.ia.legislative.services;

import com.ia.legislative.entities.Law;
import com.ia.legislative.repositories.LawRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LawService {

    private final LawRepository lawRepository;

    public List<Law> getAllLaws() {
        return lawRepository.findAll();
    }

    public Law getLawById(Long id) {
        return lawRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Law not found"));
    }

    public Law getLawByName(String name) {
        return lawRepository.findByTitle(name)
                .orElseThrow(() -> new RuntimeException("Law not found"));
    }

    public List<Law> getLawsByKeyword(String keyword) {
        String query = String.join(" | ", keyword.split("\\s+"));
        return lawRepository.searchByKeywordsNative(query);
    }

    public Law createLaw(Law law) {
        if (lawRepository.findByTitle(law.getTitle()).isPresent()) {
            throw new RuntimeException("Law already exists");
        }
        return lawRepository.save(law);
    }

    public Law updateLaw(Long id, Law law) {
        Law existingLaw = lawRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Law not found"));

        existingLaw.setTitle(law.getTitle());
        existingLaw.setDescription(law.getDescription());
        existingLaw.setKeywords(law.getKeywords());

        return lawRepository.save(existingLaw);
    }

    public void deleteLawById(Long id) {
        lawRepository.deleteById(id);
    }
}
