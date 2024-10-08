package com.example.loginplsql.controllers;

import com.example.loginplsql.Utils.Utils;
import com.example.loginplsql.daos.PresenzaRepository;
import com.example.loginplsql.models.Presenza;
import com.example.loginplsql.services.PdfGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @Autowired
    private PresenzaRepository daoAttendance;

    Logger log = LoggerFactory.getLogger(PdfController.class);

    @CrossOrigin(origins = Utils.CORSURL)
    @GetMapping("/generate")
    public ResponseEntity<String> generatePdf() throws Exception {
        String fileName = "my_table_data.pdf";
        try {
            List<Presenza> attendances = daoAttendance.getSysTimestampMonth();
            pdfGeneratorService.generatePdf(attendances, fileName);
            return ResponseEntity.ok("PDF generated successfully!");
        } catch ( Exception e ) {
            log.info(e.toString());
        }
        return ResponseEntity.ok("ERROR!");
    }
}
