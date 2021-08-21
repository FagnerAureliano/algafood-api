package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.Restaurante;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RestauranteController {

    @Autowired
    RestauranteService restauranteService;

    @GetMapping("restaurantes")
    public List<Restaurante> listar(){
        return restauranteService.listar();
    }
    @GetMapping("restaurantes/{id}")
    public ResponseEntity<Optional<Restaurante>> buscarPorId(@PathVariable Long id){
        return restauranteService.buscarPorId(id);
    }
    @PostMapping("restaurantes")
    public ResponseEntity<?> salvar(@RequestBody Restaurante restaurante){
        try {
            restauranteService.salvar(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("restaurantes/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante){

        try {
            restauranteService.atualizar(id, restaurante);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("restaurantes/{id}")
    public ResponseEntity<Optional<Restaurante>> deletar(@PathVariable Long id){
        try{
            restauranteService.remover(id);
        }catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }catch (EntidadeEmUsoException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return null;
    }
}
