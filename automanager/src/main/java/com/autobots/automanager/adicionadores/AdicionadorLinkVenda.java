package com.autobots.automanager.adicionadores;

import com.autobots.automanager.controllers.VendaController;
import com.autobots.automanager.entitades.Venda;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdicionadorLinkVenda implements AdicionadorLink<Venda> {

    @Override
    public void adicionarLink(List<Venda> lista) {
        for (Venda venda : lista) {
            long id = venda.getId();
            Link linkProprio = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(VendaController.class)
                            .visualizarVenda(id))
                    .withSelfRel();
            venda.add(linkProprio);
        }
    }

    @Override
    public void adicionarLink(Venda objeto) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(VendaController.class)
                        .listarVendas())
                .withRel("vendas");
        objeto.add(linkProprio);
    }

}
