package com.ia.legislative.services;

import com.ia.legislative.dtos.KeywordRequestDTO;
import com.ia.legislative.dtos.KeywordResponseDTO;
import com.ia.legislative.dtos.LawResponseDTO;
import com.ia.legislative.entities.Keyword;
import com.ia.legislative.entities.Law;
import com.ia.legislative.exceptions.ResourceAlreadyExistsException;
import com.ia.legislative.exceptions.ResourceNotFoundException;
import com.ia.legislative.mappers.KeywordMapper;
import com.ia.legislative.mappers.LawMapper;
import com.ia.legislative.repositories.KeywordRepository;
import com.ia.legislative.repositories.LawRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;
    private final LawRepository lawRepository;
    private final KeywordMapper keywordMapper;
    private final LawMapper lawMapper;

    public List<KeywordResponseDTO> getAllKeywords() {
        return keywordRepository.findAll()
                .stream()
                .map(keywordMapper::toDTO)
                .toList();
    }

    public KeywordResponseDTO getKeywordById(Long id) {
        Keyword keyword = keywordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Keyword with id " + id + " not found"));
        return keywordMapper.toDTO(keyword);
    }

    public KeywordResponseDTO getKeywordByText(String text) {
        Keyword keyword = keywordRepository.findByText(text.trim())
                .orElseThrow(() -> new ResourceNotFoundException("Keyword with text '" + text + "' not found"));
        return keywordMapper.toDTO(keyword);
    }

    public List<KeywordResponseDTO> searchKeywords(String text) {
        if (text == null || text.isBlank()) {
            return List.of();
        }
        String query = String.join(" | ", text.split("\\s+")).trim();
        return keywordRepository.searchByLawsNative(query)
                .stream()
                .map(keywordMapper::toDTO)
                .toList();
    }

    @Transactional
    public List<LawResponseDTO> getLawsByKeywordId(Long id) {
        if (!keywordRepository.existsById(id)) {
            throw new ResourceNotFoundException("Keyword with id " + id + " not found");
        }
        return keywordRepository.findLawsByKeywordId(id)
                .stream()
                .map(lawMapper::toDTO)
                .toList();
    }

    @Transactional
    public List<LawResponseDTO> getLawsByKeywordText(String text) {
        if (keywordRepository.findByText(text).isEmpty()) {
            throw new ResourceNotFoundException("Keyword with text '" + text + "' not found");
        }
        return keywordRepository.findLawsByKeywordText(text)
                .stream()
                .map(lawMapper::toDTO)
                .toList();
    }

    public KeywordResponseDTO createKeyword(KeywordRequestDTO dto) {
        if (keywordRepository.findByText(dto.text()).isPresent()) {
            throw new ResourceAlreadyExistsException(
                    "Keyword with text '" + dto.text() + "' already exists"
            );
        }
        Keyword keyword = new Keyword();
        keyword.setText(dto.text());

        if (dto.lawIds() != null && !dto.lawIds().isEmpty()) {
            Set<Law> laws = new HashSet<>(lawRepository.findAllById(dto.lawIds()));
            for (Law law : laws) {
                law.getKeywords().add(keyword);
            }
            keyword.setLaws(laws);
        }

        Keyword saved = keywordRepository.save(keyword);
        return keywordMapper.toDTO(saved);
    }

    public KeywordResponseDTO updateKeyword(Long id, KeywordRequestDTO dto) {
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

        if (dto.lawIds() != null && !dto.lawIds().isEmpty()) {
            Set<Law> laws = new HashSet<>(lawRepository.findAllById(dto.lawIds()));
            for (Law law : laws) {
                law.getKeywords().add(existingKeyword);
            }
            existingKeyword.setLaws(laws);
        }else {
            for (Law law : existingKeyword.getLaws()) {
                law.getKeywords().remove(existingKeyword);
            }
            existingKeyword.getLaws().clear();
        }

        return keywordMapper.toDTO(keywordRepository.save(existingKeyword));
    }

    @Transactional
    public void deleteKeywordById(Long id) {
        Keyword keyword = keywordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Keyword with id " + id + " not found"));
        for (Law law : keyword.getLaws()) {
            law.getKeywords().remove(keyword);
        }
        keywordRepository.delete(keyword);
    }
}
