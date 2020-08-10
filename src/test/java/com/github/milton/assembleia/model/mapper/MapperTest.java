package com.github.milton.assembleia.model.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.milton.assembleia.model.dto.AssociadoDto;
import com.github.milton.assembleia.model.dto.PautaDto;
import com.github.milton.assembleia.model.dto.VotoDto;
import com.github.milton.assembleia.model.entity.Associado;
import com.github.milton.assembleia.model.entity.Pauta;
import com.github.milton.assembleia.model.entity.Voto;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class MapperTest {

	ModelMapper modelMapper  = new ModelMapper();
	
	@Test
	@DisplayName("Deve efetuar o mapeamento da Entidade Pauta para o DTO")
	public void retornaPautaDtoMapeadoDePautaEntity() {
		PautaDto dto = new PautaDto();
		dto.setAssunto("testes");
		dto.setNome("teste");
		dto.setId(1L);
		Pauta entity = modelMapper.map(dto, Pauta.class);
		
		assertEquals(dto.getId(), entity.getId());
		assertEquals(dto.getAssunto(), entity.getAssunto());
		assertEquals(dto.getNome(), entity.getNome());
	}
	
	
	@Test
	@DisplayName("Deve efetuar o mapeamento do DTO PautaDTO para a Entidade")
	public void retornaPautaEntityMapeadoDePautaDto() {
		Pauta entity = new Pauta();
		entity.setAssunto("testes");
		entity.setNome("teste");
		entity.setId(1L);
		PautaDto dto = modelMapper.map(entity, PautaDto.class);
		
		assertEquals(dto.getId(), entity.getId());
		assertEquals(dto.getAssunto(), entity.getAssunto());
		assertEquals(dto.getNome(), entity.getNome());
	}
	
	@Test
	@DisplayName("Deve efetuar o mapeamento da Entidade Voto para o DTO")
	public void retornaVotoDtoMapeadoDeVotoEntity() {
		
		Voto entity = new Voto();
		entity.setId(1L);
		entity.setStatus("Sim");
		entity.setAssociado(Associado.builder().id(1L).build());
		entity.setPauta(Pauta.builder().id(1L).build());
		
		VotoDto dto = modelMapper.map(entity, VotoDto.class);
		
		assertEquals(dto.getStatus(), entity.getStatus());
		assertEquals(dto.getPautaId(), entity.getPauta().getId());
		assertEquals(dto.getAssociadoId(), entity.getAssociado().getId());
		assertEquals(dto.getId(), entity.getId());
		
	}
	
	
	@Test
	@DisplayName("Deve efetuar o mapeamento de VotoDto para a Entidade")
	public void retornaVotoEntityMapeadoDeVotoDto() {
		
		VotoDto dto = new VotoDto();
		dto.setId(1L);
		dto.setStatus("Sim");
		dto.setAssociadoId(1L);
		dto.setPautaId(1L);
		
		Voto entity = modelMapper.map(dto, Voto.class);
		
		assertEquals(dto.getStatus(), entity.getStatus());
		assertEquals(dto.getPautaId(), entity.getPauta().getId());
		assertEquals(dto.getAssociadoId(), entity.getAssociado().getId());
		assertEquals(dto.getId(), entity.getId());
		
	}
	
	

	@Test
	@DisplayName("Deve efetuar o mapeamento da Entidade Associado para o DTO")
	public void retornaAssociadoDtoMapeadoDeAssociadoEntity() {
		Associado entity = new Associado();
		entity.setId(1L);
		entity.setNome("Jorge");
		entity.setCpf("934.975.260-37");
		
		AssociadoDto dto = modelMapper.map(entity, AssociadoDto.class);
		
		assertEquals(dto.getCpf(), entity.getCpf());
		assertEquals(dto.getNome(), entity.getNome());
		assertEquals(dto.getId(), entity.getId());
	}
	
	
	@Test
	@DisplayName("Deve efetuar o mapeamento de AssociadoDto para a Entidade")
	public void retornaAssociadoEntityMapeadoDeAssociadoDto() {
		
		AssociadoDto dto = new AssociadoDto();
		dto.setId(1L);
		dto.setNome("Jorge");
		dto.setCpf("934.975.260-37");
		
		Associado entity = modelMapper.map(dto, Associado.class);
		
		assertEquals(dto.getCpf(), entity.getCpf());
		assertEquals(dto.getNome(), entity.getNome());
		assertEquals(dto.getId(), entity.getId());
		
	}
	
	
	
}
