package com.autobots.automanager.utilitarios;

import com.autobots.automanager.controllers.MercadoriaDto;
import com.autobots.automanager.entitades.Mercadoria;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CadastradorMercadoria {
    public Mercadoria cadastrarMercadoria (MercadoriaDto mercadoria) {
        Mercadoria mercadoriaCadastrada = new Mercadoria();
        mercadoriaCadastrada.setValidade(new Date());
        mercadoriaCadastrada.setFabricao(new Date());
        mercadoriaCadastrada.setCadastro(new Date());
        mercadoriaCadastrada.setNome(mercadoria.nome());
        mercadoriaCadastrada.setQuantidade(mercadoria.quantidade());
        mercadoriaCadastrada.setValor(mercadoria.valor());
        if (mercadoria.descricao().isPresent()) {
            mercadoriaCadastrada.setDescricao(mercadoria.descricao().get());
        }

        return mercadoriaCadastrada;
    }

    public Mercadoria cadastrarMercadoria (Mercadoria mercadoria) {
        Mercadoria mercadoriaCadastrada = new Mercadoria();
        mercadoriaCadastrada.setValidade(new Date());
        mercadoriaCadastrada.setFabricao(new Date());
        mercadoriaCadastrada.setCadastro(new Date());
        mercadoriaCadastrada.setNome(mercadoria.getNome());
        mercadoriaCadastrada.setQuantidade(mercadoria.getQuantidade());
        mercadoriaCadastrada.setValor(mercadoria.getValor());
        if (mercadoria.getDescricao() != null) {
            mercadoriaCadastrada.setDescricao(mercadoria.getDescricao());
        }

        return mercadoriaCadastrada;
    }
}
