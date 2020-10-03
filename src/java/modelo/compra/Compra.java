package modelo.compra;

import java.util.List;
import modelo.carrinho.CarrinhoItem;

public class Compra {
    
    private Integer id;
    private long data;
    private Integer clienteId;
    private Double total;
    private List<CarrinhoItem> carrinhoItens;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public int getCliente() {
        return clienteId;
    }

    public void setCliente(int cliente) {
        this.clienteId = cliente;
    }
    
    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<CarrinhoItem> getCarrinhoItens() {
        return carrinhoItens;
    }

    public void setCarrinhoItens(List<CarrinhoItem> carrinhoItens) {
        this.carrinhoItens = carrinhoItens;
    }

}

