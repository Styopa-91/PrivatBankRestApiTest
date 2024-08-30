package com.project.privatbankrestapitest.repository;

import com.project.privatbankrestapitest.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {}
