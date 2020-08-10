package com.github.milton.assembleia.service;

import java.util.List;

import com.github.milton.assembleia.model.entity.Pauta;

public interface PautaService {

	public Pauta criarPauta(Pauta pauta);
	
	public List<Pauta> buscarPautas();
	
	void iniciarSessao(Long id, Long duracao);

}
