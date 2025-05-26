package com.MSReabastecimiento.Reabastecimiento.controller;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MSReabastecimiento.Reabastecimiento.model.Reabastecimiento;
import com.MSReabastecimiento.Reabastecimiento.model.itemReabastecimiento;
import com.MSReabastecimiento.Reabastecimiento.service.reabastecimientoService;

@RestController
@RequestMapping("/api/reabastecimiento")
public class reabastecimientoController {

    @Autowired
    private reabastecimientoService rService;

    @PostMapping("/generar")
    public ResponseEntity<Reabastecimiento> crearPedido(@RequestBody Reabastecimiento pedido) {
        Reabastecimiento nuevo = rService.crearPedido(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @GetMapping
    public ResponseEntity<List<Reabastecimiento>> listarPedidos() {
        List<Reabastecimiento> lista = rService.listarPedidos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reabastecimiento> obtenerPedido(@PathVariable int id) {
        return rService.obtenerPedidoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<Reabastecimiento> agregarItem(
            @PathVariable int id,
            @RequestBody itemReabastecimiento item) {
        try {
            Reabastecimiento actualizado = rService.agregarItem(id, item);
            return ResponseEntity.ok(actualizado);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/autorizar")
    public ResponseEntity<?> autorizar(@PathVariable int id) {
        try {
            return ResponseEntity.ok(rService.autorizar(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Pedido no encontrado."));
        }
    }

    @PostMapping("/{id}/enviar")
    public ResponseEntity<?> enviar(@PathVariable int id) {
        try {
            return ResponseEntity.ok(rService.enviar(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Pedido no encontrado."));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/{id}/confirmar")
    public ResponseEntity<?> confirmarRecepcion(@PathVariable int id) {
        try {
            return ResponseEntity.ok(rService.confirmarRecepcion(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Pedido no encontrado."));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelar(@PathVariable int id) {
        try {
            return ResponseEntity.ok(rService.cancelar(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Pedido no encontrado."));
        }
    }

    @GetMapping("/proveedor/{idProveedor}")
    public ResponseEntity<List<Reabastecimiento>> listarPorProveedor(@PathVariable int idProveedor) {
        List<Reabastecimiento> pedidos = rService.listarPorProveedor(idProveedor);
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @PutMapping("/{id}/editar")
    public ResponseEntity<?> editarPedido(@PathVariable int id, @RequestBody Reabastecimiento pedido) {
        try {
            Reabastecimiento actualizado = rService.editarOrden(pedido, id);
            return ResponseEntity.ok(actualizado);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}/eliminar")
    public ResponseEntity<?> eliminarPedido(@PathVariable int id) {
        try {
            rService.eliminarPedido(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

}
