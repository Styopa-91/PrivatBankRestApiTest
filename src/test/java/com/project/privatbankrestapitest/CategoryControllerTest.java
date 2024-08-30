package com.project.privatbankrestapitest;

import com.project.privatbankrestapitest.controller.CategoryController;
import com.project.privatbankrestapitest.model.Category;
import com.project.privatbankrestapitest.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testGetCategories() throws Exception {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Bank");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Retail");

        Mockito.when(categoryService.findAllCategories()).thenReturn(Arrays.asList(category1, category2));

        mockMvc.perform(get("/api/categories"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", is("Bank")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", is("Retail")));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testCreateCategory() throws Exception {
        Category category = new Category();
        category.setId(1L);
        category.setName("Insurance");

        Mockito.when(categoryService.saveCategory(Mockito.any(Category.class))).thenReturn(category);

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Insurance\"}")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Insurance")));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testUpdateCategory() throws Exception {
        Category category = new Category();
        category.setId(1L);
        category.setName("Bank");

        Mockito.when(categoryService.updateCategory(Mockito.eq(1L), Mockito.any(Category.class))).thenReturn(category);

        mockMvc.perform(put("/api/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Bank\"}")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Bank")));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testDeleteCategory() throws Exception {
        mockMvc.perform(delete("/api/categories/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
