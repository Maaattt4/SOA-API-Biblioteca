package com.api.libreria.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.libreria.models.LibroModel;
import com.api.libreria.models.PrestamoModel;
import com.api.libreria.repositories.ILibroRepository;
import com.api.libreria.repositories.IPrestamoRepository;

@Service
public class PrestamoService {

    private final IPrestamoRepository prestamoRepository;
    private final ILibroRepository libroRepository;

    public PrestamoService(IPrestamoRepository prestamoRepository, ILibroRepository libroRepository) {
        this.prestamoRepository = prestamoRepository;
        this.libroRepository = libroRepository;
    }
    
    public ArrayList<PrestamoModel> obtenerPrestamos() {
        return (ArrayList<PrestamoModel>) prestamoRepository.findAll();
    }

    public Optional<PrestamoModel> obtenerPorId(Long id) {
        return prestamoRepository.findById(id);
    }

public PrestamoModel registrarPrestamo(PrestamoModel detallePrestamo) {
        Optional<LibroModel> busquedaLibro = libroRepository.findById(detallePrestamo.getLibro().getIsbn());
        
        if (busquedaLibro.isEmpty()) {
            throw new RuntimeException("Error en sistema: El libro solicitado no existe en los registros.");
        }

        LibroModel libroSeleccionado = busquedaLibro.get();

        if (libroSeleccionado.getNumeroEjemplares() < 1) {
            throw new RuntimeException("Operación denegada: Cero ejemplares disponibles en este momento.");
        }

        libroSeleccionado.setNumeroEjemplares(libroSeleccionado.getNumeroEjemplares() - 1);
        libroRepository.save(libroSeleccionado);

        return prestamoRepository.save(detallePrestamo);
    }

    public PrestamoModel registrarDevolucion(Long idFolio) {
        Optional<PrestamoModel> prestamoActivo = prestamoRepository.findById(idFolio);

        if (prestamoActivo.isEmpty()) {
            throw new RuntimeException("Fallo en la consulta: No se encontró el ticket de préstamo.");
        }

        PrestamoModel registro = prestamoActivo.get();

        if (registro.getFechaDevolucion() != null) {
            throw new RuntimeException("Aviso: El libro asociado ya consta como devuelto en el sistema.");
        }

        registro.setFechaDevolucion(LocalDateTime.now());

        LibroModel libroAsociado = registro.getLibro();
        libroAsociado.setNumeroEjemplares(libroAsociado.getNumeroEjemplares() + 1);
        libroRepository.save(libroAsociado);

        return prestamoRepository.save(registro);
    }

    public boolean eliminarPrestamo(Long id) {
        try {
            prestamoRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}