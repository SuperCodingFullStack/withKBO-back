package study.withkbo.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // HTTP 보안 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // CSRF 보호 비활성화 (API 사용 시 필요)
                .authorizeRequests()
                .requestMatchers("/api/signup", "/api/login","/api/userList").permitAll()  // 인증 없이 접근 가능
                .anyRequest().authenticated()  // 나머지 요청은 인증 필요
                .and();

        return http.build();
    }

}
