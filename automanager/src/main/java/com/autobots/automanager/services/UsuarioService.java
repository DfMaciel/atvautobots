package com.autobots.automanager.services;

import com.autobots.automanager.adicionadores.AdicionadorLinkUsuario;
import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private AdicionadorLinkUsuario adicionadorLinkUsuario;

    @Autowired
    private RepositorioEmpresa repositorioEmpresa;

    @Autowired
    private RepositorioVenda repositorioVenda;

    @Autowired
    private RepositorioVeiculo repositorioVeiculo;

    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = repositorioUsuario.findAll();
        adicionadorLinkUsuario.adicionarLink(usuarios);
        return usuarios;
    }

    public Usuario visualizarUsuario(Long id) {
        Usuario usuario = repositorioUsuario.findById(id).orElse(null);
        if (usuario != null) {
            adicionadorLinkUsuario.adicionarLink(usuario);
        }
        return usuario;
    }

    public Usuario cadastrarUsuario(Usuario usuario) {
        Usuario usuarioCadastrado = repositorioUsuario.save(usuario);
        return usuarioCadastrado;
    }

    public void cadastrarUsuario(List<Usuario> usuarios) {
        for (Usuario usuario : usuarios) {
            Usuario usuarioCadastrado = cadastrarUsuario(usuario);
        }
    }

    public void cadastrarUsuarioEmpresa(Usuario usuario, Long idEmpresa) {
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        Usuario usuarioCadastrado = repositorioUsuario.save(usuario);
        empresa.getUsuarios().add(usuarioCadastrado);
        repositorioEmpresa.save(empresa);
    }

    public void vincularUsuarioEmpresa(Long idUsuario, Long idEmpresa) {
        Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        empresa.getUsuarios().add(usuario);
        repositorioEmpresa.save(empresa);
    }

    public ResponseEntity<?> atualizarUsuario(Long id, Usuario usuario) {
        Usuario usuarioAtual = repositorioUsuario.findById(id).orElse(null);
        if (usuarioAtual != null) {
            if (usuario.getNome() != null) {
                usuarioAtual.setNome(usuario.getNome());
            }
            if (usuario.getNomeSocial() != null) {
                usuarioAtual.setNomeSocial(usuario.getNomeSocial());
            }
            if (usuario.getPerfis() != null) {
                usuarioAtual.setPerfis(usuario.getPerfis());
            }
            if (usuario.getTelefones() != null) {
                usuarioAtual.setTelefones(usuario.getTelefones());
            }
            if (usuario.getEndereco() != null) {
                usuarioAtual.setEndereco(usuario.getEndereco());
            }
            if (usuario.getDocumentos() != null) {
                usuarioAtual.setDocumentos(usuario.getDocumentos());
            }
            if (usuario.getEmails() != null) {
                usuarioAtual.setEmails(usuario.getEmails());
            }
            if (usuario.getCredenciais() != null) {
                usuarioAtual.setCredenciais(usuario.getCredenciais());
            }
            if (usuario.getMercadorias() != null) {
                usuarioAtual.setMercadorias(usuario.getMercadorias());
            }
            if (usuario.getVendas() != null) {
                usuarioAtual.setVendas(usuario.getVendas());
            }
            if (usuario.getVeiculos() != null) {
                usuarioAtual.setVeiculos(usuario.getVeiculos());
            }
            repositorioUsuario.save(usuarioAtual);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public void deletarUsuario(Long id) {
        Usuario usuario = repositorioUsuario.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        List<Venda> vendas = repositorioVenda.findAll();
        for (Venda venda : vendas) {
            if (venda.getCliente() == usuario) {
                venda.setCliente(null);
            }
            if (venda.getFuncionario() == usuario) {
                venda.setFuncionario(null);
            }
            usuario.getVendas().remove(venda);
        }

        List<Veiculo> veiculos = repositorioVeiculo.findAll();
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getProprietario() == usuario) {
                veiculo.setProprietario(null);
            }
        }

        List<Empresa> empresas = repositorioEmpresa.findAll();
        for (Empresa empresa : empresas) {
            empresa.getUsuarios().remove(usuario);
        }

        usuario.getDocumentos().clear();

        usuario.setEndereco(null);

        usuario.getTelefones().clear();

        usuario.getEmails().clear();

        usuario.getCredenciais().clear();

        usuario.getMercadorias().clear();

        repositorioUsuario.deleteById(id);
    }
}
