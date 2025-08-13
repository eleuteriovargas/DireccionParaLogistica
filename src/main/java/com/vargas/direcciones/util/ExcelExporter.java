package com.vargas.direcciones.util;



import java.io.IOException;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.vargas.direcciones.model.Empresa; // Asegúrate de que esta es la ruta correcta a tu clase Empresa

public class ExcelExporter {
    private List<Empresa> empresas;

    public ExcelExporter(List<Empresa> empresas) {
        this.empresas = empresas;
    }

    public void export(HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Empresas");

        // Cabeceros
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Nombre", "Contacto", "Teléfono", "Email", "Ciudad"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // Datos
        int rowNum = 1;
        for (Empresa empresa : empresas) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(empresa.getNombreEmpresa());
            row.createCell(1).setCellValue(empresa.getNombreContacto());
            row.createCell(2).setCellValue(empresa.getTelefono());
            row.createCell(3).setCellValue(empresa.getEmail());
            row.createCell(4).setCellValue(empresa.getCiudad());
        }

        // Autoajustar columnas
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}