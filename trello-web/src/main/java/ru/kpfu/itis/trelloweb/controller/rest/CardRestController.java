package ru.kpfu.itis.trelloweb.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.trelloapi.dto.CardDTO;
import ru.kpfu.itis.trelloapi.service.CardService;
import ru.kpfu.itis.trelloweb.security.details.UserDetailsImpl;

/**
 * @author Roman Leontev
 * 17:33 21.04.2021
 * group 11-905
 */

@RestController
@RequestMapping("/api/list_card/{list_card-id}/card")
public class CardRestController {

    @Autowired
    private CardService cardService;

    @GetMapping
    public ResponseEntity<Page<CardDTO>> getCards(@PathVariable("list_card-id") Long id, Pageable pageable) {
        return ResponseEntity.ok(cardService.getAllById(id, pageable));
    }

    @PostMapping
    public ResponseEntity<CardDTO> saveCard(@RequestBody CardDTO card, @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("list_card-id") Long listCardId) {
        Long userId = userDetails.getUserDTO().getId();
        card.setUserId(userId);
        card.setListCardId(listCardId);
        return ResponseEntity.ok(cardService.save(card));
    }

    @PutMapping("{card-id}")
    public ResponseEntity<CardDTO> updateCard(@PathVariable("card-id") Long cardId, @RequestBody CardDTO card) {
        card.setId(cardId);
        return ResponseEntity.ok(cardService.save(card));
    }

    @DeleteMapping("{card-id}")
    public void deleteCard(@PathVariable("card-id") Long id) {
        cardService.deleteById(id);
    }
}
