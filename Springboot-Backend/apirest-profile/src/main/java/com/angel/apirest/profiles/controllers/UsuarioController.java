package com.angel.apirest.profiles.controllers;

import com.angel.apirest.profiles.dto.ContrlorRequest;
import com.angel.apirest.profiles.dto.UsuarioDTO;
import com.angel.apirest.profiles.services.UsuarioServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
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
    public ResponseEntity<?> saveUsuario(@Valid @RequestBody ContrlorRequest request) {
        UsuarioDTO usuarioDTO = usuarioService.createUsuarioWithDoc(
                request.getDocumentoIdentidad(),
                request.getCorreoElectronico(),
                request.getTelefono());

        if (usuarioDTO == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MensajeResponse("No se pudo crear el usuario."));

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDTO);
    }

    @PutMapping("/updateusuario")
    public ResponseEntity<?> updateUsuario(@Valid @RequestBody ContrlorRequest request) {
        UsuarioDTO updated = usuarioService.updateUsuarioWithDoc(
                request.getIdUsuarioDTO(),
                request.getCorreoElectronico(),
                request.getTelefono(),
                request.getDocumentoIdentidad());

        if (updated == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MensajeResponse("No se pudo actualizar el usuario."));

        return ResponseEntity.ok(updated);
    }

    // ----------------- DELETE Usuario + Documento -----------------
    @DeleteMapping("/deleteusuario/{id}")
    public ResponseEntity<?> deleteUsuarioAndDoc(@PathVariable Long id) {
        boolean eliminado = usuarioService.deleteUsuarioAndDoc(id);

        if (eliminado)
            return ResponseEntity.ok(new MensajeResponse("Usuario y documento eliminados correctamente."));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensajeResponse("Usuario no encontrado."));
    }

    // ----------------- GETs -----------------
    @GetMapping("/getusuario/{id}")
    public ResponseEntity<?> getUsuarioById(@PathVariable Long id) {
        Map<String, Object> usuario = usuarioService.getUsuarioDetailsMap(id);

        if (usuario == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensajeResponse("Usuario no encontrado."));

        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/getallusuario")
    public ResponseEntity<?> getAllUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.listarUsuarios();

        if (usuarios == null || usuarios.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensajeResponse("No se encontraron usuarios."));

        return ResponseEntity.ok(usuarios);
    }

    // Clase interna para mensajes
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
