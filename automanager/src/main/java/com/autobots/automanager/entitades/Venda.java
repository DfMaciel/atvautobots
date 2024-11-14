package com.autobots.automanager.entitades;

import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(exclude = { "cliente", "funcionario", "veiculo" }, callSuper=false)
@Entity
public class Venda extends RepresentationModel<Venda> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Date cadastro;
	@Column(nullable = false, unique = true)
	private String identificacao;
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JsonIgnoreProperties(value = { "vendas" })
	private Usuario cliente;
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JsonIgnoreProperties(value = { "vendas" })
	private Usuario funcionario;
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JsonManagedReference
	private List<Mercadoria> mercadorias = new ArrayList<>();
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JsonManagedReference
	private List<Servico> servicos = new ArrayList<>();
	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JsonIgnoreProperties(value = { "vendas", "proprietario" })
	private Veiculo veiculo;
}