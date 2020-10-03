package controle.categorias;

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
import modelo.categorias.Categoria;
import modelo.categorias.CategoriaModelo;

public class CategoriasAdmServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        CategoriaModelo categoriaModelo = new CategoriaModelo();
        ArrayList<Categoria> categorias = new ArrayList<Categoria>();
        
        try {
            categorias = (ArrayList<Categoria>) categoriaModelo.obterTodos();
        } catch (Exception ex) {
            Logger.getLogger(CategoriasAdmServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (sessao.getAttribute("usuario") != null && sessao.getAttribute("usuario") instanceof Administrador){
            sessao.setAttribute("categorias", categorias);
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/administrador/categorias-adm.jsp");
            dispatcher.forward(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("InicioServlet");
            dispatcher.forward(request, response);
        }
    }

}
