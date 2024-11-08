package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelo.*;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.DocumentoRepositorio;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {
    @Autowired
    private ClienteRepositorio repositorio;
    @Autowired
    private DocumentoRepositorio documentoRepositorio;
    @Autowired
    private ClienteSelecionador selecionador;
    @Autowired
    private DocumentoCadastrador cadastrador;
    @Autowired
    private DocumentoAtualizador atualizador;
    @Autowired
    private DocumentoRemovedor removedor;
    @Autowired
    private AdicionadorLinkDocumento adicionadorLink;
    @Autowired
    private DocumentoSelecionador documentoSelecionador;
    @Autowired
    private AdicionadorLinkCliente adicionadorLinkCliente;

    @PostMapping("/cadastrar/{clienteid}")
        public ResponseEntity<?> cadastrarDocumento(@RequestBody List<Documento> documentos, @PathVariable long clienteid){
            HttpStatus status = HttpStatus.CONFLICT;
            List<Cliente> clientes = repositorio.findAll();
            Cliente cliente = selecionador.selecionar(clientes, clienteid);
            if (cliente == null) {
                status = HttpStatus.NOT_FOUND;
            } else {
                cadastrador.cadastrar(cliente, documentos);
                repositorio.save(cliente);
                status = HttpStatus.CREATED;
            }
            return new ResponseEntity<>(status);
        }

    @GetMapping("/visualizar/{id}")
        public ResponseEntity<Documento> visualizarDocumento(@PathVariable long id){
            List<Documento> documentos = documentoRepositorio.findAll();
            Documento documento = documentoSelecionador.selecionar(documentos, id);
            if (documento == null) {
                ResponseEntity<Documento> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                return resposta;
            } else {
                adicionadorLink.adicionarLink(documento);
                ResponseEntity<Documento> resposta = new ResponseEntity<>(documento, HttpStatus.FOUND);
                return resposta;
            }
        }

    @GetMapping("/listar")
        public  ResponseEntity<List<Documento>> listarDocumentos(){
            List<Documento> documentos = documentoRepositorio.findAll();
            if (documentos.isEmpty()) {
                ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(HttpStatus.NO_CONTENT);
                return resposta;
            } else {
                adicionadorLink.adicionarLink(documentos);
                ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(documentos, HttpStatus.FOUND);
                return resposta;
            }
        }

    @GetMapping("/cliente/{clienteid}")
        public ResponseEntity<List<Documento>> visualizarDocumentosPorCliente (@PathVariable long clienteid){
            List<Cliente> clientes = repositorio.findAll();
            Cliente cliente = selecionador.selecionar(clientes, clienteid);
            if (cliente == null) {
                ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                return resposta;
            } else {
                adicionadorLink.adicionarLink(cliente.getDocumentos());
                ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(cliente.getDocumentos(), HttpStatus.FOUND);
                return resposta;
            }
        }

    @PutMapping("/atualizar/{clienteid}")
        public ResponseEntity<?> atualizarDocumento(@RequestBody List<Documento> documento, @PathVariable long clienteid){
            HttpStatus status = HttpStatus.CONFLICT;
            List<Cliente> clientes = repositorio.findAll();
            Cliente cliente = selecionador.selecionar(clientes, clienteid);
            if (cliente == null) {
                status = HttpStatus.BAD_REQUEST;
            } else {
                atualizador.atualizar(cliente.getDocumentos(), documento);
                repositorio.save(cliente);
                status = HttpStatus.OK;
            }
            return new ResponseEntity<>(status);
        }

    @DeleteMapping("/excluir/{clienteid}")
        public ResponseEntity<?> excluirDocumento(@RequestBody List<Documento> documento, @PathVariable long clienteid){
            HttpStatus status = HttpStatus.CONFLICT;
            List<Cliente> clientes = repositorio.findAll();
            Cliente cliente = selecionador.selecionar(clientes, clienteid);
            if (cliente == null) {
                status = HttpStatus.BAD_REQUEST;
            } else {
                removedor.excluir(cliente, documento);
                repositorio.save(cliente);
                status = HttpStatus.OK;
            }
            return new ResponseEntity<>(status);
        }
}
