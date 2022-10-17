package com.workervalidador.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.workervalidador.model.Pedido;
import com.workervalidador.service.ValidadorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class Consumer {

    private final ObjectMapper mapper;
    private final ValidadorService validadorService;

    @RabbitListener(queues = {"${queue.name}"})
    public void consumer(@Payload Message message) throws Exception {

        // Payload
        var pedido = mapper.readValue(message.getBody(), Pedido.class);
        log.info("Pedido recebido no Validador: {}", pedido);

        // validando pedido (cartao)
        validadorService.validarPedido(pedido);
    }

}