package ru.kpfu.itis.trelloweb.controller.rest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.trelloapi.dto.CardDTO;
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

    @ApiOperation(value = "Получение всех списков карточек")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Получил все списки карточек", response = ListCardDTO.class)})
    @GetMapping
    public ResponseEntity<Page<ListCardDTO>> getListsCard(Pageable pageable) {
        return ResponseEntity.ok(listCardService.getAll(pageable));
    }

    @ApiOperation(value = "Добавление списка карточек")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Добавил список карточек", response = ListCardDTO.class)})
    @PostMapping
    public ResponseEntity<ListCardDTO> saveListCard(@RequestBody ListCardDTO listCard, @PathVariable("board-id") Long boardId) {
        listCard.setBoardId(boardId);
        return ResponseEntity.ok(listCardService.save(listCard));
    }

    @ApiOperation(value = "Получение списка карточек")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Получил список карточек", response = ListCardDTO.class)})
    @GetMapping("/{listCard-id}")
    public ResponseEntity<ListCardDTO> getListCard(@PathVariable("listCard-id") Long id) {
        return ResponseEntity.ok(listCardService.getById(id));
    }

    @ApiOperation(value = "Обновление списка карточек")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Обновил список карточек", response = ListCardDTO.class)})
    @PutMapping("/{listCard-id}")
    public ResponseEntity<ListCardDTO> updateListCard(@PathVariable("listCard-id") Long id, @RequestBody ListCardDTO listCard) {
        listCard.setId(id);
        return ResponseEntity.ok(listCardService.save(listCard));
    }

    @ApiOperation(value = "Удаление списка карточек")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Удалил список карточек", response = ListCardDTO.class)})
    @DeleteMapping("/{listCard-id}")
    public void deleteListCard(@PathVariable("listCard-id") Long id) {
        listCardService.deleteById(id);
    }
}
