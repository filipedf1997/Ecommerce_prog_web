package controle.produtos;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.categorias.Categoria;
import modelo.categorias.CategoriaModelo;
import modelo.produtos.Produto;
import modelo.produtos.ProdutoModelo;

public class AddProdutoAdmServlet extends HttpServlet {

    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idS = request.getParameter("id");        
        String descricao = request.getParameter("descricao");
        String precoS = request.getParameter("preco");
        double preco = Double.parseDouble(precoS);
        String quantS = request.getParameter("quantidade");
        int quant = Integer.parseInt(quantS);
        String[] categorias = request.getParameterValues("categorias");
        String foto = request.getParameter("foto");
        
        boolean sucesso = false;
        
        if(categorias == null){
            request.setAttribute("mensagem", "erroProduto");
            RequestDispatcher dispatcher = request.getRequestDispatcher("ProdutosAdmServlet");
            dispatcher.forward(request, response);
        }
        
        ArrayList<Categoria> listaCategorias = new ArrayList<Categoria>();
        CategoriaModelo categoriaModelo = new CategoriaModelo();
        for (int i=0; i<categorias.length; i++){
            try {
                listaCategorias.add(categoriaModelo.obter(Integer.parseInt(categorias[i])));
            } catch (Exception ex) {
                Logger.getLogger(AddProdutoAdmServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        Produto produto = new Produto();
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        produto.setQuantidade(quant);
        produto.setCategorias(listaCategorias);
        produto.setFoto(foto);
        
        ProdutoModelo produtoModelo = new ProdutoModelo();
        
        if(idS == null || idS.equals("")){
            try {
                produtoModelo.inserir(produto);
                sucesso = true;
            } catch (Exception ex) {
                sucesso = false;
            }
        } else {
            try {
                int id = Integer.parseInt(idS);
                produtoModelo.atualizar(produto, id);
                sucesso = true;
            } catch (Exception ex) {
                sucesso = false;
            }
        }
        
        if(sucesso){
            request.setAttribute("mensagem", "sucessoProduto");
        } else {
            request.setAttribute("mensagem", "erroProduto");
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("ProdutosAdmServlet");
        dispatcher.forward(request, response);
        
    }
}
