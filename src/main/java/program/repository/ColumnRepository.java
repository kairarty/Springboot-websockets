package program.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import program.entity.Column;

import java.util.List;

public interface ColumnRepository extends MongoRepository<Column, String> {
    List<Column> findAllByBoardId(String boardId);
}
