package DAO;
import MODELO.Usuario;
import FORMATOS.Mensajes;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CRUDusuario extends ConexionBD{   
    public CRUDusuario(){}
    
    
    public void MostrarTablaUsuario(JTable tabla){
        String titulo[]={"ID/USUARIO","NOMBRE","CONTRASEÑA","ROL"};
        DefaultTableModel modelo= new DefaultTableModel(null,titulo);
        tabla.setModel(modelo);
        try{
            rs=st.executeQuery("select codigous,nombreus,contraseña,rol from usuarios where indicador='A'");
            while(rs.next()){
                Usuario us =new Usuario();
                us.setCodigo(rs.getString(1));
                us.setNombre(rs.getString(2));
                us.setContraseña(rs.getString(3));
                us.setRol(rs.getString(4));
                modelo.addRow(us.RegistrarUS());   
            }
            conexion.close();
        }catch(SQLException e){
            Mensajes.ERROR("No se pudo conectar a la base de datos... " +e, "ERROR");
            System.out.println(e);
        }
    }
    public String GenerarCodigo(String rol) {
        String codigo = "";
        int contadorad = 0;
        int contadorem = 0;
        try {
            if(rol.equals("ADMINISTRADOR")){
                rs = st.executeQuery("select codigous,nombreus,contraseña,rol from usuarios where rol ='"+rol+"'");
            DecimalFormat df = new DecimalFormat("ADM0000");
            while (rs.next()) {
                contadorad++;
            }
            codigo = df.format(contadorad+1);
            }else if(rol.equals("EMPLEADO")){
                rs = st.executeQuery("select codigous,nombreus,contraseña,rol from usuarios where rol ='"+rol+"'");
                DecimalFormat df = new DecimalFormat("EMP0000");
            while (rs.next()) {
                contadorem++;
            }
            codigo = df.format(contadorem+1);
            }
        } catch (SQLException e) {
            Mensajes.ERROR("No se pudo conectar a la base de datos...", "ERROR");
        }
        return codigo;
    }
 
    public void IngresarUsuarios(Usuario us,String accion,String usuario) {
        try {
            String codigo=GenerarCodigo(us.getRol());
            ps = conexion.prepareStatement("insert into usuarios(codigous,nombreus,contraseña,rol,indicador) values(?,?,?,?,'A')");
            ps.setString(1, codigo);
            ps.setString(2, us.getNombre());
            ps.setString(3, us.getContraseña());
            ps.setString(4, us.getRol());
            ps.executeUpdate();
            
            ps = conexion.prepareStatement("insert into auditoriaUsuarios(accionus,autorus,idus,nomus,contus,rolus,fecha) values(?,?,?,?,?,?,now())");
            ps.setString(1, accion);
            ps.setString(2, usuario);
            ps.setString(3, codigo);
            ps.setString(4, us.getNombre());
            ps.setString(5, us.getContraseña());
            ps.setString(6, us.getRol());
            ps.executeUpdate();
            Mensajes.INFO("Usuario ingresado correctamente", "INFO");
        } catch (SQLException e) {
            Mensajes.ERROR("No se pudo conectar a la base de datos..." + e, "ERROR");
        }
    }
    public void ActualizarUsuario(Usuario us,String codigo,String accion,String usuario){
        try{
            if(us.getNombre().isEmpty() || us.getContraseña().isEmpty()){
                Mensajes.ERROR("Por favor complete los campos", "ERROR");
            }else{
                ps= conexion.prepareStatement("update usuarios set nombreus=?,contraseña=? where codigous='"+codigo+"'");
                ps.setString(1, us.getNombre());
                ps.setString(2, us.getContraseña());
                ps.executeUpdate();
                
                ps = conexion.prepareStatement("insert into auditoriaUsuarios(accionus,autorus,idus,nomus,contus,rolus,fecha) values(?,?,?,?,?,?,now())");
                ps.setString(1, accion);
                ps.setString(2, usuario);
                ps.setString(3, codigo);
                ps.setString(4, us.getNombre());
                ps.setString(5, us.getContraseña());
                ps.setString(6, us.getRol());
                ps.executeUpdate();
                Mensajes.INFO("Usuario actualizado correctamente", "INFO");
            }
        }catch(SQLException e){
            Mensajes.ERROR("No se pudo conectar a la base de datos..." + e, "ERROR");
        }
    }
    public void EliminarUsuario(String codigo,String accion,String usuario,String nombre,String cont,String rol){
        try{
            ps=conexion.prepareStatement("update usuarios set indicador='N' where codigous='"+codigo+"'");
            ps.executeUpdate();
            
            ps = conexion.prepareStatement("insert into auditoriaUsuarios(accionus,autorus,idus,nomus,contus,rolus,fecha) values(?,?,?,?,?,?,now())");
            ps.setString(1, accion);
            ps.setString(2, usuario);
            ps.setString(3, codigo);
            ps.setString(4, nombre);
            ps.setString(5, cont);
            ps.setString(6, rol);
            ps.executeUpdate();
            Mensajes.INFO("Usuario eliminado correctamente", "INFO");
        }catch(SQLException e){
            Mensajes.ERROR("No se pudo ejecutar la accion", "ERROR");
        }
    }
    public void MostrarBusquedaUsuario(JTable tabla,String nombre){
        String titulo[]={"ID/USUARIO","NOMBRE","CONTRASEÑA","ROL"};
        DefaultTableModel modelo= new DefaultTableModel(null,titulo);
        tabla.setModel(modelo);
        boolean resultadosEncontrados= false;
        try{
            rs=st.executeQuery("select codigous,nombreus,contraseña,rol from usuarios where indicador='A' and nombreus like '%"+nombre+"%'");
            while(rs.next()){
                Usuario us =new Usuario();
                us.setCodigo(rs.getString(1));
                us.setNombre(rs.getString(2));
                us.setContraseña(rs.getString(3));
                us.setRol(rs.getString(4));
                modelo.addRow(us.RegistrarUS());  
                resultadosEncontrados=true;
            }
            if(!resultadosEncontrados){
                Mensajes.ERROR("No se encontraron resultados", "ERROR");
                MostrarTablaUsuario(tabla);
            }
            conexion.close();
        }catch(SQLException e){
            Mensajes.ERROR("No se pudo conectar a la base de datos... " +e, "ERROR");
            System.out.println(e);
        }
    }
}
