
package MODELO;
import java.text.DecimalFormat;

public class NuevaVenta {
    private String codigo;
    private String descripcion;
    private int cantidad;
    private double precio;
    private int stock;
    public static final double IGV=0.18;
    
    public NuevaVenta(){
//        contador++;
//        DecimalFormat df=new DecimalFormat("VNT0000");
//        id=df.format(contador);
    }

    public String getCodigo() {  return codigo;}
    public void setCodigo(String codigo) {
        this.codigo = codigo;}
    
    public String getDescripcion() {  return descripcion;}
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;}

    public int getCantidad() {  return cantidad;}
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;}

    public double getPrecio() {  return precio;}
    public void setPrecio(double precio) {
        this.precio = precio;}

    public int getStock() {  return stock;}
    public void setStock(int stock) {
        this.stock = stock;}
    
    public double SubTotal(){
        return precio*cantidad;
    }
    
    public Object[] RegistrarNV(){
        DecimalFormat df= new DecimalFormat("#0.00");
        Object[] fila = {codigo,descripcion,cantidad,df.format(precio),df.format(SubTotal())};
        return fila;
    }
    
    public Object[] RegistrarDetalle(){
        DecimalFormat df= new DecimalFormat("S/#0.00");
        Object[] fila = {codigo,descripcion,cantidad,df.format(precio)};
        return fila;
    }
}
