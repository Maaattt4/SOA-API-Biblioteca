package com.api.libreria.services;

import java.util.ArrayList;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.api.libreria.models.UsuarioModel;
import com.api.libreria.repositories.IUsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private IUsuarioRepository usuarioRepository; 
    
    public Optional<UsuarioModel> obtenerPorId(Long id) {
    return usuarioRepository.findById(id);
    }

    public ArrayList<UsuarioModel> obtenerUsuarios() {
        return (ArrayList<UsuarioModel>) usuarioRepository.findAll();
    }

    public UsuarioModel guardarUsuario(UsuarioModel usuario) {
        return usuarioRepository.save(usuario);
    }

    public ArrayList<UsuarioModel> buscarPorNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }

    public ArrayList<UsuarioModel> buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }
    
    public UsuarioModel actualizarUsuario(UsuarioModel request, Long id) {
        UsuarioModel usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No existe un usuario con ID: " + id));

        usuario.setNombre(request.getNombre());
        usuario.setApellidos(request.getApellidos());
        usuario.setCorreo(request.getCorreo());
        usuario.setContrasena(request.getContrasena());
        usuario.setRol(request.getRol());
        usuario.setEstado(request.getEstado());
        usuario.setFechaCreacion(request.getFechaCreacion());
        usuario.setFechaNacimiento(request.getFechaNacimiento());
        usuario.setTelefono(request.getTelefono());
        usuario.setDireccion(request.getDireccion());

        return usuarioRepository.save(usuario);
    }

    public void eliminarUsuarioPorId(Long id) {
        if(!usuarioRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe un usuario con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}    

