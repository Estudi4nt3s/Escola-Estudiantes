<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Estudiantes - Trocar senha</title>
    <link rel="icon" href="${pageContext.request.contextPath}/utils/school.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/senha.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>
    <a class="voltar" href="${pageContext.request.contextPath}/index.jsp">
        <i class="material-icons">arrow_back</i>
        <span>Voltar</span>
    </a>
    <div class="container">
        <div class="login-box">
            <h2>Trocar senha</h2>

            <% if(request.getAttribute("msg") != null) { %>
            <p style="font-size: 13px; color:#ffd27a; text-align:center; margin-bottom: 10px;"><%= request.getAttribute("msg") %></p>
            <% } %>

            <form action="${pageContext.request.contextPath}/resetSenha" method="post">

                <input type="hidden" name="token" value="${token}">

                <label for="senha">Nova senha</label>
                <div class="input-wrapper">
                    <input type="password" id="senha" name="senha" placeholder="Digite sua nova senha" required>
                    <i class="material-icons" style="color: #ffd27a" onclick="alternarVisibilidade(event)">visibility</i>
                </div>

                <button type="submit" class="btn-primary" style="width: 100%;">Alterar senha</button>

            </form>


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
