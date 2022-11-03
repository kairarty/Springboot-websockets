package program.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import program.entity.Task;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findAllByBoardId(String boardId);

    List<Task> findAllByColumnId(String columnId);
}
