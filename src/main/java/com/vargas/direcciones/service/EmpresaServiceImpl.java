package com.vargas.direcciones.service;

import com.vargas.direcciones.model.Empresa;
import com.vargas.direcciones.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
@Transactional
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository empresaRepository;

    @Override
    public Page<Empresa> listarEmpresasPaginadas(Pageable pageable) {
        return empresaRepository.findAll(pageable);
    }

    @Override
    public List<Empresa> listarEmpresas() {
        return empresaRepository.findAll();
    }

    @Override
    public Empresa guardarEmpresa(Empresa empresa) {
        // Validación adicional
        if (empresa.getNombreEmpresa() == null || empresa.getNombreEmpresa().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la empresa es requerido");
        }
        return empresaRepository.save(empresa);
    }


    @Override
    public Optional<Empresa> obtenerEmpresaPorId(Long id) {
        return empresaRepository.findById(id);
    }

    @Override
    @Transactional
    public void eliminarEmpresa(Long id) {
        empresaRepository.deleteById(id);
    }

    @Override
    public Page<Empresa> buscarPorNombreOCiudad(String termino, Pageable pageable) {
        return empresaRepository.findByNombreEmpresaContainingIgnoreCaseOrCiudadContainingIgnoreCase(
                termino, termino, pageable);
    }


}