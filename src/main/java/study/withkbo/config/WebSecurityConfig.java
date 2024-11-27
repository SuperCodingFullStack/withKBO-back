//package study.withkbo.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//
//@Configuration
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().and()
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/api/user/login").permitAll()
//                .anyRequest().authenticated();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedOriginPattern("*"); // 모든 Origin 허용
//        configuration.addAllowedMethod("*"); // 모든 메서드 허용
//        configuration.addAllowedHeader("*"); // 모든 헤더 허용
//        configuration.addExposedHeader("Authorization"); // 응답 헤더 중 'Authorization'을 노출
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//}
