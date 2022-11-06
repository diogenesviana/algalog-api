package com.algaworks.algalog.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.algaworks.algalog.domain.exception.NegocioException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter 
@Entity
public class Entrega {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Cliente cliente;
	
	@Embedded
	private Destinatario destinatario;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "entrega")
	private List<Ocorrencia> ocorrencias = new ArrayList<>();
	
	@NotNull
	private BigDecimal taxa;
	
	@Enumerated(EnumType.STRING)
	private StatusEntrega status;
	
	@JoinColumn(name = "data_pedido")
	private OffsetDateTime dataPedido;
	
	@JoinColumn(name = "data_finalizacao")
	private OffsetDateTime dataFinalizacao;

	public Ocorrencia adicionarOcorrencia(String descricao) {
		Ocorrencia ocorrencia = new Ocorrencia();
		ocorrencia.setDescricao(descricao);
		ocorrencia.setDataRegistro(OffsetDateTime.now());
		ocorrencia.setEntrega(this);
		this.getOcorrencias().add(ocorrencia);
		return ocorrencia;
	}

	public void finalizar(Entrega entrega) {
		if(statusPendente()) {
			entrega.setStatus(StatusEntrega.FINALIZADA);
		} else {
		throw new NegocioException("Ocorrência no status " + entrega.getStatus() + ", portanto não pode ser finalizada!");
		}
	}
	
	public void cancelar(Entrega entrega) {
		if(statusPendente()) {
			entrega.setStatus(StatusEntrega.CANCELADA);
		} else {
			throw new NegocioException("Ocorrência no status " + entrega.getStatus() + ", portanto não pode ser cancelada!");
		}
	}
	
	public boolean statusPendente() {
		return StatusEntrega.PENDENTE.equals(getStatus());
	}
	
	public boolean statusCancelada() {
		return StatusEntrega.CANCELADA.equals(getStatus());
	}
	
	public boolean statusFinalizada() {
		return StatusEntrega.FINALIZADA.equals(getStatus());
	}

	
}
