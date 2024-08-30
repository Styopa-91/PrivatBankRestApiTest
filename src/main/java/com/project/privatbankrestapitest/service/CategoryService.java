package com.project.privatbankrestapitest.service;

import com.project.privatbankrestapitest.model.Card;
import com.project.privatbankrestapitest.model.Category;
import com.project.privatbankrestapitest.repository.CardRepository;
import com.project.privatbankrestapitest.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CardRepository cardRepository;

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category category) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Card not found"));

        existingCategory.setName(category.getName());

        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        List<Card> cards = cardRepository.findByCategoriesContaining(category);

        for (Card card : cards) {
            card.getCategories().remove(category);
            cardRepository.save(card); // Update the card in the database
        }
        categoryRepository.deleteById(id);
    }
}