package br.com.reconecta.app.security;

import br.com.reconecta.app.model.UsuarioPessoaFisica;
import br.com.reconecta.app.repository.UsuarioPessoaFisicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioPessoaFisicaRepository usuarioRepository;

    // O Spring chama esse método automaticamente quando alguém tenta logar
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 1. Busca o usuário no banco pelo email (que é o username da tela de login)
        // Como o repositório retorna null ou lança erro se não achar, vamos tratar:
        UsuarioPessoaFisica usuario = usuarioRepository.findAll().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));

        // 2. Retorna um objeto User do Spring com os dados do nosso banco
        return new User(
                usuario.getEmail(),
                usuario.getSenha(), // A senha criptografada que está no banco
                Collections.emptyList() // Permissões (Roles) - deixamos vazio por enquanto
        );
    }
}