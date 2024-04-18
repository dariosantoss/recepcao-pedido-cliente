package com.pedidos.controle.service.impl;

import com.pedidos.controle.dto.FiltroPedidoDTO;
import com.pedidos.controle.dto.NovoPedidoDTO;
import com.pedidos.controle.exception.PedidosException;
import com.pedidos.controle.model.Cliente;
import com.pedidos.controle.model.Pedido;
import com.pedidos.controle.repository.PedidoRepository;
import com.pedidos.controle.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoServiceImplTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private PedidoServiceImpl pedidoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testListarPedidos() {
        // Mock do retorno do repositório
        List<Pedido> pedidosMock = new ArrayList<>();
        when(pedidoRepository.findAll()).thenReturn(pedidosMock);

        // Chamar o método da classe de serviço
        List<Pedido> pedidos = pedidoService.listarPedidos();

        // Verificar se o método do repositório foi chamado e se o resultado está correto
        verify(pedidoRepository, times(1)).findAll();
        assertEquals(pedidosMock, pedidos);
    }

    @Test
    void testEncontrarPedidoPorId_PedidoExistente() {
        // Mock do retorno do repositório
        Pedido pedidoMock = new Pedido();
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedidoMock));

        // Chamar o método da classe de serviço
        Optional<Pedido> pedido = pedidoService.encontrarPedidoPorId(1L);

        // Verificar se o método do repositório foi chamado e se o resultado está correto
        verify(pedidoRepository, times(1)).findById(1L);
        assertTrue(pedido.isPresent());
        assertEquals(pedidoMock, pedido.get());
    }

    @Test
    void testEncontrarPedidoPorId_PedidoNaoExistente() {
        // Mock do retorno do repositório
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        // Chamar o método da classe de serviço e verificar se uma exceção é lançada
        assertThrows(PedidosException.class, () -> pedidoService.encontrarPedidoPorId(1L));

        // Verificar se o método do repositório foi chamado
        verify(pedidoRepository, times(1)).findById(1L);
    }

    @Test
    void testCriarPedido() {
        // Preparar dados de teste
        NovoPedidoDTO novoPedidoDTO = new NovoPedidoDTO();
        novoPedidoDTO.setClienteId(1L);
        novoPedidoDTO.setNomeProduto("Produto teste");
        novoPedidoDTO.setQuantidade(Optional.of(5));
        novoPedidoDTO.setValor(BigDecimal.valueOf(100));

        Cliente clienteMock = new Cliente();
        when(clienteService.encontrarClientePorId(1L)).thenReturn(clienteMock);

        // Chamar o método da classe de serviço
        List<Pedido> pedidosCriados = pedidoService.criarPedido(List.of(novoPedidoDTO));

        // Verificar se o método do repositório foi chamado e se o resultado está correto
        verify(pedidoRepository, times(1)).save(any());
        assertEquals(1, pedidosCriados.size());
    }

    @Test
    void testBuscarPorFiltro() {
        // Preparar dados de teste
        FiltroPedidoDTO filtroPedidoDTO = new FiltroPedidoDTO();
        filtroPedidoDTO.setTodos(true);
        Pageable pageable = Pageable.unpaged();
        Page<Pedido> pedidosPageMock = mock(Page.class);
        when(pedidoRepository.findAll(pageable)).thenReturn(pedidosPageMock);

        // Chamar o método da classe de serviço
        Page<Pedido> pedidosPage = pedidoService.buscarPorFiltro(filtroPedidoDTO, pageable);

        // Verificar se o método do repositório foi chamado e se o resultado está correto
        verify(pedidoRepository, times(1)).findAll(pageable);
        assertEquals(pedidosPageMock, pedidosPage);
    }

}
