package com.vargas.direcciones.service;

import com.vargas.direcciones.model.Empresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EmpresaService {


    Page<Empresa> listarEmpresasPaginadas(Pageable pageable);

    List<Empresa> listarEmpresas();
    Empresa guardarEmpresa(Empresa empresa);
    Optional<Empresa> obtenerEmpresaPorId(Long id);
    void eliminarEmpresa(Long id);

    Page<Empresa> buscarPorNombreOCiudad(String termino, Pageable pageable);

}

