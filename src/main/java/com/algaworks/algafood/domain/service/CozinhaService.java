package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.model.Cozinha;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CozinhaService {

    @Autowired
    CozinhaRepository cozinhaRepository;

    public List<Cozinha> listar(){
        return cozinhaRepository.findAll();
    }
    public Cozinha salvar(Cozinha cozinha){
        return cozinhaRepository.save(cozinha);
    }


    public ResponseEntity<Optional<Cozinha>>  buscarPorId(Long id){
        Optional<Cozinha> cozinha = cozinhaRepository.findById(id);
//        Redireciona
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.LOCATION, "http://localhost:8086/cozinhas");
//        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();

//         return ResponseEntity.status(HttpStatus.OK).body(cozinha);
        if(cozinha.isPresent()){
            return ResponseEntity.ok(cozinha);
        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.notFound().build();
    }


    public void atualizar(Long id, Cozinha cozinha){
        try{
            Optional<Cozinha> tmp = cozinhaRepository.findById(id);
            if(tmp.isPresent()) {
                Cozinha cozinhaTemp = Cozinha.builder().id(id).build();
                cozinhaTemp.setNome(cozinha.getNome());
                cozinhaRepository.save(cozinhaTemp);
            }
            throw new EntidadeNaoEncontradaException(String.format("Cozinha de código %d não encontrada.", id));
        }catch (EntidadeNaoEncontradaException e){
            throw new EntidadeEmUsoException(String.format("Cozinha de código %d não pode ser removida, pois está em uso.", id));
        }

    }
    public void remover( Long id){
        try {
            Optional<Cozinha> cozinhaTemp = cozinhaRepository.findById(id);
            if(cozinhaTemp.isPresent()){
                cozinhaRepository.deleteById(cozinhaTemp.get().getId());
            }else{
                throw new EntidadeNaoEncontradaException(String.format("Cozinha de código %d não encontrada.", id));
            }
        }catch (DataIntegrityViolationException e){
          throw new EntidadeEmUsoException(String.format("Cozinha de código %d não pode ser removida, pois está em uso.", id));
        }
    } 
}