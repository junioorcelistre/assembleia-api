package com.github.milton.assembleia.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.milton.assembleia.exception.RequestException;
import com.github.milton.assembleia.model.dto.PautaDto;
import com.github.milton.assembleia.model.entity.Pauta;
import com.github.milton.assembleia.service.PautaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/pautas")
@Slf4j
public class PautaController {

	@Autowired
	PautaService pautaService;
	
	@Autowired
	ModelMapper modalMapper;

	@Operation(summary = "Retorna todas as Pautas cadastradas")
	@ApiResponse(responseCode = "200", 
	description = "Foram encontradas Pautas", 
	content = @Content(mediaType = "application/json"))
	@GetMapping(headers = "X-API-Version=v1")
	ResponseEntity<List<PautaDto>> buscarPautas() {
		log.info("Buscando Pautas Cadastradas");
		List<PautaDto> pautas = pautaService.buscarPautas()
				.stream()
				.map(pauta -> modalMapper.map(pauta, PautaDto.class))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(pautas);
	}

	@Operation(summary = "Cadastra uma nova Pauta")
	@ApiResponses( value =  {
		@ApiResponse(responseCode = "201", 
	description = "Pauta foi criada", 
	content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "400",
		description = "Pauta já cadastrada",
		content = { @Content(mediaType = "application/json", 
		schema = @Schema(implementation = RequestException.class))})
	})
	@PostMapping(headers = "X-API-Version=v1")
	ResponseEntity<PautaDto> criarPauta(@RequestBody @Valid PautaDto pautaDto) {
		log.info("Criando pauta: {}", pautaDto.getNome());
		Pauta pauta = pautaService.criarPauta(modalMapper.map(pautaDto, Pauta.class));
		PautaDto dto = modalMapper.map(pauta, PautaDto.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
	}

	@Operation(summary = "Realiza o inicio de sessão de uma Pauta")
	@ApiResponses(value =  {
			@ApiResponse (responseCode = "200", 
	description = "Sessão foi iniciada com sucesso"),
			@ApiResponse(responseCode = "400",
			description = "Pauta não encontrada",
			content = { @Content(mediaType = "application/json", 
			schema = @Schema(implementation = RequestException.class))})
			})
	@PutMapping(path = "/iniciar-sessao", headers = "X-API-Version=v1")
	ResponseEntity<Void> abrirSessao(@RequestParam("codigo") Long codigoPauta, @RequestParam(name = "duracao", defaultValue = "1") Long duracaoSessao) {
		log.info("Iniciando Sessão da pauta: {}", codigoPauta);
		pautaService.iniciarSessao(codigoPauta, duracaoSessao);
		return ResponseEntity.noContent().build(); 
	}

}
