package com.ia.legislative.services;

import com.ia.legislative.dtos.KeywordDTO;
import com.ia.legislative.dtos.LawDTO;
import com.ia.legislative.entities.Law;
import com.ia.legislative.exceptions.ResourceAlreadyExistsException;
import com.ia.legislative.exceptions.ResourceNotFoundException;
import com.ia.legislative.mappers.KeywordMapper;
import com.ia.legislative.mappers.LawMapper;
import com.ia.legislative.repositories.LawRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LawService {

    private final LawRepository lawRepository;
    private final KeywordMapper keywordMapper;
    private final LawMapper lawMapper;

    public List<LawDTO> getAllLaws() {
        return lawRepository.findAll()
                .stream()
                .map(lawMapper::toDTO)
                .toList();
    }

    public LawDTO getLawById(Long id) {
        Law law = lawRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Law with id " + id + " not found"));
        return lawMapper.toDTO(law);
    }

    public LawDTO getLawByTitle(String title) {
        Law law = lawRepository.findByTitle(title)
                .orElseThrow(() -> new ResourceNotFoundException("Law with title '" + title + "' not found"));
        return lawMapper.toDTO(law);
    }

    public List<LawDTO> searchLawsByText(String keyword) {
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
    public List<KeywordDTO> getKeywordsByLawId(Long id) {
        if (!lawRepository.existsById(id)) {
            throw new ResourceNotFoundException("Law with id " + id + " not found");
        }
        return lawRepository.findKeywordsByLawId(id)
                .stream()
                .map(keywordMapper::toDTO)
                .toList();
    }

    public LawDTO createLaw(LawDTO dto) {
        if (lawRepository.findByTitle(dto.title()).isPresent()) {
            throw new ResourceAlreadyExistsException("Law with title '" + dto.title() + "' already exists");
        }
        Law law = new Law();
        law.setTitle(dto.title());

        return lawMapper.toDTO(lawRepository.save(law));
    }

    public LawDTO updateLaw(Long id, LawDTO dto) {
        Law existingLaw = lawRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Law with id " + id + " not found"));

        lawRepository.findByTitle(dto.title())
                .filter(l -> !l.getId().equals(id))
                .ifPresent(l -> {
                    throw new ResourceAlreadyExistsException("Law with title '" + dto.title() + "' already exists");
                });

        existingLaw.setTitle(dto.title());
        return lawMapper.toDTO(lawRepository.save(existingLaw));
    }

    public void deleteLawById(Long id) {
        Law law = lawRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Law with id " + id + " not found"));
        lawRepository.delete(law);
    }
}
