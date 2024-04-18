package com.pedidos.controle.service.impl;

import com.pedidos.controle.exception.PedidosException;
import com.pedidos.controle.model.Cliente;
import com.pedidos.controle.repository.ClienteRepository;
import com.pedidos.controle.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente encontrarClientePorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new PedidosException("Cliente não encontrado com o ID: " + id));
    }

    @Override
    public Cliente salvarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente atualizarCliente(Long id, Cliente cliente) {
        cliente.setId(id);
        return clienteRepository.save(cliente);
    }

    @Override
    public void deletarCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}