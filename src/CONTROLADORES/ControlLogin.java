
package CONTROLADORES;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import VISTA.LOGIN;
import VISTA.MENU_ADMIN;
import VISTA.MENU_EMP;
import DAO.LoginDao;
import FORMATOS.Mensajes;

public class ControlLogin implements ActionListener {
    LOGIN login;
    ADMnuevaventa admVenta;
    EMPnuevaventa empVenta;
    ADMcliente admCliente;
    EMPcliente empCliente;
    ADMproducto admProducto;
    ADMusuario admUsuario;
    LoginDao lg;
    EMPproducto empProducto;
    ADMempresa admEmpresa;
    EMPempresa empEmpresa;
    ADMventas admVentas;
    EMPventas empVentas;

    public ControlLogin(LOGIN lgn) {
        this.login = lgn;
        this.login.btnlogin.addActionListener(this);
    }

    public void setADMnuevaventa(ADMnuevaventa admVenta) {
        this.admVenta = admVenta;}
    public void setEMPnuevaventa(EMPnuevaventa empVenta) {
        this.empVenta=empVenta;}
    public void setADMcliente(ADMcliente admCliente) {
        this.admCliente=admCliente;}
    public void setEMPcliente(EMPcliente empCliente){
        this.empCliente=empCliente;}
    public void setADMproducto(ADMproducto admProducto){
        this.admProducto=admProducto;}
    public void setADMusuario(ADMusuario admUsuario){
        this.admUsuario=admUsuario;}
    public void setEMPproducto(EMPproducto empProducto){
        this.empProducto=empProducto;}
    public void setADMempresa(ADMempresa admEmpresa){
        this.admEmpresa=admEmpresa;}
    public void setEMPempresa(EMPempresa empEmpresa){
        this.empEmpresa=empEmpresa;}
    public void setADMventas(ADMventas admVentas){
        this.admVentas=admVentas;}
    public void setEMPventas(EMPventas empVentas){
        this.empVentas=empVentas;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login.btnlogin) {
            String username = login.userTxt.getText();
            String password = login.passTxt.getText();
            String prefix = username.substring(0, 3); 

            if (prefix.equals("ADM")) {
                lg = new LoginDao();
                lg.ValidarUsuario(username, password);  
                if(lg.usuarioValido==true){
                  MENU_ADMIN adminForm = new MENU_ADMIN();
                  adminForm.setVisible(true);
                  adminForm.NombreUsuario.setText(lg.getNombreus());
                  adminForm.CodUsuario.setText(username);
                  
                ADMnuevaventa admVentaControl = new ADMnuevaventa(adminForm);
                setADMnuevaventa(admVentaControl);
                
                ADMcliente admClienteControl = new ADMcliente(adminForm);
                setADMcliente(admClienteControl);
                
                ADMproducto admProductoControl = new ADMproducto(adminForm);
                setADMproducto(admProductoControl);
                
                ADMusuario admUsuarioControl = new ADMusuario(adminForm);
                setADMusuario(admUsuarioControl); 
                
                ADMempresa admEmpresaControl = new ADMempresa(adminForm);
                setADMempresa(admEmpresaControl);
                
                ADMventas admVentasControl = new ADMventas(adminForm);
                setADMventas(admVentasControl);
                    
                login.dispose();
                }               
            } else if (prefix.equals("EMP")) {
                lg = new LoginDao();
                lg.ValidarUsuario(username, password);
                if(lg.usuarioValido==true){
                MENU_EMP employeeForm = new MENU_EMP();
                employeeForm.setVisible(true);
                employeeForm.NombreUsuario.setText(lg.getNombreus());
                employeeForm.CodUsuario.setText(username);
                
                EMPnuevaventa empVentaControl = new EMPnuevaventa(employeeForm);
                setEMPnuevaventa(empVentaControl);
                
                EMPcliente empClienteControl = new EMPcliente(employeeForm);
                setEMPcliente(empClienteControl);
                
                EMPproducto empProductoControl = new EMPproducto(employeeForm);
                setEMPproducto( empProductoControl);
                
                EMPempresa empEmpresaControl = new EMPempresa(employeeForm);
                setEMPempresa(empEmpresaControl);
                
                EMPventas empVentasControl = new EMPventas(employeeForm);
                setEMPventas(empVentasControl);
                
                login.dispose();
                }            
            }else{
                Mensajes.ERROR("Nombre de usuario o contraseña inválido", "ERROR DE INICIO DE SESION");
            }
        
        }
        if(e.getSource()==login.exitTxt){
            System.exit(0);
        }
    }
}
