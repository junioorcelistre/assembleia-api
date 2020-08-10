package com.github.milton.assembleia.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.milton.assembleia.model.entity.Pauta;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {
	
	boolean existsByNome(String nome);

	
	
}
