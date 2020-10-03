package modelo.carrinho;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import modelo.produtos.Produto;
import modelo.produtos.ProdutoModelo;

public class CarrinhoModelo {

    public static final String CARRINHO_COMPRA_CHAVE = "carrinho";

    private static final String SEPARADOR_PRODUTO = "&";

    private static final String SEPARADOR_CAMPO = ":";

    public static final Cookie obterCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;

        for (int i = 0; cookies != null && i < cookies.length; i++) {
            if (cookies[i].getName().equals(CarrinhoModelo.CARRINHO_COMPRA_CHAVE)) {
                cookie = cookies[i];
                break;
            }
        }
        return cookie;
    }

    public static final List<CarrinhoItem> obterCarrinho(String valor) throws Exception {
        List<CarrinhoItem> carrinhoItens = new ArrayList<CarrinhoItem>();

        if (valor == null || valor.trim().length() == 0 || !valor.contains(SEPARADOR_CAMPO)) {
            return carrinhoItens;
        }

        ProdutoModelo produtoModelo = new ProdutoModelo();

        if (valor.contains(SEPARADOR_PRODUTO)) {
            String[] produtos = valor.split(SEPARADOR_PRODUTO);
            for (int i = 0; produtos != null && i < produtos.length; i++) {
                String[] item = produtos[i].split(SEPARADOR_CAMPO);
                CarrinhoItem carrinhoItem = new CarrinhoItem();
                Produto produto = produtoModelo.obter(Integer.parseInt(item[0]));
                carrinhoItem.setProduto(produto);
                carrinhoItem.setQuantidade(Integer.parseInt(item[1]));
                carrinhoItens.add(carrinhoItem);
            }
        } else {
            String[] item = valor.split(SEPARADOR_CAMPO);
            CarrinhoItem carrinhoItem = new CarrinhoItem();
            Produto produto = produtoModelo.obter(Integer.parseInt(item[0]));
            carrinhoItem.setProduto(produto);
            carrinhoItem.setQuantidade(Integer.parseInt(item[1]));
            carrinhoItens.add(carrinhoItem);
        }
        return carrinhoItens;
    }

    public static final String adicionarItem(int produtoId, int quantidade, String valor) throws Exception {
        List<CarrinhoItem> carrinhoItens = obterCarrinho(valor);

        if (carrinhoItens.isEmpty()) {
            return produtoId + SEPARADOR_CAMPO + quantidade;
        }

        boolean adicionou = false;

        String resultado = "";
        
        for (CarrinhoItem carrinhoItem : carrinhoItens) {
            if (carrinhoItem.getProduto().getId() == produtoId) {
                carrinhoItem.setQuantidade(quantidade);
                adicionou = true;
            }

            if (!resultado.isEmpty()) {
                resultado += SEPARADOR_PRODUTO;
            }

            resultado += carrinhoItem.getProduto().getId() + SEPARADOR_CAMPO + carrinhoItem.getQuantidade();
        }

        if (!adicionou) {
            resultado += SEPARADOR_PRODUTO + produtoId + SEPARADOR_CAMPO + quantidade;
        }

        return resultado;
    }

    public static final String removerItem(int produtoId, String valor) throws Exception {
        List<CarrinhoItem> carrinhoItens = obterCarrinho(valor);

        if (carrinhoItens.isEmpty()) {
            return "";
        }

        String resultado = "";

        for (CarrinhoItem carrinhoItem : carrinhoItens) {
            if (carrinhoItem.getProduto().getId() == produtoId) {
                continue;
            }

            if (!resultado.isEmpty()) {
                resultado += SEPARADOR_PRODUTO;
            }

            resultado += carrinhoItem.getProduto().getId() + SEPARADOR_CAMPO + carrinhoItem.getQuantidade();
        }

        return resultado;
    }

}
