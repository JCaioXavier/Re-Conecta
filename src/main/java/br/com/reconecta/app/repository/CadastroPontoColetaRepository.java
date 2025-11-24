package br.com.reconecta.app.repository;
import br.com.reconecta.app.model.CadastroPontoColeta;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CadastroPontoColetaRepository extends JpaRepository<CadastroPontoColeta, Long> {
    boolean existsByCnpj(String cpf);
    boolean existsByEmailPublico(String email);
}