package DAO;
import MODELO.Producto;
import FORMATOS.Mensajes;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CRUDproducto extends ConexionBD{
    public CRUDproducto(){}
    
    public void MostrarTablaProducto(JTable tabla){
        String titulo[]={"ID","DESCRIPCION","PRECIO","STOCK"};
        DefaultTableModel modelo= new DefaultTableModel(null,titulo);
        tabla.setModel(modelo);
        try{
            rs=st.executeQuery("select codigopr,descripcion,preciou,stock from productos where indicador='A'");
            while(rs.next()){
                Producto pr =new Producto();
                pr.setCodigo(rs.getString(1));
                pr.setDescripcion(rs.getString(2));
                pr.setPrecio(rs.getDouble(3));
                pr.setStock(rs.getInt(4));
                modelo.addRow(pr.RegistrarPR());   
            }
            conexion.close();
        }catch(SQLException e){
            Mensajes.ERROR("No se pudo conectar a la base de datos... " +e, "ERROR");
            System.out.println(e);
        }
    }
        public String GenerarCodigo() {
            String codigo = "";
            int contador = 0;
            try {
                rs = st.executeQuery("select codigopr,descripcion,preciou,stock from productos");
                DecimalFormat df = new DecimalFormat("PR0000");
                while (rs.next()) {
                    contador++;
                }
                codigo = df.format(contador+1);                
            } catch (SQLException e) {
                Mensajes.ERROR("No se pudo conectar a la base de datos...", "ERROR");
            }
            return codigo;
        }
            public void IngresarProductos(Producto pr, String usuario, String accion) {
                try {
                    String codigo=GenerarCodigo();
                    ps = conexion.prepareStatement("insert into productos(codigopr,descripcion,preciou,stock,indicador) values(?,?,?,?,'A')");
                    ps.setString(1, codigo);
                    ps.setString(2, pr.getDescripcion());
                    ps.setDouble(3, pr.getPrecio());
                    ps.setInt(4, pr.getStock());
                    ps.executeUpdate();

                    ps = conexion.prepareStatement("insert into auditoriaProductos(accionpr,autorpr,idpr,descpr,preciopr,stockpr,fecha) values(?,?,?,?,?,?,now())");
                    ps.setString(1, accion);
                    ps.setString(2, usuario);
                    ps.setString(3, codigo);
                    ps.setString(4, pr.getDescripcion());
                    ps.setDouble(5, pr.getPrecio());
                    ps.setInt(6, pr.getStock());
                    ps.executeUpdate();

                    Mensajes.INFO("Producto ingresado correctamente", "INFO");
                } catch (SQLException e) {
                    Mensajes.ERROR("No se pudo conectar a la base de datos..." + e, "ERROR");
                    System.out.println(e.toString());
                }
            } 
        public void ActualizarProducto(Producto pr,String codigo,String accion,String usuario){
            try{
                if(pr.getDescripcion().isEmpty() || pr.getPrecio()==0.0 || pr.getStock()==0){
                    Mensajes.ERROR("Por favor complete los campos", "ERROR");
                }else{
                    ps= conexion.prepareStatement("update productos set descripcion=?,preciou=?,stock=? where codigopr='"+codigo+"'");
                    ps.setString(1, pr.getDescripcion());
                    ps.setDouble(2, pr.getPrecio());
                    ps.setInt(3, pr.getStock());
                    ps.executeUpdate();

                    ps = conexion.prepareStatement("insert into auditoriaProductos(accionpr,autorpr,idpr,descpr,preciopr,stockpr,fecha) values(?,?,?,?,?,?,now())");
                    ps.setString(1, accion);
                    ps.setString(2, usuario);
                    ps.setString(3, codigo);
                    ps.setString(4, pr.getDescripcion());
                    ps.setDouble(5, pr.getPrecio());
                    ps.setInt(6, pr.getStock());
                    ps.executeUpdate();
                    Mensajes.INFO("Producto actualizado correctamente", "INFO");
                }
            }catch(SQLException e){
                Mensajes.ERROR("No se pudo conectar a la base de datos... " + e, "ERROR");
            }
        }
        public void EliminarProducto(String codigo,String accion,String usuario,String desc,double precio,int stock){
            try{
                ps=conexion.prepareStatement("update productos set indicador='N' where codigopr='"+codigo+"'");
                ps.executeUpdate();
                
                ps = conexion.prepareStatement("insert into auditoriaProductos(accionpr,autorpr,idpr,descpr,preciopr,stockpr,fecha) values(?,?,?,?,?,?,now())");
                ps.setString(1, accion);
                ps.setString(2, usuario);
                ps.setString(3, codigo);
                ps.setString(4, desc);
                ps.setDouble(5, precio);
                ps.setInt(6, stock);
                ps.executeUpdate();
                Mensajes.INFO("Producto eliminado correctamente", "INFO");
            }catch(SQLException e){
                Mensajes.ERROR("No se pudo ejecutar la accion", "ERROR");
            }
        }
        public void MostrarBusquedaProducto(JTable tabla,String nombre){
        String titulo[]={"ID","DESCRIPCION","PRECIO","STOCK"};
        DefaultTableModel modelo= new DefaultTableModel(null,titulo);
        tabla.setModel(modelo);
        boolean resultadosEncontrados= false;
        try{
            rs=st.executeQuery("select codigopr,descripcion,preciou,stock from productos where indicador='A' and descripcion like '%"+nombre+"%'");
            while(rs.next()){
                Producto pr =new Producto();
                pr.setCodigo(rs.getString(1));
                pr.setDescripcion(rs.getString(2));
                pr.setPrecio(rs.getDouble(3));
                pr.setStock(rs.getInt(4));
                modelo.addRow(pr.RegistrarPR()); 
                resultadosEncontrados=true;
            }
            if(!resultadosEncontrados){
                Mensajes.ERROR("No se encontraron resultados", "ERROR");
                MostrarTablaProducto(tabla);
            }
            conexion.close();
        }catch(SQLException e){
            Mensajes.ERROR("No se pudo conectar a la base de datos... " +e, "ERROR");
            System.out.println(e);
        }
    }
    
}
