package modelo.administrador;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AdministradorModelo {
    
    public Administrador obter(String login) throws Exception{   
        if(login == null || login.trim().length() == 0){
            return null;
        }
        AdministradorDAO administradorDAO = new AdministradorDAO();
        return administradorDAO.obter(login);
    }
    
    public boolean identificar(String login, String senha) throws Exception{
        if(login == null || login.trim().length() == 0 || senha == null || senha.trim().length() == 0){
            return false;
        }
        Administrador administrador = obter(login);
        return administrador.getSenha().equals(senha);
    }
    
    public void remover(int id) throws Exception{
        AdministradorDAO administradorDAO = new AdministradorDAO();
        administradorDAO.remover(id);
    }
    
    public void atualizar(Administrador administrador, int id) throws Exception{
        AdministradorDAO administradorDAO = new AdministradorDAO();
        administradorDAO.atualizar(administrador, id);
    }
}
