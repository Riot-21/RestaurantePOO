package DAO;
import MODELO.NuevaVenta;
import FORMATOS.Mensajes;
import MODELO.Venta;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class CRUDventa extends ConexionBD{
    public CRUDventa(){}
    LocalDateTime fechaActual = LocalDateTime.now();
    Timestamp fecha = Timestamp.valueOf(fechaActual);

    
    public void MostrarTablaVentas(JTable tabla){
        String titulo[]={"ID VENTA","CLIENTE","VENDEDOR","SUBTOTAL","IGV","TOTAL","FECHA"};
        DefaultTableModel modelo= new DefaultTableModel(null,titulo);
        tabla.setModel(modelo);
        try{
            rs=st.executeQuery("select codigove,cliente,vendedor,subtotal,igv,total,date(fecha) from ventas");
            while(rs.next()){
                Venta vnt =new Venta();
                vnt.setCodigo(rs.getString(1));
                vnt.setCliente(rs.getString(2));
                vnt.setVendedor(rs.getString(3));
                vnt.setSubtotal(rs.getDouble(4));
                vnt.setPigv(rs.getDouble(5));
                vnt.setTotal(rs.getDouble(6));
                Date fechaSQL = rs.getDate(7);
                LocalDate fechaLocalDate = fechaSQL.toLocalDate();
                vnt.setFecha(fechaLocalDate);
                modelo.addRow(vnt.RegistrarVenta());   
            }
            conexion.close();
        }catch(SQLException e){
            Mensajes.ERROR("No se pudo conectar a la base de datos... " +e, "ERROR");
            System.out.println(e);
        }
    }
        public void MostrarTablaVentasEmpleado(JTable tabla,String codigoemp){
        String titulo[]={"ID VENTA","CLIENTE","VENDEDOR","SUBTOTAL","IGV","TOTAL","FECHA"};
        DefaultTableModel modelo= new DefaultTableModel(null,titulo);
        tabla.setModel(modelo);
        try{
            rs=st.executeQuery("select codigove,cliente,vendedor,subtotal,igv,total,date(fecha) from ventas where vendedor='"+codigoemp+"'");
            while(rs.next()){
                Venta vnt =new Venta();
                vnt.setCodigo(rs.getString(1));
                vnt.setCliente(rs.getString(2));
                vnt.setVendedor(rs.getString(3));
                vnt.setSubtotal(rs.getDouble(4));
                vnt.setPigv(rs.getDouble(5));
                vnt.setTotal(rs.getDouble(6));
                Date fechaSQL = rs.getDate(7);
                LocalDate fechaLocalDate = fechaSQL.toLocalDate();
                vnt.setFecha(fechaLocalDate);
                modelo.addRow(vnt.RegistrarVenta());   
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
                rs = st.executeQuery("select codigove,cliente,vendedor,subtotal,igv,total,fecha from ventas ");
                DecimalFormat df = new DecimalFormat("VNT0000");
                while (rs.next()) {
                    contador++;
                }
                codigo = df.format(contador+1);                
            } catch (SQLException e) {
                Mensajes.ERROR("No se pudo conectar a la base de datos...", "ERROR");
            }
        return codigo;
    }
    public void IngresarVentas(Venta vnt) {
        try {
            ps = conexion.prepareStatement("insert into ventas(codigove,cliente,vendedor,subtotal,igv,total,fecha) values(?,?,?,?,?,?,now());");
            ps.setString(1, GenerarCodigo());
            ps.setString(2, vnt.getCliente());
            ps.setString(3, vnt.getVendedor());
            ps.setDouble(4, vnt.getSubtotal());
            ps.setDouble(5, vnt.getPigv());
            ps.setDouble(6, vnt.getTotal());
            ps.executeUpdate();
        } catch (SQLException e) {
            Mensajes.ERROR("No se pudo conectar a la base de datos..." + e, "ERROR");
        }
    }
    public void actualizarTablaVentas(JTable tabla,String fechaSeleccionada) {
        String titulo[] = {"ID VENTA", "CLIENTE", "VENDEDOR", "SUBTOTAL", "IGV", "TOTAL", "FECHA"};
        DefaultTableModel modelo = new DefaultTableModel(null, titulo);
        tabla.setModel(modelo);
        try {
            ps = conexion.prepareStatement("SELECT codigove, cliente, vendedor, subtotal, igv, total, date(fecha) FROM ventas WHERE date(fecha) = ?");
            ps.setString(1,fechaSeleccionada);
            rs = ps.executeQuery();

            boolean encontrados = false; // Variable para verificar si se encontraron datos

            while (rs.next()) {
                encontrados = true; // Se encontraron datos

                Venta vnt = new Venta();
                vnt.setCodigo(rs.getString(1));
                vnt.setCliente(rs.getString(2));
                vnt.setVendedor(rs.getString(3));
                vnt.setSubtotal(rs.getDouble(4));
                vnt.setPigv(rs.getDouble(5));
                vnt.setTotal(rs.getDouble(6));
                Date fechaSQL = rs.getDate(7);
                LocalDate fechaLocalDate = fechaSQL.toLocalDate();
                vnt.setFecha(fechaLocalDate);
                modelo.addRow(vnt.RegistrarVenta());
            }
            if (!encontrados) {
                Mensajes.ERROR("Ningún dato encontrado.","ERROR");            
                MostrarTablaVentas(tabla);
            }
            ps.close();
        } catch (SQLException e) {
            Mensajes.ERROR("No se pudo conectar a la base de datos... " + e, "ERROR");
            System.out.println(e);
        }
    }
        public void actualizarTablaVentasEmp(JTable tabla,String fechaSeleccionada,String codigoemp) {
        String titulo[] = {"ID VENTA", "CLIENTE", "VENDEDOR", "SUBTOTAL", "IGV", "TOTAL", "FECHA"};
        DefaultTableModel modelo = new DefaultTableModel(null, titulo);
        tabla.setModel(modelo);
        try {
            ps = conexion.prepareStatement("SELECT codigove, cliente, vendedor, subtotal, igv, total, date(fecha) FROM ventas WHERE date(fecha) = ? and vendedor='"+codigoemp+"'");
            ps.setString(1,fechaSeleccionada);
            rs = ps.executeQuery();

            boolean encontrados = false; // Variable para verificar si se encontraron datos

            while (rs.next()) {
                encontrados = true; // Se encontraron datos

                Venta vnt = new Venta();
                vnt.setCodigo(rs.getString(1));
                vnt.setCliente(rs.getString(2));
                vnt.setVendedor(rs.getString(3));
                vnt.setSubtotal(rs.getDouble(4));
                vnt.setPigv(rs.getDouble(5));
                vnt.setTotal(rs.getDouble(6));
                Date fechaSQL = rs.getDate(7);
                LocalDate fechaLocalDate = fechaSQL.toLocalDate();
                vnt.setFecha(fechaLocalDate);
                modelo.addRow(vnt.RegistrarVenta());
            }
            if (!encontrados) {
                Mensajes.ERROR("Ningún dato encontrado.","ERROR");            
                MostrarTablaVentasEmpleado(tabla,codigoemp);
            }
            ps.close();
        } catch (SQLException e) {
            Mensajes.ERROR("No se pudo conectar a la base de datos... " + e, "ERROR");
            System.out.println(e);
        }
    }
        public void generarGraficoPastel(String fechaSeleccionada) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        try {
            ps = conexion.prepareStatement("SELECT vendedor, SUM(total) FROM ventas WHERE date(fecha) = ? GROUP BY vendedor");
            ps.setString(1, fechaSeleccionada);
            rs = ps.executeQuery();
            while (rs.next()) {
                String vendedor = rs.getString(1);
                double ganancia = rs.getDouble(2);
                dataset.setValue(vendedor, ganancia);
            }
            JFreeChart chart = ChartFactory.createPieChart("Ventas por usuario "+fechaSeleccionada, dataset, true, true, false);
            ChartFrame frame = new ChartFrame("Gráfico de Pastel", chart);
            frame.pack();
            frame.setSize(1000, 500);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            ps.close();
        } catch (SQLException e) {
            Mensajes.ERROR("No se pudo conectar a la base de datos... " + e, "ERROR");
            System.out.println(e);
        }
    }
        
}
