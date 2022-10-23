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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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

    // SALVAR PEDIDO
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



    // BUSCA PEDIDO FALHA
    @DisplayName("Deve falhar na busca de pedido que nao existe")
    @Test
    void deveFalharNaBuscaDePedidoNaoExistente() {
        var id = 1L;

        Throwable exception = assertThrows(Throwable.class,() -> {
            Pedido pedidoSalvo = pedidoService.buscarId(id);
        });

        assertEquals("O pedido de id: " + id + " nao existe na base de dados!", exception.getMessage());
    }


    // BUSCA PEDIDO SUCESSO
    @DisplayName("Deve buscar um pedido com sucesso na base de dados")
    @Test
    void deveBuscarPedidoComSucesso() {
        var pedidoMok = mock.getPedidoSalvo();
        var id = 1L;

        // RETORNO
        Mockito.when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedidoMok));

        var pedidoSalvo = pedidoService.buscarId(id);

        // TESTES
        assertEquals(pedidoMok.getId(), pedidoSalvo.getId());
        assertNotNull(pedidoSalvo);

        Mockito.verify(pedidoRepository, Mockito.atLeastOnce()).findById(id);
    }



    // EXCLUIR PEDIDO SUCESSO
    @DisplayName("Deve excluir o pedido com sucesso")
    @Test
    void deveExcluirPedidoComSucesso() {
        // pegando um pedido ficticio
        var pedidoMok = mock.getPedidoSalvo();
        var id = 1L;

        // RETORNO
        Mockito.when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedidoMok));
        Mockito.doNothing().when(pedidoRepository).deleteById(id);

        // TESTES
        pedidoService.excluir(id);
        Mockito.verify(pedidoRepository, Mockito.atLeastOnce()).deleteById(id);
    }



    // EXCLUIR PEDIDO FALHAR
    @DisplayName("Deve falhar ao excluir o pedido que nao existe")
    @Test
    void deveFalharAoEXcluirPedidoNaoExistente() {
        var id = 1L;

        // RETORNO
        Mockito.when(pedidoRepository.findById(id)).thenReturn(Optional.empty());

        // TESTES
        Throwable exception = assertThrows(Throwable.class, () -> pedidoService.excluir(id));
        assertEquals("O pedido de id: " + id + " nao existe na base de dados!", exception.getMessage());
    }

}