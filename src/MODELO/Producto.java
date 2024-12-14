
package MODELO;
import java.text.DecimalFormat;

public class Producto {
    private String codigo;
    private String descripcion;
    private double precio;
    private int stock;


public Producto(){
}

    public String getCodigo() {return codigo;}
    public void setCodigo(String codigo) {
        this.codigo = codigo;}

    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;}

    public double getPrecio() {return precio;}
    public void setPrecio(double precio) {
        this.precio = precio;}

    public int getStock() {return stock;}
    public void setStock(int stock) {
        this.stock = stock;}

    public Object[] RegistrarPR(){  
        DecimalFormat df= new DecimalFormat("S/#0.00");
        Object[] fila = {codigo,descripcion,df.format(precio),stock};
        return fila;
    }
}
