package com.example.loginplsql.services;

import com.example.loginplsql.Utils.Utils;
import com.example.loginplsql.models.Presenza;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;
import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ExcelService {

    private XSSFWorkbook workbook;
    private final XSSFSheet sheet;

    private String excelPath;
    private final Logger log = LoggerFactory.getLogger(ExcelService.class);
    public ExcelService(String excelPAth) {
        this.excelPath = excelPAth;
        try {
            workbook = new XSSFWorkbook(new FileInputStream(excelPAth));
        } catch (IOException exception ) {
            log.info(exception.getMessage());
        }
        sheet = workbook.getSheet("Presenze");
    }

    public void setExcelYear(int year) {
        XSSFRow row = sheet.getRow(3);
        XSSFCell cell = row.getCell(12);
        cell.setCellValue(year);
    }

    public void setExcelMounth(int mounth) {
        XSSFRow row = sheet.getRow(4);
        XSSFCell cell = row.getCell(12);
        cell.setCellValue(mounth);
    }

    public void setExcelTable(List<Presenza> listPresenza) {
        List<String> listDay = Utils.getMounthDay(listPresenza.get(0).getData());
        Map<String, Presenza> map = Utils.listToMapAttendance(listPresenza);
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.RED.getIndex());
        for (int x = 10; x < 41; x++) {
            if (x-10 < listDay.size()) {
                String day = listDay.get(x-10);
                XSSFRow row = sheet.getRow(x);
                row.getCell(0).setCellValue(day);
                row.getCell(1).setCellValue(Utils.dateToNameDay(day));
                if (map.containsKey(day)) {
                    row.getCell(2).setCellValue(map.get(day).getDescrizione());
                    row.getCell(3).setCellValue(Utils.timestampToTime(map.get(day).getInizioMattina()));
                    row.getCell(4).setCellValue(Utils.timestampToTime(map.get(day).getFineMattina()));
                    row.getCell(5).setCellValue(Utils.timestampToTime(map.get(day).getInizioPomeriggio()));
                    row.getCell(6).setCellValue(Utils.timestampToTime(map.get(day).getFinePomeriggio()));
                    row.getCell(7).setCellValue(map.get(day).sumWork());
                    row.getCell(11).setCellValue(map.get(day).getRimborsoSpese());
                } else {
                    row.getCell(2).setCellValue("");
                    row.getCell(3).setCellValue("");
                    row.getCell(4).setCellValue("");
                    row.getCell(5).setCellValue("");
                    row.getCell(6).setCellValue("");
                    row.getCell(7).setCellValue("");
                    row.getCell(11).setCellValue("");
                    row.getCell(0).setCellStyle(style);
                    row.getCell(1).setCellStyle(style);
                    row.getCell(2).setCellStyle(style);
                    row.getCell(3).setCellStyle(style);
                    row.getCell(4).setCellStyle(style);
                    row.getCell(5).setCellStyle(style);
                    row.getCell(6).setCellStyle(style);
                    row.getCell(7).setCellStyle(style);
                }
            }
        }
        save();
    }

    private void save() {
        try {
            FileOutputStream outputStream = new FileOutputStream(this.excelPath);
            this.workbook.write(outputStream);
            this.workbook.close();
            outputStream.close();
        } catch (IOException exception) {
            log.info(exception.getMessage());
        }
    }

    public String getExcelPath() {
        return excelPath;
    }

    public void setExcelPath(String excelPath) {
        this.excelPath = excelPath;
    }
}
