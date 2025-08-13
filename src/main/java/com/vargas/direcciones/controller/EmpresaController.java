package com.vargas.direcciones.controller;

import com.vargas.direcciones.model.Empresa;
import com.vargas.direcciones.service.EmpresaService;
import com.vargas.direcciones.util.ExcelExporter;
import com.vargas.direcciones.util.GoogleMapsUtil;
import com.vargas.direcciones.util.PdfExporter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/empresas")
public class EmpresaController {

    private static final int PAGE_SIZE = 10;

    private final EmpresaService empresaService;
    private final GoogleMapsUtil googleMapsUtil;

    public EmpresaController(EmpresaService empresaService, GoogleMapsUtil googleMapsUtil) {
        this.empresaService = empresaService;
        this.googleMapsUtil = googleMapsUtil;
    }

    @GetMapping
    public String listarEmpresas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("nombreEmpresa"));
            Page<Empresa> paginaEmpresas = empresaService.listarEmpresasPaginadas(pageable);

            if(paginaEmpresas == null) {
                throw new RuntimeException("Error al obtener empresas paginadas");
            }

            // Procesar URLs de mapas
            paginaEmpresas.getContent().forEach(e -> {
                e.setMapUrl(googleMapsUtil.generarLinkMapa(
                        e.getDireccion(),
                        e.getCiudad(),
                        e.getPais()
                ));
            });

            model.addAttribute("empresas", paginaEmpresas.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size); // Añadido
            model.addAttribute("totalPages", paginaEmpresas.getTotalPages());
            model.addAttribute("totalItems", paginaEmpresas.getTotalElements());

            return "empresas/list";

        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar empresas: " + e.getMessage());
            return "redirect:/error";
        }
    }

    @GetMapping("/buscar")
    public String buscarEmpresas(
            @RequestParam String termino,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("nombreEmpresa"));
        Page<Empresa> resultado = empresaService.buscarPorNombreOCiudad(termino, pageable);

        resultado.getContent().forEach(e -> e.setMapUrl(
                googleMapsUtil.generarLinkMapa(e.getDireccion(), e.getCiudad(), e.getPais())
        ));

        model.addAttribute("empresas", resultado.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size); // Añadido
        model.addAttribute("totalPages", resultado.getTotalPages());
        model.addAttribute("totalItems", resultado.getTotalElements());
        model.addAttribute("terminoBusqueda", termino);

        return "empresas/list";
    }



    @GetMapping("/nueva")
    public String mostrarFormulario(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "empresas/form";
    }

    @PostMapping("/guardar")
    public String guardarEmpresa(@Valid @ModelAttribute("empresa") Empresa empresa,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "empresas/form";
        }

        empresaService.guardarEmpresa(empresa);
        redirectAttributes.addFlashAttribute("success", "Empresa guardada exitosamente");
        return "redirect:/empresas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarEmpresa(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            empresaService.eliminarEmpresa(id);
            redirectAttributes.addFlashAttribute("success", "Empresa eliminada correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la empresa");
        }
        return "redirect:/empresas";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Optional<Empresa> empresa = empresaService.obtenerEmpresaPorId(id);
        if (empresa.isEmpty()) {
            return "redirect:/empresas";
        }
        model.addAttribute("empresa", empresa.get());
        return "empresas/form";
    }


    @GetMapping("/exportar/pdf")
    public void exportarPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=empresas.pdf");

        List<Empresa> empresas = empresaService.listarEmpresas();
        PdfExporter exporter = new PdfExporter(empresas);
        exporter.export(response);
    }

    @GetMapping("/exportar/excel")
    public void exportarExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=empresas.xlsx");

        List<Empresa> empresas = empresaService.listarEmpresas();
        ExcelExporter exporter = new ExcelExporter(empresas);
        exporter.export(response);
    }

}