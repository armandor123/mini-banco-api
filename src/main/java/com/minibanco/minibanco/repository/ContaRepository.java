package com.minibanco.minibanco.repository;

import com.minibanco.minibanco.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
    // O JpaRepository já nos dá métodos como save(), findById(), delete() automaticamente
}
