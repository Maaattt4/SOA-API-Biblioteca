package com.api.libreria.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.api.libreria.models.LibroModel;
import com.api.libreria.models.PrestamoModel;
import com.api.libreria.models.UsuarioModel;
import com.api.libreria.repositories.ILibroRepository;
import com.api.libreria.repositories.IPrestamoRepository;
import com.api.libreria.repositories.IUsuarioRepository;

@Service
public class PrestamoService {

    private final IPrestamoRepository prestamoRepository;
    private final ILibroRepository libroRepository;
    private final IUsuarioRepository usuarioRepository;

public PrestamoService(IPrestamoRepository prestamoRepository, ILibroRepository libroRepository, IUsuarioRepository usuarioRepository) {
        this.prestamoRepository = prestamoRepository;
        this.libroRepository = libroRepository;
        this.usuarioRepository = usuarioRepository;
    }
    
    public ArrayList<PrestamoModel> obtenerPrestamos() {
        return (ArrayList<PrestamoModel>) prestamoRepository.findAll();
    }

    public Optional<PrestamoModel> obtenerPorId(Long id) {
        return prestamoRepository.findById(id);
    }

public PrestamoModel registrarPrestamo(PrestamoModel detallePrestamo) { 
    // 1. Buscar el libro completo en la base de datos
    LibroModel libroSeleccionado = libroRepository.findById(detallePrestamo.getLibro().getIsbn())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El libro solicitado no existe."));

    // 2. Buscar el usuario (socio) completo en la base de datos
    UsuarioModel socioSeleccionado = usuarioRepository.findById(detallePrestamo.getSocio().getId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El socio solicitado no existe."));

    // 3. Validar disponibilidad y descontar el stock
    if (libroSeleccionado.getNumeroEjemplares() < 1) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Operación denegada: Cero ejemplares disponibles en este momento.");
    }
    libroSeleccionado.setNumeroEjemplares(libroSeleccionado.getNumeroEjemplares() - 1);
    libroRepository.save(libroSeleccionado);

    // 4. Asignar los objetos completos al préstamo
    detallePrestamo.setLibro(libroSeleccionado);
    detallePrestamo.setSocio(socioSeleccionado);

    // 5. Guardar y retornar el préstamo
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

    public void eliminarPrestamo(Long id) {
        if(!prestamoRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El préstamo con ID " + id + " no existe");
        }
        prestamoRepository.deleteById(id);
    }
}