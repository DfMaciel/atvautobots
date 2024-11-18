package com.autobots.automanager.utilitarios;

import com.autobots.automanager.controllers.UsuarioDto;
import com.autobots.automanager.entitades.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
public class CadastradorUsuario {
    public Usuario cadastrarUsuario(UsuarioDto usuario) {
        Usuario usuarioCadastrado = new Usuario();
        usuarioCadastrado.setNome(usuario.nome());
        usuarioCadastrado.setNomeSocial(usuario.nomeSocial());
        usuarioCadastrado.setPerfis(usuario.perfis());

        for (Telefone telefone : usuario.telefones()) {
            Telefone telefoneCadastrado = new Telefone();
            telefoneCadastrado.setDdd(telefone.getDdd());
            telefoneCadastrado.setNumero(telefone.getNumero());
            usuarioCadastrado.getTelefones().add(telefoneCadastrado);
        }

        Endereco enderecoCadastrado = new Endereco();
        enderecoCadastrado.setEstado(usuario.endereco().getEstado());
        enderecoCadastrado.setCidade(usuario.endereco().getCidade());
        enderecoCadastrado.setBairro(usuario.endereco().getBairro());
        enderecoCadastrado.setRua(usuario.endereco().getRua());
        enderecoCadastrado.setNumero(usuario.endereco().getNumero());
        enderecoCadastrado.setCodigoPostal(usuario.endereco().getCodigoPostal());
        if (usuario.endereco().getInformacoesAdicionais() != null) {
            enderecoCadastrado.setInformacoesAdicionais(usuario.endereco().getInformacoesAdicionais());
        }
        usuarioCadastrado.setEndereco(enderecoCadastrado);

        for (Documento documento : usuario.documentos()) {
            Documento documentoCadastrado = new Documento();
            documentoCadastrado.setDataEmissao(new Date());
            documentoCadastrado.setTipo(documento.getTipo());
            documentoCadastrado.setNumero(documento.getNumero());
            usuarioCadastrado.getDocumentos().add(documentoCadastrado);
        }

        for (Email email : usuario.emails()) {
            Email emailCadastrado = new Email();
            emailCadastrado.setEndereco(email.getEndereco());
            usuarioCadastrado.getEmails().add(emailCadastrado);
        }

        Set<CredencialUsuarioSenha> credenciaisUsuarioSenha = usuario.credenciais();

        for (CredencialUsuarioSenha credencialUsuarioSenha : credenciaisUsuarioSenha) {
            CredencialUsuarioSenha credencial = new CredencialUsuarioSenha();
            credencial.setNomeUsuario(credencialUsuarioSenha.getNomeUsuario());
            credencial.setSenha(credencialUsuarioSenha.getSenha());
            credencial.setInativo(false);
            credencial.setCriacao(new Date());
            credencial.setUltimoAcesso(new Date());
            usuarioCadastrado.getCredenciais().add(credencial);
        }

//        for (Credencial credencial : usuario.getCredenciais()) {
//            if (credencial instanceof CredencialUsuarioSenha) {
//                CredencialUsuarioSenha credencialCadastrado = new CredencialUsuarioSenha();
//                credencialCadastrado.setInativo((false));
//                credencialCadastrado.setNomeUsuario(((CredencialUsuarioSenha) credencial).getNomeUsuario());
//                credencialCadastrado.setSenha(((CredencialUsuarioSenha) credencial).getSenha());
//                credencialCadastrado.setCriacao(((CredencialUsuarioSenha) credencial).getCriacao());
//                credencialCadastrado.setUltimoAcesso(((CredencialUsuarioSenha) credencial).getUltimoAcesso());
//                usuarioCadastrado.getCredenciais().add(credencialCadastrado);
//                }
//        }

        if (usuario.mercadorias() != null) {
            for (Mercadoria mercadoria : usuario.mercadorias()) {
                Mercadoria mercadoriaCadastrada = new Mercadoria();
                mercadoriaCadastrada.setValidade(new Date());
                mercadoriaCadastrada.setFabricao(new Date());
                mercadoriaCadastrada.setNome(mercadoria.getNome());
                mercadoriaCadastrada.setCadastro(new Date());
                mercadoriaCadastrada.setQuantidade(mercadoria.getQuantidade());
                mercadoriaCadastrada.setValor(mercadoria.getValor());
                mercadoriaCadastrada.setDescricao(mercadoria.getDescricao());
                usuarioCadastrado.getMercadorias().add(mercadoriaCadastrada);
            }
        }

        if (usuario.vendas() != null) {
            usuarioCadastrado.setVendas(usuario.vendas());
        }

        if (usuario.veiculos() != null) {
            usuarioCadastrado.setVeiculos(usuario.veiculos());
        }

        return usuarioCadastrado;
    }
}
