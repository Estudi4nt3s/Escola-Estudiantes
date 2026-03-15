<%@ page import="com.sistema.estudiantes.model.Admin" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    session = request.getSession(false);
    if(session != null) {
        session.invalidate();
    }
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Estudantes</title>
    <link rel="icon" href="${pageContext.request.contextPath}/utils/school.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;700;800&family=Inria+Serif:ital,wght@0,400;0,700;1,400&display=swap" rel="stylesheet">
</head>
<body>

<div class="secret-trigger" onclick="abrirAdmin()"></div>

<div id="modalAdmin" class="modal-overlay">
    <div class="modal-content">
    <h3>Área Administrativa</h3>
        <form action="${pageContext.request.contextPath}/servletLoginAdmin" method="post">
            <p style="font-size: 14px; opacity: 0.7;">Identifique-se para continuar</p>

            <input type="text" name="usuario" id="usuario" placeholder="Usuário" required>
            <div class="input-wrapper">
                <input type="password" name="senha" placeholder="Senha" required>
                <i class="material-icons" style="color: #ffd27a"
                   onclick="alternarVisibilidade(event)">visibility</i>
            </div>

            <button type="submit" class="btn-primary">Entrar</button>
            <button type="button" class="btn-primary" onclick="fecharAdmin()">Cancelar</button>

            <p onclick="fecharAdmin()"
               style="margin-top: 15px; cursor: pointer; font-size: 12px; opacity: 0.5;">
                Voltar ao login
            </p>
        </form>
    </div>
</div>

<div class="container">
    <div class="left">
        <h1>Seja bem-vindo<br>ao Estudiantes</h1>
        <hr>
        <p>A educação é a arma mais poderosa que você pode usar para mudar o mundo.</p>
        <a href="${pageContext.request.contextPath}/views/cadastro.jsp" class="btn-primary">Não tem conta? Cadastrar-se</a>
    </div>

    <div class="login-box">
        <h2>Login</h2>

        <% if (request.getSession().getAttribute("erro") != null) { %>
        <p style="color:#ffb347; margin-bottom: 15px; font-size: 14px; text-align: center;">Credenciais inválidas!</p>
        <% } %>

        <form action="${pageContext.request.contextPath}/views/loading.jsp" method="post">
            <label for="usuario">Usuário</label>
            <input type="text" name="usuario" id="usuario" required placeholder="Digite seu usuário/email">

            <label for="senha">Senha</label>
            <div class="input-wrapper">
                <input type="password" name="senha" id="senha" required placeholder="Digite sua senha">
                <i class="material-icons" style="color: #ffd27a" onclick="alternarVisibilidade(event)">visibility</i>
            </div>
            <button type="submit" class="btn-primary" style="width: 100%;">Entrar</button>
        </form>
    </div>
</div>

<script>
    const modal = document.getElementById("modalAdmin");

    function abrirAdmin() {
        modal.classList.add("active");
    }

    function fecharAdmin() {
        modal.classList.remove("active");
    }

    window.onclick = function(event) {
        if (event.target == modal) {
            fecharAdmin();
        }
    }


    history.pushState(null, null, location.href);
    window.onpopstate = function () {
        history.go(1);
    };

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