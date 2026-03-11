package com.ia.legislative.services;

import com.ia.legislative.dtos.KeywordResponseDTO;
import com.ia.legislative.dtos.LawRequestDTO;
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
public class LawService {

    private final LawRepository lawRepository;
    private final KeywordRepository keywordRepository;
    private final KeywordMapper keywordMapper;
    private final LawMapper lawMapper;

    public List<LawResponseDTO> getAllLaws() {
        return lawRepository.findAll()
                .stream()
                .map(lawMapper::toDTO)
                .toList();
    }

    public LawResponseDTO getLawById(Long id) {
        Law law = lawRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Law with id " + id + " not found"));
        return lawMapper.toDTO(law);
    }

    public LawResponseDTO getLawByTitle(String title) {
        Law law = lawRepository.findByTitle(title)
                .orElseThrow(() -> new ResourceNotFoundException("Law with title '" + title + "' not found"));
        return lawMapper.toDTO(law);
    }

    public List<LawResponseDTO> searchLawsByText(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return List.of();
        }
        String query = String.join(" | ", keyword.split("\\s+"));
        return lawRepository.searchedLawsByText(query)
                .stream()
                .map(lawMapper::toDTO)
                .toList();
    }

    @Transactional
    public List<KeywordResponseDTO> getKeywordsByLawId(Long id) {
        if (!lawRepository.existsById(id)) {
            throw new ResourceNotFoundException("Law with id " + id + " not found");
        }
        return lawRepository.findKeywordsByLawId(id)
                .stream()
                .map(keywordMapper::toDTO)
                .toList();
    }

    @Transactional
    public List<KeywordResponseDTO> getKeywordsByLawTitle(String title) {
        if (lawRepository.findByTitle(title).isEmpty()) {
            throw new ResourceNotFoundException("Law with title '" + title + "' not found");
        }
        return lawRepository.findKeywordsByLawTitle(title)
                .stream()
                .map(keywordMapper::toDTO)
                .toList();
    }

    public LawResponseDTO createLaw(LawRequestDTO dto) {
        if (lawRepository.findByTitle(dto.title()).isPresent()) {
            throw new ResourceAlreadyExistsException("Law with title '" + dto.title() + "' already exists");
        }
        Law law = new Law();
        law.setTitle(dto.title());
        law.setDescription(dto.description());

        if (dto.keywordIds() != null && !dto.keywordIds().isEmpty()) {
            Set<Keyword> keywords = new HashSet<>(keywordRepository.findAllById(dto.keywordIds()));
            law.setKeywords(keywords);
        }

        return lawMapper.toDTO(lawRepository.save(law));
    }

    public LawResponseDTO updateLaw(Long id, LawRequestDTO dto) {
        Law existingLaw = lawRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Law with id " + id + " not found"));

        lawRepository.findByTitle(dto.title())
                .filter(l -> !l.getId().equals(id))
                .ifPresent(l -> {
                    throw new ResourceAlreadyExistsException("Law with title '" + dto.title() + "' already exists");
                });

        existingLaw.setTitle(dto.title());

        if (dto.keywordIds() != null && !dto.keywordIds().isEmpty()) {
            Set<Keyword> keywords = new HashSet<>(keywordRepository.findAllById(dto.keywordIds()));
            existingLaw.setKeywords(keywords);
        } else {
            existingLaw.getKeywords().clear();
        }

        return lawMapper.toDTO(lawRepository.save(existingLaw));
    }

    public void deleteLawById(Long id) {
        Law law = lawRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Law with id " + id + " not found"));
        lawRepository.delete(law);
    }
}
