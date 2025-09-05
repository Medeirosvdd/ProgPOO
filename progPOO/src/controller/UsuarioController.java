package controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import model.Usuario;

public class UsuarioController {

    public boolean autenticar(String usuario, String senha) {
        String sql = "SELECT * from TBUSUARIO " + " WHERE email = ? and senha = ? " + " and ativo = true";

        GerenciadorConexao gerenciador = new GerenciadorConexao();

        PreparedStatement comando = null;
        ResultSet resultado = null;

        try {

            comando = gerenciador.prepararComando(sql);

            comando.setString(1, usuario);
            comando.setString(2, senha);

            resultado = comando.executeQuery();

            if (resultado.next()) {
                return true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        } finally {
            gerenciador.fecharConexao(comando, resultado);
        }
        return false;
    }

    public boolean inserir(Usuario usu) {
        String sql = "INSERT INTO TBUSUARIO (nome, email, senha, datanasc, ativo)" + "values (?, ?, ?, ?, ?)";

        GerenciadorConexao gerenciador = new GerenciadorConexao();

        PreparedStatement comando = null;
        ResultSet resultado = null;

        try {

            comando = gerenciador.prepararComando(sql);

            comando.setString(1, usu.getNome());
            comando.setString(2, usu.getEmail());
            comando.setString(3, usu.getSenha());
            comando.setDate(4, new java.sql.Date(usu.getDataNascimento().getTime()));
            comando.setBoolean(5, usu.isAtivo());

            comando.executeUpdate();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        } finally {
            gerenciador.fecharConexao(comando, resultado);
        }
        return false;
    }

    public List<Usuario> consultar() {
        String sql = "SELECT * FROM TBUSUARIO";

        GerenciadorConexao gerenciador = new GerenciadorConexao();

        PreparedStatement comando = null;
        ResultSet resultado = null;

        List<Usuario> lista = new ArrayList<>();

        try {

            comando = gerenciador.prepararComando(sql);

            resultado = comando.executeQuery();

            while (resultado.next()) {
                Usuario usu = new Usuario();

                usu.setPkUsuario(resultado.getInt("pkUsuario"));
                usu.setNome(resultado.getString("nome"));
                usu.setEmail(resultado.getString("email"));
                usu.setSenha(resultado.getString("senha"));
                usu.setDataNascimento(resultado.getDate("datanasc"));
                usu.setAtivo(resultado.getBoolean("ativo"));

                lista.add(usu);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        } finally {
            gerenciador.fecharConexao(comando, resultado);
        }
        return lista;
    }

}
