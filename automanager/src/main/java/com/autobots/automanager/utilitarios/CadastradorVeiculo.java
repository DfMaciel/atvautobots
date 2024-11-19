package com.autobots.automanager.utilitarios;

import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioVeiculo;
import com.autobots.automanager.repositorios.RepositorioVenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CadastradorVeiculo {

    @Autowired
    private CadastradorVenda cadastradorVenda;

    @Autowired
    private RepositorioVeiculo repositorioVeiculo;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private RepositorioVenda repositorioVenda;

    public Veiculo cadastrarVeiculo (Veiculo veiculo) {
        Veiculo veiculoCadastrado = new Veiculo();
        veiculoCadastrado.setModelo(veiculo.getModelo());
        veiculoCadastrado.setPlaca(veiculo.getPlaca());
        veiculoCadastrado.setTipo(veiculo.getTipo());

        repositorioVeiculo.save(veiculoCadastrado);

        if (veiculo.getProprietario() != null) {
            Usuario proprietario = new Usuario();
            proprietario.setNome(veiculo.getProprietario().getNome());
            proprietario.getVeiculos().add(veiculoCadastrado);
            veiculoCadastrado.setProprietario(proprietario);
        }
        if (veiculo.getVendas() != null) {
            for (Venda venda : veiculo.getVendas()) {
                Venda vendaAtual = cadastradorVenda.cadastradorVenda(venda);
                vendaAtual.setVeiculo(veiculoCadastrado);
                veiculoCadastrado.getVendas().add(vendaAtual);
            }
        }

        repositorioVeiculo.save(veiculoCadastrado);

        return veiculoCadastrado;
    }
}
