
package MODELO;

public class Usuario extends Persona{

   private String codigo;
   private String contraseña;
   private String rol;
   
   public Usuario(){
   }

    public String getCodigo() {return codigo;}
    public void setCodigo(String codigo) {
        this.codigo = codigo;}

    public String getContraseña() {return contraseña;}
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;}

    public String getRol() {return rol;}
    public void setRol(String rol) {
        this.rol = rol;}
    
       
   public Object[] RegistrarUS(){
    Object[] fila={codigo,super.getNombre(),contraseña,rol};
            return fila;
}
   
}
