package com.pedidos.controle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FiltroPedidoDTO {
    private String numeroControle;
    private LocalDate dataCadastro;
    private Boolean todos;
}
