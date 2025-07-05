package com.shreyash.taskmanager.mapper;
import com.shreyash.taskmanager.dto.TaskRequestDto;
import com.shreyash.taskmanager.dto.TaskResponseDto;
import com.shreyash.taskmanager.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.springframework.scheduling.config.Task;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskEntity toEntity(TaskRequestDto taskDTO);

    List<TaskResponseDto> toDtoList(List<TaskEntity> taskEntities);

    TaskResponseDto toDto(TaskEntity taskEntity);


}
