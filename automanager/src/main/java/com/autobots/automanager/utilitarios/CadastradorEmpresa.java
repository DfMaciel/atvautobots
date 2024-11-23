package com.autobots.automanager.utilitarios;

import com.autobots.automanager.entitades.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CadastradorEmpresa {

    @Autowired
    private CadastradorUsuario cadastradorUsuario;

    @Autowired
    private CadastradorMercadoria cadastradorMercadoria;

    @Autowired
    private CadastradorVenda cadastradorVenda;

    public Empresa cadastrarEmpresa (Empresa empresa) {
        Empresa empresaCadastrada = new Empresa();
        empresaCadastrada.setRazaoSocial(empresa.getRazaoSocial());
        empresaCadastrada.setNomeFantasia(empresa.getNomeFantasia());
        if (empresa.getTelefones() != null) {
            for (Telefone telefone : empresa.getTelefones()) {
                Telefone telefoneCadastrado = new Telefone();
                telefoneCadastrado.setDdd(telefone.getDdd());
                telefoneCadastrado.setNumero(telefone.getNumero());
                empresaCadastrada.getTelefones().add(telefoneCadastrado);
            }
        }

        if (empresa.getEndereco() != null) {
            Endereco enderecoCadastrado = new Endereco();
            enderecoCadastrado.setEstado(empresa.getEndereco().getEstado());
            enderecoCadastrado.setCidade(empresa.getEndereco().getCidade());
            enderecoCadastrado.setBairro(empresa.getEndereco().getBairro());
            enderecoCadastrado.setRua(empresa.getEndereco().getRua());
            enderecoCadastrado.setNumero(empresa.getEndereco().getNumero());
            enderecoCadastrado.setCodigoPostal(empresa.getEndereco().getCodigoPostal());
            if (empresa.getEndereco().getInformacoesAdicionais() != null) {
                enderecoCadastrado.setInformacoesAdicionais(empresa.getEndereco().getInformacoesAdicionais());
            }
            empresaCadastrada.setEndereco(enderecoCadastrado);
        }

        empresaCadastrada.setCadastro(new Date());

        if (empresa.getUsuarios() != null) {
            for (Usuario usuario : empresa.getUsuarios()) {
                Usuario usuarioCadastrado = cadastradorUsuario.cadastrarUsuario(usuario);
                empresaCadastrada.getUsuarios().add(usuarioCadastrado);
            }
        }

        if (empresa.getMercadorias() != null) {
            for (Mercadoria mercadoria : empresa.getMercadorias()) {
                Mercadoria mercadoriaCadastrada = cadastradorMercadoria.cadastrarMercadoria(mercadoria);
                empresaCadastrada.getMercadorias().add(mercadoriaCadastrada);
            }
        }

        if (empresa.getServicos() != null) {
            for (Servico servico : empresa.getServicos()) {
                empresaCadastrada.getServicos().add(servico);
            }
        }

        if (empresa.getVendas() != null) {
            for (Venda venda : empresa.getVendas()) {
                Venda vendaCadastrada = cadastradorVenda.cadastrarVenda(venda);
                empresaCadastrada.getVendas().add(vendaCadastrada);
            }
        }

        return empresaCadastrada;
    }
}
