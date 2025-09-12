package com.angel.apirest.profiles.controllers;

import com.angel.apirest.profiles.dto.ContrlorRequest;
import com.angel.apirest.profiles.dto.UsuarioDTO;
import com.angel.apirest.profiles.services.UsuarioServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    private final UsuarioServices usuarioService;

    public UsuarioController(UsuarioServices usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/saveusuario")
    public ResponseEntity<?> saveUsuario(@RequestBody ContrlorRequest request) {
        UsuarioDTO usuarioDTO = usuarioService.CreateDoctIdent(
                request.getDocumentoIdentidad(),
                request.getCorreoElectronico(),
                request.getTelefono());

        if (usuarioDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MensajeResponse("No se pudo crear el usuario."));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDTO);
    }

    @DeleteMapping("/deleteusuario/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
        boolean eliminado = usuarioService.DeleteUsuario(id);

        if (eliminado) {
            return ResponseEntity.ok(new MensajeResponse("Usuario eliminado correctamente."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensajeResponse("Usuario no encontrado."));
        }
    }

    @PutMapping("/updateusuario")
    public ResponseEntity<?> updateUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO updatedUsuario = usuarioService.UpdateUsuario(usuarioDTO);

        if (updatedUsuario == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MensajeResponse("No se pudo actualizar el usuario."));
        }

        return ResponseEntity.ok(updatedUsuario);
    }

    @GetMapping("/getusuario/{id}")
    public ResponseEntity<?> getUsuarioById(@PathVariable Long id) {
        Map<String, Object> usuarioCompleto = usuarioService.getUsuarioDetailsMap(id);

        if (usuarioCompleto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensajeResponse("Usuario no encontrado."));
        }

        return ResponseEntity.ok(usuarioCompleto);
    }

    @GetMapping("/getallusuario")
    public ResponseEntity<?> getAllUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.listarUsuarios();

        if (usuarios == null || usuarios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensajeResponse("No se encontraron usuarios."));
        }

        return ResponseEntity.ok(usuarios);
    }

    static class MensajeResponse {
        private String mensaje;

        public MensajeResponse(String mensaje) {
            this.mensaje = mensaje;
        }

        public String getMensaje() {
            return mensaje;
        }
    }
}