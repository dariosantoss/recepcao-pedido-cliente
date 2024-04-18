package com.pedidos.controle.service.impl;

import com.pedidos.controle.exception.PedidosException;
import com.pedidos.controle.model.Cliente;
import com.pedidos.controle.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testListarClientes() {
        
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(createCliente(1L, "Cliente 1", "cliente1@example.com", "123456789"));
        clientes.add(createCliente(2L, "Cliente 2", "cliente2@example.com", "987654321"));
        when(clienteRepository.findAll()).thenReturn(clientes);

        
        List<Cliente> resultado = clienteService.listarClientes();

        
        assertEquals(2, resultado.size());
    }

    @Test
    public void testEncontrarClientePorId_ClienteExistente() {
        
        Cliente cliente = createCliente(1L, "Cliente Teste", "teste@example.com", "999999999");
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        
        Cliente resultado = clienteService.encontrarClientePorId(1L);

        
        assertNotNull(resultado);
        assertEquals(cliente, resultado);
    }

    @Test
    public void testEncontrarClientePorId_ClienteNaoExistente() {
        
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        
        assertThrows(PedidosException.class, () -> clienteService.encontrarClientePorId(1L));
    }

    @Test
    public void testSalvarCliente() {
        
        Cliente cliente = createCliente(null, "Novo Cliente", "novo@example.com", "111111111");
        when(clienteRepository.save(cliente)).thenReturn(createCliente(1L, "Novo Cliente", "novo@example.com", "111111111"));

        
        Cliente resultado = clienteService.salvarCliente(cliente);

        
        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertEquals("Novo Cliente", resultado.getNome());
        assertEquals("novo@example.com", resultado.getEmail());
        assertEquals("111111111", resultado.getTelefone());
    }

    @Test
    public void testAtualizarCliente() {
        
        Cliente clienteExistente = createCliente(1L, "Cliente Existente", "existente@example.com", "222222222");
        Cliente clienteAtualizado = createCliente(1L, "Cliente Atualizado", "atualizado@example.com", "333333333");
        when(clienteRepository.save(clienteAtualizado)).thenReturn(clienteAtualizado);

        
        Cliente resultado = clienteService.atualizarCliente(1L, clienteAtualizado);

        
        assertNotNull(resultado);
        assertEquals(clienteAtualizado, resultado);
    }

    @Test
    public void testDeletarCliente() {

        clienteService.deletarCliente(1L);

        
        verify(clienteRepository, times(1)).deleteById(1L);
    }

    private Cliente createCliente(Long id, String nome, String email, String telefone) {
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNome(nome);
        cliente.setEmail(email);
        cliente.setTelefone(telefone);
        return cliente;
    }
}
