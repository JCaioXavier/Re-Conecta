package br.com.reconecta.app.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "contatos_parceiro")
public class ContatosParceiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ponto_id")
    private CadastroPontoColeta pontoColeta;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 14)
    private String cpf;

    @Column(length = 100)
    private String cargo;

    @Column(name = "email_interno", unique = true, nullable = false, length = 120)
    private String emailInterno;

    @Column(name = "telefone_interno", length = 20)
    private String telefoneInterno;

    @Column(length = 50)
    private String status = "Ativo";

    // --- Getters e Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public void setPontoColeta(CadastroPontoColeta pontoColeta) { this.pontoColeta = pontoColeta; }
    public void setNome(String nome) { this.nome = nome; }

    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public void setEmailInterno(String emailInterno) { this.emailInterno = emailInterno; }
    public void setTelefoneInterno(String telefoneInterno) { this.telefoneInterno = telefoneInterno; }
}