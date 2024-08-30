package com.project.privatbankrestapitest.service;

import com.project.privatbankrestapitest.model.Card;
import com.project.privatbankrestapitest.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public List<Card> findAllCards() {
        return cardRepository.findAll();
    }

    public Optional<Card> findCardById(Long id) {
        return cardRepository.findById(id);
    }

    public Card saveCard(Card card) {
        cardRepository.save(card);
        return card;
    }

    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }

}
