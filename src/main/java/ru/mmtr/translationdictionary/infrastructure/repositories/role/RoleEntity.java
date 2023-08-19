package ru.mmtr.translationdictionary.infrastructure.repositories.role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/*@Entity
@Table(name = "Roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor*/
public class RoleEntity {
    /*public static final String ROLE_ID = "role_id";
    public static final String ROLE_NAME = "role_name";
    public static final String ROLE_CREATED_AT = "created_at";
    public static final String ROLE_MODIFIED_AT = "modified_at";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ROLE_ID)
    private UUID roleId;

    @Column(name = ROLE_NAME)
    private String roleName;

    @Column(name = ROLE_CREATED_AT)
    private LocalDateTime createdAt;

    @Column(name = ROLE_MODIFIED_AT)
    private LocalDateTime modifiedAt;*/
}
