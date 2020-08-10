package com.github.milton.assembleia.model.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.github.milton.assembleia.model.entity.converter.StringConverterToBoolean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor	
public class Voto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	@Convert(converter=StringConverterToBoolean.class)
	@Column(name = "voto")
	String status;
	
	@ManyToOne
	@JoinColumn(name = "associado_id")
	Associado associado;
	
	@ManyToOne
	@JoinColumn(name = "pauta_id")
	Pauta pauta;

}
