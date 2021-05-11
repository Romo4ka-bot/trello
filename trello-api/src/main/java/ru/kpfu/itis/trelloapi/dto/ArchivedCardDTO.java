package ru.kpfu.itis.trelloapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Roman Leontev
 * 16:02 08.05.2021
 * group 11-905
 */

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArchivedCardDTO {
    private Long id;
    private Date createdAt;
    private Long cardId;
    private String cardTitle;
    private Long listCardId;
    private Long boardId;
}
