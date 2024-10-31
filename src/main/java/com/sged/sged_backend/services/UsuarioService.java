package com.sged.sged_backend.services;

import com.sged.sged_backend.auth.AuthResponse;
import com.sged.sged_backend.auth.RegisterRequest;
import com.sged.sged_backend.exceptions.UserAlreadyExistsException;
import com.sged.sged_backend.jwt.JwtService;
import com.sged.sged_backend.model.Log;
import com.sged.sged_backend.model.TipoAccion;
import com.sged.sged_backend.model.Usuario;
import com.sged.sged_backend.repository.ILogRepository;
import com.sged.sged_backend.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final IUsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final ILogRepository logRepository;

    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerUsuario(long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public void editarUsuario(Usuario usuario) {
        usuario.setContrasenia(passwordEncoder.encode(usuario.getContrasenia()));
        Log log = new Log();
        log.setTipoAccion(TipoAccion.MODIFICACION);
        log.setFechaAccion(LocalDateTime.now());
        log.setUsuario(obtenerUsuarioLogueado());
        log.setDetalle(construirDetalle(usuario));

        usuarioRepository.save(usuario);
        logRepository.save(log);
    }

    public AuthResponse register(RegisterRequest request) {
        if(usuarioRepository.existsById(request.getDni())){
            throw new UserAlreadyExistsException("Usuario con DNI " + request.getDni() + " existente.");
        }
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Usuario con email " + request.getEmail() + " existente.");
        }

        Usuario usuario = new Usuario();
        usuario.setDni(request.getDni());
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setEmail(request.getEmail());
        usuario.setTelefono(request.getTelefono());
        usuario.setPuesto(request.getPuesto());
        usuario.setContrasenia(passwordEncoder.encode(request.getContrasenia()));
        usuario.setHabilitado(true);
        usuario.setRol(request.getRol());
        usuarioRepository.save(usuario);

        Log log = new Log();
        log.setTipoAccion(TipoAccion.ALTA);
        log.setFechaAccion(LocalDateTime.now());
        log.setUsuario(obtenerUsuarioLogueado());
        log.setDetalle(construirDetalle(request));
        logRepository.save(log);

        return AuthResponse.builder().token(jwtService.getToken(usuario)).build();
    }

    private String construirDetalle(RegisterRequest request){
        return String.format("%d, %s, %s, %s, %s, %s, %s",
                request.getDni(),
                request.getNombre(),
                request.getApellido(),
                request.getEmail(),
                request.getTelefono(),
                request.getPuesto(),
                request.getRol().getNombreRol()
        );
    }
    private String construirDetalle(Usuario usuario){
        return String.format("DNI: %d, Nombre: %s, Apellido: %s, Email: %s, Teléfono: %s, Puesto: %s, Rol: %s",
                usuario.getDni(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getTelefono(),
                usuario.getPuesto(),
                usuario.getRol().getNombreRol());
    }
    private Usuario obtenerUsuarioLogueado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No authenticated user found");
        }

        String loggingUsuarioEmail = authentication.getPrincipal().toString();
        System.out.println(loggingUsuarioEmail);
        Usuario loggingUser = usuarioRepository.findByEmail(loggingUsuarioEmail)
                .orElseThrow(()-> new UsernameNotFoundException("<Token> Usuario no encontrado"));

        return loggingUser;
    }

}
