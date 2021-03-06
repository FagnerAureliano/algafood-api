package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.api.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> ,
        JpaSpecificationExecutor<Restaurante>,
        QuerydslPredicateExecutor<Restaurante>
{
}
