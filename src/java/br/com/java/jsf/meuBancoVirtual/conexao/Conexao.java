package br.com.java.jsf.meuBancoVirtual.conexao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexao {
    
    private static Connection conexao = null;
    
   private static final String url = "jdbc:mysql://localhost/mybank";
   private static final String usuario = "root";
   private static String senha = null;
         
 
    public static Connection conectar() {
 
         if(conexao == null){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conexao = DriverManager.getConnection(url, usuario, senha);
            } catch (ClassNotFoundException | SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
          }
        }
        
 
         return conexao;
    }
 
     public static void fecharConexao() throws SQLException {
        if(conexao != null){
         
                conexao.close();
                conexao = null;
            
        }
    }
}

