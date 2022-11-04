package com.algaworks.algalog.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algalog.api.model.DestinatarioDTO;
import com.algaworks.algalog.api.model.EntregaDTO;
import com.algaworks.algalog.domain.exception.NegocioException;
import com.algaworks.algalog.domain.model.Destinatario;
import com.algaworks.algalog.domain.model.Entrega;
import com.algaworks.algalog.domain.repository.EntregaRepository;
import com.algaworks.algalog.domain.service.SolicitacaoEntregaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas")
public class EntregaController {
	
	@Autowired
	private SolicitacaoEntregaService solicitacaoEntregaService;
	
	@Autowired
	private EntregaRepository entregaRepository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Entrega solicitar(@Valid @RequestBody Entrega entrega) throws NegocioException {
		return solicitacaoEntregaService.solicitar(entrega);	
	}
	
	@GetMapping
	public List<Entrega> listar(){
		return entregaRepository.findAll();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<EntregaDTO> buscar(@PathVariable Long id){
		return entregaRepository.findById(id)
				.map(entrega -> {
					EntregaDTO entregaDTO = new EntregaDTO();
					entregaDTO.setId(entrega.getId());
					entregaDTO.setNomeCliente(entrega.getCliente().getNome());
					
					Destinatario destinatario = entrega.getDestinatario();
					DestinatarioDTO destinatarioDTO = new DestinatarioDTO();
					
					destinatarioDTO.setNome(destinatario.getNome());
					destinatarioDTO.setLogradouro(destinatario.getLogradouro());
					destinatarioDTO.setBairro(destinatario.getBairro());
					destinatarioDTO.setComplemento(destinatario.getComplemento());
					destinatarioDTO.setNumero(destinatario.getNumero());
					entregaDTO.setDestinatario(destinatarioDTO);
					
					entregaDTO.setTaxa(entrega.getTaxa());
					entregaDTO.setDataPedido(entrega.getDataPedido());
					entregaDTO.setStatus(entrega.getStatus());
					entregaDTO.setDataFinalizacao(entrega.getDataFinalizacao());
					
					return ResponseEntity.ok(entregaDTO);	
				}).orElse(ResponseEntity.notFound().build());
	}
}
