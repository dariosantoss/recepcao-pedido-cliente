package com.pedidos.controle.service;

import com.pedidos.controle.model.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteService {

    List<Cliente> listarClientes();

    Cliente encontrarClientePorId(Long id);

    Cliente salvarCliente(Cliente cliente);

    Cliente atualizarCliente(Long id, Cliente cliente);

    void deletarCliente(Long id);
}