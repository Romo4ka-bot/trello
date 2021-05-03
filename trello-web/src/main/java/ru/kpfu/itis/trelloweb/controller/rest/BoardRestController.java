package ru.kpfu.itis.trelloweb.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.trelloapi.dto.BoardDTO;
import ru.kpfu.itis.trelloapi.dto.UserDTO;
import ru.kpfu.itis.trelloapi.service.BoardService;
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

    @GetMapping
    public ResponseEntity<Page<BoardDTO>> getBoards(Pageable pageable) {
        return ResponseEntity.ok(boardService.getAll(pageable));
    }

    @PostMapping
    public ResponseEntity<BoardDTO> saveBoard(@RequestBody BoardDTO board, @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("workspace-id") Long workspaceId) {
        Long userId = userDetails.getUserDTO().getId();
        board.setUserId(userId);
        board.setWorkspaceId(workspaceId);
        return ResponseEntity.ok(boardService.save(board));
    }

    @DeleteMapping("{board-id}")
    public void deleteBoard(@PathVariable("board-id") Long id) {
        boardService.deleteById(id);
    }

    @PutMapping
    public void updateBoard(@RequestBody BoardDTO board, @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("workspace-id") Long workspaceId) {
        Long userId = userDetails.getUserDTO().getId();
        board.setUserId(userId);
        board.setWorkspaceId(workspaceId);
        boardService.save(board);
    }

    @PutMapping("/{board-id}/participants")
    public ResponseEntity<UserDTO> addWorkspaceParticipant(@PathVariable("board-id") Long boardId, @RequestBody UserDTO user) {
        return ResponseEntity.ok(boardService.saveParticipant(user.getId(), boardId));
    }

    @DeleteMapping("/{board-id}/participants/{participant-id}")
    public void deleteWorkspace(@PathVariable("board-id") Long boardId, @PathVariable("participant-id") Long participantId) {
        boardService.deleteParticipant(boardId, participantId);
    }
}
