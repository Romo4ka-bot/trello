package ru.kpfu.itis.trelloweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.trelloapi.dto.*;
import ru.kpfu.itis.trelloapi.service.*;
import ru.kpfu.itis.trelloweb.security.details.CustomUserDetails;
import ru.kpfu.itis.trelloweb.security.details.UserDetailsImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roman Leontev
 * 22:32 23.04.2021
 * group 11-905
 */

@Controller
@RequestMapping("/workspace/{workspace-id}/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private ListCardService listCardService;

    @Autowired
    private UserService userService;

    @Autowired
    private ArchivedCardService archivedCardService;

    @GetMapping("/{board-id}")
    public String getBoardPage(Model model, @PathVariable("board-id") Long boardId, @PathVariable("workspace-id") Long workspaceId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<ListCardDTO> listsCard = listCardService.getAll();
        List<UserDTO> participants = boardService.getAllParticipantsByBoardId(boardId);
        BoardDTO board = boardService.getById(boardId);
        WorkspaceDTO workspace = workspaceService.getById(workspaceId);
        List<ArchivedCardDTO> archivedCards = archivedCardService.getAllByBoardId(boardId);
        String email = userDetails.getEmail();
        UserDTO user = userService.getByEmail(email);
        for (ListCardDTO listCard: listsCard) {
            List<CardDTO> cards = listCard.getCards();
            List<CardDTO> newCards = new ArrayList<>();
            for (CardDTO card: cards) {
                for (ArchivedCardDTO archivedCardDTO:archivedCards) {
                    if (!card.getId().equals(archivedCardDTO.getCardId())) {
                        newCards.add(card);
                    }
                }
            }
            listCard.setCards(newCards);
        }

        model.addAttribute("workspace", workspace);
        model.addAttribute("board", board);
        model.addAttribute("listsCard", listsCard);
        model.addAttribute("participants", participants);
        model.addAttribute("user", user);
        model.addAttribute("archivedCards", archivedCards);
        return "board";
    }

    @PostMapping
    public String saveBoard(BoardDTO board, @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("workspace-id") Long workspaceId) {
        if (userDetails != null) {
            String email = userDetails.getEmail();
            UserDTO user = userService.getByEmail(email);
            Long userId = user.getId();
            board.setUserId(userId);
            board.setWorkspaceId(workspaceId);
            BoardDTO boardDTO = boardService.save(board);
            boardService.saveParticipant(userId, boardDTO.getId());
            return "redirect:/workspace";
        } else
            return "redirect:/login";
    }

    @PostMapping("/{board-id}/list_card")
    public String saveListCard(ListCardDTO listCardDTO, @PathVariable("board-id") Long boardId, @PathVariable("workspace-id") Long workspaceId) {
        listCardDTO.setBoardId(boardId);
        listCardService.save(listCardDTO);
        return "redirect:/workspace/" + workspaceId + "/board/" + boardId;
    }

    @PostMapping("/{board-id}/list_card/{list_card-id}/delete")
    public String deleteListCard(@PathVariable("board-id") Long boardId, @PathVariable("list_card-id") Long listCardId, @PathVariable("workspace-id") Long workspaceId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails != null) {
            UserDTO user = userService.getByEmail(userDetails.getEmail());
            BoardDTO board = boardService.getById(boardId);
            if (board.getUserId().equals(user.getId())) {
                listCardService.deleteById(listCardId);
            }
            return "redirect:/workspace/" + workspaceId + "/board/" + boardId;
        } else return "redirect:/login";
    }

    @PostMapping("/{board-id}/participants")
    public String saveParticipant(UserDTO user, @PathVariable("board-id") Long boardId, @PathVariable("workspace-id") Long workspaceId, Model model) {
        System.out.println(user);
        UserDTO userDTO = userService.getByEmail(user.getEmail());
        if (userDTO != null) {
            boardService.saveParticipant(userDTO.getId(), boardId);
            workspaceService.saveParticipant(userDTO.getId(), workspaceId);
        } else
            model.addAttribute("error", "Такого пользователя не существует");
        return "redirect:/workspace/" + workspaceId + "/board/" + boardId;
    }

    @GetMapping("/{board-id}/participants/{participants-id}")
    @ResponseBody
    public ResponseEntity<UserDTO> getParticipant(@PathVariable("participants-id") Long userId) {
        return ResponseEntity.ok(userService.getById(userId));
    }

    @PostMapping("/{board-id}/participants/{participants-id}/delete")
    public String saveParticipant(@PathVariable("board-id") Long boardId, @PathVariable("participants-id") Long userId) {
        boardService.deleteParticipant(boardId, userId);
        return "redirect:/board";
    }

//    @DeleteMapping("{board-id}")
//    public void deleteBoard(@PathVariable("board-id") Long id) {
//        boardService.deleteById(id);
//    }
//
//    @PutMapping
//    public void updateBoard(BoardDTO board, @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("workspace-id") Long workspaceId) {
//        Long userId = userDetails.getUserDTO().getId();
//        board.setUserId(userId);
//        board.setWorkspaceId(workspaceId);
//        boardService.save(board);
//    }
}
