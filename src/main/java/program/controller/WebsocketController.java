package program.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import program.dto.BoardDTO;
import program.dto.ColumnDTO;
import program.dto.TaskDTO;
import program.service.BoardService;
import program.service.ColumnService;
import program.service.TaskService;

import javax.validation.Valid;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Controller     // app - начало пути  (WebsocketConfig)
public class WebsocketController {
    @Autowired
    ColumnService columnService;
    @Autowired
    TaskService taskService;
    @Autowired
    BoardService boardService;

    @MessageMapping("/update-board")
    @SendTo("/topic/update-board")
    public BoardDTO updateBoard(@Valid BoardDTO board) {
        return boardService.update(board);
    }

    @MessageMapping("/delete-board/{boardId}")
    @SendTo("/topic/delete-board")
    public String deleteBoard(@DestinationVariable String boardId) {
        boardService.delete(boardId);
        return boardId;
    }

    @MessageMapping("/create-column")
    @SendTo("/topic/create-column")
    public ColumnDTO createColumn(ColumnDTO columnDTO) {
        return columnService.save(columnDTO);
    }

    @MessageMapping("/update-column")
    @SendTo("/topic/update-column")
    public ColumnDTO updateColumn(@Valid ColumnDTO columnDTO) {
        return columnService.update(columnDTO);
    }

    @MessageMapping("/delete-column/{columnId}")
    @SendTo("/topic/delete-column")
    public String deleteColumn(@DestinationVariable String columnId) {
        columnService.delete(columnId);
        return columnId;
    }

    @MessageMapping("/create-task")
    @SendTo("/topic/create-task")
    public TaskDTO createTask(TaskDTO task) {
        return taskService.saveTask(task);
    }

    @MessageMapping("/update-task")
    @SendTo("/topic/update-task")
    public TaskDTO updateTask(TaskDTO task) {
        return taskService.updateTask(task);
    }

    @MessageMapping("/delete-task/{taskId}")
    @SendTo("/topic/delete-task")
    public String deleteTask(@DestinationVariable String taskId) {
        taskService.delete(taskId);
        return taskId;
    }
}
