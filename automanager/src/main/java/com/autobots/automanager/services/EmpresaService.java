package com.autobots.automanager.services;

import com.autobots.automanager.adicionadores.AdicionadorLinkEmpresa;
import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {
    @Autowired
    private RepositorioEmpresa repositorioEmpresa;
    @Autowired
    private AdicionadorLinkEmpresa adicionadorLinkEmpresa;

    public List<Empresa> listarEmpresas() {
        List<Empresa> empresas = repositorioEmpresa.findAll();
        adicionadorLinkEmpresa.adicionarLink(empresas);
        return empresas;
    }

    public Empresa visualizarEmpresa(Long id) {
        Empresa empresa = repositorioEmpresa.findById(id).orElse(null);
        if (empresa != null) {
            adicionadorLinkEmpresa.adicionarLink(empresa);
        }
        return empresa;
    }

    public void cadastrarEmpresa(Empresa empresa) {
        repositorioEmpresa.save(empresa);
    }

    public ResponseEntity<?> atualizarEmpresa(Long id, Empresa empresa) {
        Empresa empresaAtual = repositorioEmpresa.findById(id).orElse(null);
        if (empresaAtual != null) {
            if (empresa.getRazaoSocial() != null) {
                empresaAtual.setRazaoSocial(empresa.getRazaoSocial());
            }
            if (empresa.getNomeFantasia() != null) {
                empresaAtual.setNomeFantasia(empresa.getNomeFantasia());
            }
            if (empresa.getTelefones() != null) {
                empresaAtual.setTelefones(empresa.getTelefones());
            }
            if (empresa.getEndereco() != null) {
                empresaAtual.setEndereco(empresa.getEndereco());
            }
            if (empresa.getCadastro() != null) {
                empresaAtual.setCadastro(empresa.getCadastro());
            }
            if (empresa.getUsuarios() != null) {
                empresaAtual.setUsuarios(empresa.getUsuarios());
            }
            if (empresa.getMercadorias() != null) {
                empresaAtual.setMercadorias(empresa.getMercadorias());
            }
            if (empresa.getServicos() != null) {
                empresaAtual.setServicos(empresa.getServicos());
            }
            if (empresa.getVendas() != null) {
                empresaAtual.setVendas(empresa.getVendas());
            }
            repositorioEmpresa.save(empresa);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public void deletarEmpresa(Long id) {
        List<Empresa> empresas = repositorioEmpresa.findAll();
        try {
            repositorioEmpresa.findById(id).orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
    }
}
