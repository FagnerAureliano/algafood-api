package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.Cozinha;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CozinhaController {

    @Autowired
    public CadastroCozinhaService cadastroCozinhaService;

    @GetMapping("cozinhas")
    @ResponseBody
    public List<Cozinha> listar (){
        return cadastroCozinhaService.listar();
    }

    @GetMapping("cozinhas/{id}")
    public ResponseEntity<Optional<Cozinha>> buscar (@PathVariable Long id){
        return cadastroCozinhaService.buscarPorId(id);
    }
    @PostMapping("cozinhas")
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha){
        cadastroCozinhaService.salvar(cozinha);
       return cozinha;
    }

    @PutMapping("cozinhas/{id}")
    public ResponseEntity<Optional<Cozinha>> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha){
        return cadastroCozinhaService.atualizar(id, cozinha);
    }

    @DeleteMapping("cozinhas/{id}")
    public ResponseEntity<Optional<Cozinha>> remover (@PathVariable Long id){
        try {
            cadastroCozinhaService.remover(id);
            return ResponseEntity.noContent().build();
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }catch (EntidadeEmUsoException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
