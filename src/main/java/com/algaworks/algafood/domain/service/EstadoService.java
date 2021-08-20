package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.model.Estado;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@Service
public class EstadoService {

    @Autowired
    EstadoRepository estadoRepository;

    public List<Estado> listar(){
        return estadoRepository.findAll();
    }
    public ResponseEntity<Optional<Estado>> buscarPorId(Long id){

        Optional<Estado> estado = estadoRepository.findById(id);
        if(estado.isPresent()){
            return ResponseEntity.ok(estado);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<Estado> salvar(Estado estado){
        estadoRepository.save(estado);
        return ResponseEntity.ok(estado);
    }
    public void atualizar(Long id, Estado estado){
        try {
            Optional<Estado> estadoTemp = estadoRepository.findById(id);
            if (estadoTemp.isPresent()) {
                Estado estTemp = new Estado();
                BeanUtils.copyProperties(estTemp, estado);
                estTemp.setId(id);
                estadoRepository.save(estTemp);
            } else{
                throw new EntidadeNaoEncontradaException(String.format("Estado com o id %d não foi encontrada.",estado.getId()));
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    public ResponseEntity<Optional<Estado>> remover(Long id){
        try {
            Boolean estTemp = estadoRepository.existsById(id);
            estadoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }catch (EntidadeNaoEncontradaException e){
            throw new EntidadeNaoEncontradaException(String.format("Restaurante não encontrado com o id:", id));
        }
    }

}
