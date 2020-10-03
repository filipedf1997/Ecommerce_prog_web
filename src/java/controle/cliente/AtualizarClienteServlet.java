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

public class AtualizarClienteServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        Cliente clienteAtual = (Cliente) sessao.getAttribute("usuario");
        
        String nome = request.getParameter("nome");
        String rua = request.getParameter("rua");
        String numero = request.getParameter("numero");
        String bairro = request.getParameter("bairro");
        String cep = request.getParameter("cep");
        String endereco = rua + " - " + numero + " - " + bairro + " - " + cep;
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        
        Cliente cliente = new Cliente();
        if(nome != null && !nome.equals("")){
            cliente.setNome(nome);
        } else {
            cliente.setNome(clienteAtual.getNome());
        }
        if(rua != null && numero != null && bairro != null && cep != null
                && !rua.equals("") && !numero.equals("") && !bairro.equals("") && !cep.equals("")){
            cliente.setEndereco(endereco);
        } else {
            cliente.setEndereco(clienteAtual.getEndereco());
        }
        if(email != null && !email.equals("")){
            cliente.setEmail(email);
        } else {
            cliente.setEmail(clienteAtual.getEmail());
        }
        if(senha != null && !senha.equals("")){
            cliente.setSenha(senha);
        } else {
            cliente.setSenha(clienteAtual.getSenha());
        }       
        
        ClienteModelo clienteModelo = new ClienteModelo();
        try {
            clienteModelo.atualizar(cliente, clienteAtual.getId());
            Cliente clienteNovo = clienteModelo.obter(clienteAtual.getLogin());
            sessao.setAttribute("usuario", clienteNovo);
            request.setAttribute("mensagem", "atualizarConta");
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/cliente/perfil-cliente.jsp");
            dispatcher.forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AtualizarClienteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
