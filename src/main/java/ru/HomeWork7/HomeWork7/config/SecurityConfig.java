package ru.HomeWork7.HomeWork7.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig - класс, реализующий методы аутентификации пользователей и определяющий алгоритм работы
 * фильтра.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /**
     * filterChain - метод определяющий работу фильтра в зависимости от типа пользователей:
     * user - получает доступ к странице с открытыми данными,
     * admin - дополнительно имеет возможность выходить на страницу с конфиденциальной информацией.
     * @param http - запрос пользователя.
     * @return - страница сайта, соответствующая типу пользователя.
     * @throws Exception
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/css/**", "/favicon.ico", "/", "/index").permitAll()
                        .requestMatchers("/public-data").hasAnyRole("USER")
                        .requestMatchers("/private-data").hasAnyRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .defaultSuccessUrl("/")
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/"));
        return http.build();
    }

    /**
     * PasswordEncoder - метод шифрования паролей пользователей.
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * UserDetailsManager - метод предоставления пользователям доступа к информации в соответствии с их ролью в системе.
     * @return - информация о пользователе и его правах в ИС.
     */
    @Bean
    UserDetailsManager inMemoryUserDetailsManager() {
        var user1 = User.withUsername("user").password("{noop}123").roles("USER").build();
        var user2 = User.withUsername("admin").password("{noop}1234").roles("USER", "ADMIN").build();
        return new InMemoryUserDetailsManager(user1, user2);
    }
}
