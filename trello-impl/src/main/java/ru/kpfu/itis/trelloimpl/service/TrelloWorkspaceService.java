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

import java.util.ArrayList;
import java.util.List;

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
    private ModelMapper modelMapper;

    @Autowired
    private WorkspaceParticipantRepository participantRepository;

    @Override
    public WorkspaceDTO save(WorkspaceDTO workspaceDTO) {

        UserEntity userEntity = userRepository.findById(workspaceDTO.getUserId()).orElseThrow(IllegalArgumentException::new);

        WorkspaceEntity workspaceEntity = WorkspaceEntity.builder()
                .title(workspaceDTO.getTitle())
                .description(workspaceDTO.getDescription())
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
    public void deleteById(Long id) {
        workspaceRepository.deleteById(id);
    }

    @Override
    public WorkspaceDTO getById(Long workspaceId) {
        return modelMapper.map(workspaceRepository.findById(workspaceId).orElseThrow(IllegalArgumentException::new), WorkspaceDTO.class);
    }

    @Override
    public List<WorkspaceDTO> getAllByUserId(Long userId) {
        List<WorkspaceParticipantEntity> participantEntities = participantRepository.findAllByUserId(userId);
        List<WorkspaceDTO> workspaces = new ArrayList<>();
        for (WorkspaceParticipantEntity participantEntity : participantEntities) {
            workspaces.add(modelMapper.map(participantEntity.getWorkspace(), WorkspaceDTO.class));
        }
        return workspaces;
    }

    @Override
    public UserDTO saveParticipant(Long userId, Long workspaceId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);

        WorkspaceParticipantEntity participantEntity = WorkspaceParticipantEntity.builder()
                .user(user)
                .workspace(workspaceRepository.findById(workspaceId).orElseThrow(IllegalArgumentException::new))
                .build();

        participantRepository.save(participantEntity);
        return modelMapper.map(user, UserDTO.class);
    }
}
