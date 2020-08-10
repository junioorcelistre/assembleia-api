package com.github.milton.assembleia.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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
import com.github.milton.assembleia.model.entity.Pauta;
import com.github.milton.assembleia.model.repository.PautaRepository;
import com.github.milton.assembleia.service.impl.PautaServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class PautaServiceImplTest {

	@InjectMocks
	PautaServiceImpl service;

	@Mock
	PautaRepository repository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	@DisplayName("Deve criar uma nova Pauta")
	public void criarPautaTest() {
		// cenario
		Pauta pauta = criaUmaNovaPauta();

		when(repository.existsByNome(Mockito.anyString())).thenReturn(false);
		when(repository.save(pauta)).thenReturn(Pauta.builder().id(1l).assunto(pauta.getAssunto()).nome(pauta.getNome()).build());

		// execucao
		Pauta pautaSalva = service.criarPauta(pauta);

		// verificacao
		assertThat(pautaSalva.getId()).isNotNull();
		assertThat(pautaSalva.getAssunto()).isEqualTo("implentação de novas tecnologias");
		assertThat(pautaSalva.getNome()).isEqualTo("Pauta 2020");

	}

	@Test
	@DisplayName("Deve lançar erro ao cadastrar Pauta com o nome já existente")
	public void excecaoAoCriarPauta() {
		// cenario
		Pauta pauta = criaUmaNovaPauta();

		when(repository.existsByNome(Mockito.anyString())).thenReturn(true);

		// execucao
		Throwable exception = Assertions.catchThrowable(() -> service.criarPauta(pauta));

		// verificacoes
		assertThat(exception).isInstanceOf(RequestException.class).hasMessage("Pauta já cadastrada");

		Mockito.verify(repository, Mockito.never()).save(pauta);
	}

	private Pauta criaUmaNovaPauta() {
		return Pauta.builder().assunto("implentação de novas tecnologias").nome("Pauta 2020").build();
	}

}
