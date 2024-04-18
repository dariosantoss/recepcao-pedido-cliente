package com.pedidos.controle.restController;

import com.pedidos.controle.dto.FiltroPedidoDTO;
import com.pedidos.controle.dto.NovoPedidoDTO;
import com.pedidos.controle.model.Pedido;
import com.pedidos.controle.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<Pedido>> getAllPedidos() {
        List<Pedido> pedidos = pedidoService.listarPedidos();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<Pedido>> buscarPedidosPorFiltro(@ModelAttribute FiltroPedidoDTO filtroPedidoDTO,
                                               Pageable pageable) {
        Page<Pedido> pedidos = pedidoService.buscarPorFiltro(filtroPedidoDTO, pageable);
        return ResponseEntity.ok().body(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getPedidoById(@PathVariable Long id) {
        Optional<Pedido> pedido = pedidoService.encontrarPedidoPorId(id);
        return pedido.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<List<Pedido>> createPedidos(@RequestBody List<NovoPedidoDTO> novosPedidosDTO) {
        List<Pedido> pedidosCriados = pedidoService.criarPedido(novosPedidosDTO);
        return new ResponseEntity<>(pedidosCriados, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> updatePedido(@PathVariable Long id, @RequestBody Pedido pedido) {
        Pedido pedidoAtualizado = pedidoService.atualizarPedido(id, pedido);
        return new ResponseEntity<>(pedidoAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        pedidoService.deletarPedido(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}