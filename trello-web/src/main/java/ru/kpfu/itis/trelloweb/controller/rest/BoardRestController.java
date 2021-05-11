package ru.kpfu.itis.trelloweb.controller.rest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.trelloapi.dto.BoardDTO;
import ru.kpfu.itis.trelloapi.dto.UserDTO;
import ru.kpfu.itis.trelloapi.service.BoardService;
import ru.kpfu.itis.trelloapi.service.UserService;
import ru.kpfu.itis.trelloweb.security.details.UserDetailsImpl;

/**
 * @author Roman Leontev
 * 09:16 19.04.2021
 * group 11-905
 */

@RestController
@RequestMapping("/api/workspace/{workspace-id}/board")
public class BoardRestController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Получение всех досок")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Получил все доски", response = BoardDTO.class)})
    @GetMapping
    public ResponseEntity<Page<BoardDTO>> getBoards(Pageable pageable) {
        return ResponseEntity.ok(boardService.getAll(pageable));
    }

    @ApiOperation(value = "Создание доски")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Создал доску", response = BoardDTO.class)})
    @PostMapping
    public ResponseEntity<BoardDTO> saveBoard(@RequestBody BoardDTO board, @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("workspace-id") Long workspaceId) {
        String email = userDetails.getEmail();
        UserDTO user = userService.getByEmail(email);
        Long userId = user.getId();
        board.setUserId(userId);
        board.setWorkspaceId(workspaceId);
        return ResponseEntity.ok(boardService.save(board));
    }

    @ApiOperation(value = "Удаление доски")
    @DeleteMapping("{board-id}")
    public void deleteBoard(@PathVariable("board-id") Long id) {
        boardService.deleteById(id);
    }

    @ApiOperation(value = "Обновление доски")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Обновил доску", response = BoardDTO.class)})
    @PutMapping
    public BoardDTO updateBoard(@RequestBody BoardDTO board, @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("workspace-id") Long workspaceId) {
        String email = userDetails.getEmail();
        UserDTO user = userService.getByEmail(email);
        Long userId = user.getId();
        board.setUserId(userId);
        board.setWorkspaceId(workspaceId);
        return boardService.save(board);
    }

    @ApiOperation(value = "Добавление или обновление участника доски")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Добавил или обновил участника доски", response = UserDTO.class)})
    @PutMapping("/{board-id}/participants")
    public ResponseEntity<UserDTO> addWorkspaceParticipant(@PathVariable("board-id") Long boardId, @RequestBody UserDTO user) {
        return ResponseEntity.ok(boardService.saveParticipant(user.getId(), boardId));
    }

    @ApiOperation(value = "Удаление участника доски")
    @DeleteMapping("/{board-id}/participants/{participant-id}")
    public void deleteWorkspace(@PathVariable("board-id") Long boardId, @PathVariable("participant-id") Long participantId) {
        boardService.deleteParticipant(boardId, participantId);
    }
}
