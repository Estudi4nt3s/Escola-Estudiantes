<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Estudantes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inria+Serif:wght@300;400;700&family=Inter:wght@300;400;700&display=swap" rel="stylesheet">
</head>
<body>

    <a href="#" class="restricted">acesso restrito</a>

    <main>
        <form action="servletLogin" method="post">
            <input type="text" name="usuario">
            <input type="password" name="senha">
            <input type="submit">
        </form>
    </main>
</body>
</html>