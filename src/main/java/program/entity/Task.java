package program.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Task {
    @Id
    String id;

    @NotBlank
    String title;

    String description;
    Date createdAt = new Date();

    @JsonIgnore
    @DocumentReference(lazy = true)
    User user;

    @JsonIgnore
    @DocumentReference(lazy = true)
    Board board;

    @JsonIgnore
    @DocumentReference(lazy = true)
    Column column;
}
