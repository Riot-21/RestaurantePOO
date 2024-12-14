
package CONTROLADORES;
import DAO.CRUDempresa;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import VISTA.MENU_EMP;
import MODELO.Empresa;
import FORMATOS.Mensajes;
import VISTA.MENU_ADMIN;

public class EMPempresa {
    MENU_EMP menu4;
    CRUDempresa crud;
    Empresa em;
    
    public EMPempresa(MENU_EMP m4){
        this.menu4=m4;
        crud=new CRUDempresa();
        crud.ObtenerDatosEmpresa();
        menu4.nombreEmpresa.setText(crud.getNombreem());
        menu4.rucEmpresa.setText(Long.toString(crud.getRuc()));
        menu4.direccionEmpresa.setText(crud.getDireccion());
        menu4.razonEmpresa.setText(crud.getRazonsocial());
        menu4.telefonoEmpresa.setText(Integer.toString(crud.getTelefono()));
    }
    
}
