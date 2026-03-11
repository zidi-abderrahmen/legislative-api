package com.ia.legislative.controllers;

import com.ia.legislative.dtos.KeywordResponseDTO;
import com.ia.legislative.dtos.LawRequestDTO;
import com.ia.legislative.dtos.LawResponseDTO;
import com.ia.legislative.services.LawService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/laws")
@CrossOrigin(origins = "*")
public class LawController {

    private final LawService lawService;

    @GetMapping
    public List<LawResponseDTO> getAllLaws() {
        return lawService.getAllLaws();
    }

    @GetMapping("/{id}")
    public LawResponseDTO getLawById(@PathVariable Long id) {
        return lawService.getLawById(id);
    }

    @GetMapping("/title/{title}")
    public LawResponseDTO getLawByTitle(@PathVariable String title) {
        return lawService.getLawByTitle(title);
    }

    @GetMapping("/search")
    public List<LawResponseDTO> searchLawsByText(@RequestParam String query) {
        return lawService.searchLawsByText(query);
    }

    @GetMapping("/id/{id}/keywords")
    public List<KeywordResponseDTO> getKeywordsByLawId(@PathVariable Long id) {
        return lawService.getKeywordsByLawId(id);
    }

    @GetMapping("/title/{title}/keywords")
    public List<KeywordResponseDTO> getKeywordsByLawTitle(@PathVariable String title) {
        return lawService.getKeywordsByLawTitle(title);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LawResponseDTO createLaw(@Valid @RequestBody LawRequestDTO dto) {
        return lawService.createLaw(dto);
    }

    @PutMapping("/{id}")
    public LawResponseDTO updateLaw(@PathVariable Long id, @Valid @RequestBody LawRequestDTO dto) {
        return lawService.updateLaw(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLawById(@PathVariable Long id) {
        lawService.deleteLawById(id);
    }
}