package com.example.wavespringboot.service.impl;

import com.example.wavespringboot.service.HtmlToPdfGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

@Service
@Qualifier("itextPdfGenerator")
public class ItextPdfGenerator implements HtmlToPdfGenerator {
    @Override
    public byte[] generatePdfFromHtml(String html) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.open();

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream, false);
            renderer.finishPDF();

            document.close();
            writer.close();

            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
