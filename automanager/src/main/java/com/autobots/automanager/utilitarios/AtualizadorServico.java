package com.autobots.automanager.utilitarios;

import com.autobots.automanager.entitades.Servico;
import org.springframework.stereotype.Component;

@Component
public class AtualizadorServico {

    public Servico atualizarServico(Servico servico, Servico servicoAtualizado) {
        servico.setNome(servicoAtualizado.getNome());
        servico.setValor(servicoAtualizado.getValor());
        servico.setDescricao(servicoAtualizado.getDescricao());

        return servico;
    }
}
