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
import ru.kpfu.itis.trelloapi.dto.ListCardDTO;
import ru.kpfu.itis.trelloapi.dto.UserDTO;
import ru.kpfu.itis.trelloapi.dto.WorkspaceDTO;
import ru.kpfu.itis.trelloapi.service.UserService;
import ru.kpfu.itis.trelloapi.service.WorkspaceService;
import ru.kpfu.itis.trelloweb.security.details.UserDetailsImpl;

/**
 * @author Roman Leontev
 * 12:48 10.04.2021
 * group 11-905
 */

@RestController
@RequestMapping("/api/workspace")
public class WorkspaceRestController {

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Получение всех рабочих пространств")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Получил все рабочие пространства", response = WorkspaceDTO.class)})
    @GetMapping
    public ResponseEntity<Page<WorkspaceDTO>> getWorkspaces(Pageable pageable) {
        return ResponseEntity.ok(workspaceService.getAll(pageable));
    }

    @ApiOperation(value = "Добавление рабочего пространства")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Доюавил рабочее пространство", response = WorkspaceDTO.class)})
    @PostMapping
    public ResponseEntity<WorkspaceDTO> saveWorkspace(@RequestBody WorkspaceDTO workspaceDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String email = userDetails.getEmail();
        UserDTO user = userService.getByEmail(email);
        workspaceDTO.setUserId(user.getId());
        return ResponseEntity.ok(workspaceService.save(workspaceDTO));
    }

    @ApiOperation(value = "Удаление рабочего пространства")
    @DeleteMapping("/{workspace-id}")
    public void deleteWorkspace(@PathVariable("workspace-id") Long id) {
        workspaceService.deleteById(id);
    }
}
