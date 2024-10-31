package com.sged.sged_backend.auth;

import com.sged.sged_backend.exceptions.UserAlreadyExistsException;
import com.sged.sged_backend.jwt.JwtService;
import com.sged.sged_backend.model.Log;
import com.sged.sged_backend.model.TipoAccion;
import com.sged.sged_backend.model.Usuario;
import com.sged.sged_backend.repository.ILogRepository;
import com.sged.sged_backend.repository.IUsuarioRepository;
import com.sged.sged_backend.services.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final IUsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ILogRepository logRepository;


    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
            UserDetails usuario = usuarioRepository.findByEmail(request.getEmail())
                    .orElseThrow(()->new BadCredentialsException(("<Error> Email o contraseña inválidos.")))    ;
            String token = jwtService.getToken(usuario);
            System.out.println(LocalDateTime.now());
            Log log = new Log();
            log.setTipoAccion(TipoAccion.LOGIN);
            log.setFechaAccion(LocalDateTime.now());
            log.setUsuario((Usuario) usuario);
            logRepository.save(log);
            System.out.println(request);

            return  AuthResponse.builder().token(token).build();
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("<Error> Email o contraseña inválidos.");
        } catch (Exception e) {
            throw new RuntimeException("Ops... error durante el login", e);
        }
    }

//    public AuthResponse register(RegisterRequest request) {
//        if(usuarioRepository.existsById(request.getDni())){
//            throw new UserAlreadyExistsException("Usuario con DNI " + request.getDni() + " existente.");
//        }
//        Usuario usuario = new Usuario();
//        usuario.setDni(request.getDni());
//        usuario.setNombre(request.getNombre());
//        usuario.setApellido(request.getApellido());
//        usuario.setEmail(request.getEmail());
//        usuario.setTelefono(request.getTelefono());
//        usuario.setPuesto(request.getPuesto());
//        usuario.setContrasenia(passwordEncoder.encode(request.getContrasenia()));
//        usuario.setHabilitado(true);
//        usuario.setRol(request.getRol());
//
//        usuarioRepository.save(usuario);
//
//        String loggingUsuarioEmail = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
//        Usuario loggingUser = usuarioRepository.findByEmail(loggingUsuarioEmail)
//                .orElseThrow(()-> new UsernameNotFoundException("<Token> Usuario no encontrado"));
//
//        //System.out.println(request);
//        Log log = new Log();
//        log.setTipoAccion(TipoAccion.ALTA);
//        log.setFechaAccion(LocalDateTime.now());
//        log.setUsuario(loggingUser);
//        log.setDetalle(request.toString());
//        logRepository.save(log);
//
//        return AuthResponse.builder().token(jwtService.getToken(usuario)).build();
//
//    }
//

}
