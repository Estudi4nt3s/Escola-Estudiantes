<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Estudantes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/login.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inria+Serif:wght@300;400;700&family=Inter:wght@300;400;700&display=swap" rel="stylesheet">
</head>
<body>

    <a href="#" class="restricted">acesso restrito</a>

    <main>
        <div class="div-overlay">
            <div class="overlay"></div>
            <img src="${pageContext.request.contextPath}/3b9acabc-be87-4b60-af72-8bc163abe1d6.png" alt="Estudantes">
        </div>

        <div class="container">
            <div class="left">
                <h1>Seja bem-vindo ao Estudantes</h1>
                <div class="divider"></div>
                <p>A educação é a arma mais poderosa que você pode usar para mudar o mundo.</p>
                
                <div class="signup-section">
                    <span>Não tem conta?</span><br>
                    <button type="button" class="btn-signup" onclick="location.href='cadastro.jsp'">Cadastrar-se</button>
                </div>
            </div>

            <div class="login-box">
                <h2>Login</h2>
                <form action="LoginServlet" method="POST">
                    <div class="input-group">
                        <label for="matricula">Matrícula</label>
                        <input type="text" id="matricula" name="txtMatricula" required>
                    </div>

                    <div class="input-group">
                        <label for="senha">Senha</label>
                        <input type="password" id="senha" name="txtSenha" required>
                    </div>

                    <%-- Exemplo de mensagem de erro dinâmica --%>
                    <% if(request.getAttribute("errorMessage") != null) { %>
                        <p style="color: red; font-size: 0.8em;"><%= request.getAttribute("errorMessage") %></p>
                    <% } %>

                    <button type="submit" class="btn-enter">Entrar</button>
                </form>
            </div>
        </div>
    </main>
</body>
</html>