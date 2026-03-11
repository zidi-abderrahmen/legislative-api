package com.ia.legislative.repositories;

import com.ia.legislative.entities.Keyword;
import com.ia.legislative.entities.Law;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    Optional<Keyword> findByText(String text);

    @Query(
            value = "SELECT k.* FROM keywords k " +
                    "WHERE to_tsvector('simple', k.text) @@ to_tsquery('simple', :query)",
            nativeQuery = true
    )
    List<Keyword> searchKeywordsByText(@Param("query") String query);

    @Query("""
       SELECT l FROM Keyword k
       JOIN k.laws l
       WHERE k.id = :id
       """)
    List<Law> findLawsByKeywordId(@Param("id") Long id);
}