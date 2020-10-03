package controle.produtos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.administrador.Administrador;
import modelo.produtos.Produto;
import modelo.produtos.ProdutoModelo;

public class ProdutosAdmServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        ProdutoModelo produtoModelo = new ProdutoModelo();
        ArrayList<Produto> produtos = new ArrayList<Produto>();
        
        try {
            produtos = (ArrayList<Produto>) produtoModelo.obterTodos();
        } catch (Exception ex) {
            Logger.getLogger(ProdutosAdmServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (sessao.getAttribute("usuario") != null && sessao.getAttribute("usuario") instanceof Administrador){            
            sessao.setAttribute("produtos", produtos);
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/administrador/produtos-adm.jsp");
            dispatcher.forward(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("InicioServlet");
            dispatcher.forward(request, response);
        }
    }

}
