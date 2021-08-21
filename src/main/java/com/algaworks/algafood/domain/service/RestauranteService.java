package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.model.Cozinha;
import com.algaworks.algafood.api.model.Estado;
import com.algaworks.algafood.api.model.Restaurante;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
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
        }else{
            throw new EntidadeNaoEncontradaException(String.format("Cozinha com o id %d não foi encontrada.", cozinhaId));
        }
    }
    public void atualizar(Long id, Restaurante restaurante){
        try{
            boolean restauranteTemp = restauranteRepository.existsById(id);

            if (restaurante.getCozinha() != null) {
                boolean cozinhaTemp = cozinhaRepository.existsById(restaurante.getCozinha().getId());
                if (!cozinhaTemp){
                    throw new EntidadeNaoEncontradaException(String.format("Cozinha não foi encontrada."));
                }
            }

            if(restauranteTemp ){
//                boolean cozinhaTemp = cozinhaRepository.existsById(restaurante.getCozinha().getId());
//                if(cozinhaTemp){
                Restaurante resTemp = new Restaurante();
                BeanUtils.copyProperties(resTemp, restaurante);
                   resTemp.setId(id);
//                    Restaurante resTemp = Restaurante.builder().id(id).build();
//                    resTemp.setNome(restaurante.getNome());
//                    resTemp.setTaxaFrete(restaurante.getTaxaFrete());
//                    resTemp.setCozinha(restaurante.getCozinha());
                    restauranteRepository.save(resTemp);
//                }
            }else{
                throw new EntidadeNaoEncontradaException(String.format("Restaurante com o id %d não foi encontrado.", id));
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format("Restaurante não encontrado com o id:", id));
        }
    }
    public void remover(Long id){
        try{
            Optional<Restaurante> restauranteTemp = restauranteRepository.findById(id);
            if(restauranteTemp.isPresent()){
                restauranteRepository.deleteById(restauranteTemp.get().getId());
            }else{
                throw new EntidadeNaoEncontradaException(String.format("Restaurante com o id %d não foi encontrado."));
            }
        }catch (DataIntegrityViolationException e){
          throw new EntidadeEmUsoException(String.format("Restaurante de código %d não pode ser removida, pois está em uso.", id));
        }
    }
}
