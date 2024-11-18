package com.autobots.automanager.utilitarios;

import com.autobots.automanager.entitades.Email;
import org.springframework.stereotype.Component;

@Component
public class AtualizadorEmail {
    public Email atualizarEmail(Email email, Email novoEmail) {
        email.setEndereco(novoEmail.getEndereco());
        return email;
    }
}
