
package CONTROLADORES;
import DAO.CRUDempresa;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import VISTA.MENU_ADMIN;
import MODELO.Empresa;
import FORMATOS.Mensajes;
import MODELO.Eventos;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ADMempresa implements ActionListener,KeyListener{
    MENU_ADMIN menu5;
    CRUDempresa crud;
    Empresa em;
    Eventos event;
    
    public ADMempresa(MENU_ADMIN m5){
        this.menu5=m5;
        this.menu5.EMactualizar.addActionListener(this);
        this.menu5.nombreEmpresa.addKeyListener(this);
        this.menu5.rucEmpresa.addKeyListener(this);
        this.menu5.telefonoEmpresa.addKeyListener(this);
        MostrarDatos();
        
        menu5.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MostrarDatos();}});
        }
    
    void MostrarDatos(){
        crud=new CRUDempresa();
        crud.ObtenerDatosEmpresa();
        menu5.nombreEmpresa.setText(crud.getNombreem());
        menu5.rucEmpresa.setText(Long.toString(crud.getRuc()));
        menu5.direccionEmpresa.setText(crud.getDireccion());
        menu5.razonEmpresa.setText(crud.getRazonsocial());
        menu5.telefonoEmpresa.setText(Integer.toString(crud.getTelefono()));
    }
    
    void LeerEmpresa(){
        em=new Empresa();
        em.setNombreem(menu5.nombreEmpresa.getText());
        em.setRuc(Long.parseLong(menu5.rucEmpresa.getText()));
        em.setDireccion(menu5.direccionEmpresa.getText());
        em.setRazonsocial(menu5.razonEmpresa.getText());
        em.setTelefono(Integer.parseInt(menu5.telefonoEmpresa.getText()));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==menu5.EMactualizar){
            String ruc = menu5.rucEmpresa.getText();
            String telefono = menu5.telefonoEmpresa.getText();
            if(menu5.nombreEmpresa.getText().isEmpty() || menu5.rucEmpresa.getText().isEmpty() || menu5.direccionEmpresa.getText().isEmpty() || 
                    menu5.razonEmpresa.getText().isEmpty() || menu5.telefonoEmpresa.getText().isEmpty()){
                Mensajes.ERROR("Por favor complete todos los campos", "ERROR");
            }else if (ruc.length() != 11) {
                Mensajes.ERROR("El numero de RUC debe tener 11 dígitos", "ERROR");
                MostrarDatos();
            }else if (telefono.length() != 9) {
                Mensajes.ERROR("El numero de telefono debe tener 9 dígitos", "ERROR");
                MostrarDatos();
            }else{
            LeerEmpresa();
            crud=new CRUDempresa();
            crud.ActualizarDatosEmpresa(em);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        event=new Eventos();
        if(e.getSource()==menu5.nombreEmpresa){
            event.SoloTexto(e);
        }
        if(e.getSource()==menu5.rucEmpresa){
            event.SoloEnteros(e);
        }
        if(e.getSource()==menu5.telefonoEmpresa){
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
