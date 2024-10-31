package com.sged.sged_backend.controller;

import com.sged.sged_backend.auth.AuthResponse;
import com.sged.sged_backend.auth.AuthService;
import com.sged.sged_backend.auth.RegisterRequest;
import com.sged.sged_backend.exceptions.UserAlreadyExistsException;
import com.sged.sged_backend.model.Usuario;
import com.sged.sged_backend.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;


    @GetMapping("/usuario")
    @ResponseBody
    public List<Usuario> listarUsuarios() {
        return usuarioService.obtenerUsuarios();
    }

    @GetMapping("/usuario/{id}")
    @ResponseBody
    public Usuario buscarUsuario(@PathVariable int id) {
        return usuarioService.obtenerUsuario(id);
    }

    @PutMapping("/usuario")
    public void editarUsuario(@RequestBody Usuario usuario) {
        usuarioService.editarUsuario(usuario);
    }

    @PostMapping(value="register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        try {
            return ResponseEntity.ok(usuarioService.register(request));
        }catch (UserAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new AuthResponse(e.getMessage()));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AuthResponse("An error occurred"));
        }
    }

}
