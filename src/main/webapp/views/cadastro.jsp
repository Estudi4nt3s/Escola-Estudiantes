<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro - Estudantes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cadastro.css">

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inria+Serif:ital,wght@0,300;0,400;0,700;1,300;1,400;1,700&family=Pixelify+Sans:wght@400..700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inria+Serif:ital,wght@0,300;0,400;0,700;1,300;1,400;1,700&family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&family=Pixelify+Sans:wght@400..700&display=swap" rel="stylesheet">
</head>
<body>

<div class="container">
    <div class="left">
        <h1>Seja bem-vindo<br>ao Estudantes</h1>
        <hr>
        <p>
            A educação é a arma mais poderosa que você pode usar para mudar o mundo.
        </p>
    </div>

    <div class="login-box">
        <h2>Cadastro</h2>

        <!-- Mensagem dinâmica opcional -->
        <%
            String mensagem = (String) request.getAttribute("mensagem");
            if (mensagem != null) {
        %>
        <p style="color: green;"><%= mensagem %></p>
        <%
            }
        %>

        <form action="servletCadastro" method="post">

            <label for="nome">Nome Completo</label>
            <input type="text" name="nome" id="nome" required>

            <label for="matricula">Matrícula</label>
            <input type="text" name="matricula" id="matricula" required>

            <label for="email">E-mail</label>
            <input type="email" name="email" id="email" required>

            <label for="senha">Senha</label>
            <input type="password" name="senha" id="senha" required>

            <button type="submit">Cadastrar</button>

        </form>
    </div>
</div>

</body>
</html>