package com.gabrielgua.springemail.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.OffsetDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "tb_projects")
public class Project {

    @MongoId
    @EqualsAndHashCode.Include
    private String id;
    private String name;
    private String apiKey;
    private String destinationEmail;
    private Boolean active;
    private OffsetDateTime createdAt;

    @Indexed(name = "user_id_index")
    private Long userId;
}
