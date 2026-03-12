<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.time.format.TextStyle" %>
<%@ page import="com.sistema.estudiantes.model.Aluno" %>
<%@ page import="com.sistema.estudiantes.model.Turma" %>
<%@ page import="com.sistema.estudiantes.model.Usuario" %>
<%@ page import="com.sistema.estudiantes.model.Professor" %>
<%
    String busca = "";
    if (request.getParameter("busca") != null) {
        busca = request.getParameter("busca");
    }
    LocalDate hoje = LocalDate.now();
    String dia = String.format("%02d", hoje.getDayOfMonth());
    String mes = String.format("%02d", hoje.getMonthValue());
    Locale ptBr = new Locale("pt", "BR");
    String semana = hoje.getDayOfWeek().getDisplayName(TextStyle.SHORT, ptBr).toUpperCase().substring(0, 3);
    Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
    List<Turma> turmas = (List<Turma>) request.getSession().getAttribute("turmas");
    Professor professor = (Professor) request.getSession().getAttribute("professor");
%>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Estudiantes - Alunos</title>
    <link rel="icon" href="${pageContext.request.contextPath}/utils/school.png">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/alunos.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/carregar.css">
</head>

<body>

<aside class="sidebar">
    <div class="logo">
        <i class="material-icons">school</i>
        <span>Colégio Estudiantes</span>
    </div>

    <nav>
        <a class="menu" href="${pageContext.request.contextPath}/views/home_p.jsp"><i class="material-icons">home</i>Início</a>
        <a class="menu" href="${pageContext.request.contextPath}/views/calendario.jsp"><i class="material-icons">calendar_month</i>Calendário</a>
        <a class="menu active" href="${pageContext.request.contextPath}/views/turmas.jsp">
            <i class="material-icons">groups</i>Turmas</a>
        <a class="menu" href="${pageContext.request.contextPath}/views/perfil_p.jsp"><i class="material-icons">person</i>Perfil</a>

    </nav>
    <div class="config">
        <a class="menu" style="margin-left: -25px; color: #590101" href="${pageContext.request.contextPath}/index.jsp">
            <i class="material-icons">output</i>Sair
        </a>
    </div>
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
        <div class="alunos-topo">
            <form action="aluno" method="get">
                <select class="alunos-titulo" name="id" onchange="this.form.submit()">
            <%
                Turma turmaSelecionada = (Turma) request.getAttribute("turmaSelecionada");
                for(Turma turma:turmas){
            %>
                    <option value="<%=turma.getId()%>" <%=turma.getId() == turmaSelecionada.getId()?"selected":""%>><%= turma.getNome() %></option>
                    <% } %>
                </select>
            </form>

            <form method="get" action="${pageContext.request.contextPath}/turma" class="barra-pesquisa">
                <i class="material-icons">search</i>
                <input type="text" name="busca" placeholder="Pesquise o aluno" value="<%= busca %>">
            </form>
        </div>

        <div class="alunos">
            <%
                List<Aluno> alunos = (List<Aluno>) request.getAttribute("alunos");
                if (alunos != null && !alunos.isEmpty()) {
                    for (Aluno aluno : alunos) {
            %>
            <div class="alunos-card">
                <div class="alunos-nome">
                    <%System.out.println(aluno);%>
                    <%= aluno.getNome()%>
                </div>

                <div class="acoes-container">
                    <i class="material-icons opcoes" onclick="alternarVisibilidade(event)">more_vert</i>

                    <div class="popup">
                        <a href="${pageContext.request.contextPath}/nota?sub_acao=buscar_por_id&id=<%= aluno.getMatricula() %>" class="popup-card"
                           onclick="document.getElementById('loadingOverlay').style.display='flex'">
                            <i class="material-icons popup-icones">edit_note</i>
                            <span>Notas</span>
                        </a>
                        <a href="${pageContext.request.contextPath}/observacao?sub_acao=buscar_por_id&id=<%= aluno.getMatricula() %>" class="popup-card"
                           onclick="document.getElementById('loadingOverlay').style.display='flex'">
                            <i class="material-icons popup-icones">assignment</i>
                            <span>Observações</span>
                        </a>
                    </div>
                </div>
            </div>
            <%
                }
            } else {
            %>
            <p>Nenhum aluno encontrado.</p>
            <% } %>
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
<script src="${pageContext.request.contextPath}/js/popup.js"></script>

<div id="loadingOverlay">
    <div class="loadingBox">
        <div class="spinner"></div>
        <p>Carregando...</p>
    </div>
</div>
</body>
</html>
