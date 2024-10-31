package com.sged.sged_backend.auth;

import com.sged.sged_backend.exceptions.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(value="login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        try{
            return ResponseEntity.ok(authService.login(request));
        }catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse("Inicio de sesión falló. Credenciales inválidad"));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AuthResponse("Ops... ha ocurrido un error."));
        }
    }

//    @PostMapping(value="register")
//    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
//        try {
//            return ResponseEntity.ok(authService.register(request));
//        }catch (UserAlreadyExistsException e){
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(new AuthResponse(e.getMessage()));
//        }catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AuthResponse("An error occurred"));
//        }
//    }
}
