package com.pedidos.controle.service;

import com.pedidos.controle.dto.FiltroPedidoDTO;
import com.pedidos.controle.dto.NovoPedidoDTO;
import com.pedidos.controle.model.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PedidoService {
    List<Pedido> listarPedidos();
    Optional<Pedido> encontrarPedidoPorId(Long id);
    List<Pedido> criarPedido(List<NovoPedidoDTO> novosPedidosDTO);
    Pedido atualizarPedido(Long id, Pedido pedido);
    void deletarPedido(Long id);
    Page<Pedido> buscarPorFiltro(FiltroPedidoDTO filtroPedidoDTO, Pageable pageable);
}
