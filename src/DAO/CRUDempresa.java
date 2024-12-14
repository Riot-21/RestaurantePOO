
package DAO;

import FORMATOS.Mensajes;
import MODELO.Empresa;
import java.sql.SQLException;

public class CRUDempresa extends ConexionBD{
    public CRUDempresa(){}
    private String nombreem;
    private long ruc;
    private String direccion;
    private String razonsocial;
    private int telefono;
    
    public String getNombreem() {return nombreem;}
    public void setNombreem(String nombreem) {
        this.nombreem = nombreem;}

    public long getRuc() {return ruc;}
    public void setRuc(long ruc) {
        this.ruc = ruc;}

    public String getDireccion() {return direccion;}
    public void setDireccion(String direccion) {
        this.direccion = direccion;}

    public String getRazonsocial() {return razonsocial;}
    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;}

    public int getTelefono() {return telefono;}
    public void setTelefono(int telefono) {
        this.telefono = telefono;}
    
    
    public void ObtenerDatosEmpresa(){
        try{
            rs=st.executeQuery("select nombreem, ruc, direccion, razonSocial, telefono from empresa");
            while(rs.next()){
                nombreem=rs.getString(1);
                ruc=rs.getLong(2);
                direccion=rs.getString(3);
                razonsocial=rs.getString(4);
                telefono=rs.getInt(5);
            }
        }catch(SQLException e){
            Mensajes.ERROR("No se pudo conectar a la base de datos", "ERROR");
        }
    }
    public void ActualizarDatosEmpresa(Empresa em){
        try {
            ps=conexion.prepareStatement("update empresa set nombreem=?, ruc=?, direccion=?, razonSocial=?, telefono=?");
            ps.setString(1, em.getNombreem());
            ps.setLong(2, em.getRuc());
            ps.setString(3, em.getDireccion());
            ps.setString(4,em.getRazonsocial());
            ps.setInt(5, em.getTelefono());
            ps.executeUpdate();
            Mensajes.INFO("Se actualizo los datos correctamente", "DATOS ACTUALIZADOS");
        } catch (SQLException ex) {
            Mensajes.ERROR("No se pudo actualizar los datos de la empresa... " + ex, "ERROR");
        }
        
    }
    
}
