package com.api.libreria.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.Optional;
import com.api.libreria.models.PrestamoModel;
import com.api.libreria.services.PrestamoService;

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @GetMapping
    public ArrayList<PrestamoModel> obtenerPrestamos() {
        return prestamoService.obtenerPrestamos();
    }

    @GetMapping("/{id}")
    public Optional<PrestamoModel> obtenerPorId(@PathVariable("id") Long id) {
        return prestamoService.obtenerPorId(id);
    }

    @PostMapping
    public PrestamoModel registrarPrestamo(@RequestBody PrestamoModel prestamo) {
        return prestamoService.registrarPrestamo(prestamo);
    }

    @PutMapping("/{id}/devolver")
    public PrestamoModel registrarDevolucion(@PathVariable("id") Long id) {
        return prestamoService.registrarDevolucion(id);
    }

    @DeleteMapping("/{id}")
    public String eliminarPrestamo(@PathVariable("id") Long id) {
        boolean ok = prestamoService.eliminarPrestamo(id);
        if (ok) {
            return "Se eliminó el préstamo con ID: " + id;
        } else {
            return "No se pudo eliminar el préstamo con ID: " + id;
        }
    }
}