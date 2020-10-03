<%@page import="java.sql.Date"%>
<%@page import="modelo.carrinho.CarrinhoItem"%>
<%@page import="modelo.compra.Compra"%>
<%@page import="java.util.List"%>
<%@page import="modelo.administrador.Administrador"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="pt-br">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.1/css/bootstrap.min.css" integrity="sha384-VCmXjywReHh4PwowAiWNagnWcLhlEJLA5buUprzK8rxFgeH0kww/aWY76TfkUoSX" crossorigin="anonymous">

        <script src="https://kit.fontawesome.com/f6b573fd19.js" crossorigin="anonymous"></script>
        <link href="https://fonts.googleapis.com/css2?family=Lobster&display=swap" rel="stylesheet">

        <link rel="stylesheet" type="text/css" href="css/estilo.css">
        <script src="js/app.js" defer></script>

        <title>Donna Luna</title>
    </head>
    <body
        <%
            if (request.getAttribute("mensagem") != null) {
                String param = (String) request.getAttribute("mensagem");
        %>
        onLoad="mostrarModal('<%= param%>')"
        <%
            }
        %>
        >
        <%
            String usuarioNome = "";
            if (session.getAttribute("usuario") instanceof Administrador) {
                Administrador administrador = (Administrador) session.getAttribute("usuario");
                usuarioNome = administrador.getNome();
            }
        %>

        <header>
            <nav class="navbar navbar-expand-lg navbar-light bg-primary fixed-top">
                <div class="container">

                    <a href="InicioServlet" class="navbar-brand logo"><h1>Donna Luna</h1></a>

                    <button class="navbar-toggler" data-toggle="collapse" data-target="#menu">
                        <i class="navbar-toggler-icon"></i>
                    </button>

                    <div class="collapse navbar-collapse" id="menu">
                        <ul class="navbar-nav ml-auto">
                            <li class="nav-item mr-5 mt-1">
                                <div class="dropdown">
                                    <button class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenuButton" 
                                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        <spam>Olá, <%= usuarioNome%>!</spam>
                                    </button>
                                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                        <a class="dropdown-item" href="PerfilAdmServlet">Conta</a>
                                        <div class="dropdown-divider"></div>
                                        <h6 class="dropdown-header">Gerenciar</h6> 	
                                        <a class="dropdown-item" href="ComprasAdmServlet">Compras</a>
                                        <a class="dropdown-item" href="CategoriasAdmServlet">Categorias</a>
                                        <a class="dropdown-item" href="ProdutosAdmServlet">Produtos</a>
                                        <div class="dropdown-divider"></div>
                                        <a class="dropdown-item" href="RelatoriosAdmServlet">Relatórios</a>
                                        <div class="dropdown-divider"></div>
                                        <a class="dropdown-item" href="LogoutServlet">LogOut</a>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>

                </div>
            </nav>
        </header>

        <section class="titulos-secoes">
            <div class="container">
                <h1>Compras realizadas</h1>
                <div class="row my-4">
                    <div class="col">
                        <%
                            List<Compra> compras = (List<Compra>) session.getAttribute("compras");
                            if (compras != null && !compras.isEmpty()) {
                                for (Compra compra : compras) {
                                    Date data = new Date(compra.getData());
                        %>
                        <div class="row my-3">
                            <div class="col">
                                <h3>Identificador da compra: <%= compra.getId()%> - Cliente: <%= compra.getCliente()%></h3>
                                <p>Data: <%= data%></p>
                                <h4>Itens:</h4>
                                <%
                                    for (CarrinhoItem item : compra.getCarrinhoItens()) {
                                %>
                                <div><%= item.getQuantidade()%>x <strong><%= item.getProduto().getDescricao()%></strong> - Preço unitário: R$ <%= item.getProduto().getPreco()%> </div>
                                <%
                                    }
                                %>                                
                                <div class="row">
                                    <div class="col-md-6">
                                        <div>Preço Total: <strong>R$ <%= compra.getTotal()%></strong></div>
                                    </div>
                                    <div class="col-md-6 d-flex justify-content-end">
                                        <a href="ExcluirCompraAdmServlet?id=<%= compra.getId()%>" id="excluir" class="btn btn-danger">Excluir</a>  
                                    </div>
                                </div>
                            </div>
                        </div>

                        <hr>                      
                        <%
                                }
                            }
                        %>
                    </div>
                </div>
            </div>
        </section>

        <footer class="bg-primary py-3 mt-2">
            <div class="container">
                <p>Todos os direitos reservados</p>
            </div>
        </footer>

        <!-- Modal-->
        <div class="modal fade" id="modalMensagem" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 id="h5Modal"></h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body" id="textoModal">
                        ...
                    </div>
                    <div class="modal-footer">
                        <button type="button" data-dismiss="modal" id="btnModal"></button>
                    </div>
                </div>
            </div>
        </div>

        <!-- JS Bootstrap -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.1/js/bootstrap.min.js" integrity="sha384-XEerZL0cuoUbHE4nZReLT7nx9gQrQreJekYhJD9WNWhH8nEW+0c5qq7aIo2Wl30J" crossorigin="anonymous"></script>
    </body>
</html>

