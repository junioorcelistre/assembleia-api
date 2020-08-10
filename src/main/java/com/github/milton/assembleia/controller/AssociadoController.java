package com.github.milton.assembleia.controller;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.milton.assembleia.model.dto.AssociadoDto;
import com.github.milton.assembleia.model.entity.Associado;
import com.github.milton.assembleia.service.AssociadoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/associados")
@Slf4j
public class AssociadoController {

	@Autowired
	AssociadoService associadoService;

	@Autowired
	ModelMapper modalMapper;

	@Operation(summary = "Realiza o Cadastro de um novo associado")
	@ApiResponse(responseCode = "201", description = "Associado foi cadastrado com sucesso", content = @Content(mediaType = "application/json"))
	@PostMapping(headers = "X-API-Version=v1")
	ResponseEntity<AssociadoDto> criarAssociado(@RequestBody @Valid AssociadoDto dto) {
		log.info("Cadastrando associado: {}", dto.getNome());
		Associado associado = associadoService.cadastrarAssociado(modalMapper.map(dto, Associado.class));
		AssociadoDto associadoDto = modalMapper.map(associado, AssociadoDto.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(associadoDto);
	}

	@Operation(summary = "Atualiza os dados do associado")
	@ApiResponse(responseCode = "200", description = "Associado foi atualizado com sucesso", content = @Content(mediaType = "application/json"))
	@PutMapping(headers = "X-API-Version=v1")
	ResponseEntity<AssociadoDto> atualizarAssociado(@RequestBody @Valid AssociadoDto dto) {
		log.info("Cadastrando associado: {}", dto.getNome());
		Associado associado = associadoService.atualizarAssociado(modalMapper.map(dto, Associado.class));
		AssociadoDto associadoDto = modalMapper.map(associado, AssociadoDto.class);
		return ResponseEntity.status(HttpStatus.OK).body(associadoDto);
	}

	@Operation(summary = "Atualiza os dados do associado")
	@ApiResponse(responseCode = "200", description = "Associado foi atualizado com sucesso", content = @Content(mediaType = "application/json"))
	@GetMapping(path = "{id}", headers = "X-API-Version=v1")
	ResponseEntity<AssociadoDto> buscarAssociado(@PathParam("id") Long id) {
		log.info("Buscado associado");
		Associado associado = associadoService.buscarAssociado(id);
		AssociadoDto associadoDto = modalMapper.map(associado, AssociadoDto.class);
		return ResponseEntity.status(HttpStatus.OK).body(associadoDto);
	}

	@Operation(summary = "Deleta o associado")
	@ApiResponse(responseCode = "200", 
	description = "Associado foi atualizado com sucesso", 
	content = @Content(mediaType = "application/json"))
	@DeleteMapping(path = "{id}", headers = "X-API-Version=v1")
	ResponseEntity<Void> deletarAssociado(@PathParam("id") Long id){
		log.info("Deletando associado");
		associadoService.deletarAssociado(id);
		return ResponseEntity.noContent().build();
	}

}
