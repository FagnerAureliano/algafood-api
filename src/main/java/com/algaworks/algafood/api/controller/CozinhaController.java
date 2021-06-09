package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CozinhaController {

    @Autowired
    public CozinhaRepository repository;
    @Autowired
    public CadastroCozinhaService cadastroCozinhaService;

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
        cadastroCozinhaService.salvar(cozinha);
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

    @DeleteMapping("cozinhas/{id}")
    public ResponseEntity<Optional<Cozinha>> remover (@PathVariable Long id){
    try {
        Optional<Cozinha> cozinhaTemp = repository.findById(id);
        if(cozinhaTemp.isPresent()){
            repository.deleteById(cozinhaTemp.get().getId());
            return ResponseEntity.noContent().build();
        }
            return  ResponseEntity.notFound().build();
        }catch (DataIntegrityViolationException e){
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }

}
