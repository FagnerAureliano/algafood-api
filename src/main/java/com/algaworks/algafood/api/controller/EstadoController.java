package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.Estado;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EstadoController{

    @Autowired
    public EstadoService estadoService;

    @GetMapping("estados")
    public List<Estado> listar(){
        return estadoService.listar();
    }
    @PostMapping("estados")
    public ResponseEntity<Estado> salvar(@RequestBody Estado estado){
        estadoService.salvar(estado);
        return ResponseEntity.ok(estado);
    }

    @GetMapping("estados/{id}")
    public ResponseEntity<Optional<Estado>> buscarPorId(@PathVariable Long id){
        return estadoService.buscarPorId(id);
    }
    @PutMapping("estados/{id}")
    public ResponseEntity<Optional<Estado>> atualizar(@PathVariable Long id, @RequestBody Estado estado){
     try{
        estadoService.atualizar(id, estado);
        return ResponseEntity.ok().build();
     }catch (
    EntidadeNaoEncontradaException e){
        return ResponseEntity.notFound().build();
    }
    }
    @DeleteMapping("estados/{id}")
    public ResponseEntity<Optional<Estado>> deletar(@PathVariable long id){
     try{
        estadoService.remover(id);
        return ResponseEntity.noContent().build();
    }catch (EntidadeNaoEncontradaException e){
        return ResponseEntity.notFound().build();
    }catch (
    EntidadeEmUsoException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}

}
