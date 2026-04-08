package com.gabrielgua.springemail.infra.data;

import com.gabrielgua.springemail.domain.entity.Project;
import com.gabrielgua.springemail.domain.entity.User;
import com.gabrielgua.springemail.domain.repository.ProjectRepository;
import com.gabrielgua.springemail.domain.repository.UserRepository;
import com.gabrielgua.springemail.domain.service.ProjectService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

@Configuration
public class DummyData {

    @Bean
    CommandLineRunner seedDummyData(UserRepository userRepository, ProjectRepository projectRepository, ProjectService projectService) {

            return args -> {
                //reset db
                projectRepository.deleteAll();
                userRepository.deleteAll();

                //insert dummy data
                User user = new User();
                user.setName("Gabriel");
                user.setEmail("gabriel@springemail.com");
                user.setPassword("gabriel123");
                user.setCreatedAt(Instant.now());
                userRepository.save(user);

                Project project = new Project();
                project.setName("Project Test");
                project.setApiKey("proj_test_api_key_123987789132");
                project.setActive(true);
                project.setDestinationEmail("support@project.com");
                project.setUserId(user.getId());
                project.setCreatedAt(Instant.now());
                projectService.save(project);

                user.setProjectIds(List.of(project.getId()));
                userRepository.save(user);
            };
    }
}
