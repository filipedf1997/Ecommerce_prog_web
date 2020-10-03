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

public class ExcluirProdutoCarrinhoServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");

        Cookie cookie = CarrinhoModelo.obterCookie(request);
        try {
            String novoValor = CarrinhoModelo.removerItem(Integer.parseInt(id), cookie.getValue());
            cookie.setValue(novoValor);
        } catch (Exception ex) {
            Logger.getLogger(ExcluirProdutoCarrinhoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.setAttribute("mensagem", "excluirItemCarrinho");
        RequestDispatcher dispatcher = request.getRequestDispatcher("CarrinhoServlet");
        dispatcher.forward(request, response);
    }
}
