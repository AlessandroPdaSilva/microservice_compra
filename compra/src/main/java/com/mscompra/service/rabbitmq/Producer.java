package com.mscompra.service.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mscompra.model.Pedido;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class Producer {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;
    private final ObjectMapper mapper;

    // ENVIAR PEDIDO
    @SneakyThrows
    @PostMapping
    public void enviarPedido(Pedido pedido) {
        rabbitTemplate.convertAndSend(queue.getName(), mapper.writeValueAsString(pedido));
    }

}