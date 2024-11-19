package com.autobots.automanager.adicionadores;

import com.autobots.automanager.controllers.MercadoriaController;
import com.autobots.automanager.controllers.VeiculoController;
import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.entitades.Veiculo;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdicionadorLinkVeiculo implements AdicionadorLink<Veiculo> {

    @Override
    public void adicionarLink(List<Veiculo> lista) {
        for (Veiculo veiculo : lista) {
            long id = veiculo.getId();
            Link linkProprio = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(VeiculoController.class)
                            .visualizarVeiculo(id))
                    .withSelfRel();
            veiculo.add(linkProprio);
        }
    }

    @Override
    public void adicionarLink(Veiculo objeto) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(VeiculoController.class)
                        .listarVeiculos())
                .withRel("veiculos");
        objeto.add(linkProprio);
    }
}
