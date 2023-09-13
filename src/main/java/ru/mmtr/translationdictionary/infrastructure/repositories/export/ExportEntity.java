package ru.mmtr.translationdictionary.infrastructure.repositories.export;

import com.fasterxml.jackson.databind.JsonNode;
import io.ebean.annotation.DbJsonB;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "'Export'")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExportEntity {
    public static final String EXPORT_ID = "export_id";
    public static final String TYPE = "type";
    public static final String CREATE_MODEL = "create_model";
    public static final String USER_CREATED_ID = "user_created_id";
    public static final String CREATED_AT = "created_at";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = EXPORT_ID)
    private UUID exportId;

    @Column(name = TYPE)
    private String type;

    @Column(name = CREATE_MODEL)
    // or DbJson
    @DbJsonB
    private JsonNode createModel;

    @Column(name = USER_CREATED_ID)
    private UUID userCreatedId;

    @Column(name = CREATED_AT)
    private LocalDateTime createdAt;
}
