package br.com.reconecta.app.repository;

import br.com.reconecta.app.model.UsuarioPessoaFisica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioPessoaFisicaRepository extends JpaRepository<UsuarioPessoaFisica, Long> {
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
}