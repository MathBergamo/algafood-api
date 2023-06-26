package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<FormaPagamento> listar() {
        TypedQuery<FormaPagamento> query = manager.createQuery("from forma_pagamento", FormaPagamento.class);

        return query.getResultList();
    }

    @Override
    public FormaPagamento buscar(Long id){
        return manager.find(FormaPagamento.class, id);
    }

    @Transactional
    @Override
    public FormaPagamento salvar(FormaPagamento pagamento){
        return manager.merge(pagamento);
    }

    @Transactional
    @Override
    public void remover(FormaPagamento pagamento){
        pagamento = buscar(pagamento.getId());
        manager.remove(pagamento);
    }

}
