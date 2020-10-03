package controle.carrinho;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.carrinho.CarrinhoModelo;

public class AddProdutoCarrinhoServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String quantidade = request.getParameter("quantidade");
        Cookie cookie = CarrinhoModelo.obterCookie(request);
        try {
            String novoValor = CarrinhoModelo.adicionarItem(Integer.parseInt(id), Integer.parseInt(quantidade), cookie.getValue());
            cookie.setValue(novoValor);
            request.setAttribute("mensagem", "produtoSucesso");            
        } catch (Exception ex) {
            Logger.getLogger(AddProdutoCarrinhoServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("mensagem", ex.getMessage());            
        }
        
        request.setAttribute("mensagem", "produtoSucesso");
        RequestDispatcher dispatcher = request.getRequestDispatcher("InicioServlet");
        dispatcher.forward(request, response);
    }
}
