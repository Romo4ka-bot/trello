package ru.kpfu.itis.trelloapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Roman Leontev
 * 12:47 10.04.2021
 * group 11-905
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceDTO {
    private Long id;
    private String title;
    private String description;
    private Long userId;
    private List<BoardDTO> boards;
}
