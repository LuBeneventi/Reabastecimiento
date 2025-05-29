package com.MSReabastecimiento.Reabastecimiento.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.MSReabastecimiento.Reabastecimiento.DTO.ProveedorDTO;
import com.MSReabastecimiento.Reabastecimiento.DTO.estadoProveedor;
import com.MSReabastecimiento.Reabastecimiento.model.Reabastecimiento;
import com.MSReabastecimiento.Reabastecimiento.model.estadoReabastecimiento;
import com.MSReabastecimiento.Reabastecimiento.model.itemReabastecimiento;
import com.MSReabastecimiento.Reabastecimiento.repository.reabastecimientoRepository;

import jakarta.transaction.Transactional;

@Service
public class reabastecimientoService {

    @Autowired
    private reabastecimientoRepository rRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EmailService emailService;

    public Reabastecimiento crearPedido(Reabastecimiento pedido) {
        pedido.setFechaCreacion(LocalDate.now());
        pedido.setEstado(estadoReabastecimiento.EN_PROCESO);
        pedido.setItems(null);
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

        item.setPedido(pedido);
        pedido.getItems().add(item);
        return rRepo.save(pedido);
    }

    public Reabastecimiento autorizar(int id) {
        Reabastecimiento pedido = rRepo.findById(id).orElseThrow();
        pedido.setEstado(estadoReabastecimiento.AUTORIZADO);
        return rRepo.save(pedido);
    }

    public Reabastecimiento enviar(int id) {
        Reabastecimiento pedido = rRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pedido no encontrado"));
        if (pedido.getEstado() != estadoReabastecimiento.AUTORIZADO) {
            throw new IllegalStateException("El pedido debe estar AUTORIZADO para poder ENVIARLO.");
        }

        String urlProveedor = "http://localhost:8081/api/proveedor/" + pedido.getIdProveedor();
        ProveedorDTO proveedor = restTemplate.getForObject(urlProveedor, ProveedorDTO.class);

        if (proveedor == null || proveedor.getCorreoProv() == null || proveedor.getEstado() == estadoProveedor.INACTIVO) {
            throw new IllegalStateException("No se pudo enviar");
        }

        pedido.setEstado(estadoReabastecimiento.ENVIADO);
        rRepo.save(pedido);

        String asunto = "Confirmación de envio - Pedido #" + pedido.getIdReabastecimiento();
        String cuerpo = "Estimado " + proveedor.getNomProv() + ",\n\n"
                + "Requerimos de los siguiente productos:\n" + pedido.getItems().stream()
                        .map(item -> "- Producto ID: " + item.getIdProducto() + ", cantidad: " + item.getCantidad())
                        .collect(Collectors.joining("\n"))

                + "\nFecha: " + pedido.getFechaCreacion();

        emailService.enviarCorreo(proveedor.getCorreoProv(), asunto, cuerpo);

        return pedido;
    }

    public Reabastecimiento cancelar(int id) {
        Reabastecimiento pedido = rRepo.findById(id).orElseThrow();
        if (pedido.getEstado() == estadoReabastecimiento.RECIBIDO) {
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

    public List<Reabastecimiento> listarPorProveedor(int idProveedor) {
        return rRepo.findByIdProveedor(idProveedor);
    }

    public Reabastecimiento editarOrden(Reabastecimiento rea, int id) {
        Reabastecimiento existente = rRepo.findById(id).orElseThrow();

        if (existente.getEstado() != estadoReabastecimiento.EN_PROCESO) {
            throw new IllegalStateException("Solo se pueden editar pedidos en estado EN_PROCESO.");
        }
        existente.setIdProveedor(rea.getIdProveedor());
        existente.setItems(rea.getItems());
        return crearPedido(existente);

    }

    public void eliminarPedido(int id) {
        Reabastecimiento rea = rRepo.findById(id).orElseThrow();
        if (rRepo.existsById(id) && (rea.getEstado() == estadoReabastecimiento.CANCELADO) ||
                rea.getEstado() == estadoReabastecimiento.EN_PROCESO ||
                rea.getEstado() == estadoReabastecimiento.RECIBIDO) {
            rRepo.deleteById(id);
            return;
        }
        throw new NoSuchElementException("Fallo al eliminar");
    }

}
