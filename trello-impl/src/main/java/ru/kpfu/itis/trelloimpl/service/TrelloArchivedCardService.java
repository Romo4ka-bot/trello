package ru.kpfu.itis.trelloimpl.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.trelloapi.dto.ArchivedCardDTO;
import ru.kpfu.itis.trelloapi.service.ArchivedCardService;
import ru.kpfu.itis.trelloimpl.entity.ArchivedCardEntity;
import ru.kpfu.itis.trelloimpl.repository.ArchivedCardRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roman Leontev
 * 15:55 08.05.2021
 * group 11-905
 */

@Service
public class TrelloArchivedCardService implements ArchivedCardService {

    @Autowired
    private ArchivedCardRepository archivedCardRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ArchivedCardDTO> getAllByBoardId(Long boardId) {
        List<ArchivedCardEntity> archivedCardEntities = archivedCardRepository.findAllByBoardId(boardId);
        List<ArchivedCardDTO> archivedCards = new ArrayList<>();
        for (ArchivedCardEntity archivedCardEntity : archivedCardEntities) {
            archivedCards.add(modelMapper.map(archivedCardEntity, ArchivedCardDTO.class));
        }
        return archivedCards;
    }

    @Override
    public ArchivedCardDTO save(ArchivedCardDTO archivedCard) {
        return modelMapper.map(archivedCardRepository.save(modelMapper.map(archivedCard, ArchivedCardEntity.class)), ArchivedCardDTO.class);
    }
}
