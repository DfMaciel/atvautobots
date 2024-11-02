package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelo.DocumentoAtualizador;
import com.autobots.automanager.modelo.DocumentoCadastrador;
import com.autobots.automanager.modelo.DocumentoRemovedor;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.modelo.ClienteSelecionador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {
    @Autowired
    private ClienteRepositorio repositorio;
    @Autowired
    private ClienteSelecionador selecionador;
    @Autowired
    private DocumentoCadastrador cadastrador;
    @Autowired
    private DocumentoAtualizador atualizador;
    @Autowired
    private DocumentoRemovedor removedor;

    @PostMapping("/cadastrar/{id}")
        public void cadastrarDocumento(@RequestBody List<Documento> documento, @PathVariable long id){
            List<Cliente> clientes = repositorio.findAll();
            Cliente cliente = selecionador.selecionar(clientes, id);
            cadastrador.cadastrar(cliente, documento);
            repositorio.save(cliente);
        }

    @GetMapping("/documento/{id}")
        public List<Documento> visualizarDocumento(@PathVariable long id){
            List<Cliente> clientes = repositorio.findAll();
            return selecionador.selecionar(clientes, id).getDocumentos();
        }

    @PutMapping("/atualizar/{id}")
        public void  atualizarDocumento(@RequestBody List<Documento> documento, @PathVariable long id){
            List<Cliente> clientes = repositorio.findAll();
            Cliente cliente = selecionador.selecionar(clientes, id);
            atualizador.atualizar(cliente.getDocumentos(), documento);
            repositorio.save(cliente);
        }

    @DeleteMapping("/excluir/{id}")
        public void excluirDocumento(@RequestBody List<Documento> documento, @PathVariable long id){
            List<Cliente> clientes = repositorio.findAll();
            Cliente cliente = selecionador.selecionar(clientes, id);
            removedor.excluir(cliente, documento);
            repositorio.save(cliente);
        }
}
