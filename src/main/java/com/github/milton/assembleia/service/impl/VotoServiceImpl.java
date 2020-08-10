package com.github.milton.assembleia.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.milton.assembleia.exception.RequestException;
import com.github.milton.assembleia.model.entity.Pauta;
import com.github.milton.assembleia.model.entity.Voto;
import com.github.milton.assembleia.model.repository.AssociadoRepository;
import com.github.milton.assembleia.model.repository.PautaRepository;
import com.github.milton.assembleia.model.repository.VotoRepository;
import com.github.milton.assembleia.service.VotoService;

@Service
public class VotoServiceImpl implements VotoService {

	@Autowired
	VotoRepository votoRepository;

	@Autowired
	PautaRepository pautaRepository;

	@Autowired
	AssociadoRepository associadoRepository;

	public Voto votar(Voto voto) {
		if (pautaRepository.existsById(voto.getPauta().getId())) {
			Pauta pauta = pautaRepository.getOne(voto.getPauta().getId());
			if (pauta.getAtivacao().before(pauta.getEncerramento())) {
				if (associadoRepository.existsById(voto.getAssociado().getId())) {
					return votoRepository.save(voto);
				}
				throw new RequestException("O Associado informado não foi encontrado");
			}
			throw new RequestException("A sessão dessa Pauta já foi encerrada");
		}
		throw new RequestException("Nenhuma pauta encontrada para o Id informado");
	}

	@Override
	public Map<String, Long> contabilizar(Long codigo) {
		Pauta pauta = Pauta.builder().id(codigo).build();
		if (!votoRepository.existsByPauta(pauta)) {
			throw new RequestException("Não existem votos para serem contabilizados");
		}
		List<Voto> votos = votoRepository.findByPauta(pauta);

		Map<String, Long> resultado = votos.stream()
				.collect(Collectors.groupingBy(Voto::getStatus, Collectors.counting()));

		return resultado;
	}
}
