package com.gabrielgua.springemail.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Document(collection = "users")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
    private String email;
    private String password;
    private OffsetDateTime createdAt;

    private Set<Project> projects;
}
