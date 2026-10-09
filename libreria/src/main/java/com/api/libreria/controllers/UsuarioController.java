package com.api.libreria.controllers;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.api.libreria.models.UsuarioModel;
import com.api.libreria.services.UsuarioService;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    public Optional<UsuarioModel> obtenerPorId(@PathVariable("id") Long id) {
        return usuarioService.obtenerPorId(id);
    }

    @GetMapping
    public ArrayList<UsuarioModel> obtenerUsuarios() {
        return usuarioService.obtenerUsuarios();
    }

    @GetMapping("/nombre/{nombre}")
    public ArrayList<UsuarioModel> buscarPorNombre(@PathVariable("nombre") String nombre) {
        return usuarioService.buscarPorNombre(nombre);
    }

    @GetMapping("/correo/{correo}")
    public ArrayList<UsuarioModel> buscarPorCorreo(@PathVariable("correo") String correo) {
        return usuarioService.buscarPorCorreo(correo);
    }

    @PostMapping
    public UsuarioModel guardarUsuario(@RequestBody UsuarioModel usuario) {
        return usuarioService.guardarUsuario(usuario);
    }

    @PutMapping("/{id}")
    public UsuarioModel actualizarUsuario(@PathVariable("id") Long id, @RequestBody UsuarioModel request) {
        return usuarioService.actualizarUsuario(request, id);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable("id") Long id) {
        this.usuarioService.eliminarUsuarioPorId(id);
        return ResponseEntity.noContent().build(); 
    }
}