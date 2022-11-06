package com.algaworks.algalog.domain.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algalog.api.assembler.ClienteAssembler;
import com.algaworks.algalog.api.model.ClienteDTO;
import com.algaworks.algalog.domain.exception.NegocioException;
import com.algaworks.algalog.domain.model.Cliente;
import com.algaworks.algalog.domain.repository.ClienteRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CatalogoClienteService {
	

	private ClienteRepository clienteRepository;
	private ClienteAssembler clienteAssembler;
	
	@Transactional
	public Cliente salvar(Cliente cliente) {
		boolean emailEmUso = clienteRepository.findByEmail(cliente.getEmail())
				.stream()
				.anyMatch(clienteExistente -> !clienteExistente.equals(cliente));
		if(emailEmUso) {
			throw new NegocioException("Já existe um cliente cadastrado com esse email.");
		}
		return clienteRepository.save(cliente);
	}
	
	@Transactional
	public void deletar (Long id) {
		clienteRepository.deleteById(id);
	}
	
	public Optional<Cliente> findById(Long id) {
		return clienteRepository.findById(id);
	}
	
	public Cliente buscar(Long id) {
		return clienteRepository.findById(id)
				.orElseThrow(() -> new NegocioException("Cliente não encontrado"));
		
	}
	
	public Cliente atualizar (Long id, ClienteDTO dto) {
		Optional<Cliente> clienteOpt = findById(id);
		Cliente cliente = new Cliente();
		if(clienteOpt.isPresent()) {
			dto.setId(id);
			cliente = clienteAssembler.toEntity(dto);
			clienteRepository.save(cliente);
			return cliente;
		} else {
			throw new NegocioException("Cliente não encontrado");
		}
		
	}
}
