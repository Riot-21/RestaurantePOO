package CONTROLADORES;
import DAO.CRUDproducto;
import FORMATOS.Mensajes;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import VISTA.MENU_EMP;
import MODELO.Producto;

public class EMPproducto implements ActionListener{
    MENU_EMP menu3;
    CRUDproducto crud;
    Producto pr;
    
    public EMPproducto(MENU_EMP m3){
        this.menu3 = m3;
        this.menu3.BuscarPR.addActionListener(this);
        this.menu3.eliminarBuscarPR.addActionListener(this);
        crud=new CRUDproducto();
        crud.MostrarTablaProducto(menu3.jtblPR);

        menu3.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu3.jtblPR.clearSelection();}});
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==menu3.BuscarPR){
            String nombre;
            if(menu3.PRbuscador.getText().isEmpty()){
                Mensajes.ERROR("Por favor ingrese nombre del cliente a buscar", "ERROR");
            }else{
            nombre = menu3.PRbuscador.getText();
            crud=new CRUDproducto();
            crud.MostrarBusquedaProducto(menu3.jtblPR, nombre);
            this.menu3.PRbuscador.setText("");
            }
        }
        if (e.getSource()==menu3.eliminarBuscarPR){
            crud=new CRUDproducto();
            crud.MostrarTablaProducto(menu3.jtblPR);
            this.menu3.PRbuscador.setText("");           
        }
    }  
    
}
