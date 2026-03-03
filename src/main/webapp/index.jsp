<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Estudantes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cadastro.css">

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inria+Serif:ital,wght@0,300;0,400;0,700;1,300;1,400;1,700&family=Pixelify+Sans:wght@400..700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inria+Serif:ital,wght@0,300;0,400;0,700;1,300;1,400;1,700&family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&family=Pixelify+Sans:wght@400..700&display=swap" rel="stylesheet">
</head>
<body>

<div class="restricted">acesso restrito</div>

<div class="container">
    <div class="left">
        <h1>Seja bem-vindo<br>ao Estudantes</h1>
        <hr>
        <p>
            A educação é a arma mais poderosa que você pode usar para mudar o mundo.
        </p>

        <a href="views/cadastro.jsp"><button>
            Não tem conta? Cadastrar-se
        </button></a>
    </div>

    <div class="login-box">
        <h2>Login</h2>

        <!-- Exemplo de mensagem de erro via JSP -->
        <%
            String erro = (String) request.getSession().getAttribute("erro");
            if (erro != null) {
        %>
        <p style="color:red;">usuário ou senha inválidos!</p>
        <%
            }
        %>

        <form action="servletLogin" method="post">
            <label for="matricula">Matrícula</label>
            <input type="text" name="matricula" id="matricula" required>

            <label for="senha">Senha</label>
            <input type="password" name="senha" id="senha" required>

            <button type="submit">Entrar</button>
        </form>
    </div>
</div>

</body>
</html>