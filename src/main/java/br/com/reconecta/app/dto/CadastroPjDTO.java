package br.com.reconecta.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalTime;
import java.util.List;

// Se não tiver Lombok, crie os Getters e Setters manualmente
public class CadastroPjDTO {

    // --- DADOS EMPRESA (Tela 1) ---

    private String cnpj;

    private String razaoSocial;
    private String nomeFantasia;

    // Endereço
    private String cep;
    private String endereco; // Logradouro
    private String logradouro; // Redundante, mas ajustável
    private String numero;
    private String bairro;
    private String municipio;
    private String estado;
    private String complemento;

    // Horário
    private String diaInicio;
    private String diaFim;
    private LocalTime horaInicio;
    private LocalTime horaFim;

    // Itens (Lista de strings selecionadas)
    private List<String> itens;

    // --- DADOS REPRESENTANTE (Tela 2) ---
    private String nomeCompleto; // Do representante
    private String cpf;          // Do representante
    private String email;        // Email interno
    private String cargo;
    private String telefone;     // Telefone interno

    // --- GETTERS E SETTERS ---
    // (Gere todos eles aqui, clique com botão direito > Generate > Getter and Setter)

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }

    public String getNomeFantasia() { return nomeFantasia; }
    public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

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

    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }

    public String getDiaInicio() { return diaInicio; }
    public void setDiaInicio(String diaInicio) { this.diaInicio = diaInicio; }

    public String getDiaFim() { return diaFim; }
    public void setDiaFim(String diaFim) { this.diaFim = diaFim; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFim() { return horaFim; }
    public void setHoraFim(LocalTime horaFim) { this.horaFim = horaFim; }

    public List<String> getItens() { return itens; }
    public void setItens(List<String> itens) { this.itens = itens; }

    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}