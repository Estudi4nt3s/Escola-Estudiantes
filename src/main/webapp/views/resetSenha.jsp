<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/resetSenha" method="post">

        <input type="hidden" name="token" value="${token}">

        <label>Nova senha</label>
        <input type="password" name="senha" required>

        <button type="submit">Alterar senha</button>

    </form>

    <% if(request.getAttribute("msg") != null) { %>
    <p><%= request.getAttribute("msg") %></p>
    <% } %>
</body>
</html>
