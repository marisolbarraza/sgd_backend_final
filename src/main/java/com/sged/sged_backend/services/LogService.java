package com.sged.sged_backend.services;

import com.sged.sged_backend.model.Log;
import com.sged.sged_backend.model.Rol;
import com.sged.sged_backend.model.TipoAccion;
import com.sged.sged_backend.repository.ILogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogService {
    @Autowired
    private final ILogRepository logRepository;

    public List<Log> listarLogs(){
        return logRepository.findAll();
    }
    public Log crearLog(Log log){
        return logRepository.save(log);
    }
    public List<Log> buscarLogsPorUsuario(long id){
        return logRepository.findByUsuarioDni(id);
    }
    public List<Log> buscarLogsPorAccion(TipoAccion accion){
        return logRepository.findByTipoAccion(accion);
    }

}
