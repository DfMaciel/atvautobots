package com.autobots.automanager.utilitarios;

import com.autobots.automanager.entitades.Telefone;
import org.springframework.stereotype.Component;

@Component
public class AtualizadorTelefone {
    public Telefone atualizarTelefone(Telefone telefone, Telefone novoTelefone) {
        telefone.setDdd(novoTelefone.getDdd());
        telefone.setNumero(novoTelefone.getNumero());
        return telefone;
    }
}
