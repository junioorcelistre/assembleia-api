package com.github.milton.assembleia.model.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PautaDto {

	Long id;
	
	@NotEmpty
	String nome;
	
	@NotEmpty
	String assunto;
	
}
