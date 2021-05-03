package ru.kpfu.itis.trelloweb.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.trelloapi.dto.ListCardDTO;
import ru.kpfu.itis.trelloapi.service.ListCardService;

/**
 * @author Roman Leontev
 * 00:00 20.04.2021
 * group 11-905
 */

@RestController
@RequestMapping("/api/board/{board-id}/list_card")
public class ListCardRestController {

    @Autowired
    private ListCardService listCardService;

    @GetMapping
    public ResponseEntity<Page<ListCardDTO>> getListsCard(Pageable pageable) {
        return ResponseEntity.ok(listCardService.getAll(pageable));
    }

    @PostMapping
    public ResponseEntity<ListCardDTO> saveListCard(@RequestBody ListCardDTO listCard, @PathVariable("board-id") Long boardId) {
        listCard.setBoardId(boardId);
        return ResponseEntity.ok(listCardService.save(listCard));
    }

    @GetMapping("/{listCard-id}")
    public ResponseEntity<ListCardDTO> getListCard(@PathVariable("listCard-id") Long id) {
        return ResponseEntity.ok(listCardService.getById(id));
    }

    @PutMapping("/{listCard-id}")
    public ResponseEntity<ListCardDTO> updateListCard(@PathVariable("listCard-id") Long id, @RequestBody ListCardDTO listCard) {
        listCard.setId(id);
        return ResponseEntity.ok(listCardService.save(listCard));
    }

    @DeleteMapping("/{listCard-id}")
    public void deleteListCard(@PathVariable("listCard-id") Long id) {
        listCardService.deleteById(id);
    }
}
