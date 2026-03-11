package com.ia.legislative.services;

import com.ia.legislative.dtos.KeywordDTO;
import com.ia.legislative.dtos.LawDTO;
import com.ia.legislative.entities.Keyword;
import com.ia.legislative.exceptions.ResourceAlreadyExistsException;
import com.ia.legislative.exceptions.ResourceNotFoundException;
import com.ia.legislative.mappers.KeywordMapper;
import com.ia.legislative.mappers.LawMapper;
import com.ia.legislative.repositories.KeywordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;
    private final KeywordMapper keywordMapper;
    private final LawMapper lawMapper;

    public List<KeywordDTO> getAllKeywords() {
        return keywordRepository.findAll()
                .stream()
                .map(keywordMapper::toDTO)
                .toList();
    }

    public KeywordDTO findKeywordById(Long id) {
        Keyword keyword = keywordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Keyword with id " + id + " not found"));
        return keywordMapper.toDTO(keyword);
    }

    public KeywordDTO getKeywordByText(String text) {
        Keyword keyword = keywordRepository.findByText(text)
                .orElseThrow(() -> new ResourceNotFoundException("Keyword with text '" + text + "' not found"));
        return keywordMapper.toDTO(keyword);
    }

    public List<KeywordDTO> searchKeywordsByText(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return List.of();
        }
        String query = String.join(" | ", keyword.split("\\s+"));
        return keywordRepository.searchByLawsNative(query)
                .stream()
                .map(keywordMapper::toDTO)
                .toList();
    }

    @Transactional
    public List<LawDTO> getLawsByKeywordId(Long id) {
        if (!keywordRepository.existsById(id)) {
            throw new ResourceNotFoundException("Keyword with id " + id + " not found");
        }
        return keywordRepository.findLawsByKeywordId(id)
                .stream()
                .map(lawMapper::toDTO)
                .toList();
    }

    public KeywordDTO createKeyword(KeywordDTO dto) {
        if (keywordRepository.findByText(dto.text()).isPresent()) {
            throw new ResourceAlreadyExistsException(
                    "Keyword with text '" + dto.text() + "' already exists"
            );
        }
        Keyword keyword = new Keyword();
        keyword.setText(dto.text());

        Keyword saved = keywordRepository.save(keyword);
        return keywordMapper.toDTO(saved);
    }

    public KeywordDTO updateKeyword(Long id, KeywordDTO dto) {
        Keyword existingKeyword = keywordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Keyword with id " + id + " not found"));

        keywordRepository.findByText(dto.text())
                .filter(k -> !k.getId().equals(id))
                .ifPresent(k -> {
                    throw new ResourceAlreadyExistsException(
                            "Keyword with text '" + dto.text() + "' already exists"
                    );
                });

        existingKeyword.setText(dto.text());
        return keywordMapper.toDTO(keywordRepository.save(existingKeyword));
    }

    @Transactional
    public void deleteKeywordById(Long id) {
        Keyword keyword = keywordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Keyword with id " + id + " not found"));
        keywordRepository.delete(keyword);
    }
}
