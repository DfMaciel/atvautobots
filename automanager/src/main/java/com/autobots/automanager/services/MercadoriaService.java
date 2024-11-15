package com.autobots.automanager.services;

import com.autobots.automanager.adicionadores.AdicionadorLinkMercadoria;
import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioMercadoria;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioVenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MercadoriaService {

    @Autowired
    private RepositorioMercadoria repositorioMercadoria;

    @Autowired
    private AdicionadorLinkMercadoria adicionadorLinkMercadoria;

    @Autowired
    private RepositorioEmpresa repositorioEmpresa;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private RepositorioVenda repositorioVenda;

    public List<Mercadoria> listarMercadorias() {
        List<Mercadoria> mercadorias = repositorioMercadoria.findAll();
        adicionadorLinkMercadoria.adicionarLink(mercadorias);
        return mercadorias;
    }

    public Mercadoria visualizarMercadoria(Long id) {
        Mercadoria mercadoria = repositorioMercadoria.findById(id).orElse(null);
        if (mercadoria != null) {
            adicionadorLinkMercadoria.adicionarLink(mercadoria);
        }
        return mercadoria;
    }

    public Set<Mercadoria> visualizarMercadoriaEmpresa(Long idEmpresa) {
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        Set<Mercadoria> mercadorias = empresa.getMercadorias();
        return mercadorias;
    }

    public Set<Mercadoria> visualizarMercadoriaUsuario(Long idUsuario) {
        Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario não encontrado");
        }
        Set<Mercadoria> mercadorias = usuario.getMercadorias();
        return mercadorias;
    }

    public Mercadoria cadastrarMercadoria(Mercadoria mercadoria) {
        Mercadoria mercadoriaCadastrada = repositorioMercadoria.save(mercadoria);
        return mercadoriaCadastrada;
    }

    public void cadastrarMercadoria(List<Mercadoria> mercadorias) {
        for (Mercadoria mercadoria : mercadorias) {
            Mercadoria mercadoriaCadastrada = cadastrarMercadoria(mercadoria);
        }
    }

    public void cadastrarMercadoriaEmpresa(Mercadoria mercadoria, Long idEmpresa) {
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        Mercadoria mercadoriaCadastrada = repositorioMercadoria.save(mercadoria);
        empresa.getMercadorias().add(mercadoriaCadastrada);
        repositorioEmpresa.save(empresa);
    }

    public void cadastrarMercadoriaUsuario(Mercadoria mercadoria, Long idUsuario) {
        Usuario usuario  = repositorioUsuario.findById(idUsuario).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario não encontrado");
        }
        Mercadoria mercadoriaCadastrada = repositorioMercadoria.save(mercadoria);
        usuario.getMercadorias().add(mercadoriaCadastrada);
        repositorioUsuario.save(usuario);
    }

    public void atualizarMercadoria(Long id, Mercadoria mercadoria) {
        Mercadoria mercadoriaAtual = repositorioMercadoria.findById(id).orElse(null);
        if (mercadoriaAtual != null) {
            if (mercadoria.getValidade() != null) {
                mercadoriaAtual.setValidade(mercadoria.getValidade());
            }
            if (mercadoria.getFabricao() != null) {
                mercadoriaAtual.setFabricao(mercadoria.getFabricao());
            }
            if (mercadoria.getCadastro() != null) {
                mercadoriaAtual.setCadastro(mercadoria.getCadastro());
            }
            if (mercadoria.getNome() != null) {
                mercadoriaAtual.setNome(mercadoria.getNome());
            }
            if (mercadoria.getQuantidade() != 0) {
                mercadoriaAtual.setQuantidade(mercadoria.getQuantidade());
            }
            if (mercadoria.getValor() != 0) {
                mercadoriaAtual.setValor(mercadoria.getValor());
            }
            if (mercadoria.getDescricao() != null) {
                mercadoriaAtual.setDescricao(mercadoria.getDescricao());
            }
        }

        repositorioMercadoria.save(mercadoriaAtual);
    }

    public void deletarMercadoria(Long id) {
        Mercadoria mercadoria = repositorioMercadoria.findById(id).orElse(null);
        if (mercadoria == null) {
            throw new IllegalArgumentException("Mercadoria não encontrada");
        }

        List<Usuario> usuarios = repositorioUsuario.findAll();
        for (Usuario usuario : usuarios) {
            usuario.getMercadorias().remove(mercadoria);
            repositorioUsuario.save(usuario);
        }

        List<Empresa> empresas = repositorioEmpresa.findAll();
        for (Empresa empresa : empresas) {
            empresa.getMercadorias().remove(mercadoria);
            repositorioEmpresa.save(empresa);
        }

        List<Venda> vendas = repositorioVenda.findAll();
        for (Venda venda : vendas) {
            venda.getMercadorias().remove(mercadoria);
            repositorioVenda.save(venda);
        }

        repositorioMercadoria.deleteById(id);

    }
}
