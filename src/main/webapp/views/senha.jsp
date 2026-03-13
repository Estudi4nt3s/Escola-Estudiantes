<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Estudiantes - Recuperação de Senha</title>
    <link rel="icon" href="${pageContext.request.contextPath}/utils/school.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/senha.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;700;800&family=Inria+Serif:ital,wght@0,400;0,700;1,400&display=swap" rel="stylesheet">
</head>
<body>
<a class="voltar" href="${pageContext.request.contextPath}/index.jsp">
    <i class="material-icons">arrow_back</i>
    <span>Voltar</span>
</a>
<div class="container">

    <div class="login-box">
        <h2>Recuperação de Senha</h2>

        <% if (request.getAttribute("msg") != null) { %>
        <p style="color:#ffb347; margin-bottom: 15px; font-size: 14px; text-align: center;">
            <%= request.getAttribute("msg") %>
        </p>
        <% } %>

        <form action="${pageContext.request.contextPath}/email" method="post">

            <input type="hidden" name="sub_acao" value="procurar">
            
            <label for="email">Email</label>
            <input type="email" name="email" id="email" required placeholder="Digite seu email">

            <button type="submit" class="btn-primary" style="width: 100%;">Confirmar</button>
        </form>
    </div>
</div>

<script>
    history.pushState(null, null, location.href);
    window.onpopstate = function () {
        history.go(1);
    };
</script>
</body>
</html>