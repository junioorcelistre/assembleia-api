package com.github.milton.assembleia.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.github.milton.assembleia.exception.RequestException;
import com.github.milton.assembleia.model.dto.ResponseCpf;
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

	private static final String CPF_SERVICE = "https://user-info.herokuapp.com/users/{cpf}";

	public Voto votar(Voto voto) {
		if (pautaRepository.existsById(voto.getPauta().getId())) {
			Pauta pauta = pautaRepository.getOne(voto.getPauta().getId());
			if (pauta.getAtivacao().before(pauta.getEncerramento())) {
				if (associadoRepository.existsById(voto.getAssociado().getId())) {
					if(validaCpf(voto.getAssociado().getCpf())) {
						return votoRepository.save(voto);
					}
					throw new RequestException("O Associado não está apto para votar");
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

	private boolean validaCpf(String cpf) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<ResponseCpf> response = restTemplate.exchange(CPF_SERVICE, HttpMethod.GET, new HttpEntity(headers), ResponseCpf.class, cpf.replaceAll("[^0-9]", ""));
		System.out.println(response.getBody());
		if (response.getBody().getStatus().equalsIgnoreCase("ABLE_TO_VOTE"))
			return true;
		return false;
	}

}
