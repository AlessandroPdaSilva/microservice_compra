package com.mscompra.service.rabbitmq;

import com.mscompra.model.Pedido;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
public class Producer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue queue;

    // ENVIAR PEDIDO
    @PostMapping
    public void enviarPedido(Pedido pedido) {
        rabbitTemplate.convertAndSend(queue.getName(), pedido);
    }

}