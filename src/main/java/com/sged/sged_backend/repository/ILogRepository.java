package com.sged.sged_backend.repository;

import com.sged.sged_backend.model.Log;
import com.sged.sged_backend.model.Rol;
import com.sged.sged_backend.model.TipoAccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ILogRepository extends JpaRepository<Log, Long> {
    List<Log> findByUsuarioDni(long dni);
    List<Log> findByTipoAccion(TipoAccion accion);
}
