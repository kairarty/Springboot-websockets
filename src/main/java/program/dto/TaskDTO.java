package program.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class TaskDTO {
    String id;

    @NotBlank
    String title;
    
    String description;
    String userId;
    String boardId;
    String columnId;
}
