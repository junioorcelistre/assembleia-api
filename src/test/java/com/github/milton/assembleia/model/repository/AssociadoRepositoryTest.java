package com.github.milton.assembleia.model.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.milton.assembleia.model.entity.Associado;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class AssociadoRepositoryTest {

	
	@Autowired
	AssociadoRepository associadoRepository;
	
	@Autowired
    TestEntityManager entityManager;
	
	
    @Test
    @DisplayName("Deve salvar um novo Associado.")
    public void savePautaTest() {
    	
    	Associado associado =  Associado.builder().nome("Jorge").cpf("642.217.170-68").build();
    	
    	Associado saved = associadoRepository.save(associado);

    	assertThat(saved.getId()).isNotNull();
    }
    
    @Test
    @DisplayName("Deve deletar um Associado")
    public void deleteAssociadoTest(){

    	Associado associado = getNewAssociado();
        entityManager.persist(associado);
        Associado foundAssociado = entityManager.find( Associado.class, associado.getId() );

        associadoRepository.delete(foundAssociado);

        Associado deletedBook = entityManager.find(Associado.class, associado.getId());
        assertThat(deletedBook).isNull();
    }
    
    
    @Test
    @DisplayName("Deve obter um Associado por id")
    public void findByIdTest(){
        //cenário
        Associado associado = getNewAssociado();
        entityManager.persist(associado);

        //execucao
        Optional<Associado> foundAssociado = associadoRepository.findById(associado.getId());

        //verificacoes
        assertThat(foundAssociado.isPresent()).isTrue();
    }
    
    
    Associado getNewAssociado() {
    	return Associado.builder().cpf("929.566.290-34").nome("Olivio").build();
    }
}
