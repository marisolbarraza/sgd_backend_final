package com.sged.sged_backend.services;

import com.sged.sged_backend.model.Rol;
import com.sged.sged_backend.repository.IRolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolService {
    @Autowired
    private final IRolRepository rolRepository;

    public List<Rol> obtenerRoles() {
        return rolRepository.findAll();
    }
}
