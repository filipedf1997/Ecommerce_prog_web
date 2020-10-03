package controle.relatorios;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.administrador.Administrador;

public class RelatoriosAdmServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession();

        if (sessao.getAttribute("usuario") != null && sessao.getAttribute("usuario") instanceof Administrador){
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/administrador/relatorios-adm.jsp");
            dispatcher.forward(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("InicioServlet");
            dispatcher.forward(request, response);
        }
    }
}
