package com.example.ms_reserva.controller;

import com.example.ms_reserva.model.Cliente;
import com.example.ms_reserva.repository.ClienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @PostMapping
    public ResponseEntity<?> crearCliente(@RequestBody Cliente cliente) {
        if (cliente.getCodCli() == null || cliente.getCodCli().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El código del cliente es obligatorio");
        }
        cliente.setCodCli(cliente.getCodCli().trim());
        if (clienteRepository.existsById(cliente.getCodCli())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El cliente con ese código ya existe");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteRepository.save(cliente));
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        return ResponseEntity.ok(clienteRepository.findAll());
    }
}
