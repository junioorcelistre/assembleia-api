package com.github.milton.assembleia.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.milton.assembleia.model.dto.PautaDto;
import com.github.milton.assembleia.model.entity.Pauta;
import com.github.milton.assembleia.service.AssociadoService;
import com.github.milton.assembleia.service.PautaService;
import com.github.milton.assembleia.service.VotoService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class PautaControllerTest {

	static String PAUTA_API = "/pautas";
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	PautaService pautaService;
	
	@MockBean
	AssociadoService associadoService;
	
	@MockBean
	VotoService votoService;
	
	@Test
	@DisplayName("Deve criar a Pauta com sucesso")
	public void createPautaTest() throws Exception {
		
		PautaDto dto = createNewPauta();
		
		Pauta pauta = Pauta.builder().id(10L).nome("Novos valores").assunto("Sera decidido os novos valores").build();

		String json  = new ObjectMapper().writeValueAsString(dto);
		
		BDDMockito.given(pautaService.criarPauta(Mockito.any(Pauta.class))).willReturn(pauta);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
		.post(PAUTA_API)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)
		.header("X-API-Version", "v1")
		.content(json);
		
		mockMvc.perform(request)
		.andExpect(status().isCreated())
		.andExpect(jsonPath("id").isNotEmpty())
		.andExpect(jsonPath("nome").isNotEmpty());
		
	}
	
	
	@Test
	@DisplayName("Deve retornar um erro ao criar a Pauta sem os dados necessarios")
	public void createInvalidPautaTest() throws Exception {
		
		PautaDto dto = new PautaDto();
		
		Pauta pauta = Pauta.builder().id(10L).nome("Novos valores").assunto("Sera decidido os novos valores").build();
		
		String json  = new ObjectMapper().writeValueAsString(dto);
		
		BDDMockito.given(pautaService.criarPauta(Mockito.any(Pauta.class))).willReturn(pauta);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
		.post(PAUTA_API)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)
		.header("X-API-Version", "v1")
		.content(json);
		
		mockMvc.perform(request)
		.andExpect(status().isBadRequest());
		
	}
	
	
	public PautaDto createNewPauta() {
		return PautaDto.builder().nome("Novos valores").assunto("Sera decidido os novos valores").build();
	}
	
	
}
