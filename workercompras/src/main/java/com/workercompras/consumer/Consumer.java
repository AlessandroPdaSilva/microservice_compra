package com.workercompras.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workercompras.model.Pedido;
import com.workercompras.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class Consumer implements Serializable {

    private static final long serialVersionUID = 1L;

    private final ObjectMapper mapper;
    private final EmailService emailService;

    // ESCUTANDO MENSAGEM
    @RabbitListener(queues = {"${queue.name}"})
    public void consumer(@Payload Message message) throws IOException {
        var pedido = mapper.readValue(message.getBody(), Pedido.class);
        System.out.println("Mensagem recebida: " + pedido);
        emailService.notificarCliente("alessandropds55@gmail.com");
    }

}