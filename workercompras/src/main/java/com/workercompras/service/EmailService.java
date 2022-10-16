package com.workercompras.service;

import com.workercompras.model.Pedido;
import com.workercompras.producer.PedidoProducer;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private PedidoProducer pedidoProducer;

    public void notificarCliente(Pedido pedido) {
        /**
        // Notificar cliente por email
        var msg = new SimpleMailMessage();
        msg.setTo(pedido.getEmail());
        msg.setSubject("Compra recebida");
        msg.setText("Este é um e-mail de confirmação de compra recebida. " +
                "Agora vamos aprovar sua compra e brevemente você receberá um novo e-mail de confirmação." +
                "\nObrigado por comprar com a gente!!");

        // envio de email
        javaMailSender.send(msg);
        log.info("Cliente notificado com sucesso!!");

         */


        // enviando para fila de pedido pendente
        pedidoProducer.enviarPedido(pedido);
        log.info("Preparando pedido!!");
    }

}