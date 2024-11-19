package com.autobots.automanager.utilitarios;

import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.entitades.Servico;
import com.autobots.automanager.entitades.Venda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CadastradorVenda {

    @Autowired
    private CadastradorMercadoria cadastradorMercadoria;

    public Venda cadastradorVenda (Venda venda) {
        Venda vendaCadastrada = new Venda();
        vendaCadastrada.setCadastro(new Date());
        vendaCadastrada.setIdentificacao(venda.getIdentificacao());
        if (venda.getCliente() != null) {
            vendaCadastrada.setCliente(venda.getCliente());
        }
        if (venda.getFuncionario() != null) {
            vendaCadastrada.setFuncionario(venda.getFuncionario());
        }
        if (venda.getMercadorias() != null) {
            for (Mercadoria mercadoria : venda.getMercadorias()) {
                Mercadoria mercadoriaAtual = cadastradorMercadoria.cadastrarMercadoria(mercadoria);
                vendaCadastrada.getMercadorias().add(mercadoriaAtual);
            }
        }
        if (venda.getServicos() != null) {
            for (Servico servico : venda.getServicos()) {
                vendaCadastrada.getServicos().add(servico);
            }
        }
        if (venda.getVeiculo() != null) {
            vendaCadastrada.setVeiculo(venda.getVeiculo());
        }
        return vendaCadastrada;
    }
}
