package com.autobots.automanager.adicionadores;

import com.autobots.automanager.controllers.CredencialController;
import com.autobots.automanager.entitades.Credencial;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdicionadorLinkCredencial implements AdicionadorLink<Credencial> {

    @Override
    public void adicionarLink(List<Credencial> lista) {
        for (Credencial credencial : lista) {
            long id = credencial.getId();
            Link linkProprio = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(CredencialController.class)
                            .visualizarCredencial(id))
                    .withSelfRel();
            credencial.add(linkProprio);
        }
    }

    @Override
    public void adicionarLink(Credencial objeto) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(CredencialController.class)
                        .listarCredenciais())
                .withRel("credenciais");
        objeto.add(linkProprio);
    }
}
