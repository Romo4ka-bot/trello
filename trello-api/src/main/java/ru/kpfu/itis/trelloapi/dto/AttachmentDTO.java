package ru.kpfu.itis.trelloapi.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author Roman Leontev
 * 13:06 09.05.2021
 * group 11-905
 */

@Data
public class AttachmentDTO {
    private Long id;
    private String contentPath;
    private Date createdAt;
    private Long userId;
    private Long cardId;
}
