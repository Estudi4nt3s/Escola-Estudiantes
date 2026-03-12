<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.sistema.estudiantes.model.Aluno" %>
<%@ page import="com.sistema.estudiantes.model.Usuario" %>
<%@ page import="com.sistema.estudiantes.model.Turma" %>
<%@ page import="com.sistema.estudiantes.model.Usuario" %>

<%
    Aluno aluno = (Aluno) request.getSession().getAttribute("aluno");
    Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
    Turma turma = (Turma) request.getSession().getAttribute("turma");
    String[] data = (String[]) request.getSession().getAttribute("data");
%>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Colégio Estudiantes - Perfil</title>
    <link rel="icon" href="${pageContext.request.contextPath}/utils/school.png">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/perfil.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/carregar.css">
</head>

<body>

<aside class="sidebar">
    <div class="logo">
        <i class="material-icons">school</i>
        <span>Colégio Estudiantes</span>
    </div>

    <nav>
        <a class="menu" href="${pageContext.request.contextPath}/views/home.jsp"><i class="material-icons">home</i>Início</a>
        <a class="menu" href="${pageContext.request.contextPath}/views/disciplinas.jsp"><i class="material-icons">menu_book</i>Minhas Disciplinas</a>
        <a class="menu" href="${pageContext.request.contextPath}/nota?sub_acao=buscar_por_id&id=<%=aluno.getMatricula()%>"> <i class="material-icons">grading</i>Notas</a>
        <a class="menu active"><i class="material-icons">person</i>Perfil</a>
    </nav>
    <div class="config">
        <a class="menu" style="color: #590101" href="${pageContext.request.contextPath}/index.jsp">
            <i class="material-icons">output</i>Sair
        </a>
    </div>
</aside>

<main class="main">

    <header class="topbar">
        <div class="date">
            <i class="material-icons">calendar_today</i>
            <%=data[2].toUpperCase().charAt(0) + data[2].toLowerCase().substring(1) + ", " + data[0] + "/" + data[1]%>
        </div>

        <div class="user">
            <i class="material-icons" id="openNotification">notifications</i>
            <div class="avatar">
                <img src="${pageContext.request.contextPath}/utils/perfil.png" alt="">
                <span><%= aluno.getNome() %></span>
            </div>
        </div>
    </header>

    <div class="perfil-page">

        <div class="perfil-header-card">
            <div class="perfil-foto">
                <img src="${pageContext.request.contextPath}/utils/perfil.png" alt="Aluno">
            </div>

            <div class="perfil-info-principal">
                <h2><%= aluno.getNome() %></h2>
                <p> <%=turma.getNome() %></p>

                <div class="perfil-status ativo">
                    ● Aluno Ativo
                </div>
            </div>
        </div>

        <div class="perfil-detalhes">

            <div class="perfil-card-info">
                <h3>Informações Pessoais</h3>
                <div class="linha"><span>RA:</span> <%= aluno.getMatricula() %></div>
                <div class="linha"><span>Email:</span> <%=usuario.getEmail()%></div>
                <div class="linha"><span>Telefone:</span> <%= aluno.getTelefonePai() %></div>
                <div class="linha"><span>Turma:</span> <%=turma.getNome() %></div>

            </div>
        </div>

    </div>

</main>

<div class="notification-overlay" id="notificationOverlay">
    <div class="notification-modal">

        <div class="notification-modal-header">
            <h2>Notificações</h2>
            <button id="closeNotificationModal">✕</button>
        </div>

        <ul class="notification-modal-list">
            <li class="denied">
                <div class="notification-content">
                    <strong>Avaliação de Matemática</strong>
                    <span>Status: Negado</span>
                </div>
                <button class="confirm-btn">Confirmar</button>
            </li>
        </ul>

    </div>
</div>

<script src="${pageContext.request.contextPath}/js/notificacoes.js"></script>
<div id="loadingOverlay">
    <div class="loadingBox">
        <div class="spinner"></div>

        <p>Carregando...</p>
    </div>
</div>
<script>

    const btnNotas = document.getElementById("btnNotas");
    const loading = document.getElementById("loadingOverlay");

    btnNotas.addEventListener("click", function(e){

        e.preventDefault(); // impede abrir imediatamente

        loading.style.display = "flex";

        setTimeout(()=>{
            window.location.href = this.href;
        },500);

    });

</script>
</body>
</html>