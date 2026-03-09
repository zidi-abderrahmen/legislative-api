package com.ia.legislative.services;

import com.ia.legislative.entities.Keyword;
import com.ia.legislative.repositories.KeywordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;

    public List<Keyword> findAll() {
        return keywordRepository.findAll();
    }

    public Keyword findById(Long id) {
        return keywordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Keyword not found"));
    }

    public Keyword getKeywordByText(String text) {
        return keywordRepository.findByText(text)
                .orElseThrow(() -> new RuntimeException("Keyword not found"));
    }

    public List<Keyword> getKeywordsByKeyword(String keyword) {
        String query = String.join(" | ", keyword.split("\\s+"));
        return keywordRepository.searchByLawsNative(query);
    }

    public Keyword createKeyword(Keyword keyword) {
        if (keywordRepository.findByText(keyword.getText()).isPresent()) {
            throw new RuntimeException("Keyword already exists");
        }
        return keywordRepository.save(keyword);
    }

    public Keyword updateKeyword(Long id, Keyword keyword) {
        Keyword existingKeyword = keywordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Keyword not found"));

        existingKeyword.setText(keyword.getText());
        existingKeyword.setLaws(keyword.getLaws());

        return keywordRepository.save(existingKeyword);
    }

    public void deleteKeywordById(Long id) {
        keywordRepository.deleteById(id);
    }
}
