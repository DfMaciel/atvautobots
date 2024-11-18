package com.autobots.automanager.utilitarios;

import com.autobots.automanager.entitades.Mercadoria;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CadastradorMercadoria {
    public Mercadoria cadastrarMercadoria (Mercadoria mercadoria) {
        Mercadoria mercadoriaCadastrada = new Mercadoria();
        mercadoriaCadastrada.setValidade(mercadoria.getValidade());
        mercadoriaCadastrada.setFabricao(mercadoria.getFabricao());
        mercadoria.setCadastro(new Date());
        mercadoriaCadastrada.setNome(mercadoria.getNome());
        mercadoriaCadastrada.setQuantidade(mercadoria.getQuantidade());
        mercadoriaCadastrada.setValor(mercadoria.getValor());
        if (mercadoria.getDescricao() != null) {
            mercadoriaCadastrada.setDescricao(mercadoria.getDescricao());
        }

        return mercadoria;
    }
}
