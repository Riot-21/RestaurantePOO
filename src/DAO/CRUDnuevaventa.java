package DAO;
import MODELO.NuevaVenta;
import FORMATOS.Mensajes;
import MODELO.Cliente;
import java.sql.SQLException;
import javax.swing.JTable;

public class CRUDnuevaventa extends ConexionBD{
    public CRUDnuevaventa(){}
    CRUDventa cr;          
    
    public NuevaVenta BuscarProducto(String codigo){
        NuevaVenta nv = null;
        try{
            ps=conexion.prepareStatement("select * from productos where codigopr=? and indicador='A'");
            ps.setString(1, codigo);
            rs=ps.executeQuery();
            if(rs.next()){
                nv=new NuevaVenta();
                nv.setDescripcion(rs.getString("descripcion"));
                nv.setPrecio(rs.getDouble("preciou"));
                nv.setStock(rs.getInt("stock"));
            }
        }catch(SQLException e){
            Mensajes.ERROR("No se pudo conectar a la base de datos..."+e, "ERROR");
        }
        return nv;
    }
    
    public int BuscarStock(String codigo){
        int stock = 0;
        try{
            ps=conexion.prepareStatement("select * from productos where codigopr=? and indicador='A'");
            ps.setString(1, codigo);
            rs=ps.executeQuery();
            if(rs.next()){
                stock=rs.getInt("stock");
            }
        }catch(SQLException e){
            Mensajes.ERROR("No se pudo conectar a la base de datos..."+e, "ERROR");
        }
        return stock;
    }
    
    public Cliente BuscarCliente(String codigo){
        Cliente cl = null;
        try{
            ps=conexion.prepareStatement("select * from clientes where codigocl=? and indicador='A'");
            ps.setString(1, codigo);
            rs=ps.executeQuery();
            if(rs.next()){
                cl=new Cliente();
                cl.setNombre(rs.getString("nombrecl"));
                cl.setApellidos(rs.getString("apellidos"));
                cl.setDni(rs.getInt("dni"));
            }
        }catch(SQLException e){
            Mensajes.ERROR("No se pudo conectar a la base de datos..."+e, "ERROR");
        }
        return cl;
    }
    public void IngresarDetalleVenta(JTable tabla, NuevaVenta nv) {
        try {
            cr=new CRUDventa();
            ps = conexion.prepareStatement("insert into detalleventa(cod_ve,cod_pro,desc_pro,cantidad,precio) values(?,?,?,?,?);");
            ps.setString(1, cr.GenerarCodigo());
            ps.setString(2, nv.getCodigo());
            ps.setString(3, nv.getDescripcion());
            ps.setInt(4, nv.getCantidad());
            ps.setDouble(5, nv.getPrecio());
            ps.executeUpdate();
        } catch (SQLException e) {
            Mensajes.ERROR("No se pudo conectar a la base de datos..." + e, "ERROR");
        }
    }
    public void ActStock(int cant,String codigo){
        try{
            ps=conexion.prepareStatement("update productos set stock=? where codigopr=?;");
            ps.setInt(1, cant);
            ps.setString(2, codigo);
            ps.executeUpdate();
            
        }catch(SQLException e){
            Mensajes.ERROR("No se pudo conectar a la base de datos", "ERROR");
        }
    }
}
