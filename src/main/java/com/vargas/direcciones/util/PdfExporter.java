package com.vargas.direcciones.util;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;
import jakarta.servlet.http.HttpServletResponse; // Spring Boot 3.x (usar javax.* para Spring Boot 2.x)
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.BaseColor; // Usar BaseColor en lugar de Color para LIGHT_GRAY
import com.vargas.direcciones.model.Empresa; // Ajusta según tu paquete

public class PdfExporter {
    private List<Empresa> empresas;

    public PdfExporter(List<Empresa> empresas) {
        this.empresas = empresas;
    }

    public void export(HttpServletResponse response) throws IOException {
        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, response.getOutputStream()); // Corregido

            document.open();

            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            font.setSize(18);

            Paragraph title = new Paragraph("Lista de Empresas", font);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title); // Corregido

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100f);
            table.setSpacingBefore(10);

            // Cabeceros
            Stream.of("Nombre", "Contacto", "Teléfono", "Ciudad").forEach(header -> {
                PdfPCell cell = new PdfPCell();
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell.setPadding(5);
                cell.setPhrase(new Phrase(header));
                table.addCell(cell);
            });

            // Datos
            for (Empresa empresa : empresas) {
                table.addCell(empresa.getNombreEmpresa());
                table.addCell(empresa.getNombreContacto());
                table.addCell(empresa.getTelefono());
                table.addCell(empresa.getCiudad());
            }

            document.add(table); // Corregido
            document.close();
        } catch (DocumentException e) {
            throw new IOException("Error al generar PDF", e);
        }
    }
}