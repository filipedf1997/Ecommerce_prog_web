
package controle.cliente;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.cliente.Cliente;
import modelo.cliente.ClienteModelo;

public class CadastrarClienteServlet extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String rua = request.getParameter("rua");
        String numero = request.getParameter("numero");
        String bairro = request.getParameter("bairro");
        String cep = request.getParameter("cep");
        String endereco = rua + " - " + numero + " - " + bairro + " - " + cep;
        String email = request.getParameter("email");
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setEndereco(endereco);
        cliente.setEmail(email);
        cliente.setLogin(login);
        cliente.setSenha(senha);

        ClienteModelo clienteModelo = new ClienteModelo();
        boolean sucesso = false;
        try {
            clienteModelo.inserir(cliente);
            sucesso = true;
        } catch (Exception ex) {
            sucesso = false;
        }
        
        if(sucesso){
            request.setAttribute("mensagem", "cadastroSucesso");
        } else {
            request.setAttribute("mensagem", "cadastroFalhaLogin");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("cadastroCliente.jsp");
        dispatcher.forward(request, response);
    }
}
