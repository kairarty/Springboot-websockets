package program.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import program.dto.ColumnDTO;
import program.entity.Column;
import program.entity.Task;
import program.repository.ColumnRepository;

import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class ColumnService {
    @Autowired
    ColumnRepository repository;
    @Autowired
    ModelMapper mapper;
    @Autowired
    TaskService taskService;

    public List<ColumnDTO> getAllByBoardId(String boardId) {
        List<Column> columns = repository.findAllByBoardId(boardId);
        return columns
                .stream()
                .map(column -> mapper.map(column, ColumnDTO.class))
                .collect(Collectors.toList());
    }

    public Column getById(String id) {
        return repository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public ColumnDTO save(ColumnDTO columnDTO) {
        Column column = mapper.map(columnDTO, Column.class);
        return mapper.map(repository.save(column), ColumnDTO.class);
    }

    public ColumnDTO update(ColumnDTO columnDTO) {
        Column column = repository.findById(columnDTO.getId()).orElseThrow(ResourceNotFoundException::new);
        column.setTitle(columnDTO.getTitle());
        return mapper.map(repository.save(column), ColumnDTO.class);
    }

    public void delete(String columnId) {
        List<Task> tasks = taskService.getAllByColumnId(columnId);
        tasks.forEach(task -> taskService.delete(task.getId()));
        repository.deleteById(columnId);
    }
}
