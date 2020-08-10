package com.github.milton.assembleia.model.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.milton.assembleia.model.entity.Associado;
import com.github.milton.assembleia.model.entity.Pauta;
import com.github.milton.assembleia.model.entity.Voto;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class VotoRepositoryTest {

	@Autowired
	private VotoRepository votoRepository;

	@Autowired
    TestEntityManager entityManager;
	
	@Test
	@DisplayName("Deve salvar um Voto.")
	public void saveVotoTest() {
		
		Voto voto = Voto.builder()
				.associado(getAssociado())
				.pauta(getPauta())
				.build();

		Voto votoSalvo = votoRepository.save(voto);
		
		assertThat(votoSalvo.getId()).isNotNull();
	}

	private Associado getAssociado() {
		return Associado.builder().id(1L).build();
	}
	
	private Pauta getPauta() {
		return Pauta.builder().id(1L).build();
	}
	
}
