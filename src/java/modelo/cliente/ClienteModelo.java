package modelo.cliente;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteModelo {

    public Cliente obter(String login) throws Exception {
        if (login == null || login.trim().length() == 0) {
            return null;
        }
        ClienteDAO clienteDAO = new ClienteDAO();
        return clienteDAO.obter(login);
    }

    public boolean identificar(String login, String senha) throws Exception {
        if (login == null || login.trim().length() == 0 || senha == null || senha.trim().length() == 0) {
            return false;
        }
        Cliente cliente = obter(login);
        return cliente.getSenha().equals(senha);
    }

    public void inserir(Cliente cliente) throws Exception {
        if (cliente.getNome() == null || cliente.getNome().trim().length() == 0
                || cliente.getEndereco() == null || cliente.getEndereco().trim().length() == 0
                || cliente.getEmail() == null || cliente.getEmail().trim().length() == 0
                || cliente.getLogin() == null || cliente.getLogin().trim().length() == 0
                || cliente.getSenha() == null || cliente.getSenha().trim().length() == 0) {
            throw new Exception("Informe os dados corretamente.");
        }
        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.inserir(cliente);
    }

    public void remover(int id) throws Exception {
        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.remover(id);
    }

    public void atualizar(Cliente cliente, int id) throws Exception {
        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.atualizar(cliente, id);
    }

    public List<Cliente> obterTodos() throws Exception {
        ClienteDAO clienteDAO = new ClienteDAO();
        return clienteDAO.obterTodos();
    }
}
