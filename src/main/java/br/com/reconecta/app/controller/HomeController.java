package br.com.reconecta.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "home"; // Retorna o arquivo home.html
    }

    @GetMapping("/perfil")
    public String perfil() {
        return "perfil"; // Retorna templates/perfil.html
    }
}