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
        String sql = "INSERT INTO TBUSUARIO (nome, email, senha, datanasc, ativo, imagem)" + "values (?, ?, ?, ?, ?, )";

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
            comando.setBytes(6, );

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

    public List<Usuario> consultar(int opcaoFiltro, String filtro){
    //Montar o comando a ser executado
    //os ? são variáveis que são preenchidas mais adiante
    String sql = "SELECT * from TBUSUARIO ";
    
    if(opcaoFiltro == 3){
      sql = sql + " WHERE ativo = 1 ";
    }else if(!filtro.isEmpty()){
      switch(opcaoFiltro){
        case 0: //código igual
          sql = sql + " WHERE pkUsuario = " + filtro ;
          break;
        case 1: //nome contendo 
          sql = sql + " WHERE nome like '%" + filtro + "%' ";
          break;
        case 2: //email contendo
          sql = sql + " WHERE email like '%" + filtro + "%' ";
          break;
      }
                      
    }
//Cria uma instância do gerenciador de conexão
    //(conexão com o banco de dados),
    GerenciadorConexao gerenciador = new GerenciadorConexao();
    
    //Declara as variáveis como nulas antes do try 
    //para poder usar no finally
    PreparedStatement comando = null;
    ResultSet resultado = null;
    
    //crio a lista de usuários, vazia ainda
    List<Usuario> lista = new ArrayList<>();
    
    try{
      //prepara o sql, analisando o formato e as váriaveis
      comando = gerenciador.prepararComando(sql);
      
      //executa o comando e guarda o resultado da consulta 
      //o resultado é semelhante a uma grade
      resultado = comando.executeQuery();

      while(resultado.next()){
        Usuario usu = new Usuario();
        
        usu.setPkUsuario(resultado.getInt("pkUsuario"));
        usu.setNome(resultado.getString("nome"));
        usu.setEmail(resultado.getString("email"));
        usu.setSenha(resultado.getString("senha")); //É o HASH
        usu.setDataNascimento(resultado.getDate("dataNasc")); 
        usu.setAtivo(resultado.getBoolean("ativo")); 
        
        lista.add(usu);
      }
    } catch (SQLException e) {
      //caso ocorra um erro relacionado ao banco de dados
      //exibe popup com o erro
      JOptionPane.showMessageDialog(null, e.getMessage());      
    } finally {
      //depois de executar o try, dando erro ou não executa o finally
      gerenciador.fecharConexao(comando, resultado);
    }  
    return lista;
  }
  
  public boolean alterar(Usuario usu){
    //Montar o comando a ser executado
    //os ? são variáveis que são preenchidas mais adiante
    String sql = "UPDATE TBUSUARIO SET "
               + " nome = ?, " //1
               + " email = ?, " //2
               + " datanasc = ?, " //3
               + " ativo = ? "; //4
    if (usu.getSenha() != null){           
      sql += " ,senha = ? " ; //5
    }
               
    sql += " WHERE pkUsuario = ? "; //5 ou 6
    
    //Cria uma instância do gerenciador de conexão
    //(conexão com o banco de dados),
    GerenciadorConexao gerenciador = new GerenciadorConexao();
    
    //Declara as variáveis como nulas antes do try 
    //para poder usar no finally
    PreparedStatement comando = null;
    
    try{
      //prepara o sql, analisando o formato e as váriaveis
      comando = gerenciador.prepararComando(sql);

      //define o valor de cada variável(?) pela posição em que aparece no sql
      comando.setString(1, usu.getNome());
      comando.setString(2, usu.getEmail());
      comando.setDate(3, new java.sql.Date(usu.getDataNascimento().getTime()));
      comando.setBoolean(4, usu.isAtivo());
      
      if(usu.getSenha() != null){
        comando.setString(5, usu.getSenha());
        comando.setInt(6, usu.getPkUsuario());
      }else{
        comando.setInt(5, usu.getPkUsuario());
      }
      
      
      //executa o comando 
      comando.executeUpdate();
      return true;
    } catch (SQLException e) {
      //caso ocorra um erro relacionado ao banco de dados
      //exibe popup com o erro
      JOptionPane.showMessageDialog(null, e.getMessage());      
    } finally {
      //depois de executar o try, dando erro ou não executa o finally
      gerenciador.fecharConexao(comando);
    }  
    return false;
  }

    
}
