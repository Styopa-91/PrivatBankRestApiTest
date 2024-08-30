package com.project.privatbankrestapitest;

import com.project.privatbankrestapitest.model.Card;
import com.project.privatbankrestapitest.model.Category;
import com.project.privatbankrestapitest.repository.CardRepository;
import com.project.privatbankrestapitest.repository.CategoryRepository;
import com.project.privatbankrestapitest.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllCategories() {
        // Arrange
        List<Category> categories = new ArrayList<>();
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Bank");
        categories.add(category1);
        Category category2 = new Category();
        category2.setId(1L);
        category2.setName("Bank");
        categories.add(category2);
        when(categoryRepository.findAll()).thenReturn(categories);

        // Act
        List<Category> result = categoryService.findAllCategories();

        // Assert
        assertEquals(2, result.size());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    public void testSaveCategory() {
        // Arrange
        Category category = new Category();
        category.setId(1L);
        category.setName("Category 1");
        when(categoryRepository.save(category)).thenReturn(category);

        // Act
        Category result = categoryService.saveCategory(category);

        // Assert
        assertNotNull(result);
        assertEquals("Category 1", result.getName());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void testUpdateCategory() {
        // Arrange
        Category existingCategory = new Category();
        existingCategory.setId(1L);
        existingCategory.setName("Category 1");
        Category updatedCategory = new Category();
        updatedCategory.setId(1L);
        updatedCategory.setName("Updated Category");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(existingCategory)).thenReturn(updatedCategory);

        // Act
        Category result = categoryService.updateCategory(1L, updatedCategory);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Category", result.getName());
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).save(existingCategory);
    }

    @Test
    public void testUpdateCategoryNotFound() {
        // Arrange
        Category updatedCategory = new Category();
        updatedCategory.setId(1L);
        updatedCategory.setName("Updated Category");

        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> categoryService.updateCategory(1L, updatedCategory));
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    public void testDeleteCategory() {
        // Arrange
        Category category = new Category();
        category.setId(1L);
        category.setName("Category 1");
        List<Card> cards = new ArrayList<>();
        Card card = new Card();
        card.setId(1L);
        card.setName("Card 1");
        card.getCategories().add(category);
        cards.add(card);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(cardRepository.findByCategoriesContaining(category)).thenReturn(cards);

        // Act
        categoryService.deleteCategory(1L);

        // Assert
        assertFalse(card.getCategories().contains(category));
        verify(categoryRepository, times(1)).findById(1L);
        verify(cardRepository, times(1)).findByCategoriesContaining(category);
        verify(cardRepository, times(1)).save(card);
        verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteCategoryNotFound() {
        // Arrange
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> categoryService.deleteCategory(1L));
        verify(categoryRepository, times(1)).findById(1L);
        verify(cardRepository, never()).findByCategoriesContaining(any(Category.class));
        verify(cardRepository, never()).save(any(Card.class));
        verify(categoryRepository, never()).deleteById(1L);
    }
}
