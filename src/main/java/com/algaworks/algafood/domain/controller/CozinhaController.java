package com.algaworks.algafood.domain.controller;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CozinhaController {

    @Autowired
    public CozinhaRepository repository;

    @GetMapping("cozinhas")
    @ResponseBody
    public List<Cozinha> listar (){
        return repository.findAll();
    }

    @GetMapping("cozinhas/{id}")
    public ResponseEntity<Optional<Cozinha>> buscar (@PathVariable Long id){
        Optional<Cozinha> cozinha = repository.findById(id);

//        Redireciona
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.LOCATION, "http://localhost:8086/cozinhas");
//        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();

//         return ResponseEntity.status(HttpStatus.OK).body(cozinha);
           return ResponseEntity.ok(cozinha);
     }

}
