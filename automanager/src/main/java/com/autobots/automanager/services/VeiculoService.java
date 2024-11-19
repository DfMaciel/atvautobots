package com.autobots.automanager.services;

import com.autobots.automanager.adicionadores.AdicionadorLinkVeiculo;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioVeiculo;
import com.autobots.automanager.repositorios.RepositorioVenda;
import com.autobots.automanager.utilitarios.CadastradorVeiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeiculoService {

    @Autowired
    private RepositorioVeiculo repositorioVeiculo;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private RepositorioVenda repositorioVenda;

    @Autowired
    private CadastradorVeiculo cadastradorVeiculo;

    @Autowired
    private AdicionadorLinkVeiculo adicionadorLinkVeiculo;

    public List<Veiculo> listarVeiculos() {
        List<Veiculo> veiculos = repositorioVeiculo.findAll();
        adicionadorLinkVeiculo.adicionarLink(veiculos);
        return veiculos;
    }

    public Veiculo visualizarVeiculo(Long id) {
        Veiculo veiculo = repositorioVeiculo.findById(id).orElse(null);
        if (veiculo != null) {
            adicionadorLinkVeiculo.adicionarLink(veiculo);
        }
        return veiculo;
    }

    public ResponseEntity<?> cadastrarVeiculo(Veiculo veiculo) {
        Veiculo veiculoCadastrado = cadastradorVeiculo.cadastrarVeiculo(veiculo);
        repositorioVeiculo.save(veiculoCadastrado);
        return ResponseEntity.created(null).build();
    }

    public ResponseEntity<?> atualizarVeiculo(Long veiculoId, Veiculo veiculo) {
        Veiculo veiculoAtualizado = repositorioVeiculo.findById(veiculoId).orElse(null);
        if (veiculoAtualizado != null) {
            if (veiculo.getModelo() != null) {
                veiculoAtualizado.setModelo(veiculo.getModelo());
            }
            if (veiculo.getPlaca() != null) {
                veiculoAtualizado.setPlaca(veiculo.getPlaca());
            }
            if (veiculo.getTipo() != null) {
                veiculoAtualizado.setTipo(veiculo.getTipo());
            }
            if (veiculo.getProprietario() != null) {
                veiculoAtualizado.setProprietario(veiculo.getProprietario());
            }
            if (veiculo.getVendas() != null) {
                veiculoAtualizado.setVendas(veiculo.getVendas());
            }
            repositorioVeiculo.save(veiculoAtualizado);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> vincularVeiculoUsuario(Long veiculoId, Long usuarioId) {
        Veiculo veiculo = repositorioVeiculo.findById(veiculoId).orElse(null);
        if (veiculo != null) {
            Usuario usuario = repositorioUsuario.findById(usuarioId).orElse(null);
            if (usuario == null) {
                return ResponseEntity.notFound().build();
            }
            veiculo.setProprietario(usuario);
            repositorioVeiculo.save(veiculo);
            usuario.getVeiculos().add(veiculo);
            repositorioUsuario.save(usuario);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> excluirVeiculo(Long id) {
        Veiculo veiculo = repositorioVeiculo.findById(id).orElse(null);
        if (veiculo != null) {
            veiculo.getProprietario().getVeiculos().remove(veiculo);
            repositorioUsuario.save(veiculo.getProprietario());

            for (Venda venda : veiculo.getVendas()) {
                venda.setVeiculo(null);
                repositorioVenda.save(venda);
            }

            repositorioVeiculo.delete(veiculo);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
