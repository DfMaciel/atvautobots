package com.autobots.automanager.adicionadores;

import com.autobots.automanager.controllers.EmpresaController;
import com.autobots.automanager.entitades.Empresa;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;

public class AdicionadorLinkEmpresa implements AdicionadorLink<Empresa> {

    @Override
    public void adicionarLink(List<Empresa> lista) {
        for (Empresa empresa : lista) {
            long id = empresa.getId();
            Link linkProprio = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(EmpresaController.class)
                            .obterCliente(id))
                    .withSelfRel();
            empresa.add(linkProprio);
        }
    }

    @Override
    public void adicionarLink(Empresa objeto) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(EmpresaController.class)
                        .listarEmpresas())
                .withRel("clientes");
        objeto.add(linkProprio);
    }
}
