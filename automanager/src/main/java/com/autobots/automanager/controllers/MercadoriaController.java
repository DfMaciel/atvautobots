package com.autobots.automanager.controllers;

import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.services.MercadoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/mercadoria")
public class MercadoriaController {

    @Autowired
    private MercadoriaService mercadoriaService;

    @GetMapping("/listar")
    public ResponseEntity<List<Mercadoria>> listarMercadorias() {
        List<Mercadoria> mercadorias = mercadoriaService.listarMercadorias();
        if (mercadorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mercadorias);
    }

    @GetMapping("/visualizar/{id}")
    public ResponseEntity<Mercadoria> visualizarMercadoria(@PathVariable Long id) {
        Mercadoria mercadoria = mercadoriaService.visualizarMercadoria(id);
        if (mercadoria == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mercadoria);
    }

    @GetMapping("/visualizar/empresa/{idEmpresa}")
    public ResponseEntity<Set<Mercadoria>> visualizarMercadoriaEmpresa(@PathVariable Long idEmpresa) {
        Set<Mercadoria> mercadorias = mercadoriaService.visualizarMercadoriaEmpresa(idEmpresa);
        if (mercadorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mercadorias);
    }

    @GetMapping("/visualizar/usuario/{idUsuario}")
    public ResponseEntity<Set<Mercadoria>> visualizarMercadoriaUsuario(@PathVariable Long idUsuario) {
        Set<Mercadoria> mercadorias = mercadoriaService.visualizarMercadoriaUsuario(idUsuario);
        if (mercadorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mercadorias);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarMercadoria(@RequestBody Mercadoria mercadoria) {
        try {
            mercadoriaService.cadastrarMercadoria(mercadoria);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar/empresa/{idEmpresa}")
    public ResponseEntity<?> cadastrarMercadoriaEmpresa(@RequestBody Mercadoria mercadoria, @PathVariable Long idEmpresa) {
        try {
            mercadoriaService.cadastrarMercadoriaEmpresa(mercadoria, idEmpresa);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar/usuario/{idUsuario}")
    public ResponseEntity<?> cadastrarMercadoriaUsuario(@RequestBody Mercadoria mercadoria, @PathVariable Long idUsuario) {
        try {
            mercadoriaService.cadastrarMercadoriaUsuario(mercadoria, idUsuario);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarMercadoria(@PathVariable Long id, @RequestBody Mercadoria mercadoria) {
        try {
            mercadoriaService.atualizarMercadoria(id, mercadoria);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarMercadoria(@PathVariable Long id) {
        try {
            mercadoriaService.deletarMercadoria(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
