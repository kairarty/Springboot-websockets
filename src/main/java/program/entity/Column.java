package program.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Column {
    @Id
    String id;

    @NotBlank
    String title;

    Date createdAt = new Date();

    @JsonIgnore
    @DocumentReference(lazy = true)
    User user;

    @JsonIgnore
    @DocumentReference(lazy = true)
    Board board;

    @DocumentReference
    List<Task> tasks = new ArrayList<>();
}
