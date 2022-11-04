package com.algaworks.algalog.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algalog.api.assembler.ClienteAssembler;
import com.algaworks.algalog.api.model.ClienteDTO;
import com.algaworks.algalog.domain.exception.NegocioException;
import com.algaworks.algalog.domain.model.Cliente;
import com.algaworks.algalog.domain.repository.ClienteRepository;
import com.algaworks.algalog.domain.service.CatalogoClienteService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {


	private CatalogoClienteService clienteService;
	private ClienteRepository clienteRepository;
	private ClienteAssembler clienteAssembler;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ClienteDTO adicionar(@Valid @RequestBody ClienteDTO clienteDTO) {
		Cliente clienteConverter = clienteAssembler.toEntity(clienteDTO);
		Cliente cliente = clienteService.salvar(clienteConverter);
		return clienteAssembler.toDTO(cliente);
	}

	@GetMapping
	public List<ClienteDTO> listar() {
		return clienteAssembler.toCollectionDTO(clienteRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClienteDTO> buscar(@PathVariable Long id) {
		return clienteRepository.findById(id)
				.map(cliente -> ResponseEntity.ok(clienteAssembler.toDTO(cliente)))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ClienteDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ClienteDTO clienteDTO){
		Cliente cliente = clienteService.atualizar(id, clienteDTO);
		ClienteDTO dto = clienteAssembler.toDTO(cliente);
		return ResponseEntity.ok(dto);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		clienteService.deletar(id);
		return ResponseEntity.ok().build();
	}
}
