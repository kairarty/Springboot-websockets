package program.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import program.config.util.SecurityUtils;
import program.dto.BoardDTO;
import program.entity.Board;
import program.entity.User;
import program.model.UserDetails;
import program.repository.BoardRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class BoardService {
    @Autowired
    BoardRepository repository;
    @Autowired
    UserService userService;
    @Autowired
    ModelMapper mapper;

    public BoardDTO getById(String boardId) {
        Board board = repository.findById(boardId).orElseThrow(ResourceNotFoundException::new);
        return mapper.map(board, BoardDTO.class);
    }

    public List<BoardDTO> getAllByUserId() {
        UserDetails userDetails = SecurityUtils.getCurrentAuthenticatedUser();
        List<Board> boards = repository.findAllByUserId(userDetails.getId());
        return boards
                .stream()
                .map(board -> mapper.map(board, BoardDTO.class))
                .collect(Collectors.toList());
    }

    public void create(String title) {
        UserDetails userDetails = SecurityUtils.getCurrentAuthenticatedUser();
        User user = userService.findByEmail(userDetails.getEmail());
        Board board = Board.builder()
                .createdAt(new Date())
                .title(title)
                .user(user)
                .build();
        repository.save(board);
    }

    public BoardDTO update(BoardDTO boardDTO) {
        Board board = repository.findById(boardDTO.getId()).orElseThrow(ResourceNotFoundException::new);
        board.setTitle(boardDTO.getTitle());
        return mapper.map(repository.save(board), BoardDTO.class);
    }

    public void delete(String boardId) {
        repository.deleteById(boardId);
    }
}
