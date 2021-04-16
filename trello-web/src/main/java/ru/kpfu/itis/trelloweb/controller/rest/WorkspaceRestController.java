package ru.kpfu.itis.trelloweb.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.trelloapi.dto.UserDTO;
import ru.kpfu.itis.trelloapi.dto.WorkspaceDTO;
import ru.kpfu.itis.trelloapi.service.WorkspaceService;
import ru.kpfu.itis.trelloweb.security.details.UserDetailsImpl;

/**
 * @author Roman Leontev
 * 12:48 10.04.2021
 * group 11-905
 */

@RestController
@RequestMapping("api/workspace")
public class WorkspaceRestController {

    @Autowired
    private WorkspaceService workspaceService;

    @GetMapping
    public ResponseEntity<Page<WorkspaceDTO>> getWorkspaces(Pageable pageable) {
        return ResponseEntity.ok(workspaceService.getAll(pageable));
    }

    @PostMapping
    public ResponseEntity<WorkspaceDTO> saveWorkspace(@RequestBody WorkspaceDTO workspaceDTO, @AuthenticationPrincipal UserDetailsImpl user) {
            return ResponseEntity.ok(workspaceService.save(workspaceDTO));
    }

    @DeleteMapping("/{workspace-id}")
    public void deleteWorkspace(@PathVariable("workspace-id") String id) {
        workspaceService.delete(Long.parseLong(id));
    }

    @PutMapping("/{workspace-id}/participants")
    public ResponseEntity<UserDTO> addWorkspaceParticipant(@PathVariable("workspace-id") String workspaceId, @RequestBody UserDTO user) {
        return ResponseEntity.ok(workspaceService.addParticipant(user, Long.parseLong(workspaceId)));
    }


}
