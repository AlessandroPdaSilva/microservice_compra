package com.workervalidador.service;

import com.workervalidador.model.Cartao;
import com.workervalidador.model.Pedido;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ValidadorService {

    @Autowired
    private EmailService emailService;

    public void validarPedido(Pedido pedido) throws Exception {
        validarLimiteDisponivel(pedido.getCartao());
        validarCompraComLimite(pedido);

        emailService.notificarClienteCompraComSucesso(pedido.getEmail());
    }

    private void validarCompraComLimite(Pedido pedido) throws Exception {
        if (pedido.getValor().longValue() > pedido.getCartao().getLimiteDisponivel().longValue())
            log.error("Valor do pedido: {}. Limite disponivel: {}", pedido.getValor(), pedido.getCartao().getLimiteDisponivel());
        throw new Exception("Voce nao tem limite para efetuar essa compra!");
    }

    private void validarLimiteDisponivel(Cartao cartao) throws Exception {
        if (cartao.getLimiteDisponivel().longValue() <= 0)
            throw new Exception("Limite indisponivel!");
    }
}