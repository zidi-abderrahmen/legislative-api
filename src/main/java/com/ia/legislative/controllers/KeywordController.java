package com.ia.legislative.controllers;

import com.ia.legislative.dtos.KeywordDTO;
import com.ia.legislative.dtos.LawDTO;
import com.ia.legislative.services.KeywordService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/keywords")
@CrossOrigin(origins = "*")
public class KeywordController {

    private final KeywordService keywordService;

    @GetMapping
    public List<KeywordDTO> getAllKeywords() {
        return keywordService.getAllKeywords();
    }

    @GetMapping("/{id}")
    public KeywordDTO findKeywordById(@PathVariable Long id) {
        return keywordService.getKeywordById(id);
    }

    @GetMapping("/text/{text}")
    public KeywordDTO getKeywordByText(@PathVariable String text) {
        return keywordService.getKeywordByText(text);
    }

    @GetMapping("/search")
    public List<KeywordDTO> searchKeywordsByText(@RequestParam String keyword) {
        return keywordService.searchKeywordsByText(keyword);
    }

    @GetMapping("/{id}/laws")
    public List<LawDTO> getLawsByKeywordId(@PathVariable Long id) {
        return keywordService.getLawsByKeywordId(id);
    }

    @PostMapping
    public KeywordDTO createKeyword(@RequestBody KeywordDTO dto) {
        return keywordService.createKeyword(dto);
    }

    @PutMapping("{id}")
    public KeywordDTO updateKeyword(@PathVariable Long id, @RequestBody KeywordDTO dto) {
        return keywordService.updateKeyword(id, dto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteKeywordById(@PathVariable Long id) {
        keywordService.deleteKeywordById(id);
    }
}