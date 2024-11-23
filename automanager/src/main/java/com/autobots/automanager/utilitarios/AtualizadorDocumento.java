package com.autobots.automanager.utilitarios;

import com.autobots.automanager.entitades.Documento;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AtualizadorDocumento {
    public Documento atualizarDocumento(Documento documento, Documento novoDocumento) {
        documento.setTipo(novoDocumento.getTipo());
        documento.setDataEmissao(new Date());
        documento.setNumero(novoDocumento.getNumero());
        return documento;
    }
}
