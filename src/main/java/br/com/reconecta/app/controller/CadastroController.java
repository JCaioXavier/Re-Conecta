package br.com.reconecta.app.controller;

import br.com.reconecta.app.dto.CadastroPfDTO;
import br.com.reconecta.app.dto.CadastroPjDTO;
import br.com.reconecta.app.service.CadastroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("cadastroPjDTO") // MANTÉM OS DADOS VIVOS ENTRE AS TELAS
public class CadastroController {

    @Autowired
    private CadastroService cadastroService;

    // Inicializa o DTO (o "carrinho de compras" dos dados) quando o usuário entra no fluxo
    @ModelAttribute("cadastroPjDTO")
    public CadastroPjDTO cadastroPjDTO() {
        return new CadastroPjDTO();
    }

    // --- TELA DE ESCOLHA ---
    @GetMapping("/tipo-cadastro")
    public String tipoCadastro() {
        return "tipo-cadastro";
    }

    // --- FLUXO PESSOA FÍSICA (PF) ---
    @GetMapping("/cadastro-pf")
    public String cadastroPf() {
        return "cadastro-pf";
    }

    @PostMapping("/cadastro-pf")
    public String salvarPf(@ModelAttribute CadastroPfDTO dto) {
        try {
            cadastroService.salvarPessoaFisica(dto);
            return "redirect:/login?cadastrado=true";
        } catch (Exception e) {
            return "redirect:/cadastro-pf?erro=true";
        }
    }

    // --- FLUXO PESSOA JURÍDICA (PJ) ---

    // PASSO 1: Exibe formulário da Empresa
    @GetMapping("/cadastro-pj-empresa")
    public String cadastroPjEmpresa(@ModelAttribute("cadastroPjDTO") CadastroPjDTO dto) {
        return "cadastro-pj-empresa";
    }

    // PASSO 1 (POST): Recebe dados da empresa -> Guarda na Sessão -> Vai para Representante
    @PostMapping("/cadastro-pj-empresa")
    public String processarCadastroPj(@ModelAttribute("cadastroPjDTO") CadastroPjDTO dto) {
        // Redireciona para a próxima etapa
        return "redirect:/cadastro-pj-representante";
    }

    // PASSO 2: Exibe formulário do Representante
    @GetMapping("/cadastro-pj-representante")
    public String cadastroPjRepresentante(@ModelAttribute("cadastroPjDTO") CadastroPjDTO dto) {
        return "cadastro-pj-representante";
    }

    // PASSO FINAL: Recebe dados do representante -> Salva TUDO no Banco -> Finaliza
    @PostMapping("/finalizar-cadastro")
    public String finalizarCadastro(@ModelAttribute("cadastroPjDTO") CadastroPjDTO dto, SessionStatus status) {
        try {
            cadastroService.salvarPessoaJuridica(dto);
            status.setComplete(); // Limpa a memória
            return "redirect:/login?cadastrado=true";
        } catch (Exception e) {
            return "redirect:/cadastro-pj-empresa?erro=true";
        }
    }
}