package controle.cliente;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.cliente.Cliente;
import modelo.cliente.ClienteModelo;

public class ExcluirClienteServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        Cliente cliente = (Cliente) sessao.getAttribute("usuario");
        ClienteModelo clienteModelo = new ClienteModelo();
        try {
            clienteModelo.remover(cliente.getId());
            sessao.invalidate();
            RequestDispatcher dispatcher = request.getRequestDispatcher("InicioServlet");
            dispatcher.forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ExcluirClienteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
