package program.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class ColumnDTO {
    @NotBlank
    String id;

    @NotBlank
    String title;
    String userId;
    String boardId;
}
