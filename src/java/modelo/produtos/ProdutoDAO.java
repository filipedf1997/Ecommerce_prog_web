package modelo.produtos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.categorias.Categoria;
import java.sql.Array;
import java.sql.SQLException;
import java.sql.Statement;
import modelo.dao.DAO;

public class ProdutoDAO extends DAO {

    public void inserir(Produto produto) throws Exception {
        Connection connection = obterConexao();

        int id = -1;
        Statement statementNovoId = connection.createStatement();
        ResultSet resultSetNovoId = statementNovoId.executeQuery("SELECT nextval('produto_id_seq') AS id");
        while (resultSetNovoId.next()) {
            id = resultSetNovoId.getInt("id");
        }
        resultSetNovoId.close();
        statementNovoId.close();

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO produto (id,descricao,quantidade,preco,foto) VALUES (?,?,?,?,?)");
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, produto.getDescricao());
        preparedStatement.setInt(3, produto.getQuantidade());
        preparedStatement.setDouble(4, produto.getPreco());
        preparedStatement.setString(5, produto.getFoto());

        int resultado = preparedStatement.executeUpdate();
        preparedStatement.close();

        for (int i = 0; produto.getCategorias() != null && i < produto.getCategorias().size(); i++) {
            Categoria categoria = produto.getCategorias().get(i);
            PreparedStatement preparedStatementInserirCategoria = connection.prepareStatement("INSERT INTO produto_categoria (prod_id_fk, cat_id_fk) VALUES (?, ?)");
            preparedStatementInserirCategoria.setInt(1, id);
            preparedStatementInserirCategoria.setInt(2, categoria.getId());
            if (preparedStatementInserirCategoria.executeUpdate() <= 0) {
                throw new SQLException();
            }
            preparedStatementInserirCategoria.close();
        }

        fecharConexao(connection);

