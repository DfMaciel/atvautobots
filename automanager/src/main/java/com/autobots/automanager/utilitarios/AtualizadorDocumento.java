package com.autobots.automanager.utilitarios;

import com.autobots.automanager.entitades.Documento;
import org.springframework.stereotype.Component;

@Component
public class AtualizadorDocumento {
    public Documento atualizarDocumento(Documento documento, Documento novoDocumento) {
        documento.setTipo(novoDocumento.getTipo());
        documento.setDataEmissao(novoDocumento.getDataEmissao());
        documento.setNumero(novoDocumento.getNumero());
        return documento;
    }
}
