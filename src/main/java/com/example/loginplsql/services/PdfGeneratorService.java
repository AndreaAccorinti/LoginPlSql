package com.example.loginplsql.services;

import com.example.loginplsql.models.Presenza;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.jvnet.hk2.annotations.Service;

import java.io.FileOutputStream;
import java.util.List;

@Service
public class PdfGeneratorService {
    public void generatePdf(List<Presenza> attendances, String fileName) throws Exception {
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
        document.open();
        writeTable(document, attendances);
        document.close();
        writer.close();
    }

    private void writeTable(Document document, List<Presenza> attendances) throws Exception {
        Font regular = new Font(Font.FontFamily.HELVETICA, 12);
        Font bold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        PdfPTable table = new PdfPTable(7); // Create a single-column table
        table.addCell(new PdfPCell(new Phrase(" ", bold)));
        table.addCell(new PdfPCell(new Phrase("Day", bold)));
        table.addCell(new PdfPCell(new Phrase("Description", bold)));
        table.addCell(new PdfPCell(new Phrase("Moring I", bold)));
        table.addCell(new PdfPCell(new Phrase("Morning E", bold)));
        table.addCell(new PdfPCell(new Phrase("Afternoon I", bold)));
        table.addCell(new PdfPCell(new Phrase("Afternoon E", bold)));
        for (Presenza attendance : attendances) {
            table.addCell(new PdfPCell(new Phrase(attendance.getData().toString(), regular)));
            table.addCell(new PdfPCell(new Phrase(attendance.getDescrizione(), regular)));
            table.addCell(new PdfPCell(new Phrase(attendance.getInizioMattina().toString(), regular)));
            table.addCell(new PdfPCell(new Phrase(attendance.getFineMattina().toString(), regular)));
            table.addCell(new PdfPCell(new Phrase(attendance.getInizioPomeriggio().toString(), regular)));
            table.addCell(new PdfPCell(new Phrase(attendance.getFinePomeriggio().toString(), regular)));
        }
        document.add(table);
    }
}
