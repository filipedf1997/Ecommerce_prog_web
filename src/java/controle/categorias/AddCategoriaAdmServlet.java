package controle.categorias;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.categorias.Categoria;
import modelo.categorias.CategoriaModelo;

public class AddCategoriaAdmServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id").trim();
        String descricao = request.getParameter("descricao");
        Categoria categoria = new Categoria();
        categoria.setDescricao(descricao);
        CategoriaModelo categoriaModelo = new CategoriaModelo();
        if (id == null || id == "") {
            try {
                categoriaModelo.inserir(categoria);
            } catch (Exception ex) {
                Logger.getLogger(AddCategoriaAdmServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {           
            try {
                categoriaModelo.atualizar(categoria, Integer.parseInt(id));
            } catch (Exception ex) {
                Logger.getLogger(AddCategoriaAdmServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        request.setAttribute("mensagem", "categoriaSucesso");
        RequestDispatcher dispatcher = request.getRequestDispatcher("CategoriasAdmServlet");
        dispatcher.forward(request, response);

    }

}
