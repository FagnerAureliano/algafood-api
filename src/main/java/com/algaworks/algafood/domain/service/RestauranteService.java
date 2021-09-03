package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.model.Restaurante;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.utils.MessageUtil;
import com.algaworks.algafood.utils.PropertyExtractorUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestauranteService {

    @Autowired
    RestauranteRepository restauranteRepository;
    @Autowired
    CozinhaRepository cozinhaRepository;

    @Autowired
    private MessageUtil messageUtil;


    private final String defaultError = "database.not_found";
    private final String defaultObject = "Objeto";


    public List<Restaurante> listar( List<String> fieldList) {
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




    public List<Object> getFields(List<?> list, List<String> fieldList) {
        return fieldList == null || fieldList.isEmpty()
                ? (List<Object>) list
                : list.stream().map(item -> PropertyExtractorUtil.extract(item, fieldList)).collect(Collectors.toList());
    }

    public Object getFields(Object obj, List<String> fieldList) {
        if (obj != null) {
            return fieldList == null || fieldList.isEmpty() ? obj : PropertyExtractorUtil.extract(obj, fieldList);
        }

        throw new EntityNotFoundException(messageUtil.getMessage(defaultError, defaultObject));
    }

    public Object getFields(Object obj, List<String> fieldList, String errorMessage) {
        if (obj != null) {
            return fieldList == null || fieldList.isEmpty() ? obj : PropertyExtractorUtil.extract(obj, fieldList);
        }

        throw new EntityNotFoundException(messageUtil.getMessage(defaultError, errorMessage));
    }

    public Object getFields(Optional<?> obj, List<String> fieldList) {
        if (obj.isPresent()) {
            return fieldList == null || fieldList.isEmpty() ? obj.get() : PropertyExtractorUtil.extract(obj.get(), fieldList);
        }

        throw new EntityNotFoundException(messageUtil.getMessage(defaultError, defaultObject));
    }

    public Object getFields(Optional<?> obj, List<String> fieldList, String errorMessage) {
        if (obj.isPresent()) {
            return fieldList == null || fieldList.isEmpty() ? obj.get() : PropertyExtractorUtil.extract(obj.get(), fieldList);
        }

        throw new EntityNotFoundException(messageUtil.getMessage(defaultError, errorMessage));
    }
}
