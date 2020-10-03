package controle.administrador;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.administrador.Administrador;
import modelo.administrador.AdministradorModelo;

public class ExcluirAdmServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        Administrador administrador = (Administrador) sessao.getAttribute("usuario");
        AdministradorModelo administradorModelo = new AdministradorModelo();
        try {
            administradorModelo.remover(administrador.getId());
            sessao.invalidate();
            RequestDispatcher dispatcher = request.getRequestDispatcher("InicioServlet");
            dispatcher.forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ExcluirAdmServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
