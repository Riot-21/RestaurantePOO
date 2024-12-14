package CONTROLADORES;
import DAO.CRUDventa;
import DAO.Pdf;
import FORMATOS.Mensajes;
import REPORTES.ExcelReporteVentas;
import VISTA.MENU_ADMIN;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JFileChooser;

public class ADMventas implements ActionListener{
    MENU_ADMIN menu6;
    CRUDventa crud;
    Pdf pdf;
    
    public ADMventas(MENU_ADMIN m6){
        this.menu6=m6;
        crud=new CRUDventa();
        crud.MostrarTablaVentas(menu6.jtblVENTAS);
        Calendar cal=new GregorianCalendar();
        menu6.JCalendar.setCalendar(cal);
        menu6.BuscarFechaVenta.addActionListener(this);
        menu6.LimpiarBusqueda.addActionListener(this);
        menu6.PDFventas.addActionListener(this);
        menu6.ReporteExcelVENTAS.addActionListener(this);
        menu6.ReportePastel.addActionListener(this);
        
        menu6.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu6.jtblVENTAS.clearSelection();}});
    }
    void ActualizarTabla(){
        crud=new CRUDventa();
        crud.MostrarTablaVentas(menu6.jtblVENTAS);   
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==menu6.BuscarFechaVenta){
            crud=new CRUDventa();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String fechaSeleccionada = dateFormat.format(menu6.JCalendar.getDate());
            crud.actualizarTablaVentas(menu6.jtblVENTAS, fechaSeleccionada);
        }
        if(e.getSource()==menu6.LimpiarBusqueda){
            ActualizarTabla();
        }
        if(e.getSource()==menu6.PDFventas){
            int selectedRow = menu6.jtblVENTAS.getSelectedRow();
            if (selectedRow>=0){
                String codiventa=menu6.jtblVENTAS.getValueAt(selectedRow, 0).toString();
                pdf=new Pdf();
                pdf.AbrirPDF(codiventa);
            }else{
                Mensajes.ERROR("Por favor seleccione una fila para abrir el archivo", "ERROR");
            }   
        }
        if(e.getSource()==menu6.ReporteExcelVENTAS){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Seleccionar ruta de descarga");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int seleccion = fileChooser.showDialog(null, "Seleccionar");

            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File selectedDirectory = fileChooser.getSelectedFile();
                String rutaDescarga = selectedDirectory.getAbsolutePath();
                ExcelReporteVentas.reporte(rutaDescarga);
            }else{
                Mensajes.ERROR("Operacion cancelada por el usuario", "ERROR");
            }
        }
        if(e.getSource()==menu6.ReportePastel){
            CRUDventa crudVenta = new CRUDventa();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String fechaSeleccionada = dateFormat.format(menu6.JCalendar.getDate());
            crudVenta.generarGraficoPastel(fechaSeleccionada);
        }
    }
}
