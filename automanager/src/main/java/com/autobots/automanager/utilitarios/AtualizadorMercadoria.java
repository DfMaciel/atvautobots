package com.autobots.automanager.utilitarios;

import com.autobots.automanager.entitades.Mercadoria;
import org.springframework.stereotype.Component;

@Component
public class AtualizadorMercadoria {

    public Mercadoria atualizarMercadoria(Mercadoria mercadoria, Mercadoria mercadoriaAtualizada) {
        mercadoria.setNome(mercadoriaAtualizada.getNome());
        mercadoria.setQuantidade(mercadoriaAtualizada.getQuantidade());
        mercadoria.setValor(mercadoriaAtualizada.getValor());
        mercadoria.setDescricao(mercadoriaAtualizada.getDescricao());

        return mercadoria;
    }
}
