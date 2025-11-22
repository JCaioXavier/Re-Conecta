package br.com.reconecta.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // IMPORTANTE: Desativar CSRF temporariamente para garantir que o login funcione
                // Em produção, você deve habilitar e configurar o token no HTML
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(
                                "/login",
                                "/tipo-cadastro",
                                "/cadastro-pf",
                                "/cadastro-pj",
                                "/cadastro-pj-empresa",
                                "/cadastro-pj-representante",
                                "/finalizar-cadastro",
                                "/css/**",
                                "/images/**",
                                "/webjars/**",
                                "/error"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login") // URL para exibir a página (GET)
                        .loginProcessingUrl("/login") // URL para enviar o formulário (POST) - É a mesma!
                        .defaultSuccessUrl("/tipo-cadastro", true) // Sucesso -> Vai para escolha
                        .failureUrl("/login?error=true") // Erro -> Volta para login com aviso
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                );

        return http.build();
    }
}