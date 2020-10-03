package controle.compras;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.administrador.Administrador;
import modelo.cliente.Cliente;
import modelo.compra.Compra;
import modelo.compra.CompraModelo;

public class ComprasAdmServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession();

        if (sessao.getAttribute("usuario") != null && sessao.getAttribute("usuario") instanceof Administrador) {

            try {
                CompraModelo compraModelo = new CompraModelo();
                List<Compra> compras = compraModelo.obterCompras();
                sessao.setAttribute("compras", compras);
            } catch (Exception ex) {
                Logger.getLogger(ComprasAdmServlet.class.getName()).log(Level.SEVERE, null, ex);
            }           
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/administrador/compras-adm.jsp");
            dispatcher.forward(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("InicioServlet");
            dispatcher.forward(request, response);
        }
    }

}
