package com.MSReabastecimiento.Reabastecimiento.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.MSReabastecimiento.Reabastecimiento.model.Reabastecimiento;
import com.MSReabastecimiento.Reabastecimiento.model.estadoReabastecimiento;
import com.MSReabastecimiento.Reabastecimiento.model.itemReabastecimiento;
import com.MSReabastecimiento.Reabastecimiento.repository.reabastecimientoRepository;

@Service
public class reabastecimientoService {

    @Autowired
    private reabastecimientoRepository rRepo;

    public Reabastecimiento crearPedido(Reabastecimiento pedido) {
        pedido.setFechaCreacion(LocalDate.now());
        pedido.setEstado(estadoReabastecimiento.EN_PROCESO);
        return rRepo.save(pedido);
    }

    public List<Reabastecimiento> listarPedidos() {
        return rRepo.findAll();
    }

    public Optional<Reabastecimiento> obtenerPedidoPorId(int id) {
        return rRepo.findById(id);
    }

    public Reabastecimiento agregarItem(int id, itemReabastecimiento item) {
        Reabastecimiento pedido = rRepo.findById(id).orElseThrow();
        List<itemReabastecimiento> items = pedido.getItems();
        items.add(item);
        pedido.setItems(items);
        return rRepo.save(pedido);
    }

    public Reabastecimiento autorizar(int id) {
        Reabastecimiento pedido = rRepo.findById(id).orElseThrow();
        pedido.setEstado(estadoReabastecimiento.AUTORIZADO);
        return rRepo.save(pedido);
    }

    public Reabastecimiento enviar(int id) {
        Reabastecimiento pedido = rRepo.findById(id).orElseThrow();
        if (pedido.getEstado() != estadoReabastecimiento.AUTORIZADO) {
            throw new IllegalStateException("El pedido debe estar AUTORIZADO para poder ENVIARLO.");
        }
        pedido.setEstado(estadoReabastecimiento.ENVIADO);
        return rRepo.save(pedido);
    }

    public Reabastecimiento cancelar(int id) {
        Reabastecimiento pedido = rRepo.findById(id).orElseThrow();
        if(pedido.getEstado() == estadoReabastecimiento.RECIBIDO){
            throw new IllegalStateException("No se puede CANCELAR un pedido RECIBIDO.");
        }
        pedido.setEstado(estadoReabastecimiento.CANCELADO);
        return rRepo.save(pedido);
    }

    public Reabastecimiento confirmarRecepcion(int id) {
        Reabastecimiento pedido = rRepo.findById(id).orElseThrow();
        if (pedido.getEstado() != estadoReabastecimiento.ENVIADO) {
            throw new IllegalStateException("El pedido debe estar ENVIADO para poder confirmar la recepción.");
        }
        pedido.setEstado(estadoReabastecimiento.RECIBIDO);
        return rRepo.save(pedido);
    }

}
