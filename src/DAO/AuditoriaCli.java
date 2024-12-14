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

public class AuditoriaCli extends ConexionBD{
    public void GenerarPDFAuditoriaClientes(String ruta) {
        try {
            rs = st.executeQuery("select idacl,accioncl,autorcl,idcl,dnicl,nomcl,apcl,fecha from auditoriaClientes");
            Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK);
            Font font2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK);

            FileOutputStream archivo;
            String fileName = "AuditoriaClientes_" + UUID.randomUUID().toString();
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
            titleCell.addElement(new Phrase("Auditoria Clientes", titleFont));
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
            
            float[] columnWidths = { 8f, 20f, 17f, 17f, 28f, 20f, 20f, 20f }; 
            tabla.setWidths(columnWidths);

            tabla.addCell(new Phrase("Nro.",font));
            tabla.addCell(new Phrase("Accion",font));
            tabla.addCell(new Phrase("Autor",font));
            tabla.addCell(new Phrase("Cod. Cl.",font));
            tabla.addCell(new Phrase("DNI",font));
            tabla.addCell(new Phrase("Nombre",font));
            tabla.addCell(new Phrase("Apellidos",font));
            tabla.addCell(new Phrase("Fecha",font));

            while (rs.next()) {
                tabla.addCell(new Phrase(rs.getString("idacl"),font2));
                tabla.addCell(new Phrase(rs.getString("accioncl"),font2));
                tabla.addCell(new Phrase(rs.getString("autorcl"),font2));
                tabla.addCell(new Phrase(rs.getString("idcl"),font2));
                tabla.addCell(new Phrase(rs.getString("dnicl"),font2));
                tabla.addCell(new Phrase(rs.getString("nomcl"),font2));
                tabla.addCell(new Phrase(rs.getString("apcl"),font2));
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
