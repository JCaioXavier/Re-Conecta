package br.com.reconecta.app.controller;

import br.com.reconecta.app.dto.CadastroPfDTO;
import br.com.reconecta.app.service.CadastroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CadastroController {

    @Autowired
    private CadastroService cadastroService;

    // Tela de decisão (Escolher entre PF e PJ)
    @GetMapping("/tipo-cadastro")
    public String tipoCadastro() {
        return "tipo-cadastro";
    }

    // ==========================================
    //           FLUXO PESSOA FÍSICA
    // ==========================================

    // 1. Exibe o formulário de cadastro PF
    @GetMapping("/cadastro-pf")
    public String cadastroPf() {
        return "cadastro-pf";
    }

    // 2. Processa os dados do formulário PF (Salva no Banco)
    @PostMapping("/cadastro-pf")
    public String salvarPf(@ModelAttribute CadastroPfDTO dto) {
        // Validação básica de senha
        if (dto.getSenha() != null && dto.getConfirmarSenha() != null) {
            if (!dto.getSenha().equals(dto.getConfirmarSenha())) {
                return "redirect:/cadastro-pf?erroSenha=true";
            }
        }

        try {
            cadastroService.salvarPessoaFisica(dto);
            return "redirect:/login?cadastrado=true";

        } catch (DataIntegrityViolationException e) {
            // Captura erro de duplicidade (CPF ou Email já existem)
            // O ideal seria verificar a mensagem da exceção para saber qual campo duplicou
            // mas para simplificar, vamos assumir erro de CPF/Email
            return "redirect:/cadastro-pf?erroCpfExistente=true";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/cadastro-pf?erro=true";
        }
    }

    // ==========================================
    //           FLUXO PESSOA JURÍDICA
    // ==========================================

    // Passo 1: Exibe formulário de dados da Empresa
    @GetMapping("/cadastro-pj-empresa")
    public String cadastroPjEmpresa() {
        return "cadastro-pj-empresa";
    }

    // Processa Passo 1 -> Redireciona para Passo 2
    @PostMapping("/cadastro-pj-empresa")
    public String processarCadastroPj() {
        System.out.println("--- Dados da empresa recebidos. Indo para representante... ---");
        // (Aqui você poderia salvar os dados da empresa na sessão se fosse um fluxo real complexo)
        return "redirect:/cadastro-pj-representante";
    }

    // Passo 2: Exibe formulário do Representante
    @GetMapping("/cadastro-pj-representante")
    public String cadastroPjRepresentante() {
        return "cadastro-pj-representante";
    }

    // Passo Final: Recebe dados do representante e finaliza
    @PostMapping("/finalizar-cadastro")
    public String finalizarCadastro() {
        System.out.println("--- Cadastro PJ finalizado com sucesso! ---");
        // (Aqui você chamaria o service para salvar Empresa + Representante)
        return "redirect:/login?cadastrado=true";
    }

    // Rota de legado para compatibilidade (caso algum link antigo ainda aponte para cá)
    @GetMapping("/cadastro-pj")
    public String cadastroPjAntigo() {
        return "redirect:/cadastro-pj-empresa";
    }
}