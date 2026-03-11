package com.ia.legislative.repositories;

import com.ia.legislative.entities.Keyword;
import com.ia.legislative.entities.Law;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LawRepository extends JpaRepository<Law, Long> {

    Optional<Law> findByTitle(String title);

    @Query(
            value = "SELECT l.* FROM laws l " +
                    "JOIN law_keywords lk ON l.id = lk.law_id " +
                    "JOIN searched_keywords k ON lk.keyword_id = k.id " +
                    "WHERE to_tsvector('simple', k.text) @@ to_tsquery('simple', :query)",
            nativeQuery = true
    )
    List<Law> searchByKeywordsNative(@Param("query") String query);

    @Query("""
       SELECT k FROM Law l
       JOIN l.keywords k
       WHERE l.id = :id
       """)
    List<Keyword> findKeywordsByLawId(@Param("id") Long id);
}