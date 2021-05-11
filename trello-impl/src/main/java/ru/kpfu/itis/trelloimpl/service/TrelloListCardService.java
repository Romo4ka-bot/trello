package ru.kpfu.itis.trelloimpl.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.trelloapi.dto.BoardDTO;
import ru.kpfu.itis.trelloapi.dto.ListCardDTO;
import ru.kpfu.itis.trelloapi.service.ListCardService;
import ru.kpfu.itis.trelloimpl.entity.BoardEntity;
import ru.kpfu.itis.trelloimpl.entity.ListCardEntity;
import ru.kpfu.itis.trelloimpl.repository.BoardRepository;
import ru.kpfu.itis.trelloimpl.repository.CardRepository;
import ru.kpfu.itis.trelloimpl.repository.ListCardRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roman Leontev
 * 00:03 20.04.2021
 * group 11-905
 */

@Component
public class TrelloListCardService implements ListCardService {

    @Autowired
    private ListCardRepository listCardRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CardRepository cardRepository;

    @Override
    public Page<ListCardDTO> getAll(Pageable pageable) {
        return listCardRepository.findAll(pageable).map(listCardEntity -> modelMapper.map(listCardEntity, ListCardDTO.class));
    }

    @Override
    public List<ListCardDTO> getAll() {
        List<ListCardEntity> listCardEntities = listCardRepository.findAll();
        List<ListCardDTO> listCardDTOs = new ArrayList<>();
        for (ListCardEntity listCardEntity : listCardEntities) {
            listCardDTOs.add(modelMapper.map(listCardEntity, ListCardDTO.class));
        }
        return listCardDTOs;
    }

    @Override
    public ListCardDTO save(ListCardDTO listCard) {

        BoardEntity board = boardRepository.findById(listCard.getBoardId()).orElseThrow(IllegalArgumentException::new);

        ListCardEntity listCardEntity = ListCardEntity.builder()
                .title(listCard.getTitle())
                .board(board)
                .build();
        return modelMapper.map(listCardRepository.save(listCardEntity), ListCardDTO.class);
    }

    @Override
    public ListCardDTO getById(Long id) {

        return modelMapper.map(listCardRepository.findById(id).orElseThrow(IllegalArgumentException::new), ListCardDTO.class);
    }

    @Override
    public void deleteById(Long id) {
        cardRepository.deleteAllByListCardId(id);
        listCardRepository.deleteById(id);
    }
}
