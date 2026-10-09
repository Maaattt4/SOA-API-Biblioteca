package com.api.libreria.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.api.libreria.models.LibroModel;
import com.api.libreria.services.LibroService;

@RestController
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping
    public ArrayList<LibroModel> obtenerLibros() {
        return this.libroService.obtenerLibros();
    }

    @GetMapping(path = "/autor/{autor}")
    public ArrayList<LibroModel> buscarPorAutor(@PathVariable("autor") String autor) {
        return this.libroService.buscarPorAutor(autor);
    }

    @PostMapping
    public LibroModel guardarLibro(@RequestBody LibroModel libro) {
        return this.libroService.guardarLibro(libro);
    }

    @PutMapping(path = "/{isbn}")
    public LibroModel actualizarLibro(@RequestBody LibroModel request, @PathVariable("isbn") Long isbn) {
        return this.libroService.actualizarLibro(request, isbn);
    }

    @DeleteMapping(path = "/{isbn}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable("isbn") Long isbn) {
        this.libroService.eliminarLibro(isbn);
        return ResponseEntity.noContent().build();
    }
}
