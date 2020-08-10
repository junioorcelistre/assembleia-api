package com.github.milton.assembleia.model.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VotoDto {

	Long id;
	@NotEmpty
	String status;
	@NotEmpty
	Long associadoId;
	@NotEmpty
	Long pautaId;
	
	
}
