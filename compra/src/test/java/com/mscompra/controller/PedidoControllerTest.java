package com.mscompra.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mscompra.DadosMock;
import com.mscompra.MscompraApplication;
import com.mscompra.model.Pedido;
import com.mscompra.service.PedidoService;
import lombok.var;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MscompraApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ObjectMapper mapper;

    private static final String ROTA_PEDIDO = "/pedido";
    private DadosMock dadosMok = new DadosMock();


    // TESTE PARA CADASTRAR PEDIDO
    @DisplayName("POST - Deve cadastrar um novo pedido com sucesso no banco de dados")
    @Test // TESTE INTEGRADO
    void deveCadastrarPedidoComSucesso() throws Exception {
        var pedidoBody = dadosMok.getPedido();
        var id = 1L;

        // enjetando
        mockMvc.perform(post(ROTA_PEDIDO)// POST
                        .content(mapper.writeValueAsString(pedidoBody))// conteudo
                        .contentType(MediaType.APPLICATION_JSON)// tipo json
                        .accept(MediaType.APPLICATION_JSON))// tipo json
                        .andDo(print())// imprimir na tela
                        .andExpect(status().isOk());// status OK

        //TESTE UNITARIO
        Pedido pedidoSalvo = pedidoService.buscarId(id);

        assertEquals(pedidoSalvo.getId(), id);
        assertNotNull(pedidoSalvo);

    }


    // TESTE PARA BUSCAR PEDIDO POR ID
    @DisplayName("GET - Deve buscar o pedido com sucesso na base de dados")
    @Test
    void deveBuscarPedidoComSucesso() throws Exception {
        var id = 1L;

        mockMvc.perform(get(ROTA_PEDIDO.concat("/" + id)))// GET
                .andDo(print())// imprimir na tela
                .andExpect(status().isOk());// status OK
    }


    // TESTE PARA BUSCAR PEDIDO QUE NAO EXISTE
    @DisplayName("GET - Deve falhar ao buscar pedido que nao existe")
    @Test
    void deveFalharAoBuscarPedidoQueNaoExiste() throws Exception {
        var id = 2L;

        mockMvc.perform(get(ROTA_PEDIDO.concat("/" + id)))// GET
                .andDo(print())// imprimir na tela
                .andExpect(status().isNotFound());// status Nao encontrado
    }


    // TESTE PARA EXCLUIR PEDIDO
    @DisplayName("DELETE - Deve excluir um pedido com sucesso")
    @Test
    void deveExcluirUmPedidoComSucesso() throws Exception {
        var id = 1L;

        mockMvc.perform(delete(ROTA_PEDIDO.concat("/" + id)))// DELETE
                .andDo(print())// imprimir na tela
                .andExpect(status().isNoContent());// status sem conteudo

        // jogar exceçao caso nao conseguir excluir
        Throwable exception = assertThrows(Throwable.class, () -> pedidoService.excluir(id));

        // mensagem da exclusao
        assertEquals("O pedido de id: " + id + " nao existe na base de dados!", exception.getMessage());
    }


}