
package MODELO;
import java.time.LocalDate;
import java.text.DecimalFormat;

public class Venta {
    private String codigo;
    private String vendedor;
    private String cliente;
    private double subtotal;
    private double pigv;
    private double total;
    private LocalDate fecha;
    
    public Venta(){}

    public String getCodigo() {return codigo;}
    public void setCodigo(String codigo) {
        this.codigo = codigo;}

    public String getVendedor() {return vendedor;}
    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;}

    public String getCliente() {return cliente;}
    public void setCliente(String cliente) {
        this.cliente = cliente;}

    public double getTotal() {return total;}
    public void setTotal(double total) {
        this.total = total;}

    public double getSubtotal() {return subtotal;}
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;}

    public double getPigv() {return pigv;}
    public void setPigv(double pigv) {
        this.pigv = pigv;}

    public LocalDate getFecha() {return fecha;}
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;}
    
    public Object[] RegistrarVenta(){
        DecimalFormat df= new DecimalFormat("S/#0.00");
        Object [] fila={codigo,cliente,vendedor,df.format(subtotal),df.format(pigv),df.format(total),fecha};
        return fila;
    }
    
}
