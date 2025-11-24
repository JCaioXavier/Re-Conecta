package br.com.reconecta.app.repository;
import br.com.reconecta.app.model.ContatosParceiro;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ContatosParceiroRepository extends JpaRepository<ContatosParceiro, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByEmailInterno(String email);
}