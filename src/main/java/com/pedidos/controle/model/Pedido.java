package com.pedidos.controle.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@XmlRootElement
@Entity
@Table(name = "pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_controle", nullable = false)
    private String numeroControle;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    @Column(name = "nome_produto", nullable = false)
    private String nomeProduto;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "total")
    private BigDecimal total;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_cliente", nullable = false)
    @JsonIgnore
    private Cliente cliente;


}
