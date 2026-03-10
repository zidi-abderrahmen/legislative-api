package com.ia.legislative.repositories;

import com.ia.legislative.entities.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    Optional<Keyword> findByText(String text);

    @Query(
            value = "SELECT k.* FROM keywords k " +
                    "JOIN law_keywords lk ON k.id = lk.keyword_id " +
                    "JOIN laws l ON lk.law_id = l.id " +
                    "WHERE to_tsvector('simple', k.text) @@ to_tsquery('simple', :query)",
            nativeQuery = true
    )
    List<Keyword> searchByLawsNative(@Param("query") String query);
}