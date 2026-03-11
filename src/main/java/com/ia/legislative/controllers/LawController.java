package com.ia.legislative.controllers;

import com.ia.legislative.dtos.KeywordDTO;
import com.ia.legislative.dtos.LawDTO;
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
    public List<LawDTO> getAllLaws() {
        return lawService.getAllLaws();
    }

    @GetMapping("/{id}")
    public LawDTO getLawById(@PathVariable Long id) {
        return lawService.getLawById(id);
    }

    @GetMapping("/title/{title}")
    public LawDTO getLawByTitle(@PathVariable String title) {
        return lawService.getLawByTitle(title);
    }

    @GetMapping("/search")
    public List<LawDTO> searchLawsByText(@RequestParam String keyword) {
        return lawService.searchLawsByText(keyword);
    }

    @GetMapping("/{id}/keywords")
    public List<KeywordDTO> getKeywordsByLawId(@PathVariable Long id) {
        return lawService.getKeywordsByLawId(id);
    }

    @PostMapping
    public LawDTO createLaw(@RequestBody LawDTO dto) {
        return lawService.createLaw(dto);
    }

    @PutMapping("/{id}")
    public LawDTO updateLaw(@PathVariable Long id, @RequestBody LawDTO dto) {
        return lawService.updateLaw(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteLawById(@PathVariable Long id) {
        lawService.deleteLawById(id);
    }
}