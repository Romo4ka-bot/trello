package ru.kpfu.itis.trelloimpl.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.trelloapi.dto.BoardDTO;
import ru.kpfu.itis.trelloapi.dto.UserDTO;
import ru.kpfu.itis.trelloapi.service.BoardService;
import ru.kpfu.itis.trelloimpl.entity.BoardEntity;
import ru.kpfu.itis.trelloimpl.entity.BoardParticipantEntity;
import ru.kpfu.itis.trelloimpl.entity.UserEntity;
import ru.kpfu.itis.trelloimpl.entity.WorkspaceEntity;
import ru.kpfu.itis.trelloimpl.repository.BoardRepository;
import ru.kpfu.itis.trelloimpl.repository.UserRepository;
import ru.kpfu.itis.trelloimpl.repository.BoardParticipantRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roman Leontev
 * 09:25 19.04.2021
 * group 11-905
 */

@Component
public class TrelloBoardService implements BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BoardParticipantRepository participantRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<BoardDTO> getAll(Pageable pageable) {
        return boardRepository.findAll(pageable).map(boardEntity -> modelMapper.map(boardEntity, BoardDTO.class));
    }

    @Override
    public List<BoardDTO> getAll() {
        List<BoardEntity> boardEntities = boardRepository.findAll();
        List<BoardDTO> boardDTOs = new ArrayList<>();
        for (BoardEntity board : boardEntities) {
            boardDTOs.add(modelMapper.map(board, BoardDTO.class));
        }
        return boardDTOs;
    }

    @Override
    public BoardDTO save(BoardDTO board) {

        BoardEntity boardEntity = modelMapper.map(board, BoardEntity.class);

        return modelMapper.map(boardRepository.save(boardEntity), BoardDTO.class);
    }

    @Override
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }

    @Override
    public BoardDTO getById(Long boardId) {
        return modelMapper.map(boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new), BoardDTO.class);
    }

    @Override
    public UserDTO saveParticipant(Long userId, Long boardId) {

        UserEntity user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        BoardParticipantEntity participantEntity = BoardParticipantEntity.builder()
                .user(user)
                .board(boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new))
                .build();
        participantRepository.save(participantEntity);
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public void deleteParticipant(Long boardId, Long userId) {
        participantRepository.deleteByBoardIdAndUserId(boardId, userId);
    }

    @Override
    public List<UserDTO> getAllParticipants() {
        List<BoardParticipantEntity> boardParticipantEntities = participantRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (BoardParticipantEntity boardParticipant : boardParticipantEntities) {
            userDTOs.add(modelMapper.map(boardParticipant.getUser(), UserDTO.class));
        }
        return userDTOs;
    }
}
