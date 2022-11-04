package com.algaworks.algalog.api.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntregaRequestDTO {
	
	@Valid
	@NotNull
	private ClienteRequestDTO cliente;
	
	@Valid
	@NotNull
	private DestinatarioRequestDTO destinatario;
	
	@NotNull
	private BigDecimal taxa;
}
