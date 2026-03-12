<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Estudiantes - Cadastro</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cadastro.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/carregar.css">
    <link rel="icon" href="${pageContext.request.contextPath}/utils/school.png">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inria+Serif:ital,wght@0,300;0,400;0,700;1,300;1,400;1,700&family=Pixelify+Sans:wght@400..700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inria+Serif:ital,wght@0,300;0,400;0,700;1,300;1,400;1,700&family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&family=Pixelify+Sans:wght@400..700&display=swap" rel="stylesheet">
</head>
<body>
<a class="voltar" href="${pageContext.request.contextPath}/index.jsp">
    <i class="material-icons">arrow_back</i>
    <span>Voltar</span>
</a>
<div class="container">
    <div class="left">
        <h1>Seja bem-vindo<br>ao Estudiantes</h1>
        <hr>
        <p>
            A educação é a arma mais poderosa que você pode usar para mudar o mundo.
        </p>
    </div>

    <div class="login-box">
        <h2>Cadastro</h2>

        <!-- Mensagem dinâmica opcional -->
        <%
            String mensagem = (String) request.getSession().getAttribute("mensagem");
            if (mensagem != null) {
        %>
        <p style="color: #ffc66d;"><%= mensagem %></p>
        <%
            }
        %>

        <form action="${pageContext.request.contextPath}/servletCadastro" method="post"
              onsubmit="document.getElementById('loadingOverlay').style.display='flex'">

            <label for="nome">Nome</label>
            <input type="text" name="nome" id="nome" placeholder="Digite seu nome" required>

            <label for="sobrenome">Sobrenome</label>
            <input type="text" name="sobrenome" id="sobrenome" placeholder="Digite seu sobrenome" required>

            <label for="matricula">Matrícula</label>
            <input type="number" name="matricula" id="matricula" placeholder="Digite sua matrícula" required>

            <label>CPF:</label>
            <input type="text" name="cpf" placeholder="000.000.000-00">

            <label for="email">E-mail</label>
            <input type="email" name="email" id="email" placeholder="Digite seu email" required>

            <label for="senha">Senha</label>
            <div class="input-wrapper">
                <input type="password" name="senha" id="senha" required placeholder="Digite sua senha">
                <i class="material-icons" style="color: #ffd27a" onclick="alternarVisibilidade(event)">visibility</i>
            </div>

            <button type="submit" id="btnNotas">Cadastrar</button>

        </form>
    </div>
</div>
<div id="loadingOverlay">
    <div class="loadingBox">
        <div class="spinner"></div>
        <p>Carregando...</p>
    </div>
</div>

<script>
    function alternarVisibilidade(event) {
        const icone = event.target;
        const input = icone.closest('.input-wrapper').querySelector('input');

        if (input.type === "password") {
            input.type = "text";
            icone.textContent = "visibility_off";
        } else {
            input.type = "password";
            icone.textContent = "visibility";
        }
    }
</script>
</body>
</html>