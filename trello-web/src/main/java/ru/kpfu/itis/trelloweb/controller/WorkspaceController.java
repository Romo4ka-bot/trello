package ru.kpfu.itis.trelloweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kpfu.itis.trelloapi.dto.BoardDTO;
import ru.kpfu.itis.trelloapi.dto.UserDTO;
import ru.kpfu.itis.trelloapi.dto.WorkspaceDTO;
import ru.kpfu.itis.trelloapi.service.BoardService;
import ru.kpfu.itis.trelloapi.service.UserService;
import ru.kpfu.itis.trelloapi.service.WorkspaceService;
import ru.kpfu.itis.trelloweb.security.details.CustomUserDetails;
import ru.kpfu.itis.trelloweb.security.details.UserDetailsImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roman Leontev
 * 11:12 22.04.2021
 * group 11-905
 */

@Controller
public class WorkspaceController {

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @GetMapping("/")
    public String getIndexPage() {
        return "index";
    }

    @GetMapping("/workspace")
    public String getWorkspacePage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {

        if (userDetails != null) {
            String email = userDetails.getEmail();
            UserDTO user = userService.getByEmail(email);

            List<WorkspaceDTO> workspaces = workspaceService.getAllByUserId(user.getId());
            List<BoardDTO> boards = boardService.getAllByUserId(user.getId());

            for (WorkspaceDTO workspace: workspaces) {
                List<BoardDTO> newBoards = new ArrayList<>();
                for (BoardDTO board : workspace.getBoards()) {
                    for (BoardDTO board2: boards) {
                        if (board.getId().equals(board2.getId())) {
                            newBoards.add(board);
                        }
                    }
                }
                workspace.setBoards(newBoards);
            }

            model.addAttribute("workspaces", workspaces);
            model.addAttribute("user", user);
            return "workspace";
        } else
            return "redirect:/login";
    }

    @PostMapping("/workspace")
    public String saveWorkspace(@AuthenticationPrincipal UserDetailsImpl userDetails, WorkspaceDTO workspace) {
        if (userDetails != null) {
            String email = userDetails.getEmail();
            UserDTO user = userService.getByEmail(email);
            Long userId = user.getId();
            workspace.setUserId(userId);
            WorkspaceDTO workspaceDTO = workspaceService.save(workspace);
            workspaceService.saveParticipant(userId, workspaceDTO.getId());
            return "redirect:/workspace";
        } else
            return "redirect:/login";
    }
}
