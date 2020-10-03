package modelo.compra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.carrinho.CarrinhoItem;
import modelo.dao.DAO;
import modelo.produtos.ProdutoModelo;

public class CompraDAO extends DAO {

    public void inserir(Compra compra) throws Exception {
        Connection connection = obterConexao();

        int id = -1;
        Statement statementNovoId = connection.createStatement();
        ResultSet resultSetNovoId = statementNovoId.executeQuery("SELECT nextval('compra_id_seq') AS id");
        while (resultSetNovoId.next()) {
            id = resultSetNovoId.getInt("id");
        }
        resultSetNovoId.close();
        statementNovoId.close();

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO compra (id,data,pessoa_id_fk,total) VALUES (?,?,?,?)");
        preparedStatement.setInt(1, id);
        preparedStatement.setLong(2, compra.getData());
        preparedStatement.setInt(3, compra.getCliente());
        preparedStatement.setDouble(4, compra.getTotal());

        int resultado = preparedStatement.executeUpdate();
        preparedStatement.close();

        for (int i = 0; compra.getCarrinhoItens() != null && i < compra.getCarrinhoItens().size(); i++) {
            CarrinhoItem compraItem = compra.getCarrinhoItens().get(i);
            PreparedStatement preparedStatementInserirProdutoQnt = connection.prepareStatement("INSERT INTO compra_produto (compra_id_fk, prod_id_fk, quantidade) VALUES (?, ?, ?)");
            preparedStatementInserirProdutoQnt.setInt(1, id);
            preparedStatementInserirProdutoQnt.setInt(2, compraItem.getProduto().getId());
            preparedStatementInserirProdutoQnt.setInt(3, compraItem.getQuantidade());
            if (preparedStatementInserirProdutoQnt.executeUpdate() <= 0) {
                throw new SQLException();
            }
            preparedStatementInserirProdutoQnt.close();
        }

        fecharConexao(connection);

        if (resultado != 1) {
            throw new Exception("Não foi possível inserir esta compra");
        }
    }

    public void remover(int id) throws Exception {
        Connection connection = obterConexao();

        PreparedStatement preparedStatement1 = connection.prepareStatement("DELETE FROM compra_produto WHERE compra_id_fk=?");
        preparedStatement1.setInt(1, id);
        preparedStatement1.executeUpdate();
        preparedStatement1.close();

        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM compra WHERE id=?");
        preparedStatement.setInt(1, id);
        int resultado = preparedStatement.executeUpdate();
        preparedStatement.close();

        fecharConexao(connection);

        if (resultado != 1) {
            throw new Exception("Não foi possível deletar esta compra");
        }
    }    

    public List<Compra> obterCompras() throws Exception {
        List<Compra> compras = new ArrayList<Compra>();
        Connection connection = obterConexao();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id,data,total,pessoa_id_fk FROM compra ORDER BY pessoa_id_fk asc");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Compra compra = new Compra();
            compra.setId(resultSet.getInt("id"));
            compra.setData(resultSet.getLong("data"));
            compra.setTotal(resultSet.getDouble("total"));
            compra.setCliente(resultSet.getInt("pessoa_id_fk"));

            PreparedStatement preparedStatementItens = connection.prepareStatement("SELECT prod_id_fk, quantidade FROM compra_produto WHERE compra_id_fk = ?");
            preparedStatementItens.setInt(1, compra.getId());
            ResultSet resultSetProdutoQnt = preparedStatementItens.executeQuery();
            List<CarrinhoItem> carrinhoItens = new ArrayList<>();
            while (resultSetProdutoQnt.next()) {
                CarrinhoItem carrinhoItem = new CarrinhoItem();
                ProdutoModelo produtoModelo = new ProdutoModelo();
                carrinhoItem.setProduto(produtoModelo.obter(resultSetProdutoQnt.getInt("prod_id_fk")));
                carrinhoItem.setQuantidade(resultSetProdutoQnt.getInt("quantidade"));
                carrinhoItens.add(carrinhoItem);
            }
            resultSetProdutoQnt.close();
            preparedStatementItens.close();

            compra.setCarrinhoItens(carrinhoItens);

            compras.add(compra);
        }
        resultSet.close();
        preparedStatement.close();

        fecharConexao(connection);

        if (compras.isEmpty()) {
            throw new Exception("Não foi possível obter as compras");
        }
        return compras;
    }

    public List<Compra> obterComprasCliente(int id) throws Exception {
        List<Compra> compras = new ArrayList<Compra>();
        Connection connection = obterConexao();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id,data,total FROM compra WHERE pessoa_id_fk = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Compra compra = new Compra();
            compra.setId(resultSet.getInt("id"));
            compra.setData(resultSet.getLong("data"));
            compra.setTotal(resultSet.getDouble("total"));
            compra.setCliente(id);

            PreparedStatement preparedStatementItens = connection.prepareStatement("SELECT prod_id_fk, quantidade FROM compra_produto WHERE compra_id_fk = ?");
            preparedStatementItens.setInt(1, compra.getId());
            ResultSet resultSetProdutoQnt = preparedStatementItens.executeQuery();
            List<CarrinhoItem> carrinhoItens = new ArrayList<>();
            while (resultSetProdutoQnt.next()) {
                CarrinhoItem carrinhoItem = new CarrinhoItem();
                ProdutoModelo produtoModelo = new ProdutoModelo();
                carrinhoItem.setProduto(produtoModelo.obter(resultSetProdutoQnt.getInt("prod_id_fk")));
                carrinhoItem.setQuantidade(resultSetProdutoQnt.getInt("quantidade"));
                carrinhoItens.add(carrinhoItem);
            }
            resultSetProdutoQnt.close();
            preparedStatementItens.close();

            compra.setCarrinhoItens(carrinhoItens);

            compras.add(compra);
        }
        resultSet.close();
        preparedStatement.close();

        fecharConexao(connection);

        if (compras.isEmpty()) {
            throw new Exception("Não foi possível obter as compras");
        }
        return compras;
    }
    
    public int obterComprasQuantidade(long dataInicial, long dataFinal, int id) throws Exception {
        int qntCompra = 0;
        Connection connection = obterConexao();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM compra WHERE pessoa_id_fk = ? AND compra.data > ? AND compra.data < ?");
        preparedStatement.setInt(1, id);
        preparedStatement.setLong(2, dataInicial);
        preparedStatement.setLong(3, dataFinal);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            qntCompra++;
        }
        resultSet.close();
        preparedStatement.close();

        fecharConexao(connection);

        return qntCompra;
    }
    
    public List<Compra> obterComprasData(long dataInicial, long dataFinal) throws Exception {
        List<Compra> compras = new ArrayList<Compra>();
        Connection connection = obterConexao();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM compra WHERE compra.data > ? AND compra.data < ? ORDER BY data asc");
        preparedStatement.setLong(1, dataInicial);
        preparedStatement.setLong(2, dataFinal);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Compra compra = new Compra();
            compra.setId(resultSet.getInt("id"));
            compra.setData(resultSet.getLong("data"));
            compra.setTotal(resultSet.getDouble("total"));

            compras.add(compra);
        }
        resultSet.close();
        preparedStatement.close();

        fecharConexao(connection);

        return compras;
    }
}
