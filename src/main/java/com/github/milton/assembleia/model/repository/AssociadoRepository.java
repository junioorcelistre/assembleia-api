package com.github.milton.assembleia.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.milton.assembleia.model.entity.Associado;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long> {

}
