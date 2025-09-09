package utils;

import com.sun.istack.internal.logging.Logger;
import java.awt.Graphics2D;
import java.net.URL;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

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

    public static String converterDateToString(Date data) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/mm/yyyy");
        String texto = "";
        
        try {
            texto = formato.format(data);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao formatar a data");
        }
        return texto;
    }
    
    public static File escolherImagem(){
        File arquivo = null;
        
        //Criar um escolhedor de arquivos
        JFileChooser exploradorArquivos = new JFileChooser();
        
        exploradorArquivos.setDialogTitle("escolha um arquivo");
        
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imagens", "jpg", "png");
        
        exploradorArquivos.setFileFilter(filtro);
        
        exploradorArquivos.setMultiSelectionEnabled(false);
        
        int resultado = exploradorArquivos.showOpenDialog(null);
        
        if(resultado == JFileChooser.APPROVE_OPTION) {
            
            arquivo = exploradorArquivos.getSelectedFile();
        
        }
        
        return arquivo;
    }

    public static Icon convertFileToIcon(File arquivo){
        ImageIcon icon = new ImageIcon(arquivo.getAbsolutePath());        
        return icon;
    }
    
    public static ImageIcon redimensionarImagem(Icon icone, int largura, int altura){
        Image imagemOriginal = ((ImageIcon) icone).getImage();
        
        Image novaImagem = imagemOriginal.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
        
        return new ImageIcon(novaImagem); 
    }
    
    public static byte[] converterIconToBytes(Icon icon){
        BufferedImage image = new BufferedImage( icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        icon.paintIcon(null, g2d, 0, 0);
        g2d.dispose();
        
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        try{
            ImageIO.write(image, "png", byteArray);
        }catch(IOException erro){
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, erro);
            
        }
        return byteArray.toByteArray();
    }
    
}
