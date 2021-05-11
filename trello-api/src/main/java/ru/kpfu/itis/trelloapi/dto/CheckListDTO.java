package ru.kpfu.itis.trelloapi.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Roman Leontev
 * 13:22 09.05.2021
 * group 11-905
 */

@Data
public class CheckListDTO {
    private Long id;
    private String title;
    private Date createdAt;
    private Long userId;
    private Long cardId;
    private List<CheckListTaskDTO> checkListTasks;
    private List<String> tasks;
}
