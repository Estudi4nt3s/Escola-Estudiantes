<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sistema.estudiantes.model.Turma" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.time.format.TextStyle" %>
<%@ page import="com.sistema.estudiantes.model.Professor" %>
<%
    String busca = "";
    if (request.getParameter("busca") != null) {
        busca = request.getParameter("busca");
    }
    @SuppressWarnings("unchecked")
    List<Turma> turmas = (List<Turma>) request.getSession().getAttribute("turmas");
    Professor professor = (Professor) request.getSession().getAttribute("professor");
    LocalDate hoje = LocalDate.now();
    String dia = String.format("%02d",hoje.getDayOfMonth());
    String mes = String.format("%02d",hoje.getMonthValue());
    Locale ptBr = new Locale("pt", "BR");
    String semana = hoje.getDayOfWeek().getDisplayName(TextStyle.SHORT, ptBr).toUpperCase().substring(0, 3);
%>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Colégio Estudiantes - Início</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/turmas.css">
</head>

<body>

<aside class="sidebar">
    <div class="logo">
        <i class="material-icons">school</i>
        <span>Colégio Estudiantes</span>
    </div>

    <nav>
        <a class="menu active" href="home_p.jsp"><i class="material-icons">home</i>Início</a>
        <a class="menu" href="${pageContext.request.contextPath}/views/calendario.jsp"><i class="material-icons">calendar_month</i>Calendário</a>
        <a class="menu" href="${pageContext.request.contextPath}/views/perfil_p.jsp"><i class="material-icons">person</i>Perfil</a>
        <a class="menu">
            <i class="material-icons">calendar_month</i>Turmas</a>
    </nav>
</aside>

<main class="main">

    <header class="topbar">
        <div class="date">
            <i class="material-icons">calendar_today</i>
            <%=semana.toUpperCase().charAt(0) + semana.toLowerCase().substring(1) + ", " + dia + "/" + mes%>
        </div>

        <div class="user">
            <i class="material-icons" id="openNotification">notifications</i>
            <div class="avatar">
                <img src="${pageContext.request.contextPath}/utils/perfil.png" alt="">
                <span><%=professor.getNome()%></span>
            </div>
        </div>
    </header>

    <div class="main-content">
        <div class="turmas-topo">
            <div class="turmas-titulo">
                Turmas
            </div>

            <form method="get" action="${pageContext.request.contextPath}/turma" class="barra-pesquisa">
                <i class="material-icons">search</i>
                <input type="text" name="busca" placeholder="Pesquise a turma" value="<%= busca %>">
            </form>
        </div>

        <div class="turmas">
            <%
                if (turmas != null && !turmas.isEmpty()) {
                    for (Turma turma : turmas) {
            %>
            <div class="turmas-card">
                <h3><%= turma.getSerie() + " " + turma.getLetra() %></h3>
                <a href="${pageContext.request.contextPath}/aluno?id=<%= turma.getId() %>">
                    <i class="material-icons">arrow_forward</i>
                </a>
            </div>
            <%
                }
            } else {
            %>
            <p>Nenhuma turma encontrada.</p>
            <%
                }
            %>
        </div>
    </div>
</main>
<!-- Overlay -->
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

            <li class="denied">
                <div class="notification-content">
                    <strong>Avaliação 2ª Guerra</strong>
                    <span>Status: Negado</span>
                </div>

                <button class="confirm-btn">Confirmar</button>
            </li>


            <li class="denied">
                <div class="notification-content">
                    <strong>Trabalho de Geografia</strong>
                    <span>Status: Negado</span>
                </div>

                <button class="confirm-btn">Confirmar</button>
            </li>
        </ul>

    </div>
</div>

<script src="${pageContext.request.contextPath}/js/notificacoes.js"></script>
</body>

</html>