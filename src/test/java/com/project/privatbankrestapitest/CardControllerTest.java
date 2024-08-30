package com.project.privatbankrestapitest;

import com.project.privatbankrestapitest.controller.CardController;
import com.project.privatbankrestapitest.model.Card;
import com.project.privatbankrestapitest.service.CardService;
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
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(CardController.class)
public class CardControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardService cardService;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testGetAllCards() throws Exception {
        Card card1 = new Card();
        card1.setId(1L);
        card1.setName("Card1");

        Card card2 = new Card();
        card2.setId(2L);
        card2.setName("Card2");

        Mockito.when(cardService.findAllCards()).thenReturn(Arrays.asList(card1, card2));

        mockMvc.perform(get("/api/cards"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", is("Card1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", is("Card2")));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testCreateCard() throws Exception {
        Card card = new Card();
        card.setId(1L);
        card.setName("New Card");

        Mockito.when(cardService.saveCard(Mockito.any(Card.class))).thenReturn(card);

        mockMvc.perform(post("/api/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Card\"}")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())) // Ensure CSRF token is included
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("New Card")));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testUpdateCard() throws Exception {
        Card card = new Card();
        card.setId(1L);
        card.setName("Updated Card");

        Mockito.when(cardService.findCardById(1L)).thenReturn(Optional.of(card));
        Mockito.when(cardService.saveCard(Mockito.any(Card.class))).thenReturn(card);

        mockMvc.perform(put("/api/cards/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Card\"}")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())) // Ensure CSRF token is included
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Updated Card")));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testDeleteCard() throws Exception {
        mockMvc.perform(delete("/api/cards/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testUpdateCardNotFound() throws Exception {
        Mockito.when(cardService.findCardById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/cards/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Card\"}")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
