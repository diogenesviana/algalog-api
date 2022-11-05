package com.algaworks.algalog.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algalog.api.assembler.OcorrenciaAssembler;
import com.algaworks.algalog.api.model.OcorrenciaDTO;
import com.algaworks.algalog.api.model.input.OcorrenciaRequestDTO;
import com.algaworks.algalog.domain.model.Entrega;
import com.algaworks.algalog.domain.model.Ocorrencia;
import com.algaworks.algalog.domain.service.BuscaEntregaService;
import com.algaworks.algalog.domain.service.RegistroOcorrenciaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas/{id}/ocorrencias")
public class OcorrenciaController {
	
	private BuscaEntregaService buscaEntregaService;
	private OcorrenciaAssembler ocorrenciaAssembler;
	private RegistroOcorrenciaService registroOcorrenciaService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OcorrenciaDTO registrar(@PathVariable Long id,
			@Valid @RequestBody OcorrenciaRequestDTO ocorrenciaRequestDTO) {
		Ocorrencia ocorrencia = 
				registroOcorrenciaService.registrar(id, ocorrenciaRequestDTO.getDescricao());
		
		return ocorrenciaAssembler.toDTO(ocorrencia);
	}
	
	@GetMapping
	public List<OcorrenciaDTO> listar (@PathVariable Long id){
		Entrega entrega = buscaEntregaService.buscar(id);
		return ocorrenciaAssembler.toCollectionDTO(entrega.getOcorrencias());
	}
}
