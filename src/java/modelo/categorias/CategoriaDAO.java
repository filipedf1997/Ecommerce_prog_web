package modelo.categorias;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.dao.DAO;

public class CategoriaDAO extends DAO {
    
    public void inserir(Categoria categoria) throws Exception{
        Connection connection = obterConexao();
        
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO categoria (descricao) VALUES (?)");
        preparedStatement.setString(1, categoria.getDescricao());
        int resultado = preparedStatement.executeUpdate();
        preparedStatement.close();
        
        fecharConexao(connection);
        
        if(resultado != 1){
            throw new Exception("Não foi possível inserir esta categoria");
        }
    }
    
    public void atualizar(Categoria categoria, int id) throws Exception{
        Connection connection = obterConexao();
        
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE categoria SET descricao=? WHERE id=?");
        preparedStatement.setString(1, categoria.getDescricao());
        preparedStatement.setInt(2, id);
        int resultado = preparedStatement.executeUpdate();
        preparedStatement.close();
        
        fecharConexao(connection);
        
        if(resultado != 1){
            throw new Exception("Não foi possível atualizar esta categoria");
        }
    }
    
    public void remover(int id) throws Exception{
        Connection connection = obterConexao();
        
        PreparedStatement preparedStatement2 = connection.prepareStatement("DELETE FROM produto_categoria WHERE cat_id_fk=?");
        preparedStatement2.setInt(1, id);
        preparedStatement2.executeUpdate();
        preparedStatement2.close();
        
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM categoria WHERE id=?");
        preparedStatement.setInt(1, id);
        int resultado = preparedStatement.executeUpdate();
        preparedStatement.close();
        
        fecharConexao(connection);
        
        if(resultado != 1){
            throw new Exception("Não foi possível deletar esta categoria");
        }
    }
    
    public Categoria obter(int id) throws Exception{
        Categoria categoria = null;
        Connection connection = obterConexao();
        
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, descricao FROM categoria WHERE id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            categoria = new Categoria();
            categoria.setId(resultSet.getInt("id"));
            categoria.setDescricao(resultSet.getString("descricao"));
        }
        resultSet.close();
        preparedStatement.close();
        
        fecharConexao(connection);
        
        if(categoria == null){
            throw new Exception("Não foi possível obter esta categoria");
        }
        return categoria;
    }
    
    public List<Categoria> obterTodos() throws Exception{
        List<Categoria> categorias = new ArrayList<Categoria>();
        Connection connection = obterConexao();
        
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, descricao FROM categoria");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            Categoria categoria = new Categoria();
            categoria.setId(resultSet.getInt("id"));
            categoria.setDescricao(resultSet.getString("descricao"));
            
            categorias.add(categoria);
        }
        resultSet.close();
        preparedStatement.close();
        
        fecharConexao(connection);
        
        if(categorias.isEmpty()){
            throw new Exception("Não foi possível obter as categorias");
        }
        return categorias;
    }
}