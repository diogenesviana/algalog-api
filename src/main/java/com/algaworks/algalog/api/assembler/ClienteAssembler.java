package com.algaworks.algalog.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algalog.api.model.ClienteDTO;
import com.algaworks.algalog.domain.model.Cliente;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ClienteAssembler {
	
	private ModelMapper modelMapper;
	
	public ClienteDTO toDTO(Cliente cliente) {
		return modelMapper.map(cliente, ClienteDTO.class);
	}
	
	public List<ClienteDTO> toCollectionDTO(List<Cliente> clientes){
		return clientes.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}
	
	public Cliente toEntity(ClienteDTO cliente) {
		return modelMapper.map(cliente, Cliente.class);
	}
	
	public List<Cliente> toCollectionEntity(List<ClienteDTO> clientes){
		return clientes.stream()
				.map(this::toEntity)
				.collect(Collectors.toList());
	}
}
