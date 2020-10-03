package controle.usuario;

import controle.categorias.CategoriasAdmServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.administrador.Administrador;
import modelo.carrinho.CarrinhoModelo;
import modelo.categorias.Categoria;
import modelo.categorias.CategoriaModelo;
import modelo.cliente.Cliente;
import modelo.produtos.Produto;
import modelo.produtos.ProdutoModelo;

public class InicioServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProdutoModelo produtoModelo = new ProdutoModelo();
        ArrayList<Produto> todosProdutos = new ArrayList<Produto>();
        ArrayList<Produto> produtosEstoque = new ArrayList<Produto>();

        try {
            todosProdutos = produtoModelo.obterTodos();
            for (Produto produto : todosProdutos) {
                if (produto.getQuantidade() > 0) {
                    produtosEstoque.add(produto);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(InicioServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.setAttribute("produtos", produtosEstoque);
        
        //Cookies
        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;

        for (int i = 0; cookies != null && i < cookies.length; i++) {
            if (cookies[i].getName().equals(CarrinhoModelo.CARRINHO_COMPRA_CHAVE)) {
                cookie = cookies[i];
                break;
            }
        }
        
        if (cookie == null) {
            cookie = new Cookie(CarrinhoModelo.CARRINHO_COMPRA_CHAVE, "");
        }
        cookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie);
        
        //
        HttpSession sessao = request.getSession();

        if (sessao.getAttribute("usuario") != null && sessao.getAttribute("usuario") instanceof Administrador) {
            CategoriaModelo categoriaModelo = new CategoriaModelo();
            ArrayList<Categoria> categorias = new ArrayList<Categoria>();
            try {
                categorias = (ArrayList<Categoria>) categoriaModelo.obterTodos();
            } catch (Exception ex) {
                Logger.getLogger(CategoriasAdmServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            sessao.setAttribute("categorias", categorias);
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/administrador/home-adm.jsp");
            dispatcher.forward(request, response);
        } else if (sessao.getAttribute("usuario") != null && sessao.getAttribute("usuario") instanceof Cliente) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/cliente/home-cliente.jsp");
            dispatcher.forward(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        }

    }
}
