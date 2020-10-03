package controle.categorias;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.categorias.CategoriaModelo;

public class ExcluirCategoriasAdmServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        CategoriaModelo categoriaModelo = new CategoriaModelo();
        try {
            categoriaModelo.remover(id);
        } catch (Exception ex) {
            Logger.getLogger(ExcluirCategoriasAdmServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.setAttribute("mensagem", "excluirCategoria");
        RequestDispatcher dispatcher = request.getRequestDispatcher("CategoriasAdmServlet");
        dispatcher.forward(request, response);

    }

}
