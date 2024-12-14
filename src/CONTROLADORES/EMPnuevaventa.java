
package CONTROLADORES;
import DAO.CRUDnuevaventa;
import DAO.CRUDproducto;
import DAO.CRUDventa;
import DAO.Pdf;
import FORMATOS.Mensajes;
import MODELO.Cliente;
import MODELO.Eventos;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import VISTA.MENU_EMP;
import MODELO.NuevaVenta;
import MODELO.Venta;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class EMPnuevaventa implements ActionListener,KeyListener{
    MENU_EMP menu1;
    CRUDnuevaventa crud;
    CRUDventa crv;
    CRUDproducto crudpr;
    NuevaVenta nv;
    Venta vnt;
    Cliente cl;
    Pdf pdf;
    Eventos event;
    DefaultTableModel modelotabla;
    String[] TituloTabla={"CÓDIGO","DESCRIPCIÓN","CANTIDAD","PRECIO","TOTAL"};
    double subtotal=0.00;
    double precioigv=0.00;

    
    public EMPnuevaventa(MENU_EMP m1){
        this.menu1=m1;
        this.menu1.NVagregar.addActionListener(this);
        this.menu1.NVcancelar.addActionListener(this);
        this.menu1.NVguardar.addActionListener(this);
        this.menu1.NVeliminar.addActionListener(this);
        this.menu1.NVbuscarPR.addActionListener(this);
        this.menu1.NVbuscarCL.addActionListener(this);
        this.menu1.NVcantidad.addKeyListener(this);
        menu1.NVdescripcion.setFocusable(false);
        menu1.NVprecio.setFocusable(false);
        menu1.NVstock.setFocusable(false);
        menu1.NVnombre.setFocusable(false);
        modelotabla=new DefaultTableModel(null,TituloTabla);
        this.menu1.jtblNV.setModel(modelotabla);  
        
        menu1.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            menu1.jtblNV.clearSelection();}});
    }
    
    private void calcularSubTotal() {
        subtotal= 0.00;
        int numFila=menu1.jtblNV.getRowCount();
        for (int i=0;i<numFila;i++){
            String valor = String.valueOf(menu1.jtblNV.getModel().getValueAt(i, 4));
            double cal = Double.parseDouble(valor.replace("S/", "").trim());
            subtotal=subtotal+cal;
        }
        menu1.NVsubtotal.setText(String.format("%.2f",subtotal));
    }
    
    public void calcularIGV(){
        precioigv = subtotal*NuevaVenta.IGV;
        menu1.NVigv.setText(String.format("%.2f",precioigv));
    }
    public void calcularTotal(){
        double tp=subtotal+precioigv;
        menu1.NVtotal.setText(String.format("%.2f",tp));
    }
       
    void LimpiarEntradas(){
        this.menu1.NVcodigo.setText("");
        this.menu1.NVcantidad.setText("");
        this.menu1.NVprecio.setText("");
        this.menu1.NVstock.setText("");
        this.menu1.NVdescripcion.setText("");
        this.menu1.NVcodigo.requestFocus();
    }
    void ActualizarTabla(){
        crv=new CRUDventa();
        crv.MostrarTablaVentasEmpleado(menu1.jtblVENTAS,menu1.CodUsuario.getText());
        
    }
    void LeerNuevaVenta(){
        nv=new NuevaVenta();
        nv.setCodigo(menu1.NVcodigo.getText());
        nv.setDescripcion(menu1.NVdescripcion.getText());
        nv.setCantidad(Integer.parseInt(menu1.NVcantidad.getText()));
        nv.setPrecio(Double.parseDouble(menu1.NVprecio.getText()));
        nv.SubTotal();
    }
    void LeerVenta(){
        vnt=new Venta();
        vnt.setCliente(menu1.NVdni.getText());
        vnt.setVendedor(menu1.CodUsuario.getText());
        vnt.setSubtotal(Double.parseDouble(menu1.NVsubtotal.getText()));
        vnt.setPigv(Double.parseDouble(menu1.NVigv.getText()));
        vnt.setTotal(Double.parseDouble(menu1.NVtotal.getText()));
    }
    void LeerCliente(){
        cl=new Cliente();
        String codigo=menu1.NVdni.getText();
        cl=crud.BuscarCliente(codigo);
        cl.setDni(cl.getDni());
        cl.setNombre(cl.getNombre());
        cl.setApellidos(cl.getApellidos());
    }
    void LimpiarTabla(){
        modelotabla.setRowCount(0);
        LimpiarEntradas();
        this.menu1.NVnombre.setText("");
        this.menu1.NVdni.setText("");
        this.menu1.NVsubtotal.setText("");
        this.menu1.NVigv.setText("");
        this.menu1.NVtotal.setText("");
    }
    void ActualizarStock(){
        crud=new CRUDnuevaventa();
        crudpr=new CRUDproducto();
        nv=new NuevaVenta();
        for(int i=0;i<menu1.jtblNV.getRowCount();i++){
            String codigo=menu1.jtblNV.getValueAt(i, 0).toString();
            int cant=Integer.parseInt(menu1.jtblNV.getValueAt(i, 2).toString());
            nv=crud.BuscarProducto(codigo);
            int stock=nv.getStock();
            int stockActual=stock-cant;
            crud.ActStock(stockActual, codigo); 
        }
        crudpr.MostrarTablaProducto(menu1.jtblPR);
    }
@Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == menu1.NVagregar){
            if (menu1.NVcodigo.getText().isEmpty() || menu1.NVdni.getText().isEmpty() ||
                menu1.NVdescripcion.getText().isEmpty() || menu1.NVcantidad.getText().isEmpty() ||
                menu1.NVprecio.getText().isEmpty() || menu1.NVstock.getText().isEmpty() ||
                menu1.NVnombre.getText().isEmpty()) {
                Mensajes.ERROR("Por favor, complete todos los campos", "ERROR");
                return;
            }else {
                int stock=Integer.parseInt(menu1.NVstock.getText());
                int cant=Integer.parseInt(menu1.NVcantidad.getText());
                if(stock>=cant && cant>0){
                    LeerNuevaVenta();
                    for(int i=0;i<menu1.jtblNV.getRowCount();i++){
                        if(menu1.jtblNV.getValueAt(i, 0).equals(menu1.NVcodigo.getText())){
                            Mensajes.ERROR("El producto ya está registrado", "ERROR");
                            LimpiarEntradas();
                            return;
                        }
                    }
                    modelotabla.addRow(nv.RegistrarNV());
                    calcularSubTotal();
                    calcularIGV();
                    calcularTotal();
                    LimpiarEntradas();
                    menu1.jtblNV.clearSelection();
                }else{ 
                    Mensajes.ERROR("Stock no disponible", "ERROR");
                    menu1.NVcantidad.requestFocus();
                }
            }
        }
        if(e.getSource()== menu1.NVguardar){
            if (modelotabla.getRowCount() == 0) {
                Mensajes.ERROR("La tabla está vacía. Agregue al menos un producto", "ERROR");
                return;
            } else{
                calcularSubTotal();
                calcularIGV();
                calcularTotal();     
                int opcion = Mensajes.CONFIRMACION("Confirmación", "¿Confirmar el pedido?");
                if (opcion == JOptionPane.YES_OPTION) {
                    vnt=new Venta(); 
                    cl=new Cliente();
                    nv=new NuevaVenta();
                    crud=new CRUDnuevaventa();
                    for(int i=0;i<menu1.jtblNV.getRowCount();i++){
                        String codigo=menu1.jtblNV.getValueAt(i, 0).toString();
                        String desc=menu1.jtblNV.getValueAt(i, 1).toString();
                        int cant=Integer.parseInt(menu1.jtblNV.getValueAt(i, 2).toString());
                        double precio=Double.parseDouble(menu1.jtblNV.getValueAt(i, 4).toString());
                        nv.setCodigo(codigo);
                        nv.setDescripcion(desc);
                        nv.setCantidad(cant);
                        nv.setPrecio(precio);
                        crud.IngresarDetalleVenta(menu1.jtblNV,nv);
                    }
                    LeerVenta();
                    LeerCliente();
                    pdf= new Pdf();
                    pdf.GenerarPDF(menu1.jtblNV,cl,vnt);
                    crv=new CRUDventa();
                    nv=new NuevaVenta();
                    crv.IngresarVentas(vnt);
                    ActualizarTabla();
                    ActualizarStock();
                    Mensajes.INFO("Pedido confirmado!", "INFO");
                    LimpiarTabla();
                } 
            }            
        }
        if (e.getSource() == menu1.NVcancelar) {
            int opcion = Mensajes.CONFIRMACION("Confirmación", "¿Estás seguro de cancelar el pedido?");
            if (opcion == JOptionPane.YES_OPTION) {
                LimpiarTabla();
                Mensajes.INFO("Pedido cancelado", "INFO");
            }else{
                menu1.jtblNV.clearSelection();}
        }
        if (e.getSource() == menu1.NVeliminar) {
            LimpiarEntradas();
            int selectedRow = menu1.jtblNV.getSelectedRow();
            if (selectedRow >= 0) {
                int opcion = Mensajes.CONFIRMACION("Confirmación", "¿Estás seguro de eliminar la fila?");
                if (opcion == JOptionPane.YES_OPTION) {
                    modelotabla.removeRow(selectedRow);
                    calcularSubTotal();
                    calcularIGV();
                    calcularTotal();
                    Mensajes.INFO("Fila eliminada", "INFO");
                    } else{
                    menu1.jtblNV.clearSelection();
                }
            }else {
            Mensajes.ERROR("Por favor, seleccione una fila para eliminar", "ERROR");
            }
            
        }
        if(e.getSource()==menu1.NVbuscarPR){
            crud=new CRUDnuevaventa();
            nv= new NuevaVenta();
            String codigo=menu1.NVcodigo.getText();
            nv=crud.BuscarProducto(codigo);
            if(crud.BuscarProducto(codigo)!=null){
            menu1.NVdescripcion.setText(nv.getDescripcion());
            menu1.NVprecio.setText(Double.toString(nv.getPrecio()));
            menu1.NVstock.setText(Integer.toString(nv.getStock()));
            menu1.NVcantidad.requestFocus();
            }else{
                Mensajes.ERROR("No se encontro ningun producto", "ERROR");
                menu1.NVcodigo.setText("");
                menu1.NVcodigo.requestFocus();
            }
        }
        if(e.getSource()==menu1.NVbuscarCL){
            crud=new CRUDnuevaventa();
            cl=new Cliente();
            String codigo=menu1.NVdni.getText();
            cl=crud.BuscarCliente(codigo);
            if(crud.BuscarCliente(codigo)!=null){
            menu1.NVnombre.setText(cl.getNombre()+" "+cl.getApellidos());
            }else{
                Mensajes.ERROR("No se encontro ningun cliente", "ERROR");
                menu1.NVdni.setText("");
                menu1.NVdni.requestFocus();
            }
        }
}

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getSource()==menu1.NVcantidad){
            event=new Eventos();
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
