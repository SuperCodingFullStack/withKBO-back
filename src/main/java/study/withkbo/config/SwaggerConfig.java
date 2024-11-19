package study.withkbo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "API 명세서",
                description = "슈퍼 코딩 3차 팀 프로젝트 API 명세서",
                version = "v1")
)

@Configuration
public class SwaggerConfig {
}