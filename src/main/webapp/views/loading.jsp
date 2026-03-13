<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Carregando...</title>
    <link rel="icon" href="${pageContext.request.contextPath}/utils/school.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/loading.css">

</head>

<script>
    window.onload = function(){
        setTimeout(function(){
            document.getElementById("formLogin").submit();
        }, 1200); // tempo para mostrar o loading
    }
</script>

<body>

<div class="container">

    <div class="card">

        <h2>Entrando no sistema...</h2>

        <div class="spinner"></div>

        <p>Aguarde enquanto verificamos suas credenciais</p>

    </div>

</div>

<!-- FORM QUE ENVIA POST PARA O SERVLET -->
<form id="formLogin"
      action="${pageContext.request.contextPath}/servletLogin"
      method="post">

    <input type="hidden" name="usuario" value="${param.usuario}">
    <input type="hidden" name="senha" value="${param.senha}">

</form>

</body>
</html>