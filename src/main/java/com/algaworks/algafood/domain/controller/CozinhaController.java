package com.algaworks.algafood.domain.controller;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        if(cozinha.isPresent()){
            return ResponseEntity.ok(cozinha);
        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.notFound().build();
    }
    @PostMapping("cozinhas")
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha){
       repository.save(cozinha);
       return cozinha;
    }
    @PutMapping("cozinhas/{id}")
    public ResponseEntity<Optional<Cozinha>> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha){
        Optional<Cozinha> tmp = repository.findById(id);
        if(tmp.isPresent()){
            Cozinha cozinhaTemp = Cozinha.builder().id(id).build();
            cozinhaTemp.setNome(cozinha.getNome());
            repository.save(cozinhaTemp);

            return  ResponseEntity.ok(tmp);
        }
       return  ResponseEntity.notFound().build();
    }


}
