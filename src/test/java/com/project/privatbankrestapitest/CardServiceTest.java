package com.project.privatbankrestapitest;

import com.project.privatbankrestapitest.model.Card;
import com.project.privatbankrestapitest.repository.CardRepository;
import com.project.privatbankrestapitest.service.CardService;
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
public class CardServiceTest {
    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardService cardService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllCards() {
        // Arrange
        List<Card> cards = new ArrayList<>();
        Card card1 = new Card();
        card1.setId(1L);
        card1.setName("New Card");
        cards.add(card1);
        Card card2 = new Card();
        card2.setId(1L);
        card2.setName("New Card");
        cards.add(card2);
        when(cardRepository.findAll()).thenReturn(cards);

        // Act
        List<Card> result = cardService.findAllCards();

        // Assert
        assertEquals(2, result.size());
        verify(cardRepository, times(1)).findAll();
    }

    @Test
    public void testFindCardById() {
        // Arrange
        Card card = new Card();
        card.setId(1L);
        card.setName("Card 1");
        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));

        // Act
        Optional<Card> result = cardService.findCardById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Card 1", result.get().getName());
        verify(cardRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveCard() {
        // Arrange
        Card card = new Card();
        card.setId(1L);
        card.setName("Card 1");
        when(cardRepository.save(card)).thenReturn(card);

        // Act
        Card result = cardService.saveCard(card);

        // Assert
        assertNotNull(result);
        assertEquals("Card 1", result.getName());
        verify(cardRepository, times(1)).save(card);
    }

    @Test
    public void testDeleteCard() {
        // Act
        cardService.deleteCard(1L);

        // Assert
        verify(cardRepository, times(1)).deleteById(1L);
    }
}
