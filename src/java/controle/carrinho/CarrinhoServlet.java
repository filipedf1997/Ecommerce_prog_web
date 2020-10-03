package controle.carrinho;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.carrinho.CarrinhoItem;
import modelo.carrinho.CarrinhoModelo;
import modelo.cliente.Cliente;

public class CarrinhoServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        
        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;

        for (int i = 0; cookies != null && i < cookies.length; i++) {
            if (cookies[i].getName().equals(CarrinhoModelo.CARRINHO_COMPRA_CHAVE)) {
                cookie = cookies[i];
                break;
            }
        }
        cookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie);
        
        try {
            List<CarrinhoItem> carrinhoItens = CarrinhoModelo.obterCarrinho(cookie.getValue());
            if (!carrinhoItens.isEmpty()) {
                request.setAttribute("carrinho", carrinhoItens);
            }
        } catch (Exception ex) {
            request.setAttribute("mensagem", ex.getMessage());
        }
        
        if (sessao.getAttribute("usuario") != null && sessao.getAttribute("usuario") instanceof Cliente){            
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/cliente/carrinho-cliente.jsp");
            dispatcher.forward(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("carrinho.jsp");
            dispatcher.forward(request, response);
        }
    }
}
