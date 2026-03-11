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
                    "WHERE to_tsvector('simple', l.title) @@ to_tsquery('simple', :query)",
            nativeQuery = true
    )
    List<Law> searchedLawsByText(@Param("query") String query);

    @Query("""
       SELECT k FROM Law l
       JOIN l.keywords k
       WHERE l.id = :id
       """)
    List<Keyword> findKeywordsByLawId(@Param("id") Long id);

    @Query("""
        SELECT k FROM Law l
        JOIN l.keywords k
        WHERE l.title = :title
        """)
    List<Keyword> findKeywordsByLawTitle(@Param("title") String title);
}