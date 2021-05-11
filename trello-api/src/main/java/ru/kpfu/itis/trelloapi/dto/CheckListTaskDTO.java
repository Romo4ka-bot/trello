package ru.kpfu.itis.trelloapi.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author Roman Leontev
 * 13:22 09.05.2021
 * group 11-905
 */

@Builder
@Data
public class CheckListTaskDTO {
    private Long id;
    private String title;
    private Boolean done;
    private Date createdAt;
    private Long userId;
    private Long checkListId;
}
