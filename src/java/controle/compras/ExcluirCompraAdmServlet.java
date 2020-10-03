package controle.compras;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.compra.CompraModelo;

public class ExcluirCompraAdmServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        CompraModelo compraModelo = new CompraModelo();
        
        try {
            compraModelo.remover(id);
        } catch (Exception ex) {
            Logger.getLogger(ExcluirCompraAdmServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.setAttribute("mensagem", "excluirCompra");
        RequestDispatcher dispatcher = request.getRequestDispatcher("ComprasAdmServlet");
        dispatcher.forward(request, response);
    }
}
