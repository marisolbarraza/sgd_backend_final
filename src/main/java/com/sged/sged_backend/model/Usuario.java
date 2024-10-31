package com.sged.sged_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Usuario implements UserDetails {
    @Id private long dni;
    @Column(nullable=false) @NonNull
    private String nombre;
    @Column(nullable=false) @NonNull
    private String apellido;
    @Column(nullable=false, unique = true) @NonNull
    private String email;
    @Column(nullable=false) @NonNull
    private String contrasenia;
    @Column(nullable=false) @NonNull
    private String telefono;
    @Column(nullable=false) @NonNull
    private String puesto;
    @Column(nullable=false) @NonNull
    private boolean isHabilitado;
    @ManyToOne @JoinColumn(name="idRol", nullable=false)@NonNull
    private Rol rol;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rol.getNombreRol()));
    }

    @Override
    public String getPassword() { return getContrasenia(); }

    @Override
    public String getUsername() { return getEmail(); }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return isHabilitado(); }
}
