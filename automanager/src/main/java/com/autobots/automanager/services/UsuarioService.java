package com.autobots.automanager.services;

import com.autobots.automanager.adicionadores.AdicionadorLinkUsuario;
import com.autobots.automanager.controllers.AtualizarUsuarioDto;
import com.autobots.automanager.controllers.UsuarioDto;
import com.autobots.automanager.entitades.*;
import com.autobots.automanager.repositorios.*;
import com.autobots.automanager.utilitarios.AtualizadorDocumento;
import com.autobots.automanager.utilitarios.AtualizadorEmail;
import com.autobots.automanager.utilitarios.AtualizadorTelefone;
import com.autobots.automanager.utilitarios.CadastradorUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.Document;
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

    @Autowired
    private CadastradorUsuario CadastradorUsuario;

    @Autowired
    private AtualizadorDocumento atualizadorDocumento;

    @Autowired
    private AtualizadorTelefone atualizadorTelefone;

    @Autowired
    private AtualizadorEmail atualizadorEmail;

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

    public void cadastrarUsuario(UsuarioDto usuario) {
        Usuario usuarioCadastrado = CadastradorUsuario.cadastrarUsuario(usuario);
        repositorioUsuario.save(usuarioCadastrado);
    }

    public void cadastrarUsuarioEmpresa(UsuarioDto usuario, Long idEmpresa) {
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        Usuario usuarioCadastrado = CadastradorUsuario.cadastrarUsuario(usuario);
        Usuario usuarioSalvo = repositorioUsuario.save(usuarioCadastrado);
        empresa.getUsuarios().add(usuarioSalvo);
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

    public ResponseEntity<?> atualizarUsuario(Long id, AtualizarUsuarioDto usuario) {
        Usuario usuarioAtual = repositorioUsuario.findById(id).orElse(null);
        if (usuarioAtual != null) {
            if (usuario.nome().isPresent()) {
                usuarioAtual.setNome(String.valueOf(usuario.nome()));
            }
            if (usuario.nomeSocial().isPresent()) {
                usuarioAtual.setNomeSocial(String.valueOf(usuario.nomeSocial()));
            }
            if (usuario.perfis().isPresent()) {
                usuarioAtual.setPerfis(usuario.perfis().get());
            }
            if (usuario.telefones().isPresent()) {
                for (Telefone telefone : usuario.telefones().get()) {
                    for (Telefone telefoneAtual : usuarioAtual.getTelefones()) {
                        if (telefone.getId().equals(telefoneAtual.getId())) {
                            atualizadorTelefone.atualizarTelefone(telefoneAtual, telefone);
                        }
                    }
                }
            }
            if (usuario.endereco().isPresent()) {
                usuarioAtual.setEndereco(usuario.endereco().get());
            }
            if (usuario.documentos().isPresent()) {
                for (Documento documento : usuario.documentos().get()) {
                    for (Documento documentoAtual : usuarioAtual.getDocumentos()) {
                        if (documento.getId().equals(documentoAtual.getId())) {
                            atualizadorDocumento.atualizarDocumento(documentoAtual, documento);
                        }
                    }
                }
            }
            if (usuario.emails().isPresent()) {
                for (Email email : usuario.emails().get()) {
                    for (Email emailAtual : usuarioAtual.getEmails()) {
                        if (email.getId().equals(emailAtual.getId())) {
                            atualizadorEmail.atualizarEmail(emailAtual, email);
                        }
                    }
                }
            }
            if (usuario.credenciais().isPresent()) {
                usuarioAtual.getCredenciais().addAll(usuario.credenciais().get());
            }
            if (usuario.mercadorias().isPresent()) {
                usuarioAtual.getMercadorias().addAll(usuario.mercadorias().get());
            }
            if (usuario.vendas().isPresent()) {
                usuarioAtual.getVendas().addAll(usuario.vendas().get());
            }
            if (usuario.veiculos().isPresent()) {
                usuarioAtual.getVeiculos().addAll(usuario.veiculos().get());
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
