package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormaPagamentoService {

    @Autowired
    public FormaPagamentoRepository formaPagamentoRepository;

    public List<FormaPagamento> listar(){
        return formaPagamentoRepository.findAll();
    }

}
