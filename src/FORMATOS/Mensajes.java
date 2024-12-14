package FORMATOS;

import javax.swing.JOptionPane;

public class Mensajes {
    public static void ERROR(String mensaje, String titulo){
        JOptionPane.showMessageDialog(null, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
    }
    
    public static int CONFIRMACION(String titulo, String mensaje){
        return JOptionPane.showConfirmDialog(null, mensaje,titulo,JOptionPane.YES_NO_OPTION);
    }
    
    public static void INFO(String mensaje, String titulo){
        JOptionPane.showMessageDialog(null, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
    }
}
