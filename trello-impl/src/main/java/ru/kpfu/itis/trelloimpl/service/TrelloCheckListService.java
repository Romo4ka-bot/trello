package ru.kpfu.itis.trelloimpl.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.trelloapi.dto.CheckListDTO;
import ru.kpfu.itis.trelloapi.dto.CheckListTaskDTO;
import ru.kpfu.itis.trelloapi.service.CheckListService;
import ru.kpfu.itis.trelloimpl.entity.CheckListEntity;
import ru.kpfu.itis.trelloimpl.entity.CheckListTaskEntity;
import ru.kpfu.itis.trelloimpl.repository.CheckListRepository;
import ru.kpfu.itis.trelloimpl.repository.CheckListTaskRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roman Leontev
 * 00:58 11.05.2021
 * group 11-905
 */

@Service
public class TrelloCheckListService implements CheckListService {

    @Autowired
    private CheckListRepository checkListRepository;

    @Autowired
    private CheckListTaskRepository checkListTaskRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CheckListDTO> getAll() {
        List<CheckListEntity> checkLists = checkListRepository.findAll();
        List<CheckListDTO> checkListsDTO = new ArrayList<>();
        for (CheckListEntity checkList : checkLists) {
            checkListsDTO.add(modelMapper.map(checkList, CheckListDTO.class));
        }
        return checkListsDTO;
    }

    @Override
    public CheckListDTO save(CheckListDTO checkList) {
        List<CheckListTaskDTO> checkListTasks = checkList.getCheckListTasks();
        checkList.setCheckListTasks(null);
        CheckListEntity checkListEntity = modelMapper.map(checkList, CheckListEntity.class);

        if (checkListTasks != null) {
            List<CheckListTaskEntity> checkListTaskEntities = new ArrayList<>();
            for (CheckListTaskDTO checkListTask : checkListTasks) {
                checkListTask.setCheckListId(checkListEntity.getId());
                checkListTaskEntities.add(modelMapper.map(checkListTask, CheckListTaskEntity.class));
            }
            checkListTaskRepository.saveAll(checkListTaskEntities);
        }else {
            checkList.setCheckListTasks(new ArrayList<>());
        }
        return modelMapper.map(checkListRepository.save(checkListEntity), CheckListDTO.class);
    }

    @Override
    public void deleteById(Long checkListId) {
        checkListRepository.deleteById(checkListId);
    }
}
