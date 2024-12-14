package CONTROLADORES;
import DAO.CRUDventa;
import DAO.Pdf;
import FORMATOS.Mensajes;
import REPORTES.ExcelRepVentasEmp;
import VISTA.MENU_EMP;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JFileChooser;

public class EMPventas implements ActionListener{
    MENU_EMP menu5;
    CRUDventa crud;
    Pdf pdf;
    
    public EMPventas(MENU_EMP m5){
        this.menu5=m5;
        crud=new CRUDventa();
        crud.MostrarTablaVentasEmpleado(menu5.jtblVENTAS,menu5.CodUsuario.getText());
        Calendar cal=new GregorianCalendar();
        menu5.JCalendar.setCalendar(cal);
        menu5.BuscarFechaVenta.addActionListener(this);
        menu5.LimpiarBusqueda.addActionListener(this);
        menu5.ReporteExcelVENTAS.addActionListener(this);
        menu5.PDFventas.addActionListener(this);
        menu5.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu5.jtblVENTAS.clearSelection();}});
    }
    void ActualizarTabla(){
        crud=new CRUDventa();
        crud.MostrarTablaVentasEmpleado(menu5.jtblVENTAS,menu5.CodUsuario.getText());  
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==menu5.BuscarFechaVenta){
            crud=new CRUDventa();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String fechaSeleccionada = dateFormat.format(menu5.JCalendar.getDate());
            crud.actualizarTablaVentasEmp(menu5.jtblVENTAS, fechaSeleccionada,menu5.CodUsuario.getText());
        }
        if(e.getSource()==menu5.LimpiarBusqueda){
            ActualizarTabla();
        }
        if(e.getSource()==menu5.PDFventas){
            int selectedRow = menu5.jtblVENTAS.getSelectedRow();
            if (selectedRow>=0){
                String codiventa=menu5.jtblVENTAS.getValueAt(selectedRow, 0).toString();
                pdf=new Pdf();
                pdf.AbrirPDF(codiventa);
            }else{
                Mensajes.ERROR("Por favor seleccione una fila para abrir el archivo", "ERROR");
            }   
        }
        if(e.getSource()==menu5.ReporteExcelVENTAS){
            String vendedor=menu5.CodUsuario.getText();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Seleccionar ruta de descarga");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int seleccion = fileChooser.showDialog(null, "Seleccionar");

            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File selectedDirectory = fileChooser.getSelectedFile();
                String rutaDescarga = selectedDirectory.getAbsolutePath();
                ExcelRepVentasEmp.reporte(vendedor,rutaDescarga);
            }else{
                Mensajes.ERROR("Operacion cancelada por el usuario", "ERROR");
            }
        }
    }
}
