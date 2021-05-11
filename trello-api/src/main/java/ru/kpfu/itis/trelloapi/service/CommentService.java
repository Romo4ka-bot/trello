package ru.kpfu.itis.trelloapi.service;

import ru.kpfu.itis.trelloapi.dto.CommentDTO;

/**
 * @author Roman Leontev
 * 20:42 10.05.2021
 * group 11-905
 */

public interface CommentService {
    CommentDTO save(CommentDTO comment);

    void delete(CommentDTO comment);

    CommentDTO getById(Long commentId);
}
