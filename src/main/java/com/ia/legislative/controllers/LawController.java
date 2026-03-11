package com.ia.legislative.controllers;

import com.ia.legislative.dtos.KeywordResponseDTO;
import com.ia.legislative.dtos.LawResponseDTO;
import com.ia.legislative.services.LawService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
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
    public List<LawResponseDTO> searchLawsByText(@RequestParam String keyword) {
        return lawService.searchLawsByText(keyword);
    }

    @GetMapping("/{id}/keywords")
    public List<KeywordResponseDTO> getKeywordsByLawId(@PathVariable Long id) {
        return lawService.getKeywordsByLawId(id);
    }

    @PostMapping
    public LawResponseDTO createLaw(@RequestBody LawResponseDTO dto) {
        return lawService.createLaw(dto);
    }

    @PutMapping("/{id}")
    public LawResponseDTO updateLaw(@PathVariable Long id, @RequestBody LawResponseDTO dto) {
        return lawService.updateLaw(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteLawById(@PathVariable Long id) {
        lawService.deleteLawById(id);
    }
}