package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.Cidade;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CidadeController {

    @Autowired
    public CidadeService cidadeService;

    @GetMapping("cidades")
    public List<Cidade> listar(){
        return cidadeService.listar();
    }

    @GetMapping("cidades/{id}")
    public ResponseEntity<Optional<Cidade>> buscarPorId(@PathVariable Long id){
        return cidadeService.buscarPorId(id);
    }

    @PostMapping("cidades")
    public ResponseEntity<Cidade> cidade(@RequestBody Cidade cidade){
        cidadeService.salvar(cidade);
        return ResponseEntity.ok(cidade);
    }

    @PutMapping("cidades/{id}")
    public ResponseEntity<Optional<Cidade>>atualizar(@PathVariable Long id, @RequestBody Cidade cidade){
        try {
            cidadeService.atualizar(id, cidade);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }catch (EntidadeNaoEncontradaException e ){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("cidades/{id}")
    public ResponseEntity<Optional<Cidade>> remover(@PathVariable Long id){
        try {
            cidadeService.remover(id);
            return ResponseEntity.noContent().build();
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }catch (EntidadeEmUsoException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
