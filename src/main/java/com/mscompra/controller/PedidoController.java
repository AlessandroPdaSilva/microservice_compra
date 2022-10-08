package com.mscompra.controller;

import com.mscompra.model.Pedido;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/pedido")
public class PedidoController {

    @PostMapping(value = "")
    public ResponseEntity<?> salvarPedido(@RequestBody Pedido pedido){

        return ResponseEntity.ok(pedido);
    }

}
