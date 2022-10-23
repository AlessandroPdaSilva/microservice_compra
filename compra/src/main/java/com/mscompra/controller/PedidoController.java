package com.mscompra.controller;

import com.mscompra.model.Pedido;
import com.mscompra.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping(value = "/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // SALVAR PEDIDO
    @PostMapping(value = "")
    public ResponseEntity<?> salvarPedido(@RequestBody @Valid Pedido pedido){
        pedidoService.salvar(pedido);
        return ResponseEntity.ok(pedido);
    }

    // BUSCAR PEDIDO ID
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getPedidoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.buscarId(id));
    }

}
