<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro - Estudantes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/cadastro.css">
    
    <%-- Fontes (mantidas conforme original) --%>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inria+Serif:ital,wght@0,300;0,400;0,700;1,300;1,400;1,700&family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&family=Pixelify+Sans:wght@400..700&display=swap" rel="stylesheet">
</head>
<body>

    <main>
        <div class="div-overlay">
            <div class="overlay"></div>
            <img src="${pageContext.request.contextPath}/3b9acabc-be87-4b60-af72-8bc163abe1d6.png" alt="Estudantes">
        </div>

        <div class="container">
            <div class="left">
                <h1>Seja bem-vindo ao Estudantes</h1>
                <p>A educação é a arma mais poderosa que você pode usar para mudar o mundo.</p>
            </div>

            <div class="login-box">
                <h1>Cadastro</h1>

                <%-- Início do Formulário --%>
                <form action="CadastroServlet" method="POST">
                    
                    <label for="cpf">CPF</label>
                    <input type="text" id="cpf" name="txtCpf" required>

                    <label for="matricula">Matrícula</label>
                    <input type="text" id="matricula" name="txtMatricula" required>

                    <label for="email">E-mail</label>
                    <input type="email" id="email" name="txtEmail" required>

                    <label for="senha">Senha</label>
                    <input type="password" id="senha" name="txtSenha" required>

                    <%-- Botão agora é do tipo submit para enviar o form --%>
                    <button type="submit">Cadastrar</button>
                    
                </form>
                <%-- Fim do Formulário --%>
                
            </div>
        </div>
    </main>
</body>
</html>