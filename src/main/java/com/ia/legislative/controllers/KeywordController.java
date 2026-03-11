package com.ia.legislative.controllers;

import com.ia.legislative.dtos.KeywordRequestDTO;
import com.ia.legislative.dtos.KeywordResponseDTO;
import com.ia.legislative.dtos.LawResponseDTO;
import com.ia.legislative.services.KeywordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/keywords")
@CrossOrigin(origins = "*")
public class KeywordController {

    private final KeywordService keywordService;

    @GetMapping
    public List<KeywordResponseDTO> getAllKeywords() {
        return keywordService.getAllKeywords();
    }

    @GetMapping("/{id}")
    public KeywordResponseDTO findKeywordById(@PathVariable Long id) {
        return keywordService.getKeywordById(id);
    }

    @GetMapping("/text/{text}")
    public KeywordResponseDTO getKeywordByText(@PathVariable String text) {
        return keywordService.getKeywordByText(text);
    }

    @GetMapping("/search")
    public List<KeywordResponseDTO> searchKeywordsByText(@RequestParam String query) {
        return keywordService.searchKeywords(query);
    }

    @GetMapping("/id/{id}/laws")
    public List<LawResponseDTO> getLawsByKeywordId(@PathVariable Long id) {
        return keywordService.getLawsByKeywordId(id);
    }

    @GetMapping("/text/{text}/laws")
    public List<LawResponseDTO> getLawsByKeywordText(@PathVariable String text) {
        return keywordService.getLawsByKeywordText(text);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KeywordResponseDTO createKeyword(@Valid @RequestBody KeywordRequestDTO dto) {
        return keywordService.createKeyword(dto);
    }

    @PutMapping("/{id}")
    public KeywordResponseDTO updateKeyword(@PathVariable Long id, @Valid @RequestBody KeywordRequestDTO dto) {
        return keywordService.updateKeyword(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteKeywordById(@PathVariable Long id) {
        keywordService.deleteKeywordById(id);
    }
}