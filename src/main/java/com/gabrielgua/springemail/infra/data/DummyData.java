package com.gabrielgua.springemail.infra.data;

import com.gabrielgua.springemail.domain.entity.Project;
import com.gabrielgua.springemail.domain.entity.User;
import com.gabrielgua.springemail.domain.entity.UserRole;
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
    CommandLineRunner seedDummyData(
            UserRepository userRepository,
            ProjectRepository projectRepository,
            ProjectService projectService
    ) {

        return args -> {

            // reset db
            projectRepository.deleteAll();
            userRepository.deleteAll();

            var now = Instant.now();

            // ========================
            // 👑 ADMIN
            // ========================
            User admin = new User();
            admin.setName("Gabriel Admin");
            admin.setEmail("admin@springemail.com");
            admin.setRole(UserRole.ROLE_ADMIN);
            admin.setPassword("$2y$10$SvJ.hPeeq6LFoIslBmt71ekT9lnvVhb62aeIRH96/S3KlE8PY3gcW");
            admin.setCreatedAt(now);
            userRepository.save(admin);

            // ========================
            // 👤 USER 1
            // ========================
            User user1 = new User();
            user1.setName("Lucas Silva");
            user1.setEmail("lucas@wiivalife.com");
            user1.setRole(UserRole.ROLE_USER);
            user1.setPassword(admin.getPassword());
            user1.setCreatedAt(now);
            userRepository.save(user1);

            // ========================
            // 👤 USER 2
            // ========================
            User user2 = new User();
            user2.setName("Marina Costa");
            user2.setEmail("marina@fitnutrition.com");
            user2.setRole(UserRole.ROLE_USER);
            user2.setPassword(admin.getPassword());
            user2.setCreatedAt(now);
            userRepository.save(user2);

            // ========================
            // 📦 PROJECTS - USER 1
            // ========================
            Project p1 = new Project();
            p1.setName("Wiiva Life Landing");
            p1.setApiKey("wiiva_api_key_123456");
            p1.setActive(true);
            p1.setDestinationEmail("contato@wiivalife.com");
            p1.setUserId(user1.getId());
            p1.setCreatedAt(now);
            p1.setAllowedOrigins(List.of(
                    "https://wiivalife.com",
                    "http://localhost:5173"
            ));
            projectService.save(p1);

            Project p2 = new Project();
            p2.setName("Wiiva Checkout");
            p2.setApiKey("wiiva_checkout_987654");
            p2.setActive(true);
            p2.setDestinationEmail("vendas@wiivalife.com");
            p2.setUserId(user1.getId());
            p2.setCreatedAt(now);
            p2.setAllowedOrigins(List.of(
                    "https://checkout.wiivalife.com"
            ));
            projectService.save(p2);

            // ========================
            // 📦 PROJECTS - USER 2
            // ========================
            Project p3 = new Project();
            p3.setName("Fit Nutrition Website");
            p3.setApiKey("fit_api_key_456789");
            p3.setActive(true);
            p3.setDestinationEmail("suporte@fitnutrition.com");
            p3.setUserId(user2.getId());
            p3.setCreatedAt(now);
            p3.setAllowedOrigins(List.of(
                    "https://fitnutrition.com"
            ));
            projectService.save(p3);

            // ========================
            // LINK PROJECTS → USERS
            // ========================
            user1.setProjectIds(List.of(p1.getId(), p2.getId()));
            user2.setProjectIds(List.of(p3.getId()));
            admin.setProjectIds(List.of()); // admin não precisa

            userRepository.saveAll(List.of(user1, user2, admin));
        };
    }
}
