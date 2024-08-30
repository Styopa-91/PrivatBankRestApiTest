package com.project.privatbankrestapitest.controller;

import com.project.privatbankrestapitest.model.Card;
import com.project.privatbankrestapitest.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping
    public ResponseEntity<List<Card>> getAllCards() {
        return new ResponseEntity<>(cardService.findAllCards(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Card> createCard(@RequestBody Card card) {
        return new ResponseEntity<>(cardService.saveCard(card), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Card> updateCard(@PathVariable Long id, @RequestBody Card updatedCard) {
        Optional<Card> existingCard = cardService.findCardById(id);
        if (existingCard.isPresent()) {
            updatedCard.setId(id);
            return ResponseEntity.ok(cardService.saveCard(updatedCard));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }
}
