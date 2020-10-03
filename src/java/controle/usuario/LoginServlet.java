package controle.usuario;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.administrador.Administrador;
import modelo.administrador.AdministradorModelo;
import modelo.cliente.Cliente;
import modelo.cliente.ClienteModelo;

public class LoginServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        String perfilAdm = request.getParameter("perfilAdm");

        boolean sucesso = false;
        
        if(perfilAdm == null){
            ClienteModelo clienteModelo = new ClienteModelo();
            Cliente cliente = null;

            try {
                sucesso = clienteModelo.identificar(login, senha);
                if(sucesso){
                    cliente = clienteModelo.obter(login);
                    HttpSession session = request.getSession(true);
                    session.setAttribute("usuario", cliente);
                }
            } catch (Exception ex) {
                sucesso = false;
            }

            if(sucesso){
                RequestDispatcher dispatcher = request.getRequestDispatcher("InicioServlet");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("mensagem", "Login ou senha inválidos CLIENTE");
                RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            AdministradorModelo administradorModelo = new AdministradorModelo();
            Administrador administrador = null;

            try {
                sucesso = administradorModelo.identificar(login, senha);
                if(sucesso){
                    administrador = administradorModelo.obter(login);
                    HttpSession session = request.getSession(true);
                    session.setAttribute("usuario", administrador);
                }
            } catch (Exception ex) {
                sucesso = false;
            }

            if(sucesso){                
                RequestDispatcher dispatcher = request.getRequestDispatcher("InicioServlet");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("mensagem", "Login ou senha inválidos ADM");
                RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);
            }
        }
    }
}
