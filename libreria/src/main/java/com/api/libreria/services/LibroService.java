package com.api.libreria.services;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.libreria.models.LibroModel;
import com.api.libreria.repositories.ILibroRepository;

@Service
public class LibroService {
    
    @Autowired
    ILibroRepository libroRepository;

    public ArrayList<LibroModel> obtenerLibros(){
        return (ArrayList<LibroModel>) libroRepository.findAll();
    }

    public ArrayList<LibroModel> buscarPorAutor(String autor){
    return libroRepository.findByAutor(autor);
    }

    public ArrayList<LibroModel> buscarPorTitulo(String titulo){
        return libroRepository.findByTitulo(titulo);
    }

    public LibroModel guardarLibro(LibroModel libro){
        return libroRepository.save(libro);
    }

    public LibroModel actualizarLibro(LibroModel datosNuevos, Long idIsbn) {
        Optional<LibroModel> busquedaLibro = libroRepository.findById(idIsbn);
        
        if (busquedaLibro.isEmpty()) {
            throw new RuntimeException("Fallo al actualizar: El ISBN " + idIsbn + " no figura en los registros.");
        }
        
        LibroModel libroAEditar = busquedaLibro.get();
        libroAEditar.setTitulo(datosNuevos.getTitulo());
        libroAEditar.setAutor(datosNuevos.getAutor());
        libroAEditar.setAñoPublicacion(datosNuevos.getAñoPublicacion());
        libroAEditar.setNumeroEjemplares(datosNuevos.getNumeroEjemplares());
        
        return libroRepository.save(libroAEditar);
    }

    public boolean eliminarLibro(Long isbn){
        try{
            libroRepository.deleteById(isbn);
            return true;
        } catch (Exception err){
            return false;
        }
    }
}