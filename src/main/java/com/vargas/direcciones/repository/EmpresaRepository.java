package com.vargas.direcciones.repository;

import com.vargas.direcciones.model.Empresa;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    Page<Empresa> findByNombreEmpresaContainingIgnoreCaseOrCiudadContainingIgnoreCase(
            String nombre, String ciudad, Pageable pageable);
}