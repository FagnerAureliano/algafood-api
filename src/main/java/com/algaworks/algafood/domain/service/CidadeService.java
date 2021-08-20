package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.model.Cidade;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@Service
public class CidadeService{

    @Autowired
    public CidadeRepository cidadeRepository;

    public List<Cidade> listar(){
        return cidadeRepository.findAll();
    }
    public ResponseEntity<Optional<Cidade>> buscarPorId(long id){
        try {
            Optional<Cidade> cidade = cidadeRepository.findById(id);
            return ResponseEntity.ok(cidade);
        }catch(EntidadeNaoEncontradaException e){
            throw new EntidadeNaoEncontradaException(String.format("Cozinha com o id %d não foi encontrada."));
        }
    }
    public ResponseEntity<Cidade> salvar(Cidade cidade){
        cidadeRepository.save(cidade);
        return ResponseEntity.ok(cidade);
    }
    public void atualizar (Long id, Cidade cidade){
        try {
            boolean existCidade = cidadeRepository.existsById(id);
            if(existCidade){
                Cidade cidadeTemp = new Cidade();
                BeanUtils.copyProperties(cidadeTemp, cidade);
                cidadeTemp.setId(id);
                cidadeRepository.save(cidadeTemp);
            }else {
                throw new EntidadeNaoEncontradaException(String.format("Cidade com o id %d não foi encontrado.", id));
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    public ResponseEntity<Optional<Cidade>> remover(Long id) {
        try {
            boolean existsCidade = cidadeRepository.existsById(id);
            if (existsCidade) {
                cidadeRepository.deleteById(id);
                return ResponseEntity.ok().build();
            } else {
                throw new EntidadeNaoEncontradaException(String.format("Cidade com o id %d não foi encontrado.", id));
            }
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Cidade de código %d não pode ser removida, pois está em uso.", id));
        }
    }
}
