package com.github.milton.assembleia.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.milton.assembleia.exception.RequestException;
import com.github.milton.assembleia.model.entity.Associado;
import com.github.milton.assembleia.model.repository.AssociadoRepository;
import com.github.milton.assembleia.service.AssociadoService;

@Service
public class AssociadoServiceImpl implements AssociadoService {

	AssociadoRepository associadoRepository;
	
	@Autowired
	public AssociadoServiceImpl(AssociadoRepository associadoRepository) {
		this.associadoRepository = associadoRepository;
	}
	
	@Override
	public Associado cadastrarAssociado(Associado associado) {
		Associado associadoSalvo = associadoRepository.save(associado);
		return associadoSalvo;
	}

	@Override
	public Associado atualizarAssociado(Associado associado) {
		Associado associadoSalvo = associadoRepository.save(associado);
		return associadoSalvo;
	}

	@Override
	public void deletarAssociado(Long codigoAssociado) {
		associadoRepository.delete(Associado.builder().id(codigoAssociado).build());
	}

	@Override
	public Associado buscarAssociado(Long codigoAssociado) {
		Optional<Associado> associado = associadoRepository.findById(codigoAssociado);
		if(associado.isPresent()) {
			return associado.get();
		}
		throw new RequestException("Nenhum Associado Encontrado com o Id informado");
	}

	
	
}
