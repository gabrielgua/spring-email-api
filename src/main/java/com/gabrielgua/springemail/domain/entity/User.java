package com.gabrielgua.springemail.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "tb_users")
public class User {

    @MongoId
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private String email;
    private String password;
    private OffsetDateTime createdAt;

    private Set<String> projectIds;
}
