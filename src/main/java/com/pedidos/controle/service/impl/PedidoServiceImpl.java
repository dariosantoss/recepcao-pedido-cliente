package com.pedidos.controle.service.impl;

import com.pedidos.controle.dto.FiltroPedidoDTO;
import com.pedidos.controle.dto.NovoPedidoDTO;
import com.pedidos.controle.exception.PedidosException;
import com.pedidos.controle.model.Cliente;
import com.pedidos.controle.model.Pedido;
import com.pedidos.controle.repository.PedidoRepository;
import com.pedidos.controle.service.ClienteService;
import com.pedidos.controle.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PedidoServiceImpl implements PedidoService {

    private static final int LIMITE_PEDIDOS = 10;
    private final PedidoRepository pedidoRepository;
    private final ClienteService clienteService;

    @Autowired
    public PedidoServiceImpl(PedidoRepository pedidoRepository, ClienteService clienteService) {
        this.pedidoRepository = pedidoRepository;
        this.clienteService = clienteService;
    }

    @Override
    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    @Override
    public Optional<Pedido> encontrarPedidoPorId(Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        if (pedido.isEmpty()) {
            throw new PedidosException("Pedido não encontrado com o ID: " + id);
        }
        return pedido;
    }

    @Override
    public List<Pedido> criarPedido(List<NovoPedidoDTO> novosPedidosDTO) {
        List<Pedido> pedidosCriados = new ArrayList<>();
        regraLimitarPedido(novosPedidosDTO);
        for (NovoPedidoDTO novoPedidoDTO : novosPedidosDTO) {
            regraNumeroControleCadastrado(novoPedidoDTO);
            Integer quantidade = regraVerificaQuantidade(novoPedidoDTO.getQuantidade().get());
            BigDecimal valor = regraAplicarDesconto(novoPedidoDTO.getQuantidade().get(), novoPedidoDTO.getValor());
            novoPedidoDTO.setQuantidade(quantidade.describeConstable());
            novoPedidoDTO.setValor(valor);
            Pedido pedido = salvarPedido(novoPedidoDTO);
            pedidosCriados.add(pedido);
        }
        return pedidosCriados;
    }

    public Page<Pedido> buscarPorFiltro(FiltroPedidoDTO filtroPedidoDTO, Pageable pageable) {
        if (Boolean.TRUE.equals(filtroPedidoDTO.getTodos())) {
            return pedidoRepository.findAll(pageable);
        }
        if (filtroPedidoDTO.getNumeroControle() != null && filtroPedidoDTO.getDataCadastro() != null) {
            return pedidoRepository.findByNumeroControleAndDataCadastro(
                    filtroPedidoDTO.getNumeroControle(), filtroPedidoDTO.getDataCadastro(), pageable);
        }

        if (filtroPedidoDTO.getNumeroControle() != null) {
            return pedidoRepository.findByNumeroControle(
                    filtroPedidoDTO.getNumeroControle(), pageable);
        }

        if (filtroPedidoDTO.getDataCadastro() != null) {
            return pedidoRepository.findByDataCadastro(
                    filtroPedidoDTO.getDataCadastro(), pageable);
        }
        return pedidoRepository.findAll(pageable);
    }

    private BigDecimal regraAplicarDesconto(Integer quantidade, BigDecimal valor){

        if(quantidade >= 5 && quantidade < 10){
            BigDecimal percentualDesconto = new BigDecimal("0.05");
            return desconto(valor, percentualDesconto);
        }
        if(quantidade >= 10){
            BigDecimal percentualDesconto = new BigDecimal("0.10");
            return desconto(valor, percentualDesconto);
        }
        return valor;
    }

    private static BigDecimal desconto(BigDecimal valor, BigDecimal percentualDesconto) {
        BigDecimal desconto = valor.multiply(percentualDesconto);
        BigDecimal novoValor = valor.subtract(desconto);
        return novoValor;
    }

    private Integer regraVerificaQuantidade(Integer quantidade){
        return quantidade>=1 ? quantidade : 1;
    }
    private void regraNumeroControleCadastrado(NovoPedidoDTO novoPedidoDTO){
        if(pedidoRepository.findByNumeroControle(novoPedidoDTO.getNumeroControle()).isPresent()){
            throw new PedidosException("O númerode controle " + novoPedidoDTO.getNumeroControle() + " já foi cadastrado! Tente outro número de controle.");
        }
    }

    private static void regraLimitarPedido(List<NovoPedidoDTO> novosPedidosDTO) {
        if (novosPedidosDTO.size() > LIMITE_PEDIDOS) {
            throw new PedidosException("O limite de " + LIMITE_PEDIDOS + " pedidos para o cliente foi atingido.");
        }
    }


    private Pedido salvarPedido(NovoPedidoDTO novoPedidoDTO) {
        Cliente cliente = clienteService.encontrarClientePorId(novoPedidoDTO.getClienteId());
        LocalDate dataCadastro = isereDataCadastro(novoPedidoDTO);
        BigDecimal total = novoPedidoDTO.getValor().multiply(BigDecimal.valueOf(novoPedidoDTO.getQuantidade().get()));
        Pedido pedido = new Pedido();
        pedido.setNumeroControle(novoPedidoDTO.getNumeroControle());
        pedido.setDataCadastro(dataCadastro);
        pedido.setNomeProduto(novoPedidoDTO.getNomeProduto());
        pedido.setValor(novoPedidoDTO.getValor());
        pedido.setQuantidade(novoPedidoDTO.getQuantidade().get());
        pedido.setTotal(total);
        pedido.setCliente(cliente);

        return pedidoRepository.save(pedido);
    }


    private static LocalDate isereDataCadastro(NovoPedidoDTO novoPedidoDTO) {
        LocalDate dataCadastro = (Objects.isNull(novoPedidoDTO.getDataCadastro()) || novoPedidoDTO.getDataCadastro().isEmpty()) ?
                LocalDate.now() : LocalDate.parse(novoPedidoDTO.getDataCadastro().get());
        return dataCadastro;
    }

    @Override
    public Pedido atualizarPedido(Long id, Pedido pedido) {
        pedido.setId(id); // Define o ID do pedido fornecido para garantir que a atualização seja feita no pedido correto
        return pedidoRepository.save(pedido);
    }

    @Override
    public void deletarPedido(Long id) {
        pedidoRepository.deleteById(id);
    }
}