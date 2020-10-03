function validaCadastro() {
    var nome = document.getElementById("nome").value
    var rua = document.getElementById("ruas").value
    var numero = document.getElementById("numero").value
    var bairro = document.getElementById("bairro").value
    var cep = document.getElementById("cep").value
    var email = document.getElementById("email").value
    var login = document.getElementById("login").value
    var senha = document.getElementById("senha").value
    var verificaSenha = validaSenha()
    //Validação caso o navegador não suporte o required e se a senha é forte
    if (nome === "" || rua === "" || numero === "" ||
            bairro === "" || cep === "" || email === "" || login === "" || !verificaSenha) {
        mostrarModal("cadastroFalha")
        return false
    }
    return true
}

function validaSenha() {
    var senha = document.getElementById("senha").value
    var regex = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/
    return regex.test(senha)
}

function mostrarModal(tipo) {
    if (tipo == "produtoSucesso") {
        document.getElementById("h5Modal").innerHTML = "Produto adicionado ao carrinho!"
        document.getElementById("h5Modal").className = "modal-title text-success"
        document.getElementById("textoModal").innerHTML = "O produto foi adicionado/alterado ao carrinho com sucesso. Compre mais!"
        document.getElementById("btnModal").innerHTML = "Voltar"
        document.getElementById("btnModal").className = "btn btn-success"
        $("#modalMensagem").modal("show")
    } else if (tipo == "cadastroFalha") {
        document.getElementById("h5Modal").innerHTML = "Não foi possível realizar o cadastro"
        document.getElementById("h5Modal").className = "modal-title text-danger"
        document.getElementById("textoModal").innerHTML = "Por favor, preencha todos os campos e escolha uma senha forte para efetuar o seu cadastro."
        document.getElementById("btnModal").innerHTML = "Voltar"
        document.getElementById("btnModal").className = "btn btn-danger"
        $("#modalMensagem").modal("show")
    } else if (tipo == "compraSucesso") {
        document.getElementById("h5Modal").innerHTML = "Compra realizada com sucesso!"
        document.getElementById("h5Modal").className = "modal-title text-success"
        document.getElementById("textoModal").innerHTML = "Parabéns! Você comprou todos os produtos que estavam no carrinho!"
        document.getElementById("btnModal").innerHTML = "Voltar"
        document.getElementById("btnModal").className = "btn btn-success"
        $("#modalMensagem").modal("show")
    } else if (tipo == "compraFalha") { 
        document.getElementById("h5Modal").innerHTML = "Não foi possível realizar a compra!"
        document.getElementById("h5Modal").className = "modal-title text-danger"
        document.getElementById("textoModal").innerHTML = "Certifique-se de estar logado e com produtos no carrinho para efetuar a compra."
        document.getElementById("btnModal").innerHTML = "Voltar"
        document.getElementById("btnModal").className = "btn btn-danger"
        $("#modalMensagem").modal("show")
    } else if (tipo == "loginFalha") {
        document.getElementById("h5Modal").innerHTML = "Login ou senha inválidos"
        document.getElementById("h5Modal").className = "modal-title text-danger"
        document.getElementById("textoModal").innerHTML = "Favor preencher os dados corretamente."
        document.getElementById("btnModal").innerHTML = "Voltar"
        document.getElementById("btnModal").className = "btn btn-danger"
        $("#modalMensagem").modal("show")
    } else if (tipo == "cadastroFalhaLogin") {
        document.getElementById("h5Modal").innerHTML = "Não foi possível realizar o cadastro"
        document.getElementById("h5Modal").className = "modal-title text-danger"
        document.getElementById("textoModal").innerHTML = "O login escolhido já existe, favor escolher outro."
        document.getElementById("btnModal").innerHTML = "Voltar"
        document.getElementById("btnModal").className = "btn btn-danger"
        $("#modalMensagem").modal("show")
    } else if (tipo == "cadastroSucesso") {
        document.getElementById("h5Modal").innerHTML = "Cadastro realizado com sucesso!"
        document.getElementById("h5Modal").className = "modal-title text-success"
        document.getElementById("textoModal").innerHTML = "Seu cadastro foi feito com sucesso. Aproveite as nossas ofertas!"
        document.getElementById("btnModal").innerHTML = "Voltar"
        document.getElementById("btnModal").className = "btn btn-success"
        $("#modalMensagem").modal("show")
    } else if (tipo == "atualizarConta") {
        document.getElementById("h5Modal").innerHTML = "Conta atualizada com sucesso!"
        document.getElementById("h5Modal").className = "modal-title text-success"
        document.getElementById("textoModal").innerHTML = "Suas informações foram atualizadas com sucesso!"
        document.getElementById("btnModal").innerHTML = "Voltar"
        document.getElementById("btnModal").className = "btn btn-success"
        $("#modalMensagem").modal("show")
    } else if (tipo == "excluirConta") {
        $("#modalExcluir").modal("show")
    } else if (tipo == "erroProduto") {
        document.getElementById("h5Modal").innerHTML = "Erro ao cadastrar"
        document.getElementById("h5Modal").className = "modal-title text-danger"
        document.getElementById("textoModal").innerHTML = "Não foi possível cadastrar/alterar o produto. Preencha os campos corretamente e tente novamente."
        document.getElementById("btnModal").innerHTML = "Voltar"
        document.getElementById("btnModal").className = "btn btn-danger"
        $("#modalMensagem").modal("show")
    } else if (tipo == "sucessoProduto") {
        document.getElementById("h5Modal").innerHTML = "Produto cadastrado com sucesso!"
        document.getElementById("h5Modal").className = "modal-title text-success"
        document.getElementById("textoModal").innerHTML = "O produto foi cadastrado/alterado com sucesso!"
        document.getElementById("btnModal").innerHTML = "Voltar"
        document.getElementById("btnModal").className = "btn btn-success"
        $("#modalMensagem").modal("show")
    } else if (tipo == "excluirProduto") {
        document.getElementById("h5Modal").innerHTML = "Produto excluído com sucesso!"
        document.getElementById("h5Modal").className = "modal-title text-success"
        document.getElementById("textoModal").innerHTML = "O produto foi excluído com sucesso!"
        document.getElementById("btnModal").innerHTML = "Voltar"
        document.getElementById("btnModal").className = "btn btn-success"
        $("#modalMensagem").modal("show")
    } else if (tipo == "categoriaSucesso") {
        document.getElementById("h5Modal").innerHTML = "Categoria cadastrada com sucesso!"
        document.getElementById("h5Modal").className = "modal-title text-success"
        document.getElementById("textoModal").innerHTML = "A categoria foi cadastrada/alterada com sucesso!"
        document.getElementById("btnModal").innerHTML = "Voltar"
        document.getElementById("btnModal").className = "btn btn-success"
        $("#modalMensagem").modal("show")
    } else if (tipo == "excluirCategoria") {
        document.getElementById("h5Modal").innerHTML = "Categoria excluída com sucesso!"
        document.getElementById("h5Modal").className = "modal-title text-success"
        document.getElementById("textoModal").innerHTML = "A categoria foi excluída com sucesso!"
        document.getElementById("btnModal").innerHTML = "Voltar"
        document.getElementById("btnModal").className = "btn btn-success"
        $("#modalMensagem").modal("show")
    } else if (tipo == "excluirItemCarrinho") {
        document.getElementById("h5Modal").innerHTML = "Produto excluído com sucesso!"
        document.getElementById("h5Modal").className = "modal-title text-success"
        document.getElementById("textoModal").innerHTML = "O produto foi excluído do carrinho com sucesso!"
        document.getElementById("btnModal").innerHTML = "Voltar"
        document.getElementById("btnModal").className = "btn btn-success"
        $("#modalMensagem").modal("show")
    } else if (tipo == "excluirCompra") {
        document.getElementById("h5Modal").innerHTML = "Compra excluída com sucesso!"
        document.getElementById("h5Modal").className = "modal-title text-success"
        document.getElementById("textoModal").innerHTML = "A compra foi excluída com sucesso!"
        document.getElementById("btnModal").innerHTML = "Voltar"
        document.getElementById("btnModal").className = "btn btn-success"
        $("#modalMensagem").modal("show")
    } else if (tipo == "relatorioSucesso") {
        document.getElementById("h5Modal").innerHTML = "Relatório gerado com sucesso!"
        document.getElementById("h5Modal").className = "modal-title text-success"
        document.getElementById("textoModal").innerHTML = "O relatório foi gerado com sucesso e será aberto no seu leitor de PDFs!"
        document.getElementById("btnModal").innerHTML = "Voltar"
        document.getElementById("btnModal").className = "btn btn-success"
        $("#modalMensagem").modal("show")
    } else if (tipo == "relatorioFalha") {
        document.getElementById("h5Modal").innerHTML = "Falha ao gerar o relatório"
        document.getElementById("h5Modal").className = "modal-title text-danger"
        document.getElementById("textoModal").innerHTML = "Não foi possível gerar o relatório. Tente novamente."
        document.getElementById("btnModal").innerHTML = "Voltar"
        document.getElementById("btnModal").className = "btn btn-danger"
        $("#modalMensagem").modal("show")
    }
}

