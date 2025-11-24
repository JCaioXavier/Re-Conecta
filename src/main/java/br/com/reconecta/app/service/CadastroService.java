package br.com.reconecta.app.service;

import br.com.reconecta.app.dto.CadastroPfDTO;
import br.com.reconecta.app.dto.CadastroPjDTO;
import br.com.reconecta.app.model.CadastroPontoColeta;
import br.com.reconecta.app.model.ContatosParceiro;
import br.com.reconecta.app.model.UsuarioPessoaFisica;
import br.com.reconecta.app.repository.CadastroPontoColetaRepository;
import br.com.reconecta.app.repository.ContatosParceiroRepository;
import br.com.reconecta.app.repository.UsuarioPessoaFisicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastroService {

    @Autowired
    private UsuarioPessoaFisicaRepository usuarioRepository;
    @Autowired
    private CadastroPontoColetaRepository pontoColetaRepository;
    @Autowired
    private ContatosParceiroRepository contatoRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // --- MÉTODO AUXILIAR DE VERIFICAÇÃO GLOBAL ---
    // Evita duplicidade de CPF/Email entre PF e PJ
    private void verificarDuplicidade(String cpf, String email, String cnpj) {
        // 1. Checa na tabela de PF
        if (cpf != null && usuarioRepository.existsByCpf(cpf)) {
            throw new IllegalArgumentException("Este CPF já está cadastrado como Pessoa Física.");
        }
        if (email != null && usuarioRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Este e-mail já está cadastrado como Pessoa Física.");
        }

        // 2. Checa na tabela de Representantes (PJ)
        if (cpf != null && contatoRepository.existsByCpf(cpf)) {
            throw new IllegalArgumentException("Este CPF já está cadastrado como Representante de Empresa.");
        }
        if (email != null && contatoRepository.existsByEmailInterno(email)) {
            throw new IllegalArgumentException("Este e-mail já está cadastrado como Representante.");
        }

        // 3. Checa na tabela de Empresas (Se tiver CNPJ)
        if (cnpj != null && pontoColetaRepository.existsByCnpj(cnpj)) {
            throw new IllegalArgumentException("Este CNPJ já está cadastrado.");
        }
        if (email != null && pontoColetaRepository.existsByEmailPublico(email)) {
            // Nota: Muitas empresas usam email geral, então isso pode ser flexível dependendo da regra
            // throw new IllegalArgumentException("Este e-mail já está sendo usado como contato público.");
        }
    }

    // --- SALVAR PESSOA FÍSICA ---
    @Transactional
    public void salvarPessoaFisica(CadastroPfDTO dto) {
        System.out.println("--- Service: Iniciando salvamento PF ---");

        // Validação Cruzada
        verificarDuplicidade(dto.getCpf(), dto.getEmail(), null);

        UsuarioPessoaFisica usuario = new UsuarioPessoaFisica();
        usuario.setNomeCompleto(dto.getNomeCompleto());
        usuario.setCpf(dto.getCpf());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefone(dto.getTelefone());

        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        usuarioRepository.save(usuario);
        System.out.println("--- Service: PF Salva com ID: " + usuario.getId() + " ---");
    }

    // --- SALVAR PESSOA JURÍDICA (Empresa + Representante) ---
    @Transactional
    public void salvarPessoaJuridica(CadastroPjDTO dto) {
        System.out.println("--- Service: INICIANDO GRAVAÇÃO PJ ---");

        // Validação Cruzada (Verifica CNPJ da empresa e CPF/Email do Representante)
        verificarDuplicidade(dto.getCpf(), dto.getEmail(), dto.getCnpj());

        // 1. Criar e Preencher a Empresa (Ponto de Coleta)
        CadastroPontoColeta ponto = new CadastroPontoColeta();
        ponto.setCnpj(dto.getCnpj());
        ponto.setRazaoSocial(dto.getRazaoSocial());
        ponto.setNomeFantasia(dto.getNomeFantasia());

        // Endereço
        ponto.setCep(dto.getCep());
        ponto.setLogradouro(dto.getLogradouro() != null ? dto.getLogradouro() : dto.getEndereco());
        ponto.setNumero(dto.getNumero());
        ponto.setBairro(dto.getBairro());
        ponto.setMunicipio(dto.getMunicipio());
        ponto.setEstado(dto.getEstado());

        // Horários
        ponto.setHorarioAbertura(dto.getHoraInicio());
        ponto.setHorarioFechamento(dto.getHoraFim());

        // Formatar Dias de Funcionamento (Ex: "SEG a SEX")
        String dias = (dto.getDiaInicio() != null ? dto.getDiaInicio() : "") + " a " + (dto.getDiaFim() != null ? dto.getDiaFim() : "");
        ponto.setDiasFuncionamento(dias);

        // Formatar Itens Aceitos (Lista -> String separada por vírgula)
        if (dto.getItens() != null && !dto.getItens().isEmpty()) {
            String itensStr = String.join(", ", dto.getItens());
            ponto.setItensAceitos(itensStr);
        } else {
            ponto.setItensAceitos("Nenhum selecionado");
        }

        // Contatos Públicos (Usando dados do representante como padrão)
        ponto.setEmailPublico(dto.getEmail());
        ponto.setTelefonePublico(dto.getTelefone());

        // Salvar Empresa Primeiro (Para gerar o ID)
        CadastroPontoColeta pontoSalvo = pontoColetaRepository.save(ponto);
        System.out.println("--- Service: Empresa salva com ID: " + pontoSalvo.getId() + " ---");

        // 2. Criar e Salvar o Representante
        ContatosParceiro contato = new ContatosParceiro();
        contato.setPontoColeta(pontoSalvo); // Liga o representante à empresa salva acima
        contato.setNome(dto.getNomeCompleto());
        contato.setCpf(dto.getCpf());
        contato.setCargo(dto.getCargo());
        contato.setEmailInterno(dto.getEmail());
        contato.setTelefoneInterno(dto.getTelefone());

        contatoRepository.save(contato);
        System.out.println("--- Service: Representante salvo com sucesso! ---");
    }
}