package study.withkbo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 클라이언트에서 쿠키, 인증 정보를 허용
        config.addAllowedOriginPattern("*"); // 모든 도메인 허용 (필요시 특정 도메인만 허용)
        config.addAllowedHeader("*"); // 모든 헤더 허용
        config.addExposedHeader("Authorization"); // 클라이언트에서 사용할 응답 헤더 노출
        config.addAllowedMethod("*"); // 모든 HTTP 메서드 허용


        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
