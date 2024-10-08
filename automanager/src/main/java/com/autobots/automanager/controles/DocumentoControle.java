package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.modelo.ClienteSelecionador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {
    @Autowired
    ClienteRepositorio repositorio;
    @Autowired
    ClienteSelecionador selecionador;

    @GetMapping("/documento/{id}")
        public List<Documento> visualizarDocumento(@PathVariable long id){
            List<Cliente> clientes = repositorio.findAll();
            return selecionador.selecionar(clientes, id).getDocumentos();
        }
}
