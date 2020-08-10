package com.github.milton.assembleia.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.milton.assembleia.exception.RequestException;
import com.github.milton.assembleia.model.entity.Pauta;
import com.github.milton.assembleia.model.repository.PautaRepository;
import com.github.milton.assembleia.service.PautaService;

@Service
public class PautaServiceImpl implements PautaService {
	
	@Autowired
	PautaRepository pautaRepository;

	public Pauta criarPauta(Pauta pauta) {
		  if( pautaRepository.existsByNome(pauta.getNome())){
			  throw new RequestException("Pauta já cadastrada");
		  }
		return pautaRepository.save(pauta);
	}

	public List<Pauta> buscarPautas() {
		return pautaRepository.findAll();
	}

	public void iniciarSessao(Long id, Long duracao) {
		if (pautaRepository.existsById(id)) {
			Pauta pauta = Pauta.builder()
					.id(id)
					.ativacao(new Timestamp(System.currentTimeMillis()))
					.encerramento(new Timestamp(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(duracao)))
					.build();
			pautaRepository.save(pauta);
		}else {
			  throw new RequestException("Pauta não cadastrada");
		} 
			
	}
}
