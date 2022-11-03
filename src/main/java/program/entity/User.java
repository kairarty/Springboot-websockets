package program.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Document
public class User {
    @Id
    String id;

    @NotBlank
    String userName;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  // только на запись. При чтении не возвращается
    String password;

    @NotBlank
    @Indexed(unique = true)     // уникальное поле для mongoDB
    String email;

    Date createdAt = new Date();

    @ReadOnlyProperty
    @DocumentReference(lookup = "{'user':?#{#self._id} }")  // чтобы не сохранять User при сохранении Board
    List<Board> boards = new ArrayList<>();
}
