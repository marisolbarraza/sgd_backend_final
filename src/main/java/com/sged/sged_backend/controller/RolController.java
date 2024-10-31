package com.sged.sged_backend.controller;


import com.sged.sged_backend.model.Rol;
import com.sged.sged_backend.services.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RolController {
    @Autowired
    private final RolService rolService;

    @GetMapping("/Rol")
    @ResponseBody
    public List<Rol> obtenerRoles(){
        return rolService.obtenerRoles();
    }
}
