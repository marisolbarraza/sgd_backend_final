package com.sged.sged_backend.controller;

import com.sged.sged_backend.model.Log;
import com.sged.sged_backend.model.TipoAccion;
import com.sged.sged_backend.services.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("/auditoria")
    @ResponseBody
    public List<Log> listarLogs() {
        return logService.listarLogs();
    }
    @GetMapping("/auditoria/usuario/{dni}")
    @ResponseBody
    public List<Log> buscarLog(@PathVariable long dni) {
        return logService.buscarLogsPorUsuario(dni);
    }
    @PostMapping("/auditoria")
    @ResponseBody
    public void crearLog(@RequestBody Log log) {
        logService.crearLog(log);
    }

    @GetMapping("/auditoria/accion/{accion}")
    @ResponseBody
    public List<Log> buscarLogPorAccion(@PathVariable TipoAccion accion) {
        return logService.buscarLogsPorAccion(accion);
    }
}
