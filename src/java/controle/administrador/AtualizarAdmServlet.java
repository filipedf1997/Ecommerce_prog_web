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

public class AtualizarAdmServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        Administrador administradorAtual = (Administrador) sessao.getAttribute("usuario");
        
        String nome = request.getParameter("nome");        
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        
        Administrador administrador = new Administrador();
        if(nome != null && !nome.equals("")){
            administrador.setNome(nome);
        } else {
            administrador.setNome(administradorAtual.getNome());
        }        
        if(email != null && !email.equals("")){
            administrador.setEmail(email);
        } else {
            administrador.setEmail(administradorAtual.getEmail());
        }
        if(senha != null && !senha.equals("")){
            administrador.setSenha(senha);
        } else {
            administrador.setSenha(administradorAtual.getSenha());
        }       
        
        AdministradorModelo administradorModelo = new AdministradorModelo();
        try {
            administradorModelo.atualizar(administrador, administradorAtual.getId());
            Administrador admNovo = administradorModelo.obter(administradorAtual.getLogin());
            sessao.setAttribute("usuario", admNovo);
            request.setAttribute("mensagem", "atualizarConta");
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/administrador/perfil-adm.jsp");
            dispatcher.forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AtualizarAdmServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
