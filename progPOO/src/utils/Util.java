package utils;

import java.net.URL;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import java.security.NoSuchAlgorithmException;

public class Util {

    public static Image getIcone() {
        URL caminhoImage = Util.class.getResource("/images/aaa.png");

        ImageIcon icon = new ImageIcon(caminhoImage);

        return icon.getImage();
    }

    public static Date converterStringToDate(String texto) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/mm/yyyy");

        Date data = null;

        try {

            data = formato.parse(texto);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao convertrer a data");
        }
        return data;
    }

    public static String calcularHash(String senha) {
        String hashSHA1 = "";
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA1");

            sha1.update(senha.getBytes());

            byte[] digest = sha1.digest();

            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            hashSHA1 = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Algoritimo SHA! não encontrado");
        }
        return hashSHA1;
    }

    public static String converterDataToString(Date data) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/mm/yyyy");
        String texto = "";
        
        try {
            texto = formato.format(data);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao formatar a data");
        }
        return texto;
    }

}
