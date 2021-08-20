package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.api.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository  extends JpaRepository<Estado, Long> {
}
