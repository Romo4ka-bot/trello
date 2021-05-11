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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roman Leontev
 * 17:50 04.05.2021
 * group 11-905
 */

@Controller
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private UserService userService;

    @Autowired
    private ListCardService listCardService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CheckListService checkListService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private ArchivedCardService archivedCardService;

    @GetMapping("/workspace/{workspace-id}/board/{board-id}/list_card/{list_card-id}/card/{card-id}")
    public String getCardPage(@PathVariable("workspace-id") Long workspaceId, @PathVariable("board-id") Long boardId, @PathVariable("card-id") Long cardId, @AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable("list_card-id") Long listCardId, Model model) {
        CardDTO card = cardService.getById(cardId);
        ListCardDTO listCard = listCardService.getById(listCardId);
        String email = userDetails.getEmail();
        UserDTO user = userService.getByEmail(email);
        List<CheckListDTO> checkLists = checkListService.getAll();

        model.addAttribute("card", card);
        model.addAttribute("listCard", listCard);
        model.addAttribute("user", user);
        model.addAttribute("checkLists", checkLists);
        model.addAttribute("boardId", boardId);
        model.addAttribute("workspaceId", workspaceId);
        return "card_content";
    }

    @GetMapping("aaa")
    public String get() {
        return "card.html";
    }

    @PostMapping("/workspace/{workspace-id}/board/{board-id}/list_card/{list_card-id}/card")
    public String saveCard(CardDTO card, @AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable("list_card-id") Long listCardId, @PathVariable("board-id") Long boardId, @PathVariable("workspace-id") Long workspaceId) {
        if (userDetails != null) {
            String email = userDetails.getEmail();
            UserDTO user = userService.getByEmail(email);
            card.setUserId(user.getId());
            card.setListCardId(listCardId);
            cardService.save(card);
            return "redirect:/workspace/" + workspaceId + "/board/" + boardId;
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/workspace/{workspace-id}/board/{board-id}/list_card/{list_card-id}/card/{card-id}/delete")
    public String deleteCard(@PathVariable("card-id") Long cardId, @PathVariable("board-id") Long boardId, @PathVariable("workspace-id") Long workspaceId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails != null) {
            String email = userDetails.getEmail();
            UserDTO user = userService.getByEmail(email);
            BoardDTO board = boardService.getById(boardId);
            if (board.getUserId().equals(user.getId())) {
                cardService.deleteById(cardId);
            }
            return "redirect:/workspace/" + workspaceId + "/board/" + boardId;
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/card/{card-id}/desc")
    @ResponseBody
    public ResponseEntity<CardDTO> saveDescription(@RequestBody CardDTO card, @PathVariable("card-id") Long cardId) {
        CardDTO cardDTO = cardService.getById(cardId);
        cardDTO.setDescription(card.getDescription());
        return ResponseEntity.ok(cardService.update(cardDTO));
    }

    @PostMapping("/card/comment")
    @ResponseBody
    public ResponseEntity<CommentDTO> saveComment(@RequestBody CommentDTO comment) {
        return ResponseEntity.ok(commentService.save(comment));
    }

    @PostMapping("/card/comment/{comment-id}/delete")
    @ResponseBody
    public ResponseEntity<?> deleteComment(@PathVariable("comment-id") Long commentId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails != null) {
            String email = userDetails.getEmail();
            UserDTO user = userService.getByEmail(email);
            CommentDTO comment = commentService.getById(commentId);
            if (user.getId().equals(comment.getUserId())) {
                commentService.delete(comment);
                return ResponseEntity.ok().build();
            } else return ResponseEntity.badRequest().build();
        } else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/workspace/{workspace-id}/board/{board-id}/list_card/{list_card-id}/card/{card-id}/check_list")
    public String getCheckList(@PathVariable("workspace-id") Long workspaceId, @PathVariable("board-id") Long boardId, @PathVariable("card-id") Long cardId, @PathVariable("list_card-id") Long listCardId, Model model) {
        model.addAttribute("workspaceId", workspaceId);
        model.addAttribute("boardId", boardId);
        model.addAttribute("cardId", cardId);
        model.addAttribute("listCardId", listCardId);
        return "creator_check_list";
    }

    @PostMapping("/workspace/{workspace-id}/board/{board-id}/list_card/{list_card-id}/card/{card-id}/check_list")
    public String saveCheckList(CheckListDTO checkList, @PathVariable("workspace-id") Long workspaceId, @PathVariable("board-id") Long boardId, @PathVariable("card-id") Long cardId, @PathVariable("list_card-id") Long listCardId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails != null) {
            String email = userDetails.getEmail();
            UserDTO user = userService.getByEmail(email);
            System.out.println(checkList);
            List<String> tasks = checkList.getTasks();
            List<CheckListTaskDTO> checkListTasks = new ArrayList<>();
            for (String checkListTask: tasks) {
                checkListTasks.add(CheckListTaskDTO.builder()
                .done(false)
                .title(checkListTask)
                .userId(user.getId())
                .build());
            }
            checkList.setUserId(user.getId());
            checkList.setCardId(cardId);
            checkList.setCheckListTasks(checkListTasks);

            checkListService.save(checkList);
            return "redirect:/workspace/" + workspaceId + "/board/" + boardId + "/list_card/" + listCardId + "/card/" + cardId;
        } else return "redirect:/login";
    }

    @PostMapping("/check_list/{check_list-id}/delete")
    @ResponseBody
    public ResponseEntity<?> deleteCheckList(@PathVariable("check_list-id") Long checkListId) {
        checkListService.deleteById(checkListId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/workspace/{workspace-id}/board/{board-id}/list_card/{list_card-id}/card/{card-id}/archive")
    public String archivedCard(@PathVariable("workspace-id") Long workspaceId, @PathVariable("board-id") Long boardId, @PathVariable("card-id") Long cardId, @PathVariable("list_card-id") Long listCardId) {
        ArchivedCardDTO archivedCard = ArchivedCardDTO.builder()
                .cardId(cardId)
                .listCardId(listCardId)
                .boardId(boardId)
                .build();
        archivedCardService.save(archivedCard);
        return "redirect:/workspace/" + workspaceId + "/board/" + boardId;
    }
}
