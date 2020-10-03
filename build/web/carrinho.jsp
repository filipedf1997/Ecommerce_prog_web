<%@page import="modelo.carrinho.CarrinhoItem"%>
<%@page import="java.util.List"%>
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
        <header>
            <nav class="navbar navbar-expand-lg navbar-light bg-primary fixed-top">
                <div class="container">

                    <a href="InicioServlet" class="navbar-brand logo"><h1>Donna Luna</h1></a>

                    <button class="navbar-toggler" data-toggle="collapse" data-target="#menu">
                        <i class="navbar-toggler-icon"></i>
                    </button>

                    <div class="collapse navbar-collapse" id="menu">
                        <ul class="navbar-nav ml-auto">

                            <li class="nav-item">
                                <a class="nav-link" href="login.jsp">
                                    <div class="row" id="nav-login" >
                                        <div class="col-md-5 d-flex justify-content-end align-items-center">
                                            <i class="fas fa-user-circle fa-2x"></i>
                                        </div>
                                        <div class="col-md-6">
                                            Entre ou Cadastra-se
                                        </div>
                                    </div>
                                </a>
                            </li>

                            <li class="nav-item d-flex align-items-center">
                                <a class="nav-link" href="CarrinhoServlet">
                                    <i class="fas fa-shopping-cart fa-2x"></i>
                                </a>
                            </li>

                        </ul>
                    </div>

                </div>
            </nav>
        </header>

        <section id="carrinho" class="titulos-secoes">
            <div class="container">
                <h1>Carrinho</h1>

                <div class="row my-4">
                    <div class="col-md-8">

                        <%
                            List<CarrinhoItem> carrinhoItens = (List<CarrinhoItem>) request.getAttribute("carrinho");
                            double total = 0;
                            if (carrinhoItens != null) {
                                for (CarrinhoItem item : carrinhoItens) {
                        %>
                        <div class="row my-3">
                            <div class="col-md-4">
                                <img src="img/<%= item.getProduto().getFoto()%>" class="img-fluid">
                            </div>
                            <div class="col-md-8">
                                <h3><%= item.getProduto().getDescricao()%></h3>
                                <div class="form-row">
                                    <div class="col-md-6">
                                        <h5 class="text-danger preço"> <%= item.getProduto().getPreco()%> </h5>
                                        <form action="AtualizarProdutoCarrinhoServlet?id=<%= item.getProduto().getId()%>" method="post">
                                            <div class="d-flex flex-row mt-4">
                                                <input type="number" class="form-control" min="1" max="<%= item.getProduto().getQuantidade()%>" value="<%= item.getQuantidade()%>" style="width: 80px" name="quantidade" >
                                                <button type="submit" id="excluir" class="btn btn-primary ml-3">Atualizar</button>  
                                            </div>
                                        </form>                                        
                                    </div>
                                    <div class="col-md-6 d-flex justify-content-end align-items-end">
                                        <a href="ExcluirProdutoCarrinhoServlet?id=<%= item.getProduto().getId()%>" id="excluir" class="btn btn-danger">Excluir</a>  
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%
                                total += item.getProduto().getPreco() * item.getQuantidade();
                            }
                        } else {
                        %>
                        <h3>Carrinho vazio :(</h3>
                        <%
                            }
                        %>

                    </div>

                    <div class="col-md-4">
                        <div class="d-flex justify-content-end">
                            <h2>Total</h2>
                        </div>
                        <div class="d-flex justify-content-end">
                            <h4 class="text-danger">R$ <%= total%></h4>  
                        </div>
                        <div class="d-flex justify-content-end">
                            <a href="CompraServlet?total=<%= total%>" class="btn btn-primary"><strong>Comprar</strong></a>
                        </div>
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
