package ru.kpfu.itis.trelloapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.kpfu.itis.trelloapi.dto.UserDTO;
import ru.kpfu.itis.trelloapi.dto.WorkspaceDTO;

/**
 * @author Roman Leontev
 * 20:59 10.04.2021
 * group 11-905
 */

public interface WorkspaceService {
    WorkspaceDTO save(WorkspaceDTO workspaceDTO);

    Page<WorkspaceDTO> getAll(Pageable pageable);

    UserDTO addParticipant(UserDTO user, Long workspaceId);

    void delete(Long id);
}
