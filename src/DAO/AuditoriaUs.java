package DAO;

import FORMATOS.Mensajes;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class AuditoriaUs extends ConexionBD{
    public void GenerarPDFAuditoriaUsuarios(String ruta) {
        try {
            rs = st.executeQuery("select idaus,accionus,autorus,idus,nomus,contus,rolus,fecha from auditoriaUsuarios");
            Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK);
            Font font2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK);

            FileOutputStream archivo;
            String fileName = "AuditoriaUsuarios_" + UUID.randomUUID().toString();
            File file = new File(ruta + "/" + fileName + ".pdf");
            archivo = new FileOutputStream(file);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();

            Image img = Image.getInstance("src/IMAGENES/LOGO_PIZZA.png");
            img.scaleToFit(100, 100); 

            Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 24, Font.BOLD, BaseColor.BLACK);
            Font dateFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK);
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMMMM yyyy");
            String fechaActual = dateFormat.format(new Date());

            PdfPTable headerTable = new PdfPTable(3);
            headerTable.setWidthPercentage(100);

            PdfPCell imageCell = new PdfPCell(img);
            imageCell.setBorder(Rectangle.NO_BORDER);
            headerTable.addCell(imageCell);

            PdfPCell titleCell = new PdfPCell();
            titleCell.setBorder(Rectangle.NO_BORDER);
            titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCell.addElement(new Phrase("Auditoria Usuarios", titleFont));
            headerTable.addCell(titleCell);

            PdfPCell dateCell = new PdfPCell(new Phrase(fechaActual, dateFont));
            dateCell.setBorder(Rectangle.NO_BORDER);
            dateCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            dateCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            headerTable.addCell(dateCell);

            doc.add(headerTable);

            doc.add(new Paragraph(" "));

            PdfPTable tabla = new PdfPTable(8);
            tabla.setWidthPercentage(100);
            
            float[] columnWidths = { 8f, 20f, 16f, 17f, 22f, 18f, 16f, 20f }; 
            tabla.setWidths(columnWidths);

            tabla.addCell(new Phrase("Nro.",font));
            tabla.addCell(new Phrase("Accion",font));
            tabla.addCell(new Phrase("Autor",font));
            tabla.addCell(new Phrase("Cod. Us.",font));
            tabla.addCell(new Phrase("Nombres",font));
            tabla.addCell(new Phrase("Contraseña",font));
            tabla.addCell(new Phrase("Rol",font));
            tabla.addCell(new Phrase("Fecha",font));

            while (rs.next()) {
                tabla.addCell(new Phrase(rs.getString("idaus"),font2));
                tabla.addCell(new Phrase(rs.getString("accionus"),font2));
                tabla.addCell(new Phrase(rs.getString("autorus"),font2));
                tabla.addCell(new Phrase(rs.getString("idus"),font2));
                tabla.addCell(new Phrase(rs.getString("nomus"),font2));
                tabla.addCell(new Phrase(rs.getString("contus"),font2));
                tabla.addCell(new Phrase(rs.getString("rolus"),font2));
                tabla.addCell(new Phrase(rs.getString("fecha"),font2));
            }

            doc.add(tabla);
            doc.close();
            archivo.close();

            Desktop.getDesktop().open(file);
        } catch (DocumentException | IOException | SQLException e) {
            Mensajes.ERROR("No se pudo realizar el PDF", "ERROR");
        }
    }
}
