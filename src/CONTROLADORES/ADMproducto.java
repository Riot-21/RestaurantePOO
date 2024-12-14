
package CONTROLADORES;
import DAO.AuditoriaProd;
import DAO.CRUDproducto;
import FORMATOS.Mensajes;
import MODELO.Eventos;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import VISTA.MENU_ADMIN;
import MODELO.Producto;
import REPORTES.ExcelProductos;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class ADMproducto implements ActionListener, KeyListener{
    MENU_ADMIN menu3;
    CRUDproducto crud;
    Producto pr;
    Eventos event;
    AuditoriaProd apr;

    public ADMproducto(MENU_ADMIN m3){
        this.menu3 = m3;
        this.menu3.PRguardar.addActionListener(this);
        this.menu3.PRagregar.addActionListener(this);
        this.menu3.PReditar.addActionListener(this);
        this.menu3.PReliminar.addActionListener(this);
        this.menu3.BuscarPR.addActionListener(this);
        this.menu3.PRprecio.addKeyListener(this);
        this.menu3.PRstock.addKeyListener(this);
        this.menu3.eliminarBuscarPR.addActionListener(this);
        this.menu3.PRguardar.setEnabled(false);
        this.menu3.ReporteExcelPR.addActionListener(this);
        this.menu3.AuditoriaPR.addActionListener(this);
        crud=new CRUDproducto();
        crud.MostrarTablaProducto(menu3.jtblPR);
        
        menu3.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu3.jtblPR.clearSelection();
                menu3.PRguardar.setEnabled(false);
                menu3.PRagregar.setEnabled(true);
                LimpiarEntradas();}});
    }
    void LeerProducto(){
        pr=new Producto();
        pr.setDescripcion(menu3.PRdescripcion.getText());
        pr.setPrecio(Double.parseDouble(menu3.PRprecio.getText()));
        pr.setStock(Integer.parseInt(menu3.PRstock.getText()));
    }
    
    void LimpiarEntradas(){
        this.menu3.PRdescripcion.setText("");
        this.menu3.PRprecio.setText("");
        this.menu3.PRstock.setText("");
        this.menu3.PRdescripcion.requestFocus();
    } 
    
    void ActualizarTabla(){
        crud=new CRUDproducto();
        crud.MostrarTablaProducto(menu3.jtblPR);
        LimpiarEntradas();
    }
        
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menu3.PRagregar) {
            int selectedRow = menu3.jtblPR.getSelectedRow();
                if (selectedRow >= 0) {
                    Mensajes.ERROR("Producto ya existente. No se puede agregar a la tabla", "ERROR");
                    menu3.jtblPR.clearSelection();
                    LimpiarEntradas();
                }else if (menu3.PRdescripcion.getText().isEmpty() || menu3.PRprecio.getText().isEmpty() || menu3.PRstock.getText().isEmpty()) {
                    Mensajes.ERROR("Por favor, complete todos los campos", "Error");
                    return;
                }else{
                    LeerProducto();           
                    crud=new CRUDproducto();
                    String us=menu3.CodUsuario.getText();
                    String accion="AGREGAR";
                    crud.IngresarProductos(pr,us,accion);
                    ActualizarTabla();
                }               
        }
        if (e.getSource() == menu3.PReliminar) {
            int selectedRow = menu3.jtblPR.getSelectedRow();
                if (selectedRow >= 0) {
                int option= Mensajes.CONFIRMACION("Confirmación","¿Está seguro de eliminar el producto?");
                if (option == JOptionPane.YES_OPTION) {
                    String codigo = menu3.jtblPR.getValueAt(selectedRow, 0).toString();
                    String descripcion = menu3.jtblPR.getValueAt(selectedRow, 1).toString();
                    double precio = Double.parseDouble(menu3.jtblPR.getValueAt(selectedRow, 2).toString().substring(2));
                    int stock = Integer.parseInt(menu3.jtblPR.getValueAt(selectedRow, 3).toString());
                    
                    crud=new CRUDproducto();
                    String us=menu3.CodUsuario.getText();
                    String accion="ELIMINAR";
                    crud.EliminarProducto(codigo,accion,us,descripcion,precio,stock);
                    ActualizarTabla();
                    }else{menu3.jtblPR.clearSelection();}
                } else {
                    Mensajes.ERROR("Por favor, seleccione una fila para eliminar", "ERROR");
                }
        }
        if (e.getSource() == menu3.PReditar) {
                int selectedRow = menu3.jtblPR.getSelectedRow();
                if (selectedRow >= 0) {
                    String codigo = menu3.jtblPR.getValueAt(selectedRow, 0).toString();
                    String descripcion = menu3.jtblPR.getValueAt(selectedRow, 1).toString();
                    String precio = menu3.jtblPR.getValueAt(selectedRow, 2).toString();
                    String stock = menu3.jtblPR.getValueAt(selectedRow, 3).toString();
                    String valorNumerico=precio.substring(2);
                            
                    menu3.PRdescripcion.setText(descripcion);
                    menu3.PRprecio.setText(valorNumerico);
                    menu3.PRstock.setText(stock);
                    menu3.PRguardar.setEnabled(true);
                    menu3.PRagregar.setEnabled(false);
                } else {
                    Mensajes.ERROR("Por favor, seleccione una fila para editar", "ERROR");
                }
        }
        if (e.getSource() == menu3.PRguardar) {
            int selectedRow = menu3.jtblPR.getSelectedRow();
                if (selectedRow >= 0) {
                int option= Mensajes.CONFIRMACION("Confirmación","¿Está seguro de actualizar el producto?");
                if (option == JOptionPane.YES_OPTION) {
                    String codigo = menu3.jtblPR.getValueAt(selectedRow, 0).toString();
                    LeerProducto();
                    crud=new CRUDproducto();
                    String us=menu3.CodUsuario.getText();
                    String accion="ACTUALIZAR";
                    crud.ActualizarProducto(pr, codigo,accion,us);
                    ActualizarTabla();
                    menu3.PRguardar.setEnabled(false);
                    menu3.PRagregar.setEnabled(true);
                    }else{
                        menu3.jtblPR.clearSelection();
                        menu3.PRguardar.setEnabled(false);
                        menu3.PRagregar.setEnabled(true);
                        LimpiarEntradas();
                    }
                } else {
                    Mensajes.ERROR("Por favor, seleccione una fila para actualizar datos", "ERROR");
                }
        }
        if (e.getSource()== menu3.ReporteExcelPR){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Seleccionar ruta de descarga");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int seleccion = fileChooser.showDialog(null, "Seleccionar");

            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File selectedDirectory = fileChooser.getSelectedFile();
                String rutaDescarga = selectedDirectory.getAbsolutePath();
                ExcelProductos.reporte(rutaDescarga);
            }else{
                Mensajes.ERROR("Operacion cancelada por el usuario", "ERROR");
            }
        }
        if (e.getSource()==menu3.BuscarPR){
            String nombre;
            if(menu3.PRbuscador.getText().isEmpty()){
                Mensajes.ERROR("Por favor ingrese nombre del producto a buscar", "ERROR");
            }else{
            nombre = menu3.PRbuscador.getText();
            crud=new CRUDproducto();
            crud.MostrarBusquedaProducto(menu3.jtblPR, nombre);
            this.menu3.PRbuscador.setText("");
            }
        }
        if (e.getSource()==menu3.eliminarBuscarPR){
            ActualizarTabla();
            this.menu3.PRbuscador.setText("");
            menu3.PRguardar.setEnabled(false);
            menu3.PRagregar.setEnabled(true);
        }
        if(e.getSource()==menu3.AuditoriaPR){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Seleccionar ruta de descarga");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            apr=new AuditoriaProd();

            int seleccion = fileChooser.showDialog(null, "Seleccionar");

            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File selectedDirectory = fileChooser.getSelectedFile();
                String rutaDescarga = selectedDirectory.getAbsolutePath();
                apr.GenerarPDFAuditoriaProductos(rutaDescarga);
            }else{
                Mensajes.ERROR("Operacion cancelada por el usuario", "ERROR");
            }
        }
        
    }    

    @Override
    public void keyTyped(KeyEvent e) {
        event=new Eventos();
        if(e.getSource()==menu3.PRprecio){
            event.SoloDecimales(e, menu3.PRprecio);
        }
        if(e.getSource()==menu3.PRstock){
            event.SoloEnteros(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
