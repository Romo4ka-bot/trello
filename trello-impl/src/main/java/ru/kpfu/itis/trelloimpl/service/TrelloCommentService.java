package ru.kpfu.itis.trelloimpl.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.trelloapi.dto.CommentDTO;
import ru.kpfu.itis.trelloapi.service.CommentService;
import ru.kpfu.itis.trelloimpl.entity.CommentEntity;
import ru.kpfu.itis.trelloimpl.repository.CommentRepository;

/**
 * @author Roman Leontev
 * 20:42 10.05.2021
 * group 11-905
 */

@Service
public class TrelloCommentService implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDTO save(CommentDTO comment) {
        CommentEntity commentEntity = modelMapper.map(comment, CommentEntity.class);
        return modelMapper.map(commentRepository.save(commentEntity), CommentDTO.class);
    }

    @Override
    public CommentDTO getById(Long commentId) {
        return modelMapper.map(commentRepository.findById(commentId).orElseThrow(IllegalArgumentException::new), CommentDTO.class);
    }

    @Override
    public void delete(CommentDTO comment) {
        CommentEntity commentEntity = modelMapper.map(comment, CommentEntity.class);
        commentRepository.delete(commentEntity);
    }
}
