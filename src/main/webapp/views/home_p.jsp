<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Colégio Estudiantes - Início</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="css/home_p.css">
</head>

<body>

    <aside class="sidebar">
        <div class="logo">
            <i class="material-icons">school</i>
            <span>Colégio Estudiantes</span>
        </div>

        <nav>
            <a class="menu active" href="home_p.jsp"><i class="material-icons">home</i>Início</a>
            <a class="menu" href="disciplinas_p.jsp"> <i class="material-icons">menu_book</i>Disciplinas</a>
            <a class="menu"><i class="material-icons">calendar_month</i>Calendário</a>
            <a class="menu" href="turmas_p.jsp"><i class="material-icons">calendar_today</i>Turmas</a>
        </nav>

        <div class="config">
            <a class="menu" style="margin-left: -25px;" href="perfil_p.jsp">
                <i class="material-icons">person</i>Perfil
            </a>
        </div>
    </aside>

    <main class="main">

        <header class="topbar">
            <div class="date">
                <i class="material-icons">calendar_today</i>
                ${dataAtualExtenso}
            </div>

            <div class="user">
                <div class="avatar">
                    <img src="${professor.fotoUrl}" alt="avatar">
                    <span>${professor.nome}</span>
                </div>
            </div>
        </header>

        <section class="dashboard">

            <div class="left">

                <div class="welcome">
                    <%-- Saudação personalizada com o primeiro nome --%>
                    <h2>Olá, ${professor.primeiroNome}!</h2>
                    <p>Pronto para as aulas de hoje?</p>
                </div>

                <div class="flex">
                    <%-- Loop para gerar os cards de conteúdo das aulas --%>
                    <c:forEach var="aula" items="${aulasDoDia}" varStatus="status">
                        <div class="card card${status.count}">
                            <h3>${aula.turma}</h3>
                            <p>${aula.tema}</p>
                            <img src="img/${aula.disciplinaIcone}.png" alt="${aula.disciplina}">
                        </div>
                    </c:forEach>
                    
                    <c:if test="${empty aulasDoDia}">
                        <p>Você não possui aulas agendadas para hoje.</p>
                    </c:if>
                </div>

            </div>

            <div class="content-today">
                <h2>Aulas de Hoje</h2>
                <ul>
                    <%-- Loop para a lista lateral de horários --%>
                    <c:forEach var="aula" items="${aulasDoDia}">
                        <li>
                            <strong>${aula.turma}</strong> - ${aula.horarioInicio} às ${aula.horarioFim}
                        </li>
                    </c:forEach>
                </ul>
            </div>

        </section>

    </main>

    <script src="js/notificacoes.js"></script>
</body>
</html>