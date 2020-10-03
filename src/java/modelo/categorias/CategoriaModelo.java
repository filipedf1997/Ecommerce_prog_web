package modelo.categorias;

import java.util.List;

public class CategoriaModelo {
    
    public List<Categoria> obterTodos()throws Exception{
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        return categoriaDAO.obterTodos();
    }
    
    public void remover(int id)throws Exception{
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        categoriaDAO.remover(id);        
    }
    
    public void inserir(Categoria categoria)throws Exception{
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        categoriaDAO.inserir(categoria);        
    }
    
    public void atualizar(Categoria categoria, int id)throws Exception{
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        categoriaDAO.atualizar(categoria, id);        
    }
    
    public Categoria obter(int id) throws Exception{
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        return categoriaDAO.obter(id);
    }
}
