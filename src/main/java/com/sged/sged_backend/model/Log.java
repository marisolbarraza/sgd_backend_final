package com.sged.sged_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Log {
    @Id @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long idLog;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false) @NonNull
    private TipoAccion tipoAccion;
    @Column(nullable = true)
    private String detalle;
    @Column(nullable = false) @NonNull
    private LocalDateTime fechaAccion;
    @ManyToOne @JoinColumn(name="dni", nullable=false)@NonNull
    private Usuario usuario;

}
