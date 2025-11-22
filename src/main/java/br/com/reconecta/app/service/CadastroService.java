package br.com.reconecta.app.service;

import br.com.reconecta.app.dto.CadastroPfDTO;
import br.com.reconecta.app.model.EnderecoPessoaFisica;
import br.com.reconecta.app.model.UsuarioPessoaFisica;
import br.com.reconecta.app.repository.EnderecoPessoaFisicaRepository;
import br.com.reconecta.app.repository.UsuarioPessoaFisicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroService {

    @Autowired
    private UsuarioPessoaFisicaRepository usuarioRepository;

    @Autowired
    private EnderecoPessoaFisicaRepository enderecoRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public void salvarPessoaFisica(CadastroPfDTO dto) {
        // 1. Salvar Usuário
        UsuarioPessoaFisica usuario = new UsuarioPessoaFisica();
        usuario.setNomeCompleto(dto.getNomeCompleto());
        usuario.setCpf(dto.getCpf());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefone(dto.getTelefone());

        // Criptografar senha antes de salvar
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));

        UsuarioPessoaFisica usuarioSalvo = usuarioRepository.save(usuario);

        // 2. Salvar Endereço (Mesmo que venha vazio do form atual, cria o registro)
        EnderecoPessoaFisica endereco = new EnderecoPessoaFisica();
        endereco.setUsuario(usuarioSalvo);
        endereco.setCep(dto.getCep()); // Pode ser null se o form não tiver
        endereco.setLogradouro(dto.getLogradouro());
        // ... outros campos

        enderecoRepository.save(endereco);
    }
}