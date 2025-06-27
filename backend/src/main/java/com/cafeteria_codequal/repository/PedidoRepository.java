package com.cafeteria_codequal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafeteria_codequal.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}

