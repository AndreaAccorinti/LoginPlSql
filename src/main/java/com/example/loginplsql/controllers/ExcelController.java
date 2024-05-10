package com.example.loginplsql.controllers;
import com.example.loginplsql.daos.ExcelRepository;
import com.example.loginplsql.daos.PresenzaRepository;
import com.example.loginplsql.models.Presenza;
import com.example.loginplsql.services.ExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/excel")
public class ExcelController {
    @Autowired
    ExcelRepository daoExcel;

    @Autowired
    PresenzaRepository daoPresenza;
    Logger log = LoggerFactory.getLogger(ExcelController.class);
    @GetMapping("/test-excel")
    void writeExcel() {
        List<Presenza> listPresenza = null;
        try {
            listPresenza = daoPresenza.getSysTimestampMonth();

        }catch (Exception e) {
            log.info("Fallito getSysTimestampMounth");
        }
        ExcelService excelService = new ExcelService("C://development/___Rapp V08_def.xlsm");
        log.info("Excel importato con successo!");
        excelService.setExcelYear(2024);
        log.info("Anno aggiornato con successo!");
        excelService.setExcelMounth(2);
        log.info("Mese aggiornato con successo!");
        if (listPresenza != null && listPresenza.size() > 0) {
            excelService.setExcelTable(listPresenza);
            log.info("Dati della tabella aggiornati con successo!");
        }
    }
}
