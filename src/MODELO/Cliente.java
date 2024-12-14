
package MODELO;

public class Cliente extends Persona {
    private String codigo;
    
    public Cliente(){}

    public String getCodigo() {return codigo;}
    public void setCodigo(String codigo) {
        this.codigo = codigo;}
    
    public Object[] RegistrarCL(){    
        Object[] fila = {codigo,super.getDni(),super.getNombre(),super.getApellidos()};
        return fila;
    }
    
    
    
}
