package com.gabrielgua.springemail.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;
import java.util.List;

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
    private Instant createdAt;
    private List<String> allowedOrigins;

    @Indexed(name = "user_id_index")
    private String userId;
}
