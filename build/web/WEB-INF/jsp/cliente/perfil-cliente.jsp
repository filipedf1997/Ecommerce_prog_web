<%@page import="modelo.cliente.Cliente"%>
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
                onLoad="mostrarModal('<%= param %>')"
        <%
            }
        %>
    >
        <%
            String usuarioNome = "";
            if (session.getAttribute("usuario") instanceof Cliente) {
                Cliente cliente = (Cliente) session.getAttribute("usuario");
                usuarioNome = cliente.getNome();
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
                                        <a class="dropdown-item" href="PerfilClienteServlet">Conta</a>
                                        <a class="dropdown-item" href="ComprasRealizadasServlet">Compras Realizadas</a>
                                        <div class="dropdown-divider"></div>
                                        <a class="dropdown-item" href="LogoutServlet">LogOut</a>
                                    </div>
                                </div>
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

        <section id="area-form" class="cadastrar">
            <div class="container">

                <h2>Atualizar dados</h2>

                <form action="AtualizarClienteServlet" method="post" >
                    <small class="text-danger">
                        Preencha os campos que deseja alterar. As informações não
                        preenchidas não serão alteradas.
                    </small>
                    <div class="form-group">
                        <label for="nome">Nome completo</label>
                        <input type="text" id="nome" class="form-control" name="nome">
                    </div>

                    <hr>

                    <div class="form-row">
                        <div class="col-md-9">
                            <label for="rua">Logradouro</label>
                            <input type="text" id="rua" class="form-control" name="rua">
                        </div>

                        <div class="col-md-3">
                            <label for="numero">Número</label>
                            <input type="text" id="numero" class="form-control" name="numero">
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="col-md-6">
                            <label for="bairro">Bairro</label>
                            <input type="text" id="bairro" class="form-control" name="bairro">
                        </div>

                        <div class="col-md-6">
                            <label for="cep">CEP</label>
                            <input type="text" id="cep" class="form-control" name="cep">
                        </div>
                    </div>

                    <hr>

                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" id="email" class="form-control" name="email">
                    </div>

                    <div class="form-group">
                        <label for="senha">Senha</label>
                        <input type="password" id="senha" class="form-control" name="senha">
                    </div>

                    <div class="px-5">
                        <button type="submit" class="form-control btn btn-primary">Atualizar conta</button>
                    </div>
                </form>

                <h2>Deseja excluir a conta?:(</h2>
                <div class="btn-excluir">
                    <button class="form-control btn btn-danger" onClick="mostrarModal('excluirConta')">Excluir conta</button>
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
        
        <!-- Modal Excluir-->
        <div class="modal fade" id="modalExcluir" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 id="h5Modal">Aviso</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body" id="textoModal">
                        Tem certeza que você deseja excluir a sua conta? As alterações serão permanentes.
                    </div>
                    <div class="modal-footer">
                        <button type="button" data-dismiss="modal" id="btnModal" class="btn btn-secondary">Voltar</button>
                        <a class="btn btn-danger" href="ExcluirClienteServlet" id="btnExcluir">Excluir</a>
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
