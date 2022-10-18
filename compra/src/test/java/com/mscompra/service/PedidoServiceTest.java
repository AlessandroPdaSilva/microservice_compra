package com.mscompra.service;

import com.mscompra.DadosMock;
import com.mscompra.model.Pedido;
import com.mscompra.repository.PedidoRepository;
import com.mscompra.service.rabbitmq.Producer;
import lombok.var;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    // classe testada
    @InjectMocks
    private PedidoService pedidoService;

    // injecao das classes internas de pedidoService
    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private Producer producer;

    // injetando dados Pedido
    private DadosMock mock = new DadosMock();

    // Metodo de teste
    @DisplayName("Salvar pedido com sucesso")
    @Test
    void deveSalvarUmPedidoComSucesso() {

        var pedidoMok = mock.getPedido();

        // RETORNOS
        Mockito.when(pedidoRepository.save(Mockito.any(Pedido.class))).thenReturn(pedidoMok);
        Mockito.doNothing().when(producer).enviarPedido(Mockito.any(Pedido.class));

        var pedidoSalvo = pedidoService.salvar(pedidoMok);

        // TESTES
        assertEquals(pedidoMok.getCep(), pedidoSalvo.getCep());
        assertNotNull(pedidoSalvo.getCep());
    }

}