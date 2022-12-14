package com.mscompra.service;

import com.mscompra.model.Pedido;
import com.mscompra.repository.PedidoRepository;
import com.mscompra.service.rabbitmq.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final Producer producer;

    // SALVAR PEDIDO
    public Pedido salvar(Pedido pedido) {

        pedido = pedidoRepository.save(pedido);
        producer.enviarPedido(pedido);

        return pedido;
    }

    // BUSCAR PEDIDO
    public Pedido buscarId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("O pedido de id: " + id + " nao existe na base de dados!"));
    }

    // EXCLUIR PEDIDO
    public void excluir(Long id) {
        Pedido pedido = buscarId(id);
        pedidoRepository.deleteById(pedido.getId());
    }


}
