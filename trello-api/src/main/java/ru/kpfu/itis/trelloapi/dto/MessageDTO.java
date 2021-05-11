package ru.kpfu.itis.trelloapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Roman Leontev
 * 22:54 11.05.2021
 * group 11-905
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

    private String text;
}