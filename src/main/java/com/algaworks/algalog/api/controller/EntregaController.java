package com.algaworks.algalog.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algalog.api.assembler.EntregaAssembler;
import com.algaworks.algalog.api.model.EntregaDTO;
import com.algaworks.algalog.domain.exception.NegocioException;
import com.algaworks.algalog.domain.model.Entrega;
import com.algaworks.algalog.domain.repository.EntregaRepository;
import com.algaworks.algalog.domain.service.SolicitacaoEntregaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas")
public class EntregaController {
	
	private SolicitacaoEntregaService solicitacaoEntregaService;
	private EntregaRepository entregaRepository;
	private EntregaAssembler entregaAssembler;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EntregaDTO solicitar(@Valid @RequestBody EntregaDTO entregaDTO) throws NegocioException {
		Entrega entrega = entregaAssembler.toEntity(entregaDTO);
		Entrega entregaSolicitada = solicitacaoEntregaService.solicitar(entrega);
		return entregaAssembler.toDTO(entregaSolicitada);
	}
	
	@GetMapping
	public List<EntregaDTO> listar(){
		return entregaAssembler.toCollectionDTO(entregaRepository.findAll());
	}
	
	@GetMapping("{id}")
	public ResponseEntity<EntregaDTO> buscar(@PathVariable Long id){
		return entregaRepository.findById(id)
				.map(entrega -> ResponseEntity.ok(entregaAssembler.toDTO(entrega)))
				.orElse(ResponseEntity.notFound().build());
	}
}
