package com.github.milton.assembleia.controller;

import java.util.Map;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.milton.assembleia.exception.RequestException;
import com.github.milton.assembleia.model.dto.VotoDto;
import com.github.milton.assembleia.model.entity.Voto;
import com.github.milton.assembleia.service.VotoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/votos")
@Slf4j
public class VotoController {

	@Autowired
	VotoService votoService;

	@Autowired
	ModelMapper modalMapper;
	
	@Operation(summary = "Contabiliza o voto para os parametros especificados")
	@ApiResponses( value =  {
			@ApiResponse(responseCode = "200", 
		description = "Voto foi contabilizado", 
		content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400",
			description = "Valor recebido não compreende nenhuma pauta ou associado",
			content = { @Content(mediaType = "application/json", 
			schema = @Schema(implementation = RequestException.class))})
		})
	@PostMapping(headers = "X-API-Version=v1")
	ResponseEntity<VotoDto> votar(@RequestBody @Valid VotoDto votoDto) {
		log.info("Computando Voto na pauta: {}", votoDto.getPautaId());
		Voto voto = votoService.votar(modalMapper.map(votoDto, Voto.class));
		VotoDto dto = modalMapper.map(voto, VotoDto.class);
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}
	
	@Operation(summary = "Contabiliza os votos na Pauta especificada")
	@ApiResponses( value =  {
			@ApiResponse(responseCode = "200", 
		description = "Total de voto Sim e Não", 
		content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400",
			description = "Não existem votos na pauta para contabilizar",
			content = { @Content(mediaType = "application/json", 
			schema = @Schema(implementation = RequestException.class))})
		})
	@GetMapping(path = "/{codigo}/contabilizar",  headers = "X-API-Version=v1")
	ResponseEntity<Map<String, Long>> contabilizarVotos(@PathParam("codigo") Long codigoPauta){
		log.info("Contabilizando Votos da pauta: {}", codigoPauta);
		Map<String, Long> response = votoService.contabilizar(codigoPauta);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
