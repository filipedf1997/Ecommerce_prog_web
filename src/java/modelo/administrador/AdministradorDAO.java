package modelo.administrador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.dao.DAO;

public class AdministradorDAO extends DAO{
    
    public void inserir(Administrador administrador) throws Exception{
        Connection connection = obterConexao();
        
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO administrador (nome,email,login,senha) VALUES (?,?,?,?)");
        preparedStatement.setString(1, administrador.getNome());
        preparedStatement.setString(2, administrador.getEmail());
        preparedStatement.setString(3, administrador.getLogin());
        preparedStatement.setString(4, administrador.getSenha());
        int resultado = preparedStatement.executeUpdate();
        preparedStatement.close();
        
        fecharConexao(connection);
        
        if(resultado != 1){
            throw new Exception("Não foi possível inserir este administrador");
        }
    }
    
    public void atualizar(Administrador administrador, int id) throws Exception{
        Connection connection = obterConexao();
        
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE administrador SET nome=?,email=?,senha=? WHERE id=?");
        preparedStatement.setString(1, administrador.getNome());
        preparedStatement.setString(2, administrador.getEmail());      
        preparedStatement.setString(3, administrador.getSenha());
        preparedStatement.setInt(4, id);
        int resultado = preparedStatement.executeUpdate();
        preparedStatement.close();
        
        fecharConexao(connection);
        
        if(resultado != 1){
            throw new Exception("Não foi possível atualizar este administrador");
        }
    }
    
    public void remover(int id) throws Exception{
        Connection connection = obterConexao();
        
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM administrador WHERE id=?");
        preparedStatement.setInt(1, id);
        int resultado = preparedStatement.executeUpdate();
        preparedStatement.close();
        
        fecharConexao(connection);
        
        if(resultado != 1){
            throw new Exception("Não foi possível deletar este administrador");
        }
    }
    
    public Administrador obter(int id) throws Exception{
        Administrador administrador = null;
        Connection connection = obterConexao();
        
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, nome, endereco, email, login, senha FROM administrador WHERE id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            administrador = new Administrador();
            administrador.setId(resultSet.getInt("id"));
            administrador.setNome(resultSet.getString("nome"));
            administrador.setEmail(resultSet.getString("email"));
            administrador.setLogin(resultSet.getString("login"));
            administrador.setSenha(resultSet.getString("senha"));
        }
        resultSet.close();
        preparedStatement.close();
        
        fecharConexao(connection);
        
        if(administrador == null){
            throw new Exception("Não foi possível obter este administrador");
        }
        return administrador;
    }
    
    public Administrador obter(String login) throws Exception{
        Administrador administrador = null;
        Connection connection = obterConexao();
        
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, nome, email, login, senha FROM administrador WHERE login=?");
        preparedStatement.setString(1, login);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            administrador = new Administrador();
            administrador.setId(resultSet.getInt("id"));
            administrador.setNome(resultSet.getString("nome"));
            administrador.setEmail(resultSet.getString("email"));
            administrador.setLogin(resultSet.getString("login"));
            administrador.setSenha(resultSet.getString("senha"));
        }
        resultSet.close();
        preparedStatement.close();
        
        fecharConexao(connection);
        
        if(administrador == null){
            throw new Exception("Não foi possível obter este administrador");
        }
        return administrador;
    }
    
    public List<Administrador> obterTodos() throws Exception{
        List<Administrador> administradores = new ArrayList<Administrador>();
        Connection connection = obterConexao();
        
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, nome, email, login, senha FROM administrador");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            Administrador administrador = new Administrador();
            administrador.setId(resultSet.getInt("id"));
            administrador.setNome(resultSet.getString("nome"));
            administrador.setEmail(resultSet.getString("email"));
            administrador.setLogin(resultSet.getString("login"));
            administrador.setSenha(resultSet.getString("senha"));
            
            administradores.add(administrador);
        }
        resultSet.close();
        preparedStatement.close();
        
        fecharConexao(connection);
        
        if(administradores.isEmpty()){
            throw new Exception("Não foi possível obter os administradores");
        }
        return administradores;
    }
}











