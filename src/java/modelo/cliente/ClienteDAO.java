package modelo.cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.dao.DAO;

public class ClienteDAO extends DAO{
    
    public void inserir(Cliente cliente) throws Exception{
        Connection connection = obterConexao();
        
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO cliente (nome,endereco,email,login,senha) VALUES (?,?,?,?,?)");
        preparedStatement.setString(1, cliente.getNome());
        preparedStatement.setString(2, cliente.getEndereco());
        preparedStatement.setString(3, cliente.getEmail());
        preparedStatement.setString(4, cliente.getLogin());
        preparedStatement.setString(5, cliente.getSenha());
        int resultado = preparedStatement.executeUpdate();
        preparedStatement.close();
        
        fecharConexao(connection);
        
        if(resultado != 1){
            throw new Exception("Não foi possível inserir este cliente");
        }
    }
    
    public void atualizar(Cliente cliente, int id) throws Exception{
        Connection connection = obterConexao();
        
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE cliente SET nome=?,endereco=?,email=?,senha=? WHERE id=?");
        preparedStatement.setString(1, cliente.getNome());
        preparedStatement.setString(2, cliente.getEndereco());
        preparedStatement.setString(3, cliente.getEmail());
        preparedStatement.setString(4, cliente.getSenha());
        preparedStatement.setInt(5, id);
        int resultado = preparedStatement.executeUpdate();
        preparedStatement.close();
        
        fecharConexao(connection);
        
        if(resultado != 1){
            throw new Exception("Não foi possível atualizar este cliente");
        }
    }
    
    public void remover(int id) throws Exception{
        Connection connection = obterConexao();
        
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM cliente WHERE id=?");
        preparedStatement.setInt(1, id);
        int resultado = preparedStatement.executeUpdate();
        preparedStatement.close();
        
        fecharConexao(connection);
        
        if(resultado != 1){
            throw new Exception("Não foi possível deletar este cliente");
        }
    }
    
    public Cliente obter(int id) throws Exception{
        Cliente cliente = null;
        Connection connection = obterConexao();
        
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, nome, endereco, email, login, senha FROM cliente WHERE id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            cliente = new Cliente();
            cliente.setId(resultSet.getInt("id"));
            cliente.setNome(resultSet.getString("nome"));
            cliente.setEndereco(resultSet.getString("endereco"));
            cliente.setEmail(resultSet.getString("email"));
            cliente.setLogin(resultSet.getString("login"));
            cliente.setSenha(resultSet.getString("senha"));
        }
        resultSet.close();
        preparedStatement.close();
        
        fecharConexao(connection);
        
        if(cliente == null){
            throw new Exception("Não foi possível obter este cliente");
        }
        return cliente;
    }
    
    public Cliente obter(String login) throws Exception{
        Cliente cliente = null;
        Connection connection = obterConexao();
        
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, nome, endereco, email, login, senha FROM cliente WHERE login=?");
        preparedStatement.setString(1, login);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            cliente = new Cliente();
            cliente.setId(resultSet.getInt("id"));
            cliente.setNome(resultSet.getString("nome"));
            cliente.setEndereco(resultSet.getString("endereco"));
            cliente.setEmail(resultSet.getString("email"));
            cliente.setLogin(resultSet.getString("login"));
            cliente.setSenha(resultSet.getString("senha"));
        }
        resultSet.close();
        preparedStatement.close();
        
        fecharConexao(connection);
        
        if(cliente == null){
            throw new Exception("Não foi possível obter este cliente");
        }
        return cliente;
    }
    
    public List<Cliente> obterTodos() throws Exception{
        List<Cliente> clientes = new ArrayList<Cliente>();
        Connection connection = obterConexao();
        
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, nome, endereco, email, login, senha FROM cliente");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            Cliente cliente = new Cliente();
            cliente.setId(resultSet.getInt("id"));
            cliente.setNome(resultSet.getString("nome"));
            cliente.setEndereco(resultSet.getString("endereco"));
            cliente.setEmail(resultSet.getString("email"));
            cliente.setLogin(resultSet.getString("login"));
            cliente.setSenha(resultSet.getString("senha"));
            
            clientes.add(cliente);
        }
        resultSet.close();
        preparedStatement.close();
        
        fecharConexao(connection);
        
        if(clientes.isEmpty()){
            throw new Exception("Não foi possível obter os clientes");
        }
        return clientes;
    }
}











