package ru.kpfu.itis.trelloimpl.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.trelloapi.dto.CardDTO;
import ru.kpfu.itis.trelloapi.service.CardService;
import ru.kpfu.itis.trelloimpl.entity.CardEntity;
import ru.kpfu.itis.trelloimpl.entity.ListCardEntity;
import ru.kpfu.itis.trelloimpl.entity.UserEntity;
import ru.kpfu.itis.trelloimpl.repository.CardRepository;
import ru.kpfu.itis.trelloimpl.repository.ListCardRepository;
import ru.kpfu.itis.trelloimpl.repository.UserRepository;

/**
 * @author Roman Leontev
 * 17:39 21.04.2021
 * group 11-905
 */

@Component
public class TrelloCardService implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ListCardRepository listCardRepository;

    @Override
    public Page<CardDTO> getAllById(Long id, Pageable pageable) {
        return cardRepository.findAllById(id, pageable).map(cardEntity -> modelMapper.map(cardEntity, CardDTO.class));
    }

    @Override
    public CardDTO save(CardDTO card) {

        UserEntity userEntity = userRepository.findById(card.getUserId()).orElseThrow(IllegalArgumentException::new);

        ListCardEntity listCardEntity = listCardRepository.findById(card.getListCardId()).orElseThrow(IllegalArgumentException::new);


        CardEntity cardEntity = CardEntity.builder()
                .title(card.getTitle())
                .description(card.getDescription())
                .user(userEntity)
                .listCard(listCardEntity)
                .build();

        return modelMapper.map(cardRepository.save(cardEntity), CardDTO.class);
    }

    @Override
    public void deleteById(Long id) {
        cardRepository.deleteById(id);
    }

    @Override
    public CardDTO getById(Long cardId) {
        return modelMapper.map(cardRepository.findById(cardId).orElseThrow(IllegalArgumentException::new), CardDTO.class);
    }

    @Override
    public CardDTO update(CardDTO card) {
        CardEntity cardEntity = modelMapper.map(card, CardEntity.class);
        return modelMapper.map(cardRepository.save(cardEntity), CardDTO.class);
    }
}
