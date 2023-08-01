package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping("{id}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long id) {
        try {
            Restaurante restaurante = restauranteRepository.buscar(id);
            return ResponseEntity.ok().body(restaurante);

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public List<Restaurante> listar() {
        return restauranteRepository.listar();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Restaurante> atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {
        Restaurante restauranteAtual = restauranteRepository.buscar(id);

        if (restauranteAtual != null) {
            restauranteAtual.setNome(restaurante.getNome());
            BeanUtils.copyProperties(restaurante, restauranteAtual, "id");

            restauranteAtual = restauranteRepository.salvar(restauranteAtual);

            return ResponseEntity.ok().body(restauranteAtual);
        }

        return ResponseEntity.notFound().build();
    }

}
