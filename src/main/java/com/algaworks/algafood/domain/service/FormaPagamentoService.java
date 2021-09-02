package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.model.FormaPagamento;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@Service
public class FormaPagamentoService {

    @Autowired
    public FormaPagamentoRepository formaPagamentoRepository;

    public List<FormaPagamento> listar(){
        return formaPagamentoRepository.findAll();
    }
    public Optional<FormaPagamento> buscarPorId(Long id){
        return formaPagamentoRepository.findById(id);
    }

    public ResponseEntity<FormaPagamento> salvar(FormaPagamento formaPagamento){
        formaPagamentoRepository.save(formaPagamento);
        return ResponseEntity.ok(formaPagamento);
    }
    public ResponseEntity<FormaPagamento> atualizar(FormaPagamento formaPagamento, Long id) throws InvocationTargetException, IllegalAccessException {
       FormaPagamento pagamento = formaPagamentoRepository.findById(id)
               .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Entidade não encontrada")));

            FormaPagamento formTemp = new FormaPagamento();
            BeanUtils.copyProperties(formTemp, formaPagamento);
            formaPagamentoRepository.save(formaPagamento);
            return ResponseEntity.ok(formaPagamento);

    }
    public void deletar (Long id){
        try{
            formaPagamentoRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format("Não existe com esse id: %d", id));
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException( String.format("Em uso"));
        }
    }
}
