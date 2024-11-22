package study.withkbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
@EnableMethodSecurity(prePostEnabled = true)
public class WithKboApplication {

    public static void main(String[] args) {
        SpringApplication.run(WithKboApplication.class, args);
    }

}
