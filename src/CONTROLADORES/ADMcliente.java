
package CONTROLADORES;
import DAO.AuditoriaCli;
import DAO.CRUDcliente;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import VISTA.MENU_ADMIN;
import MODELO.Cliente;
import FORMATOS.Mensajes;
import MODELO.Eventos;
import REPORTES.ExcelClientes;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class ADMcliente implements ActionListener, KeyListener {
    MENU_ADMIN menu2;
    CRUDcliente crud;
    Cliente cl;
    Eventos event;
    AuditoriaCli acl;

    public ADMcliente(MENU_ADMIN m2) {
        this.menu2 = m2;
        this.menu2.CLagregar.addActionListener(this);
        this.menu2.CLguardar.addActionListener(this);
        this.menu2.CLeliminar.addActionListener(this);
        this.menu2.CLeditar.addActionListener(this);
        this.menu2.BuscarCL.addActionListener(this);
        this.menu2.eliminarBuscarCL.addActionListener(this);
        this.menu2.CLnombre.addKeyListener(this);
        this.menu2.CLapellidos.addKeyListener(this);
        this.menu2.CLdni.addKeyListener(this);
        this.menu2.CLbuscador.addKeyListener(this);
        this.menu2.ReporteExcelCL.addActionListener(this);
        this.menu2.AuditoriaCL.addActionListener(this);
        menu2.CLguardar.setEnabled(false);
        crud=new CRUDcliente();
        crud.MostrarTablaCliente(menu2.jtblCL);
        
        menu2.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu2.jtblCL.clearSelection();
                menu2.CLguardar.setEnabled(false);
                menu2.CLagregar.setEnabled(true);
                LimpiarEntradas();}});
    }
    void LeerCliente(){
        cl=new Cliente();
        cl.setDni(Integer.parseInt(menu2.CLdni.getText()));
        cl.setNombre(menu2.CLnombre.getText());
        cl.setApellidos(menu2.CLapellidos.getText());
    }

    void LimpiarEntradas() {
        this.menu2.CLnombre.setText("");
        this.menu2.CLapellidos.setText("");
        this.menu2.CLdni.setText("");
        this.menu2.CLnombre.requestFocus();
    }
    
    void ActualizarTabla(){
        crud=new CRUDcliente();
        crud.MostrarTablaCliente(menu2.jtblCL);
        LimpiarEntradas();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menu2.CLagregar) {
            int selectedRow = menu2.jtblCL.getSelectedRow();
            String dniText = menu2.CLdni.getText();
                if (selectedRow >= 0) {
                    Mensajes.ERROR("Cliente ya existente. No se puede agregar a la tabla", "ERROR");
                    menu2.jtblCL.clearSelection();
                    LimpiarEntradas();
                }else if (menu2.CLdni.getText().isEmpty() || menu2.CLnombre.getText().isEmpty() || menu2.CLapellidos.getText().isEmpty()) {
                    Mensajes.ERROR("Por favor, complete todos los campos", "Error");
                    return; 
                }else if (dniText.length() != 8) {
                    Mensajes.ERROR("El DNI debe tener 8 dígitos", "ERROR");
                    return;
                }else{
                    int dni=Integer.parseInt(menu2.CLdni.getText());
                    for(int i=0;i<menu2.jtblCL.getRowCount();i++){
                        if(menu2.jtblCL.getValueAt(i, 1).equals(dni)){
                            Mensajes.ERROR("El cliente ya está registrado", "ERROR");
                            LimpiarEntradas();
                            return;}
                        }
                            LeerCliente();
                            crud=new CRUDcliente();
                            String us=menu2.CodUsuario.getText();
                            String accion="AGREGAR";
                            crud.IngresarClientes(cl,accion,us);
                            ActualizarTabla();            
                }
        }
        if (e.getSource() == menu2.CLeliminar) {
            int selectedRow = menu2.jtblCL.getSelectedRow();
                if (selectedRow >= 0) {
                int option= Mensajes.CONFIRMACION("Confirmación","¿Está seguro de eliminar el cliente?");
                if (option == JOptionPane.YES_OPTION) {
                    String codigo = menu2.jtblCL.getValueAt(selectedRow, 0).toString();
                    int dni = Integer.parseInt(menu2.jtblCL.getValueAt(selectedRow, 1).toString());
                    String nombre = menu2.jtblCL.getValueAt(selectedRow, 2).toString();
                    String apellidos = menu2.jtblCL.getValueAt(selectedRow, 3).toString();
                    
                    crud=new CRUDcliente();
                    String us=menu2.CodUsuario.getText();
                    String accion="ELIMINAR";
                    crud.EliminarCliente(codigo,accion,us,dni,nombre,apellidos);
                    ActualizarTabla();
                    }else{menu2.jtblCL.clearSelection();}
                } else {
                    Mensajes.ERROR("Por favor, seleccione una fila para eliminar", "ERROR");
                }
        }
        if (e.getSource() == menu2.CLeditar) {
                int selectedRow = menu2.jtblCL.getSelectedRow();
                if (selectedRow>=0) {
                    String codigo = menu2.jtblCL.getValueAt(selectedRow, 0).toString();
                    String dni = menu2.jtblCL.getValueAt(selectedRow, 1).toString();
                    String nombre = menu2.jtblCL.getValueAt(selectedRow, 2).toString();
                    String apellidos = menu2.jtblCL.getValueAt(selectedRow, 3).toString();

                    menu2.CLdni.setText(dni);
                    menu2.CLnombre.setText(nombre);
                    menu2.CLapellidos.setText(apellidos);
                    menu2.CLguardar.setEnabled(true);
                    menu2.CLagregar.setEnabled(false);
                } else {
                    Mensajes.ERROR("Por favor, seleccione una fila para editar", "ERROR");
                }
        }
        if (e.getSource() == menu2.CLguardar) {
            int selectedRow = menu2.jtblCL.getSelectedRow();
                if (selectedRow >= 0) {
                int option= Mensajes.CONFIRMACION("Confirmación","¿Está seguro de actualizar el cliente?");
                if (option == JOptionPane.YES_OPTION) {
                    String codigo = menu2.jtblCL.getValueAt(selectedRow, 0).toString();
                    String dniText= menu2.CLdni.getText();
                    LeerCliente();
                    if (dniText.length() != 8){
                        Mensajes.ERROR("El DNI debe tener 8 digitos", "ERROR");
                        return;
                    }
                    crud=new CRUDcliente();
                    String us=menu2.CodUsuario.getText();
                    String accion="ACTUALIZAR";
                    crud.ActualizarCliente(cl, codigo,accion,us);
                    ActualizarTabla();
                    menu2.CLguardar.setEnabled(false);
                    menu2.CLagregar.setEnabled(true);
                    }else{
                        menu2.jtblCL.clearSelection();
                        menu2.CLguardar.setEnabled(false);
                        menu2.CLagregar.setEnabled(true);
                        LimpiarEntradas();
                    }
                } else {
                    Mensajes.ERROR("Por favor, seleccione una fila para actualizar datos", "ERROR");
                }
        }
        if(e.getSource()==menu2.ReporteExcelCL){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Seleccionar ruta de descarga");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int seleccion = fileChooser.showDialog(null, "Seleccionar");

            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File selectedDirectory = fileChooser.getSelectedFile();
                String rutaDescarga = selectedDirectory.getAbsolutePath();
                ExcelClientes.reporte(rutaDescarga);
            }else{
                Mensajes.ERROR("Operacion cancelada por el usuario", "ERROR");
            }
        }
        if (e.getSource()==menu2.BuscarCL){
            String nombre;
            if(menu2.CLbuscador.getText().isEmpty()){
                Mensajes.ERROR("Por favor ingrese nombre del cliente a buscar", "ERROR");
            }else{
            nombre = menu2.CLbuscador.getText();
            crud=new CRUDcliente();
            crud.MostrarBusquedaCliente(menu2.jtblCL, nombre);
            this.menu2.CLbuscador.setText("");
            }
        }
        if (e.getSource()==menu2.eliminarBuscarCL){
            ActualizarTabla();
            this.menu2.CLbuscador.setText("");
            menu2.CLguardar.setEnabled(false);
            menu2.CLagregar.setEnabled(true);
        }
        if(e.getSource()==menu2.AuditoriaCL){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Seleccionar ruta de descarga");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            acl=new AuditoriaCli();

            int seleccion = fileChooser.showDialog(null, "Seleccionar");

            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File selectedDirectory = fileChooser.getSelectedFile();
                String rutaDescarga = selectedDirectory.getAbsolutePath();
                acl.GenerarPDFAuditoriaClientes(rutaDescarga);
            }else{
                Mensajes.ERROR("Operacion cancelada por el usuario", "ERROR");
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        event=new Eventos();
        if(e.getSource()==menu2.CLnombre){
            event.SoloTexto(e);
        }
        if(e.getSource()==menu2.CLapellidos){
            event.SoloTexto(e);
        }
        if(e.getSource()==menu2.CLdni){
            event.SoloEnteros(e);
        }
        if(e.getSource()==menu2.CLbuscador){
            event.SoloTexto(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
        
}
