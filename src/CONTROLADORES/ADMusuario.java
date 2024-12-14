
package CONTROLADORES;
import DAO.AuditoriaUs;
import DAO.CRUDusuario;
import FORMATOS.Mensajes;
import MODELO.Eventos;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import VISTA.MENU_ADMIN;
import MODELO.Usuario;
import REPORTES.ExcelUsuarios;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class ADMusuario implements ActionListener,KeyListener{
    MENU_ADMIN menu4;
    CRUDusuario crud;
    Usuario us;
    Eventos event;
    AuditoriaUs aus;
    
    public ADMusuario(MENU_ADMIN m4){
        this.menu4 = m4;
        this.menu4.USagregar.addActionListener(this);
        this.menu4.USguardar.addActionListener(this);
        this.menu4.USeliminar.addActionListener(this);
        this.menu4.USeditar.addActionListener(this);
        this.menu4.BuscarUS.addActionListener(this);
        this.menu4.eliminarBuscarUS.addActionListener(this);
        this.menu4.USnombre.addKeyListener(this);
        this.menu4.USbuscador.addKeyListener(this);
        this.menu4.ReporteExcelUS.addActionListener(this);
        this.menu4.AuditoriaUS.addActionListener(this);
        menu4.USguardar.setEnabled(false);
        crud=new CRUDusuario();
        crud.MostrarTablaUsuario(menu4.jtblUS);
        
        menu4.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu4.jtblUS.clearSelection();
                LimpiarEntradas();
                menu4.USrol.setEnabled(true);
                menu4.USagregar.setEnabled(true);
                menu4.USguardar.setEnabled(false);}});
                
    }
    
    void LeerUsuario(){
        us=new Usuario();
        us.setNombre(menu4.USnombre.getText());
        us.setContraseña(menu4.UScontraseña.getText());
        us.setRol(menu4.USrol.getSelectedItem().toString());
    }
    
    void LimpiarEntradas(){
        this.menu4.USnombre.setText("");
        this.menu4.UScontraseña.setText("");
        this.menu4.USrol.setSelectedIndex(0);
        this.menu4.USnombre.requestFocus();
        menu4.jtblUS.clearSelection();
    }
    
    void ActualizarTabla(){
        crud=new CRUDusuario();
        crud.MostrarTablaUsuario(menu4.jtblUS);
        LimpiarEntradas();
    }
        
    @Override
    public void actionPerformed(ActionEvent e) {        
        if (e.getSource() == menu4.USagregar) {
            int selectedRow = menu4.jtblUS.getSelectedRow();
                if (selectedRow >= 0) {
                    Mensajes.ERROR("Usuario ya existente. No se puede agregar a la tabla", "ERROR");
                    menu4.jtblUS.clearSelection();
                    LimpiarEntradas();
                }else if (menu4.USnombre.getText().isEmpty() || menu4.UScontraseña.getText().isEmpty()) {
                    Mensajes.ERROR("Por favor, complete todos los campos", "Error");
                    return;
                }else{
                    LeerUsuario();           
                    crud=new CRUDusuario();
                    String usuario=menu4.CodUsuario.getText();
                    String accion="AGREGAR";
                    crud.IngresarUsuarios(us,accion,usuario);
                    ActualizarTabla();
                }
        }
        if (e.getSource() == menu4.USeliminar) {
            int selectedRow = menu4.jtblUS.getSelectedRow();
                if (selectedRow >= 0) {
                int option= Mensajes.CONFIRMACION("Confirmación","¿Está seguro de eliminar el usuario?");
                if (option == JOptionPane.YES_OPTION) {
                    String codigo = menu4.jtblUS.getValueAt(selectedRow, 0).toString();
                    String nombre = menu4.jtblUS.getValueAt(selectedRow, 1).toString();
                    String contraseña = menu4.jtblUS.getValueAt(selectedRow, 2).toString();
                    String rol = menu4.jtblUS.getValueAt(selectedRow, 3).toString();
                    
                    crud=new CRUDusuario();
                    String usuario=menu4.CodUsuario.getText();
                    String accion="ELIMINAR";
                    crud.EliminarUsuario(codigo,accion,usuario,nombre,contraseña,rol);
                    ActualizarTabla();
                    }else{menu4.jtblUS.clearSelection();}
                } else {
                    Mensajes.ERROR("Por favor, seleccione una fila para eliminar", "ERROR");
                }
        }
        if (e.getSource() == menu4.USeditar) {
                int selectedRow = menu4.jtblUS.getSelectedRow();
                if (selectedRow >= 0) {
                    String codigo = menu4.jtblUS.getValueAt(selectedRow, 0).toString();
                    String nombre = menu4.jtblUS.getValueAt(selectedRow, 1).toString();
                    String contraseña = menu4.jtblUS.getValueAt(selectedRow, 2).toString();
                    String rol = menu4.jtblUS.getValueAt(selectedRow, 3).toString();

                    menu4.USnombre.setText(nombre);
                    menu4.UScontraseña.setText(contraseña);
                    menu4.USrol.setSelectedItem(rol);
                    menu4.USrol.setEnabled(false);  
                    menu4.USguardar.setEnabled(true);
                    menu4.USagregar.setEnabled(false);
                } else {
                    Mensajes.ERROR("Por favor, seleccione una fila para editar", "ERROR");
                }
        }
        if (e.getSource() == menu4.USguardar) {
            int selectedRow = menu4.jtblUS.getSelectedRow();
                if (selectedRow >= 0) {
                int option= Mensajes.CONFIRMACION("Confirmación","¿Está seguro de actualizar el usuario?");
                if (option == JOptionPane.YES_OPTION) {
                    String codigo = menu4.jtblUS.getValueAt(selectedRow, 0).toString();
                    LeerUsuario();
                    crud=new CRUDusuario();
                    String usuario=menu4.CodUsuario.getText();
                    String accion="ACTUALIZAR";
                    crud.ActualizarUsuario(us, codigo,accion,usuario);
                    ActualizarTabla();
                    menu4.USguardar.setEnabled(false);
                    menu4.USagregar.setEnabled(true);
                    menu4.USrol.setEnabled(true); 
                    }else{
                        menu4.jtblUS.clearSelection();
                        menu4.USguardar.setEnabled(false);
                        menu4.USagregar.setEnabled(true);
                        menu4.USrol.setEnabled(true); 
                        LimpiarEntradas();
                    }
                } else {
                    Mensajes.ERROR("Por favor, seleccione una fila para actualizar datos", "ERROR");
                }
        }
        if(e.getSource()==menu4.ReporteExcelUS){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Seleccionar ruta de descarga");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int seleccion = fileChooser.showDialog(null, "Seleccionar");

            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File selectedDirectory = fileChooser.getSelectedFile();
                String rutaDescarga = selectedDirectory.getAbsolutePath();
                ExcelUsuarios.reporte(rutaDescarga);
            }else{
                Mensajes.ERROR("Operacion cancelada por el usuario", "ERROR");
            }
        }
        if (e.getSource()==menu4.BuscarUS){
            String nombre;
            if(menu4.USbuscador.getText().isEmpty()){
                Mensajes.ERROR("Por favor ingrese nombre del usuario a buscar", "ERROR");
            }else{
            nombre = menu4.USbuscador.getText();
            crud=new CRUDusuario();
            crud.MostrarBusquedaUsuario(menu4.jtblUS, nombre);
            this.menu4.USbuscador.setText("");
            }
        }
        if (e.getSource()==menu4.eliminarBuscarUS){
            ActualizarTabla();
            this.menu4.USbuscador.setText("");
            menu4.USguardar.setEnabled(false);
            menu4.USrol.setEnabled(true);
            menu4.USagregar.setEnabled(true);
        }
        if(e.getSource()==menu4.AuditoriaUS){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Seleccionar ruta de descarga");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            aus=new AuditoriaUs();

            int seleccion = fileChooser.showDialog(null, "Seleccionar");

            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File selectedDirectory = fileChooser.getSelectedFile();
                String rutaDescarga = selectedDirectory.getAbsolutePath();
                aus.GenerarPDFAuditoriaUsuarios(rutaDescarga);
            }else{
                Mensajes.ERROR("Operacion cancelada por el usuario", "ERROR");
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        event=new Eventos();
        if(e.getSource()==menu4.USnombre){
            event.SoloTexto(e);
        }
        if(e.getSource()==menu4.USbuscador){
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
