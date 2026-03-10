package com.ia.legislative.controllers;

import com.ia.legislative.entities.Law;
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
    public List<Law> getAllLaws() {
        return lawService.getAllLaws();
    }

    @GetMapping("/{id}")
    public Law getLawById(@PathVariable Long id) {
        return lawService.getLawById(id);
    }

    @GetMapping("/title/{name}")
    public Law getLawByName(@PathVariable String name) {
        return lawService.getLawByName(name);
    }

    @GetMapping("/search")
    public List<Law> getLawsByKeyword(@RequestParam String keyword) {
        return lawService.getLawsByKeyword(keyword);
    }

    @PostMapping
    public Law createLaw(@RequestBody Law law) {
        return lawService.createLaw(law);
    }

    @PutMapping("/{id}")
    public Law updateLaw(@PathVariable Long id, @RequestBody Law law) {
        return lawService.updateLaw(id, law);
    }

    @DeleteMapping("/{id}")
    public void deleteLawById(@PathVariable Long id) {
        lawService.deleteLawById(id);
    }
}