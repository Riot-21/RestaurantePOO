package DAO;
import java.sql.*;
import FORMATOS.Mensajes;

public class ConexionBD implements Parametros{
    Connection conexion;
    Statement st;
    ResultSet rs;
    PreparedStatement ps;
    
    public ConexionBD(){
        try{
            Class.forName(DRIVER);
            conexion=DriverManager.getConnection(RUTA,USUARIO,CLAVE);
            st=conexion.createStatement();
        }catch(Exception ex){
            Mensajes.INFO("No se puedo conectar a la base de datos...","ERROR");
        }
    }
    public Connection getConexion() {
    return conexion;
}
}
