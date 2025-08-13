package com.vargas.direcciones.controller.api;

import com.vargas.direcciones.model.Empresa;
import com.vargas.direcciones.service.EmpresaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*") // Permitir acceso desde cualquier frontend (React Native)
public class EmpresaRestController {

    private final EmpresaService empresaService;

    public EmpresaRestController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping
    public ResponseEntity<List<Empresa>> listarEmpresas() {
        List<Empresa> empresas = empresaService.listarEmpresas();
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> obtenerPorId(@PathVariable Long id) {
        Optional<Empresa> empresa = empresaService.obtenerEmpresaPorId(id);
        return empresa.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Empresa> crearEmpresa(@RequestBody Empresa empresa) {
        Empresa guardada = empresaService.guardarEmpresa(empresa);
        return ResponseEntity.ok(guardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empresa> actualizarEmpresa(@PathVariable Long id, @RequestBody Empresa empresa) {
        Optional<Empresa> existente = empresaService.obtenerEmpresaPorId(id);
        if (existente.isEmpty()) return ResponseEntity.notFound().build();

        empresa.setId(id); // asegura que actualiza, no crea
        Empresa actualizada = empresaService.guardarEmpresa(empresa);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpresa(@PathVariable Long id) {
        Optional<Empresa> empresa = empresaService.obtenerEmpresaPorId(id);
        if (empresa.isEmpty()) return ResponseEntity.notFound().build();

        empresaService.eliminarEmpresa(id);
        return ResponseEntity.noContent().build();
    }
}
