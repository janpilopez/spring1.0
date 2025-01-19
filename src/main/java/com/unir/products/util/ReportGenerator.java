package com.unir.products.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@Service
public class ReportGenerator {

	public byte[] exportToPdf(JasperPrint jasperPrint) throws JRException, IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		// Exportamos el reporte JasperPrint a formato PDF
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

		// Retornamos el PDF como un arreglo de bytes
		return outputStream.toByteArray();
		//
	}

	public ResponseEntity<Resource> exportInvoice(int idCli, int idOrden) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'exportInvoice'");
	}
}
