package com.project.privatbankrestapitest.repository;

import com.project.privatbankrestapitest.model.Card;
import com.project.privatbankrestapitest.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByCategoriesContaining(Category category);
}
