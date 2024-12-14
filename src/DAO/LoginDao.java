
package DAO;
import FORMATOS.Mensajes;
import java.sql.SQLException;

public class LoginDao extends ConexionBD{
    public LoginDao(){}
    public boolean usuarioValido = false; 
    private String nombreus;
    
    
public void ValidarUsuario(String user, String pass) {
    try {       
        rs = st.executeQuery("select codigous, contraseña, nombreus from usuarios where indicador='A'");       
        while (rs.next()) {
            String usuarioCorrecto = rs.getString(1);
            String contCorrecto = rs.getString(2);            
            if (user.equals(usuarioCorrecto) && pass.equals(contCorrecto)) {
                usuarioValido = true;
                nombreus=rs.getString(3);
                break; 
            }
        }       
        if (usuarioValido) {
            Mensajes.INFO("BIENVENIDO "+user+" !!","LOGIN EXITOSO");
        } else {
            Mensajes.ERROR("Nombre de usuario o contraseña inválido", "ERROR DE INICIO DE SESION");
        }       
        conexion.close();
    } catch (SQLException e) {
        System.out.println("ERROR. No se pudo validar los datos. " + e);
    }
}

    public String getNombreus() {
        return nombreus;
    }

    public void setNombreus(String nombreus) {
        this.nombreus = nombreus;
    }
}
