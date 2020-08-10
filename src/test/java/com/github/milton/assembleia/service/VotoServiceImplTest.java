package com.github.milton.assembleia.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.milton.assembleia.exception.RequestException;
import com.github.milton.assembleia.model.entity.Associado;
import com.github.milton.assembleia.model.entity.Pauta;
import com.github.milton.assembleia.model.entity.Voto;
import com.github.milton.assembleia.model.repository.AssociadoRepository;
import com.github.milton.assembleia.model.repository.PautaRepository;
import com.github.milton.assembleia.model.repository.VotoRepository;
import com.github.milton.assembleia.service.impl.VotoServiceImpl;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class VotoServiceImplTest {
	
	@InjectMocks
	VotoServiceImpl service;

	@Mock
	VotoRepository votoRepository;
	
	@Mock
	PautaRepository pautaRepository;
	
	@Mock
	AssociadoRepository associadoRepository;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	
	@Test
	@DisplayName("Deve conputar o voto")
	public void votarTest() {
		// cenario
		Voto voto = criaUmNovoVoto();
		Pauta pauta = Pauta.builder().assunto("Mock").id(1L).nome("Pauta 2020").ativacao(new Date(2020, 8, 05, 10, 10, 00)).encerramento(new Date(2020, 8, 05, 10, 20, 00)).build();
		Associado associado = Associado.builder().cpf("929.566.290-34").nome("Jorge").id(1L).build();

		when(pautaRepository.existsById(voto.getPauta().getId())).thenReturn(true);
		when(pautaRepository.getOne(voto.getPauta().getId())).thenReturn(pauta);
		when(associadoRepository.existsById(voto.getAssociado().getId())).thenReturn(true);
		when(votoRepository.save(voto)).thenReturn(Voto.builder().id(1l).associado(Associado.builder().id(1L).build()).pauta(Pauta.builder().id(1L).build()).status("Sim").build());

		// execucao
		Voto votoSalvo = service.votar(voto);

		// verificacao
		assertThat(votoSalvo.getId()).isNotNull();
		assertThat(votoSalvo.getStatus()).isEqualTo("Sim");
		assertThat(votoSalvo.getAssociado().getId()).isNotNull();
		assertThat(votoSalvo.getPauta().getId()).isNotNull();

	}
	
	
	@Test
	@DisplayName("Deve Erro de pauta não encontrada")
	public void votarErroDePautaNaoEncontradaTest() {
		// cenario
		Voto voto = criaUmNovoVoto();
		when(pautaRepository.existsById(voto.getPauta().getId())).thenReturn(false);

		// execucao
		Throwable exception = Assertions.catchThrowable(() -> service.votar(voto));
		
		// verificacao
		assertThat(exception).isInstanceOf(RequestException.class).hasMessage("Nenhuma pauta encontrada para o Id informado");
		Mockito.verify(votoRepository, Mockito.never()).save(voto);
	}
	
	
	@Test
	@DisplayName("Deve Erro de Sessão da pauta já foi encerrada")
	public void votarErroDeSessaoDaPautaEncerradaTest() {

		// cenario
		Voto voto = criaUmNovoVoto();
		Pauta pauta = Pauta.builder().assunto("Mock").id(1L).nome("Pauta 2020").ativacao(new Date(2020, 8, 05, 10, 20, 00)).encerramento(new Date(2020, 8, 05, 10, 10, 00)).build();
		
		when(pautaRepository.existsById(voto.getPauta().getId())).thenReturn(true);
		when(pautaRepository.getOne(voto.getPauta().getId())).thenReturn(pauta);
		

		// execucao
		Throwable exception = Assertions.catchThrowable(() -> service.votar(voto));
		
		// verificacao
		assertThat(exception).isInstanceOf(RequestException.class).hasMessage("A sessão dessa Pauta já foi encerrada");
		Mockito.verify(votoRepository, Mockito.never()).save(voto);

	}
	
	
	@Test
	@DisplayName("Deve Erro de Associado não Encontrado")
	public void votarErroDeAssociadoNaoEncontradoTest() {

		// cenario
		Voto voto = criaUmNovoVoto();
		Pauta pauta = Pauta.builder().assunto("Mock").id(1L).nome("Pauta 2020").ativacao(new Date(2020, 8, 05, 10, 10, 00)).encerramento(new Date(2020, 8, 05, 10, 20, 00)).build();

		when(pautaRepository.existsById(voto.getPauta().getId())).thenReturn(true);
		when(pautaRepository.getOne(voto.getPauta().getId())).thenReturn(pauta);
		when(associadoRepository.existsById(voto.getAssociado().getId())).thenReturn(false);

		// execucao
		Throwable exception = Assertions.catchThrowable(() -> service.votar(voto));
		
		// verificacao
		assertThat(exception).isInstanceOf(RequestException.class).hasMessage("O Associado informado não foi encontrado");
		Mockito.verify(votoRepository, Mockito.never()).save(voto);

	}
	
	
	
	
	private Voto criaUmNovoVoto() {
		return Voto.builder().status("Sim").associado(Associado.builder().id(1L).build()).pauta(Pauta.builder().id(1L).build()).build();
	}
	
}
