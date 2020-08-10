package com.github.milton.assembleia.model.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.milton.assembleia.model.entity.Associado;
import com.github.milton.assembleia.model.entity.Pauta;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class PautaRepositoryTest {

    @Autowired
	private PautaRepository pautaRepository;


    @Test
    @DisplayName("Deve salvar uma nova Pauta.")
    public void savePautaTest() {

    	Pauta pauta =  Pauta.builder().nome("Aumento de taxas").assunto("Será discutido o aumento de taxas").build();
    	
    	Pauta saved = pautaRepository.save(pauta);
    	
    	assertThat(saved.getId()).isNotNull();
    }
    

    Pauta getNewPauta() {
    	return  Pauta.builder().nome("Aumento de taxas").assunto("Será discutido o aumento de taxas").id(7L).build();
    }
	
	
}
