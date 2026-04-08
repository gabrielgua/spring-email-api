package com.gabrielgua.springemail.domain.repository;

import com.gabrielgua.springemail.domain.entity.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {

    List<Project> findByUserId(String userId);

    Optional<Project> findByApiKey(String apiKey);
}
