package modelo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import modelo.config.Config;

public class DAO {
    
    protected Connection obterConexao() throws Exception{
        try{
            Class.forName(Config.JDBC_DRIVER);
            Connection conexao = DriverManager.getConnection(Config.JDBC_URL, Config.JDBC_USUARIO, Config.JDBC_SENHA);
            return conexao;
        } catch(ClassNotFoundException ex){
            throw new Exception("Driver JDBC não encontrado");
        } catch(SQLException ex){
            throw new Exception("Não foi possível obter uma conexão com o SGBD");
        }
    }
    
    protected void fecharConexao(Connection connection) throws Exception{
        if(connection != null && !connection.isClosed()){
            connection.close();
        }
    }
}
