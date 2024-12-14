package DAO;
import MODELO.Cliente;
import FORMATOS.Mensajes;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CRUDcliente extends ConexionBD{
    public CRUDcliente(){}
    
    public void MostrarTablaCliente(JTable tabla){
        String titulo[]={"ID","DNI","NOMBRE","APELLIDOS"};
        DefaultTableModel modelo= new DefaultTableModel(null,titulo);
        tabla.setModel(modelo);
        try{
            rs=st.executeQuery("select codigocl,dni,nombrecl,apellidos from clientes where indicador='A'");
            while(rs.next()){
                Cliente cl =new Cliente();
                cl.setCodigo(rs.getString(1));
                cl.setDni(rs.getInt(2));
                cl.setNombre(rs.getString(3));
                cl.setApellidos(rs.getString(4));
                modelo.addRow(cl.RegistrarCL());   
            }
            conexion.close();
        }catch(SQLException e){
            Mensajes.ERROR("No se pudo conectar a la base de datos... " +e, "ERROR");
        }
    }
        public String GenerarCodigo() {
            String codigo = "";
            int contador = 0;
            try {
                rs = st.executeQuery("select codigocl,dni,nombrecl,apellidos from clientes");
                DecimalFormat df = new DecimalFormat("CL0000");
                while (rs.next()) {
                    contador++;
                }
                codigo = df.format(contador+1);                
            } catch (SQLException e) {
                Mensajes.ERROR("No se pudo conectar a la base de datos...", "ERROR");
            }
        return codigo;
    }
        public void IngresarClientes(Cliente cl,String accion,String usuario) {
            try {
                String codigo=GenerarCodigo();
                ps = conexion.prepareStatement("insert into clientes(codigocl,dni,nombrecl,apellidos,indicador) values(?,?,?,?,'A')");
                ps.setString(1, codigo);
                ps.setInt(2, cl.getDni());
                ps.setString(3, cl.getNombre());
                ps.setString(4, cl.getApellidos());
                ps.executeUpdate();
                
                ps = conexion.prepareStatement("insert into auditoriaClientes(accioncl,autorcl,idcl,dnicl,nomcl,apcl,fecha) values(?,?,?,?,?,?,now())");
                ps.setString(1, accion);
                ps.setString(2, usuario);
                ps.setString(3, codigo);
                ps.setInt(4, cl.getDni());
                ps.setString(5, cl.getNombre());
                ps.setString(6, cl.getApellidos());
                ps.executeUpdate();
                Mensajes.INFO("Cliente ingresado correctamente", "INFO");
            } catch (SQLException e) {
                Mensajes.ERROR("No se pudo conectar a la base de datos..." + e, "ERROR");
        }
    }       
        public void ActualizarCliente(Cliente cl,String codigo,String accion,String usuario){
            try{
                if(cl.getDni()==0 || cl.getNombre().isEmpty() || cl.getApellidos().isEmpty()){
                    Mensajes.ERROR("Por favor complete los campos", "ERROR");
                }else{
                    ps= conexion.prepareStatement("update clientes set dni=?,nombrecl=?,apellidos=? where codigocl='"+codigo+"'");
                    ps.setInt(1, cl.getDni());
                    ps.setString(2, cl.getNombre());
                    ps.setString(3, cl.getApellidos());
                    ps.executeUpdate();
                    
                    ps = conexion.prepareStatement("insert into auditoriaClientes(accioncl,autorcl,idcl,dnicl,nomcl,apcl,fecha) values(?,?,?,?,?,?,now())");
                    ps.setString(1, accion);
                    ps.setString(2, usuario);
                    ps.setString(3, codigo);
                    ps.setInt(4, cl.getDni());
                    ps.setString(5, cl.getNombre());
                    ps.setString(6, cl.getApellidos());
                    ps.executeUpdate();
                    Mensajes.INFO("Cliente actualizado correctamente", "INFO");
                }
            }catch(SQLException e){
                Mensajes.ERROR("No se pudo conectar a la base de datos... " + e, "ERROR");
            }
        }
        public void EliminarCliente(String codigo,String accion,String usuario,int dni,String nombre,String apellido){
            try{
                ps=conexion.prepareStatement("update clientes set indicador='N' where codigocl='"+codigo+"'");
                ps.executeUpdate();
                
                ps = conexion.prepareStatement("insert into auditoriaClientes(accioncl,autorcl,idcl,dnicl,nomcl,apcl,fecha) values(?,?,?,?,?,?,now())");
                ps.setString(1, accion);
                ps.setString(2, usuario);
                ps.setString(3, codigo);
                ps.setInt(4, dni);
                ps.setString(5, nombre);
                ps.setString(6, apellido);
                ps.executeUpdate();
                Mensajes.INFO("Cliente eliminado correctamente", "INFO");
            }catch(SQLException e){
                Mensajes.ERROR("No se pudo ejecutar la accion", "ERROR");
            }
        }
        public void MostrarBusquedaCliente(JTable tabla,String nombre){
        String titulo[]={"ID","DNI","NOMBRE","APELLIDOS"};
        DefaultTableModel modelo= new DefaultTableModel(null,titulo);
        tabla.setModel(modelo);
        boolean resultadosEncontrados= false;
        try{
            rs=st.executeQuery("select codigocl,dni,nombrecl,apellidos from clientes where indicador='A' and nombrecl like '%"+nombre+"%'");
            while(rs.next()){
                Cliente cl =new Cliente();
                cl.setCodigo(rs.getString(1));
                cl.setDni(rs.getInt(2));
                cl.setNombre(rs.getString(3));
                cl.setApellidos(rs.getString(4));
                modelo.addRow(cl.RegistrarCL()); 
                resultadosEncontrados=true;
            }
            if(!resultadosEncontrados){
                Mensajes.ERROR("No se encontraron resultados", "ERROR");
                MostrarTablaCliente(tabla);
            }
            conexion.close();
        }catch(SQLException e){
            Mensajes.ERROR("No se pudo conectar a la base de datos... " +e, "ERROR");
        }
    }
    
}
