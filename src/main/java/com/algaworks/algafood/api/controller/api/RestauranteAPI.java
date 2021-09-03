package com.algaworks.algafood.api.controller.api;

import org.springframework.data.domain.Pageable;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

public interface RestauranteAPI {

    Object listar(  List<String> fieldList )
            throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException;
}
