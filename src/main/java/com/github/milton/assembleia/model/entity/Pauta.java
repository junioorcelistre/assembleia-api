package com.github.milton.assembleia.model.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Pauta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id; 
	
	@Column(name = "nome")
	String nome;
	
	@Column(name = "assunto")
	String assunto;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_ativacao")
	Date ativacao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_encerramento")
	Date encerramento;
	
}
