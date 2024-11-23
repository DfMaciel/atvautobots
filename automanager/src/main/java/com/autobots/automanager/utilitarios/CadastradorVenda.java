package com.autobots.automanager.utilitarios;

import com.autobots.automanager.controllers.MercadoriaDto;
import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.entitades.Servico;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.repositorios.RepositorioMercadoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class CadastradorVenda {

    @Autowired
    private CadastradorMercadoria cadastradorMercadoria;

    @Autowired
    private
    RepositorioMercadoria repositorioMercadoria;

    @Autowired
    private CadastradorUsuario cadastradorUsuario;

    public Venda cadastrarVenda(Venda venda) {
        Venda vendaCadastrada = new Venda();
        vendaCadastrada.setCadastro(new Date());
        vendaCadastrada.setIdentificacao(venda.getIdentificacao());
        if (venda.getCliente() != null) {
            Usuario cliente = cadastradorUsuario.cadastrarUsuario(venda.getCliente());
            vendaCadastrada.setCliente(cliente);
        }
        if (venda.getFuncionario() != null) {
            Usuario funcionario = cadastradorUsuario.cadastrarUsuario(venda.getFuncionario());
            vendaCadastrada.setFuncionario(funcionario);
        }
        if (venda.getMercadorias() != null) {
            for (Mercadoria mercadoria : venda.getMercadorias()) {
                MercadoriaDto mercadoriaDto = new MercadoriaDto(
                        mercadoria.getNome(),
                        mercadoria.getQuantidade(),
                        mercadoria.getValor(),
                        Optional.ofNullable(mercadoria.getDescricao())
                );
                Mercadoria mercadoriaAtual = cadastradorMercadoria.cadastrarMercadoria(mercadoriaDto);
                repositorioMercadoria.save(mercadoriaAtual);
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
