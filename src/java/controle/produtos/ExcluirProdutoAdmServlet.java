package controle.produtos;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.produtos.ProdutoModelo;

public class ExcluirProdutoAdmServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        ProdutoModelo produtoModelo = new ProdutoModelo();
        
        try {
            produtoModelo.remover(id);
        } catch (Exception ex) {
            Logger.getLogger(ExcluirProdutoAdmServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.setAttribute("mensagem", "excluirProduto");
        RequestDispatcher dispatcher = request.getRequestDispatcher("ProdutosAdmServlet");
        dispatcher.forward(request, response);
    }
}
