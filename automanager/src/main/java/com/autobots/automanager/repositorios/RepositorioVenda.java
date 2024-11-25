package com.autobots.automanager.repositorios;

import com.autobots.automanager.entitades.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositorioVenda extends JpaRepository<Venda, Long> {
    List<Venda> findByClienteId(long clienteId);
    List<Venda> findByVendedorId(Long id);
}
