package modelo.compra;

import java.util.List;

public class CompraModelo {
    public void inserir(Compra compra) throws Exception{
        CompraDAO compraDAO = new CompraDAO();
        compraDAO.inserir(compra);
    }
    
    public List<Compra> obterComprasCliente(int id) throws Exception{
        CompraDAO compraDAO = new CompraDAO();
        return compraDAO.obterComprasCliente(id);
    }
    
    public List<Compra> obterCompras() throws Exception{
        CompraDAO compraDAO = new CompraDAO();
        return compraDAO.obterCompras();
    }
    
    public void remover(int id) throws Exception{
        CompraDAO compraDAO = new CompraDAO();
        compraDAO.remover(id);
    }
    
    public int obterComprasQuantidade(long dataInicial, long dataFinal, int id) throws Exception{
        CompraDAO compraDAO = new CompraDAO();
        return compraDAO.obterComprasQuantidade(dataInicial, dataFinal, id);
    }
    
    public List<Compra> obterComprasData(long dataInicial, long dataFinal) throws Exception{
        CompraDAO compraDAO = new CompraDAO();
        return compraDAO.obterComprasData(dataInicial, dataFinal);
    }
}
