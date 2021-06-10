package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.model.Restaurante;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestauranteService {

    @Autowired
    RestauranteRepository restauranteRepository;
    @Autowired
    CozinhaRepository cozinhaRepository;

    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    public ResponseEntity<Optional<Restaurante>> buscarPorId(Long id) {
        Optional<Restaurante> restaurante = restauranteRepository.findById(id);
        if (restaurante.isPresent()) {
            return ResponseEntity.ok(restaurante);
        }
        return ResponseEntity.notFound().build();
    }

    public void salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        boolean cozinhaTemp = cozinhaRepository.existsById(cozinhaId);
        if (cozinhaTemp) {
            restauranteRepository.save(restaurante);
        }
        throw new EntidadeNaoEncontradaException(String.format("Cozinha com o id %d não foi encontrada.", cozinhaId));
    }
    public void atualizar(Long id, Restaurante restaurante){
        try{
        Optional<Restaurante> restauranteTemp = restauranteRepository.findById(id);
            if(restauranteTemp.isPresent()){
//                boolean cozinhaTemp = cozinhaRepository.existsById(restaurante.getCozinha().getId());
//                if(cozinhaTemp){
                    Restaurante resTemp = Restaurante.builder().id(id).build();
                    resTemp.setNome(restaurante.getNome());
                    resTemp.setTaxaFrete(restaurante.getTaxaFrete());
                    resTemp.setCozinha(restaurante.getCozinha());
                    restauranteRepository.save(resTemp);
//                }
            }
            throw new EntidadeNaoEncontradaException(String.format("Cozinha com o id %d não foi encontrada.",restaurante.getCozinha().getId()));
        }catch (EntidadeNaoEncontradaException e){
            throw new EntidadeNaoEncontradaException(String.format("Teste", id));
        }

    }
}
