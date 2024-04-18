package com.pedidos.controle.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Optional;

@Getter
@Setter
public class NovoPedidoDTO {
    private String numeroControle;
    private Optional<String> dataCadastro;
    private String nomeProduto;
    private BigDecimal valor;
    private Optional<Integer> quantidade;
    private Long clienteId;
}
