package com.ia.legislative.controllers;

import com.ia.legislative.entities.Keyword;
import com.ia.legislative.services.KeywordService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/keywords")
@CrossOrigin(origins = "*")
public class KeywordController {

    private final KeywordService keywordService;

    @GetMapping
    public List<Keyword> findAll() {
        return keywordService.findAll();
    }

    @GetMapping("/{id}")
    public Keyword findById(@PathVariable Long id) {
        return keywordService.findById(id);
    }

    @GetMapping("/text/{text}")
    public Keyword getKeywordByText(@PathVariable String text) {
        return keywordService.getKeywordByText(text);
    }

    @GetMapping("/search")
    public List<Keyword> getKeywordsByKeyword(@RequestParam String keyword) {
        return keywordService.getKeywordsByKeyword(keyword);
    }

    @PostMapping
    public Keyword createKeyword(@RequestBody Keyword keyword) {
        return keywordService.createKeyword(keyword);
    }

    @PutMapping("{id}")
    public Keyword updateKeyword(@PathVariable Long id, @RequestBody Keyword keyword) {
        return keywordService.updateKeyword(id, keyword);
    }

    @DeleteMapping("{id}")
    public void deleteKeyword(@PathVariable Long id) {
        keywordService.deleteKeywordById(id);
    }
}
