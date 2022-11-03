package program.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import program.dto.ColumnDTO;
import program.service.ColumnService;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/api/boards")
public class ColumnController {
    @Autowired
    ColumnService service;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{boardId}/columns")
    public List<ColumnDTO> getAllBoardColumns(@PathVariable("boardId") String boardId) {
        return service.getAllByBoardId(boardId);
    }
}
