package br.com.reconecta.app.model;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "cadastro_ponto_coleta")
public class CadastroPontoColeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ... (outros campos: cnpj, razaoSocial, endereco...) ...
    @Column(nullable = false, length = 18)
    private String cnpj;
    @Column(name = "razao_social", length = 150)
    private String razaoSocial;
    @Column(name = "nome_fantasia", length = 150)
    private String nomeFantasia;
    @Column(length = 9)
    private String cep;
    @Column(length = 150)
    private String logradouro;
    @Column(length = 10)
    private String numero;
    @Column(length = 150)
    private String bairro;
    @Column(length = 100)
    private String municipio;
    @Column(length = 100)
    private String estado;

    @Column(name = "horario_abertura")
    private LocalTime horarioAbertura;

    @Column(name = "horario_fechamento")
    private LocalTime horarioFechamento;

    // --- NOVOS CAMPOS ---
    @Column(name = "dias_funcionamento")
    private String diasFuncionamento; // Ex: "SEG a SEX"

    @Column(name = "itens_aceitos")
    private String itensAceitos; // Ex: "celulares,pilhas,baterias"

    // ... (campos email, telefone, status) ...
    @Column(name = "email_publico", length = 120)
    private String emailPublico;
    @Column(name = "telefone_publico", length = 20)
    private String telefonePublico;
    @Column(name = "status_ponto")
    private String statusPonto = "Pendente";

    // --- GETTERS E SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    // ... (Getters e Setters antigos) ...
    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }
    public String getNomeFantasia() { return nomeFantasia; }
    public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }
    public String getMunicipio() { return municipio; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalTime getHorarioAbertura() { return horarioAbertura; }
    public void setHorarioAbertura(LocalTime horarioAbertura) { this.horarioAbertura = horarioAbertura; }
    public LocalTime getHorarioFechamento() { return horarioFechamento; }
    public void setHorarioFechamento(LocalTime horarioFechamento) { this.horarioFechamento = horarioFechamento; }
    public String getEmailPublico() { return emailPublico; }
    public void setEmailPublico(String emailPublico) { this.emailPublico = emailPublico; }
    public String getTelefonePublico() { return telefonePublico; }
    public void setTelefonePublico(String telefonePublico) { this.telefonePublico = telefonePublico; }
    public String getStatusPonto() { return statusPonto; }
    public void setStatusPonto(String statusPonto) { this.statusPonto = statusPonto; }

    // Novos Getters/Setters
    public String getDiasFuncionamento() { return diasFuncionamento; }
    public void setDiasFuncionamento(String diasFuncionamento) { this.diasFuncionamento = diasFuncionamento; }

    public String getItensAceitos() { return itensAceitos; }
    public void setItensAceitos(String itensAceitos) { this.itensAceitos = itensAceitos; }
}