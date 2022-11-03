package program.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import program.dto.TaskDTO;
import program.entity.Column;
import program.entity.Task;
import program.repository.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class TaskService {
    @Autowired
    TaskRepository repository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ColumnService columnService;

    public List<TaskDTO> getAllByBoardId(String boardId) {
        List<Task> tasks = repository.findAllByBoardId(boardId);
        return tasks.stream()
                .map(task -> modelMapper.map(task, TaskDTO.class))
                .collect(Collectors.toList());
    }

    public List<Task> getAllByColumnId(String columnId) {
        return repository.findAllByColumnId(columnId);
    }

    public TaskDTO getById(String id) {
        Task task = repository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return modelMapper.map(task, TaskDTO.class);
    }

    public TaskDTO saveTask(TaskDTO taskDTO) {
        Task task = modelMapper.map(taskDTO, Task.class);
        return modelMapper.map(repository.save(task), TaskDTO.class);
    }

    public TaskDTO updateTask(TaskDTO taskDTO) {
        Task task = repository.findById(taskDTO.getId()).orElseThrow(ResourceNotFoundException::new);
        Column column = columnService.getById(taskDTO.getColumnId());

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setColumn(column);
        return modelMapper.map(repository.save(task), TaskDTO.class);
    }

    public void delete(String taskId) {
        repository.deleteById(taskId);
    }
}
