package DAO;
import FORMATOS.Mensajes;
import MODELO.Cliente;
import MODELO.Venta;
import VISTA.*;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
public class Pdf {
    MENU_ADMIN menua;
    Cliente cl;
    CRUDnuevaventa crudnv;
    CRUDempresa cem;
    CRUDventa cventa;
    
    public void GenerarPDF(JTable tabla,Cliente clien,Venta vnt){
        cem=new CRUDempresa();
        cventa=new CRUDventa();
        try{       
            FileOutputStream archivo;
            File file = new File("src/PDF/"+cventa.GenerarCodigo()+".pdf");
            archivo = new FileOutputStream(file);
            Document doc= new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            Image img = Image.getInstance("src/IMAGENES/LOGO_PIZZA.png");
            
            Paragraph fecha = new Paragraph();
            Font negrita=new Font(Font.FontFamily.TIMES_ROMAN,12,Font.BOLD,new BaseColor(91,135,68));
            Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
            Font negritablanco = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, BaseColor.WHITE);
            fecha.add(Chunk.NEWLINE);
            Date date = new Date();
            Phrase fechadatos=new Phrase("Boleta: "+cventa.GenerarCodigo()+"\nFecha: "+ new SimpleDateFormat("dd-MM-yyyy").format(date)+"\n\n",font);
            fecha.add(fechadatos);
            
            PdfPTable Encabezado = new PdfPTable(4);
            Encabezado.setWidthPercentage(100);
            Encabezado.getDefaultCell().setBorder(0);
            float[] ColumnaEncabezado= new float[]{30f,20f,80f,40f};
            Encabezado.setWidths(ColumnaEncabezado);
            Encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);
            
            Encabezado.addCell(img);
            
            cem.ObtenerDatosEmpresa();
            Long ruc=cem.getRuc();
            String nom=cem.getNombreem();
            int tel=cem.getTelefono();
            String dir=cem.getDireccion();
            String ra=cem.getRazonsocial();
            
            Encabezado.addCell("");
            Phrase empresaInfo = new Phrase("Ruc: " + ruc + "\nNombre: " + nom + "\nTelefono: " + tel + "\nDireccion: " + dir + "\nRazon Social: " + ra, font);
            Encabezado.addCell(empresaInfo);
            Encabezado.addCell(fecha);
            doc.add(Encabezado);
            
            Paragraph cli= new Paragraph();
            cli.add(Chunk.NEWLINE);
            cli.add(new Phrase("Datos del cliente\n\n",font));
            doc.add(cli);
            
            PdfPTable tablacl=new PdfPTable(3);
            tablacl.setWidthPercentage(100);
            tablacl.getDefaultCell().setBorder(0);
            float[] Columnacl=new float[]{50f,50f,50f};
            tablacl.setWidths(Columnacl);
            tablacl.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell cl1=new PdfPCell(new Phrase("DNI",negrita));
            PdfPCell cl2=new PdfPCell(new Phrase("NOMBRE",negrita));
            PdfPCell cl3=new PdfPCell(new Phrase("APELLIDOS",negrita));
            cl1.setBorder(0);
            cl2.setBorder(0);
            cl3.setBorder(0);
            tablacl.addCell(cl1);
            tablacl.addCell(cl2);
            tablacl.addCell(cl3);
            tablacl.addCell(new Phrase(String.valueOf(clien.getDni()),font));
            tablacl.addCell(new Phrase(clien.getNombre(),font));
            tablacl.addCell(new Phrase(clien.getApellidos(),font));
            
            doc.add(tablacl);
            
            //Productos
            PdfPTable tablapr=new PdfPTable(4);
            tablapr.setWidthPercentage(100);
            tablapr.getDefaultCell().setBorder(0);
            float[] Columnapr=new float[]{15f,40f,20f,20f};
            tablapr.setWidths(Columnapr);
            tablapr.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell pr1=new PdfPCell(new Phrase("CANTIDAD",negritablanco));
            PdfPCell pr2=new PdfPCell(new Phrase("DETALLE DE PEDIDO",negritablanco));
            PdfPCell pr3=new PdfPCell(new Phrase("PRECIO UNIT.",negritablanco));
            PdfPCell pr4=new PdfPCell(new Phrase("IMPORTE",negritablanco));
            pr1.setBorder(0);
            pr2.setBorder(0);
            pr3.setBorder(0);
            pr4.setBorder(0);
            pr1.setBackgroundColor(new BaseColor(91,135,68));
            pr2.setBackgroundColor(new BaseColor(91,135,68));
            pr3.setBackgroundColor(new BaseColor(91,135,68));
            pr4.setBackgroundColor(new BaseColor(91,135,68));
            tablapr.addCell(pr1);
            tablapr.addCell(pr2);
            tablapr.addCell(pr3);
            tablapr.addCell(pr4);
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel(); 
            for(int i=0;i<modelo.getRowCount();i++){
                String producto=modelo.getValueAt(i, 1).toString();
                String cantidad=modelo.getValueAt(i, 2).toString();
                String preciou=modelo.getValueAt(i, 3).toString();
                String total=modelo.getValueAt(i, 4).toString();
                tablapr.addCell(new Phrase(cantidad,font));
                tablapr.addCell(new Phrase(producto,font));
                tablapr.addCell(new Phrase(preciou,font));
                tablapr.addCell(new Phrase(total,font));
            }
            doc.add(tablapr);
            
            Paragraph infoPago =new Paragraph();
            infoPago.add(Chunk.NEWLINE);
            infoPago.add(new Phrase("Subtotal: "+String.valueOf(vnt.getSubtotal())+"\nIGV: "+String.valueOf(vnt.getPigv())+"\nTOTAL: "+String.valueOf(vnt.getTotal()),font));
            infoPago.setAlignment(Element.ALIGN_RIGHT);
            doc.add(infoPago);
            
            Paragraph firma= new Paragraph();
            firma.add(Chunk.NEWLINE);
            firma.add(new Phrase("Cancelación y Firma \n\n -----------------------",font));
            firma.setAlignment(Element.ALIGN_CENTER);
            doc.add(firma);
            
            Paragraph mensaje= new Paragraph();
            mensaje.add(Chunk.NEWLINE);
            mensaje.add(new Phrase("Gracias por su compra",font));
            mensaje.setAlignment(Element.ALIGN_CENTER);
            doc.add(mensaje);
            
            doc.close();
            archivo.close();
            Desktop.getDesktop().open(file);
        }catch(Exception e){
            Mensajes.ERROR("No se pudo realizar el PDF "+e, "ERROR");
            System.out.println(e.toString());
        }
    }
    public void AbrirPDF(String codigoventa){
        try {
            File file= new File("src/PDF/"+codigoventa+".pdf");
            Desktop.getDesktop().open(file);
        } catch (IOException ex) {
            Mensajes.ERROR("Error al abrir el PDF", "ERROR");
        }
        
    }
}
