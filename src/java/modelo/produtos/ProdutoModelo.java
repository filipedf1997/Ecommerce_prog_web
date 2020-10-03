package modelo.produtos;

import java.util.ArrayList;
import java.util.List;

public class ProdutoModelo {
    
    public void inserir(Produto produto)throws Exception{
        ProdutoDAO produtoDAO = new ProdutoDAO();
        produtoDAO.inserir(produto);
    }
    
    public ArrayList<Produto> obterTodos()throws Exception{
        ProdutoDAO produtoDAO = new ProdutoDAO();
        return (ArrayList<Produto>) produtoDAO.obterTodos();
    }
    
    public Produto obter(int id)throws Exception{
        ProdutoDAO produtoDAO = new ProdutoDAO();
        return produtoDAO.obter(id);
    }
    
    public void remover(int id)throws Exception{
        ProdutoDAO produtoDAO = new ProdutoDAO();
        produtoDAO.remover(id);
    }
    
    public void atualizar(Produto produto, int id)throws Exception{
        ProdutoDAO produtoDAO = new ProdutoDAO();
        produtoDAO.atualizar(produto, id);
    }
    
    public List<Produto> obterProdSemEstoque()throws Exception{
        ProdutoDAO produtoDAO = new ProdutoDAO();
        return (List<Produto>) produtoDAO.obterProdSemEstoque();
    }
}
