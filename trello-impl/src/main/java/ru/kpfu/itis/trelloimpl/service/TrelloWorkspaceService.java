package ru.kpfu.itis.trelloimpl.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.trelloapi.dto.UserDTO;
import ru.kpfu.itis.trelloapi.dto.WorkspaceDTO;
import ru.kpfu.itis.trelloimpl.entity.UserEntity;
import ru.kpfu.itis.trelloimpl.entity.WorkspaceEntity;
import ru.kpfu.itis.trelloimpl.entity.WorkspaceParticipantEntity;
import ru.kpfu.itis.trelloimpl.repository.UserRepository;
import ru.kpfu.itis.trelloimpl.repository.WorkspaceParticipantRepository;
import ru.kpfu.itis.trelloimpl.repository.WorkspaceRepository;

/**
 * @author Roman Leontev
 * 20:59 10.04.2021
 * group 11-905
 */

@Service
public class TrelloWorkspaceService implements ru.kpfu.itis.trelloapi.service.WorkspaceService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private WorkspaceParticipantRepository participantRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public WorkspaceDTO save(WorkspaceDTO workspaceDTO) {

        UserEntity userEntity = userRepository.findById(workspaceDTO.getUserId()).orElseThrow(IllegalArgumentException::new);

        WorkspaceEntity workspaceEntity = WorkspaceEntity.builder()
                .title(workspaceDTO.getTitle())
                .description(workspaceDTO.getDescription())
                .privacyType(WorkspaceEntity.PrivacyType.PRIVATE)
                .user(userEntity)
                .build();

        workspaceRepository.save(workspaceEntity);

        return modelMapper.map(workspaceEntity, WorkspaceDTO.class);
    }

    @Override
    public Page<WorkspaceDTO> getAll(Pageable pageable) {
        return workspaceRepository.findAll(pageable).map(workspaceEntity -> modelMapper.map(workspaceEntity, WorkspaceDTO.class));
    }

    @Override
    public UserDTO addParticipant(UserDTO user, Long workspaceId) {

        UserEntity userEntity = userRepository.findById(user.getId()).orElseThrow(IllegalArgumentException::new);
        WorkspaceEntity workspace = workspaceRepository.findById(workspaceId).orElseThrow(IllegalAccessError::new);

        WorkspaceParticipantEntity participant = WorkspaceParticipantEntity.builder()
                .user(userEntity)
                .workspace(workspace)
                .build();

        return modelMapper.map(participantRepository.save(participant).getUser(), UserDTO.class);
    }

    @Override
    public void delete(Long id) {
        workspaceRepository.deleteById(id);
    }
}
