package com.unir.products.moduloReporte.service;

import java.util.Map;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.repo.InputStreamResource;

public interface ReportGeneratorService {
    byte[] generatePDF(String uri, Map<String, Object> parameters, JRDataSource dataSource) throws JRException;

    ResponseEntity<ByteArrayResource> exportInvoice(int idCli, int idOrden) throws JRException;
}
