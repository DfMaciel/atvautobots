package com.autobots.automanager.adicionadores;

import com.autobots.automanager.controllers.ServicoController;
import com.autobots.automanager.entitades.Servico;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdicionadorLinkServico implements AdicionadorLink<Servico> {

        @Override
        public void adicionarLink(List<Servico> lista) {
            for (Servico servico : lista) {
                long id = servico.getId();
                Link linkProprio = WebMvcLinkBuilder
                        .linkTo(WebMvcLinkBuilder
                                .methodOn(ServicoController.class)
                                .visualizarServico(id))
                        .withSelfRel();
                servico.add(linkProprio);
            }
        }

        @Override
        public void adicionarLink(Servico objeto) {
            Link linkProprio = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(ServicoController.class)
                            .listarServicos())
                    .withRel("servicos");
            objeto.add(linkProprio);
        }

}
