package com.github.milton.assembleia.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.milton.assembleia.model.entity.Pauta;
import com.github.milton.assembleia.model.entity.Voto;


@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {

	Voto findByAssociadoIdAndPautaId(Long associadoId, Long pautaId);
	
	boolean existsByPauta(Pauta pauta);
	
	List<Voto> findByPauta(Pauta pauta);
	
}
