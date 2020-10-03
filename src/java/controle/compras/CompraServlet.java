package controle.compras;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.carrinho.CarrinhoItem;
import modelo.carrinho.CarrinhoModelo;
import modelo.cliente.Cliente;
import modelo.compra.Compra;
import modelo.compra.CompraModelo;
import modelo.produtos.Produto;
import modelo.produtos.ProdutoModelo;

public class CompraServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        Cookie cookie = CarrinhoModelo.obterCookie(request);
        String mensagem = "compraFalha";

        Timestamp date = new Timestamp(System.currentTimeMillis());
        date.getTime();
        
        Double total = Double.parseDouble(request.getParameter("total"));
        
        if (sessao.getAttribute("usuario") != null && sessao.getAttribute("usuario") instanceof Cliente) {
            Cliente cliente = (Cliente) sessao.getAttribute("usuario");

            try {
                List<CarrinhoItem> carrinhoItens = CarrinhoModelo.obterCarrinho(cookie.getValue());
                if (carrinhoItens != null && !carrinhoItens.isEmpty()) {
                    Compra compra = new Compra();
                    compra.setCliente(cliente.getId());
                    compra.setData(date.getTime());
                    compra.setTotal(total);
                    compra.setCarrinhoItens(carrinhoItens);                    
                    CompraModelo compraModelo = new CompraModelo();
                    compraModelo.inserir(compra);
                    
                    mensagem = "compraSucesso";
                    cookie.setValue("");
                                        
                    for (int i = 0; i < carrinhoItens.size(); i++) {
                        Produto prod_carrinho = carrinhoItens.get(i).getProduto();
                        Produto produto = new Produto();                        
                        ProdutoModelo produtoModelo = new ProdutoModelo();
                                                
                        produto.setDescricao(prod_carrinho.getDescricao());
                        produto.setPreco(prod_carrinho.getPreco());
                        produto.setFoto(prod_carrinho.getFoto());
                        produto.setQuantidade(prod_carrinho.getQuantidade() - carrinhoItens.get(i).getQuantidade());
                        produto.setCategorias(prod_carrinho.getCategorias());
                        
                        produtoModelo.atualizar(produto, prod_carrinho.getId());
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(CompraServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        request.setAttribute("mensagem", mensagem);
        RequestDispatcher dispatcher = request.getRequestDispatcher("CarrinhoServlet");
        dispatcher.forward(request, response);
    }
}
