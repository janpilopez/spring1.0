package com.unir.products.moduloReporte.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties.Tomcat.Resource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ReporteGeneratorServiceImp implements ReportGeneratorService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public byte[] generatePDF(String uri, Map<String, Object> parameters, JRDataSource dataSource) throws JRException {

        // 1. Cargar y compilar el archivo JRXML desde resources
        // 1. Cargar y compilar el archivo JRXML
        InputStream jrxmlInputStream = getClass().getClassLoader().getResourceAsStream(uri);
        // String jrxmlFile = "src/main/resources/jasper/PruebasProdc.jrxml";
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlInputStream);

        // 2. Crear una lista de objetos (por ejemplo, estudiantes)
        ArrayList<String> students = new ArrayList<>();
        students.add("Juan");
        students.add("Maria");
        students.add("Pedro");

        // 3. Crear un JRBeanCollectionDataSource para pasar la lista al reporte
        // JRBeanCollectionDataSource dataSource = new
        // JRBeanCollectionDataSource(students);

        // 4. Crear un Map de parámetros (puedes agregar parámetros si es necesario)
        Map<String, Object> parametersMap = new HashMap<>();
        // Si tienes parámetros que necesitas pasar, agrégales aquí.
        // Ejemplo: parameters.put("paramName", "value");

        // 5. Llenar el reporte con los datos y crear el JasperPrint
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Exportamos el reporte JasperPrint a formato PDF
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        // Retornamos el PDF como un arreglo de bytes
        return outputStream.toByteArray();
        // return new byte[0];
    }

    public ResponseEntity<ByteArrayResource> exportInvoice(int idCli, int idOrden) throws JRException {
        // Consulta SQL directa para obtener los productos (sin modelo)
        String sql = "SELECT * FROM products WHERE id = ?";
        // Obtener los datos desde la base de datos
        List<Map<String, Object>> products = jdbcTemplate.queryForList(sql, idCli);

        // Convertir la lista de Map<String, Object> a JRDataSource
        JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(products);

        // Cargar el archivo .jasper (completo o pre-compilado)
        InputStream jasperStream = getClass().getResourceAsStream("/jasper/Preparacion.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperStream);

        // Parámetros del informe (puedes pasar parámetros adicionales si lo necesitas)
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("idCli", idCli);
        parameters.put("idOrden", idOrden);
        parameters.put("dsProducts", jrDataSource); // Pasar el JRBeanCollectionDataSource como parámetro
        System.out.println(parameters);
        // JasperReport jasperReport = JasperCompileManager.compileReport(jasperStream);

        // Rellenar el informe con datos y parámetros
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

        System.out.println(jrDataSource);
        // Exportar el informe a PDF
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, pdfOutputStream);

        // Crear un ByteArrayResource desde el byte array del PDF
        ByteArrayResource resource = new ByteArrayResource(pdfOutputStream.toByteArray());

        // Devolver el archivo como respuesta con el encabezado adecuado para
        // descargarlo como PDF

        // VERSION 6.20.6 FORMATO PDF
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=factura.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

}
