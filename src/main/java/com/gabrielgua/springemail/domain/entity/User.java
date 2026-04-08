package com.gabrielgua.springemail.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "tb_users")
public class User {

    @MongoId
    @EqualsAndHashCode.Include
    private String id;
    private String name;

    private UserRole role;
    private String email;
    private String password;
    private Instant createdAt;

    private List<String> projectIds = new ArrayList<>();

    public Boolean isNew() {
        return this.id == null;
    }

}
