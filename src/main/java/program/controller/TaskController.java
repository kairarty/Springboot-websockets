package program.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import program.dto.TaskDTO;
import program.service.TaskService;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    TaskService service;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public TaskDTO get(@PathVariable("id") String id) {
        return service.getById(id);
    }
}
