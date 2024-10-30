package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.*;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/endereco")
public class EnderecoControle {
    @Autowired
    private ClienteRepositorio repositorio;
    @Autowired
    private ClienteSelecionador selecionador;
    @Autowired
    private EnderecoAtualizador atualizador;

    @GetMapping("/endereco/{id}")
    public Endereco visualizarEndereco(@PathVariable long id){
        List<Cliente> clientes = repositorio.findAll();
        return selecionador.selecionar(clientes, id).getEndereco();
    }

    @PutMapping("/atualizar/{id}")
    public void  atualizarEndereco(@RequestBody Endereco endereco, @PathVariable long id){
        List<Cliente> clientes = repositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, id);
        atualizador.atualizar(cliente.getEndereco(), endereco);
        repositorio.save(cliente);
    }
}
