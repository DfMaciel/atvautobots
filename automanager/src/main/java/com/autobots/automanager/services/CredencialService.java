package com.autobots.automanager.services;

import com.autobots.automanager.adicionadores.AdicionadorLinkCredencial;
import com.autobots.automanager.controllers.CredencialDto;
import com.autobots.automanager.entitades.Credencial;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.repositorios.RepositorioCredencial;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.utilitarios.SelecionadorUsuario;
import com.autobots.automanager.utilitarios.VerificadorPermissao;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class CredencialService {

    @Autowired
    private RepositorioCredencial repositorioCredencial;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private AdicionadorLinkCredencial adicionadorLinkCredencial;

    @Autowired
    private SelecionadorUsuario selecionadorUsuario;

    @Autowired
    private VerificadorPermissao verificadorPermissao;


    public List<Credencial> listarCredenciais() {
        List<Credencial> credencials = repositorioCredencial.findActiveCredenciais();
        adicionadorLinkCredencial.adicionarLink(credencials);
        return credencials;
    }

    public Credencial visualizarCredencial(Long id) {
        Credencial credencial = repositorioCredencial.findActiveCredencialById(id).orElse(null);
        if (credencial != null) {
            adicionadorLinkCredencial.adicionarLink(credencial);
        }
        return credencial;
    }

    public Credencial listarCredenciaisUsuario(Long idUsuario, String username) {
        List<Usuario> usuarios = repositorioUsuario.findAll();
        Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
        Usuario usuarioSelecionado = selecionadorUsuario.selecionarUsername(usuarios, username);

        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }

        boolean permissao = verificadorPermissao.verificar(usuarioSelecionado.getPerfis(), usuario.getPerfis());

        if (!permissao) {
            throw new IllegalArgumentException("Usuário sem permissão.");
        }

        Credencial credencial = usuario.getCredencial();
        adicionadorLinkCredencial.adicionarLink(credencial);
        return credencial;
    }

    public void cadastrarCredencial (Long idUsuario, CredencialDto credencial, String username) {
        List<Usuario> usuarios = repositorioUsuario.findAll();
        Usuario usuarioSelecionado = selecionadorUsuario.selecionarUsername(usuarios, username);
        Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }

        boolean permissao = verificadorPermissao.verificar(usuarioSelecionado.getPerfis(), usuario.getPerfis());

        if (!permissao) {
            throw new IllegalArgumentException("Usuário sem permissão.");
        }

        Credencial credencialCadastrar = new Credencial();
        if (credencial.nomeUsuario().isPresent()) {
            credencialCadastrar.setNomeUsuario(credencial.nomeUsuario().get());
        }
        if (credencial.senha().isPresent()) {
            credencialCadastrar.setSenha(credencial.senha().get());
        }
        credencialCadastrar.setInativo(false);
        repositorioCredencial.save(credencialCadastrar);

        usuario.setCredencial(credencialCadastrar);
        repositorioUsuario.save(usuario);
    }

    public void atualizarCredencial(Long id, CredencialDto credencial, String username) {
        Credencial credencialAtualizado = repositorioCredencial.findActiveCredencialById(id).orElse(null);
        List<Usuario> usuarios = repositorioUsuario.findAll();
        Usuario usuarioSelecionado = selecionadorUsuario.selecionarUsername(usuarios, username);
        Usuario usuarioAtualizado = null;
        for (Usuario usuario : usuarios) {
            if (usuario.getCredencial().getId().equals(id)) {
                usuarioAtualizado = usuario;
                break;
            }
        }
        if (usuarioAtualizado == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }

        boolean permissao = verificadorPermissao.verificar(usuarioSelecionado.getPerfis(), usuarioAtualizado.getPerfis());

        if (!permissao) {
            throw new IllegalArgumentException("Usuário sem permissão.");
        }

        if (credencialAtualizado != null) {
            if (credencial.nomeUsuario().isPresent()) {
                credencialAtualizado.setNomeUsuario(credencial.nomeUsuario().get());
            }
            if (credencial.senha().isPresent()) {
                credencialAtualizado.setSenha(credencial.senha().get());
            }
            repositorioCredencial.save(credencialAtualizado);
        }

        else {
            throw new IllegalArgumentException("Credencial não encontrada.");
        }
    }

    public void deletarCredencial(Long id) {
        Credencial credencial = repositorioCredencial.findById(id).orElse(null);
        if (credencial != null) {
            credencial.setInativo(true);
            repositorioCredencial.save(credencial);
        }
        else {
            throw new IllegalArgumentException("Credencial não encontrada.");
        }
    }
}
