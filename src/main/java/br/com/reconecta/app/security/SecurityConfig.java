package br.com.reconecta.app.config;

import br.com.reconecta.app.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Injetamos o nosso serviço que busca o utilizador no banco
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desabilita CSRF para facilitar o desenvolvimento
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests((requests) -> requests
                        // --- ROTAS PÚBLICAS (Qualquer um pode acessar) ---
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

                        // --- ROTAS PRIVADAS (Exige Login) ---
                        // Tudo o que não está na lista acima (ex: /home, /perfil) cai aqui
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .usernameParameter("username")
                        .passwordParameter("senha")
                        .loginPage("/login")               // Nossa página HTML de login
                        .loginProcessingUrl("/login")      // URL para onde o form envia o POST
                        .defaultSuccessUrl("/home", true)  // Se der certo, vai para a Home
                        .failureUrl("/login?error=true")   // Se der errado, volta com erro
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                );

        return http.build();
    }

    // --- CONFIGURAÇÃO DE AUTENTICAÇÃO ---

    // Define o algoritmo de criptografia da senha (o mesmo usado no cadastro)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Conecta o nosso Service e o Encoder ao sistema de autenticação do Spring
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // Expõe o gerenciador de autenticação (necessário para o Spring Boot)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}