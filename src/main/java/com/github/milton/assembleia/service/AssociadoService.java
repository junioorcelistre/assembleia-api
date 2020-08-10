package com.github.milton.assembleia.service;

import com.github.milton.assembleia.model.entity.Associado;

public interface AssociadoService {

	Associado cadastrarAssociado(Associado associado);
	Associado atualizarAssociado(Associado associado);
	void deletarAssociado(Long codigoAssociado);
	Associado buscarAssociado(Long codigoAssociado);
	
}
