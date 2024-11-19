package com.autobots.automanager.controllers;

import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.services.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @GetMapping("/listar")
    public ResponseEntity<List<Veiculo>> listarVeiculos() {
        List<Veiculo> veiculos =  veiculoService.listarVeiculos();
        if (veiculos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(veiculos);
    }

    @GetMapping("/visualizar/{id}")
    public ResponseEntity<Veiculo> visualizarVeiculo(@PathVariable Long id) {
        Veiculo veiculo = veiculoService.visualizarVeiculo(id);
        if (veiculo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(veiculo);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarVeiculo(@RequestBody Veiculo veiculo) {
        try {
            veiculoService.cadastrarVeiculo(veiculo);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarVeiculo(@PathVariable Long id, @RequestBody Veiculo veiculo) {
        try {
            veiculoService.atualizarVeiculo(id, veiculo);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/vincular/{idVeiculo}/usuario/{idUsuario}")
    public ResponseEntity<?> vincularVeiculoUsuario(@PathVariable Long idVeiculo, @PathVariable Long idUsuario) {
        try {
            veiculoService.vincularVeiculoUsuario(idVeiculo, idUsuario);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluirVeiculo(@PathVariable Long id) {
        try {
            ResponseEntity<?> resposta = veiculoService.excluirVeiculo(id);
            return resposta;
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
