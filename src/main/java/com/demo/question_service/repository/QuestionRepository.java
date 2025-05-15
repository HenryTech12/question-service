package com.demo.question_service.repository;

import com.demo.question_service.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    Optional<List<Question>> findByCategory(String category);
    @Query(value = "SELECT * FROM question q WHERE q.category=:category LIMIT :numOfQuestion", nativeQuery = true)
    Optional<List<Question>> findByCategoryAndNumOfQuestion(String category, int numOfQuestion);
}
