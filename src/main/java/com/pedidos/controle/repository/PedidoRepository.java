package com.pedidos.controle.repository;

import com.pedidos.controle.model.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    

    Page<Pedido> findByNumeroControleAndDataCadastro(String numeroControle, LocalDate dataCadastro, Pageable pageable);

    Page<Pedido> findByDataCadastro(LocalDate dataCadastro, Pageable pageable);

    Page<Pedido> findByNumeroControle(String numeroControle, Pageable pageable);

    Optional<Pedido> findByNumeroControle(String numeroControle);
}
