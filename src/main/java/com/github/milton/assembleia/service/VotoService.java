package com.github.milton.assembleia.service;

import java.util.Map;

import com.github.milton.assembleia.model.entity.Voto;

public interface VotoService {

	public Voto votar(Voto voto);

	Map<String, Long> contabilizar(Long codigo);
	
}
