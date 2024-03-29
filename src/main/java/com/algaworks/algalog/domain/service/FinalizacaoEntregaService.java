package com.algaworks.algalog.domain.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.algaworks.algalog.domain.model.Entrega;
import com.algaworks.algalog.domain.repository.EntregaRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FinalizacaoEntregaService {
	
	private EntregaRepository entregaRepository;
	private BuscaEntregaService buscaEntregaService;
	
	@Transactional
	public void finalizar (Long id) {
		Entrega entrega = buscaEntregaService.buscar(id);
		entrega.finalizar(entrega);
		entregaRepository.save(entrega);
	}

	@Transactional
	public void cancelar(Long id) {
		Entrega entrega = buscaEntregaService.buscar(id);
		entrega.cancelar(entrega);
		entregaRepository.save(entrega);
		
	}
}
