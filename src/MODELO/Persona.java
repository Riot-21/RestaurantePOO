
package MODELO;

public abstract class Persona {
    private String nombre;
    private String apellidos;
    private int dni;
    
    Persona(){}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {
        this.nombre = nombre;}

    public String getApellidos() {return apellidos;}
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;}

    public int getDni() {return dni;}
    public void setDni(int dni) {
        this.dni = dni;}
    
    
}
