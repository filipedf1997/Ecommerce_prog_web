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
import modelo.cliente.Cliente;
import modelo.compra.Compra;
import modelo.compra.CompraModelo;

public class ComprasRealizadasServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession();

        if (sessao.getAttribute("usuario") != null && sessao.getAttribute("usuario") instanceof Cliente) {
            Cliente cliente = (Cliente) sessao.getAttribute("usuario");

            try {
                CompraModelo compraModelo = new CompraModelo();
                List<Compra> compras = compraModelo.obterComprasCliente(cliente.getId());
                sessao.setAttribute("compras", compras);
            } catch (Exception ex) {
                Logger.getLogger(ComprasRealizadasServlet.class.getName()).log(Level.SEVERE, null, ex);
            }           
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/cliente/compras-realizadas.jsp");
            dispatcher.forward(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("InicioServlet");
            dispatcher.forward(request, response);
        }
    }

}
