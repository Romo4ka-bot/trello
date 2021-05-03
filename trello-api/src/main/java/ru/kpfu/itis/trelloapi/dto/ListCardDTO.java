package ru.kpfu.itis.trelloapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author Roman Leontev
 * 00:09 20.04.2021
 * group 11-905
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListCardDTO {
    private Long id;
    private String title;
    private Date createdAt;
    private Long boardId;
    private List<CardDTO> cards;
}
