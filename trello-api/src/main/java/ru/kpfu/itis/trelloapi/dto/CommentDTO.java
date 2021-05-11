package ru.kpfu.itis.trelloapi.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author Roman Leontev
 * 15:00 09.05.2021
 * group 11-905
 */

@Data
public class CommentDTO {
    private Long id;
    private String text;
    private Date createdAt;
    private Long userId;
    private String userFirstName;
    private Long cardId;
}
