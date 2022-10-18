package com.mscompra;

import com.mscompra.model.Pedido;

import java.math.BigDecimal;
import java.util.Date;

public class DadosMock {

    public Pedido getPedido() {
        return Pedido.builder()
                .valor(BigDecimal.TEN)
                .dataCompra(new Date())
                .cpfCliente("111.222.333-44")
                .cep("12345678")
                .build();
    }

}