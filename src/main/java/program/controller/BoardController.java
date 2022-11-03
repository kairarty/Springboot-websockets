package program.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import program.dto.BoardDTO;
import program.dto.TaskDTO;
import program.service.BoardService;
import program.service.TaskService;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/api/boards")
public class BoardController {
    @Autowired
    BoardService service;
    @Autowired
    TaskService taskService;

    @GetMapping
    public List<BoardDTO> getAllForCurrentUser() {
        return service.getAllByUserId();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public BoardDTO get(@PathVariable("id") String id) {
        return service.getById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public void create(@RequestBody String title) {
        service.create(title);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{boardId}/tasks")
    public List<TaskDTO> getAllBoardColumns(@PathVariable("boardId") String boardId) {
        return taskService.getAllByBoardId(boardId);
    }
}