        if (resultado != 1) {
            throw new Exception("Não foi possível inserir este administrador");
        }
    }

    public void atualizar(Produto produto, int id) throws Exception {
        Connection connection = obterConexao();

        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE produto SET descricao=?,preco=?,foto=?,quantidade=? WHERE id=?");
        preparedStatement.setString(1, produto.getDescricao());
        preparedStatement.setDouble(2, produto.getPreco());
        preparedStatement.setString(3, produto.getFoto());
        preparedStatement.setInt(4, produto.getQuantidade());        
        preparedStatement.setInt(5, id);
        int resultado = preparedStatement.executeUpdate();
        preparedStatement.close();
        
        PreparedStatement preparedStatement2 = connection.prepareStatement("DELETE FROM produto_categoria WHERE prod_id_fk=?");
        preparedStatement2.setInt(1, id);
        preparedStatement2.executeUpdate();
        preparedStatement2.close();

        for (int i = 0; produto.getCategorias() != null && i < produto.getCategorias().size(); i++) {
            Categoria categoria = produto.getCategorias().get(i);
            PreparedStatement preparedStatementInserirCategoria = connection.prepareStatement("INSERT INTO produto_categoria (prod_id_fk, cat_id_fk) VALUES (?, ?)");
            preparedStatementInserirCategoria.setInt(1, id);
            preparedStatementInserirCategoria.setInt(2, categoria.getId());
            if (preparedStatementInserirCategoria.executeUpdate() <= 0) {
                throw new SQLException();
            }
            preparedStatementInserirCategoria.close();
        }

        fecharConexao(connection);

        if (resultado != 1) {
            throw new Exception("Não foi possível atualizar este produto");
        }
    }

    public void remover(int id) throws Exception {
        //remoção de produto_categoria
        Connection connection2 = obterConexao();

        PreparedStatement preparedStatement2 = connection2.prepareStatement("DELETE FROM produto_categoria WHERE prod_id_fk=?");
        preparedStatement2.setInt(1, id);
        preparedStatement2.executeUpdate();
        preparedStatement2.close();

        fecharConexao(connection2);

        //remoção de produto
        Connection connection = obterConexao();

        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM produto WHERE id=?");
        preparedStatement.setInt(1, id);
        int resultado = preparedStatement.executeUpdate();
        preparedStatement.close();

        fecharConexao(connection);

        if (resultado != 1) {
            throw new Exception("Não foi possível deletar este produto");
        }
    }

    public Produto obter(int id) throws Exception {
        Produto produto = null;
        Connection connection = obterConexao();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT descricao,quantidade,preco,foto FROM produto WHERE id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            produto = new Produto();
            produto.setId(id);
            produto.setDescricao(resultSet.getString("descricao"));
            produto.setQuantidade(resultSet.getInt("quantidade"));
            produto.setPreco(resultSet.getDouble("preco"));
            produto.setFoto(resultSet.getString("foto"));
            
            PreparedStatement preparedStatementProdutoCategorias = connection.prepareStatement("SELECT c.id, c.descricao FROM produto_categoria AS pc, categoria AS c WHERE pc.prod_id_fk = ? AND c.id = pc.cat_id_fk");
            preparedStatementProdutoCategorias.setInt(1, id);
            ResultSet resultSetProdutoCategorias = preparedStatementProdutoCategorias.executeQuery();
            ArrayList<Categoria> categorias = new ArrayList<>();
            while (resultSetProdutoCategorias.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(resultSetProdutoCategorias.getInt("id"));
                categoria.setDescricao(resultSetProdutoCategorias.getString("descricao"));
                categorias.add(categoria);
            }
            resultSetProdutoCategorias.close();
            preparedStatementProdutoCategorias.close();
            produto.setCategorias(categorias);
        }
        resultSet.close();
        preparedStatement.close();

        fecharConexao(connection);

        if (produto == null) {
            throw new Exception("Não foi possível obter este produto");
        }
        return produto;
    }

    public List<Produto> obterTodos() throws Exception {
        List<Produto> produtos = new ArrayList<Produto>();
        Connection connection = obterConexao();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id,descricao,quantidade,preco,foto FROM produto");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Produto produto = new Produto();
            produto.setId(resultSet.getInt("id"));
            produto.setDescricao(resultSet.getString("descricao"));
            produto.setQuantidade(resultSet.getInt("quantidade"));
            produto.setPreco(resultSet.getDouble("preco"));
            produto.setFoto(resultSet.getString("foto"));

            PreparedStatement preparedStatementProdutoCategorias = connection.prepareStatement("SELECT c.id, c.descricao FROM produto_categoria AS pc, categoria AS c WHERE pc.prod_id_fk = ? AND c.id = pc.cat_id_fk");
            preparedStatementProdutoCategorias.setInt(1, produto.getId());
            ResultSet resultSetProdutoCategorias = preparedStatementProdutoCategorias.executeQuery();
            ArrayList<Categoria> categorias = new ArrayList<>();
            while (resultSetProdutoCategorias.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(resultSetProdutoCategorias.getInt("id"));
                categoria.setDescricao(resultSetProdutoCategorias.getString("descricao"));
                categorias.add(categoria);
            }
            resultSetProdutoCategorias.close();
            preparedStatementProdutoCategorias.close();
            produto.setCategorias(categorias);

            produtos.add(produto);
        }
        resultSet.close();
        preparedStatement.close();

        fecharConexao(connection);

        if (produtos.isEmpty()) {
            throw new Exception("Não foi possível obter os produtos");
        }
        return produtos;
    }
    
    public List<Produto> obterProdSemEstoque() throws Exception {
        List<Produto> produtos = new ArrayList<Produto>();
        Connection connection = obterConexao();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id,descricao,quantidade,preco,foto FROM produto WHERE quantidade=? ORDER BY descricao asc");
        preparedStatement.setInt(1, 0);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Produto produto = new Produto();
            produto.setId(resultSet.getInt("id"));
            produto.setDescricao(resultSet.getString("descricao"));
            produto.setQuantidade(resultSet.getInt("quantidade"));
            produto.setPreco(resultSet.getDouble("preco"));
            produto.setFoto(resultSet.getString("foto"));

            PreparedStatement preparedStatementProdutoCategorias = connection.prepareStatement("SELECT c.id, c.descricao FROM produto_categoria AS pc, categoria AS c WHERE pc.prod_id_fk = ? AND c.id = pc.cat_id_fk");
            preparedStatementProdutoCategorias.setInt(1, produto.getId());
            ResultSet resultSetProdutoCategorias = preparedStatementProdutoCategorias.executeQuery();
            ArrayList<Categoria> categorias = new ArrayList<>();
            while (resultSetProdutoCategorias.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(resultSetProdutoCategorias.getInt("id"));
                categoria.setDescricao(resultSetProdutoCategorias.getString("descricao"));
                categorias.add(categoria);
            }
            resultSetProdutoCategorias.close();
            preparedStatementProdutoCategorias.close();
            produto.setCategorias(categorias);

            produtos.add(produto);
        }
        resultSet.close();
        preparedStatement.close();

        fecharConexao(connection);

        if (produtos.isEmpty()) {
            throw new Exception("Não foi possível obter os produtos");
        }
        return produtos;
    }
}
