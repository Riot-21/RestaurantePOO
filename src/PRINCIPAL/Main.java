package PRINCIPAL;
import VISTA.*;
import CONTROLADORES.*;

public class Main {
    static LOGIN l1;
    static ADMnuevaventa admVenta;
    static ControlLogin controllgn;
    static EMPnuevaventa empVenta;
    
    public static void main(String[] args) {
        l1 = new LOGIN();
        l1.setVisible(true);
        l1.setLocationRelativeTo(null);
        
        MENU_ADMIN menuAdmin = new MENU_ADMIN();
        MENU_EMP menuEmp = new MENU_EMP();
        
        admVenta = new ADMnuevaventa(menuAdmin);
        empVenta = new EMPnuevaventa(menuEmp);

        
        controllgn = new ControlLogin(l1);

        // Configurar interacción entre los componentes
        l1.setControlLogin(controllgn);
        controllgn.setADMnuevaventa(admVenta);
        controllgn.setEMPnuevaventa(empVenta);
        
    }
}
