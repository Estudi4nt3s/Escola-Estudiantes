<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Colégio Estudiantes - Turmas</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="css/turmas_p.css">
</head>

<body>

    <aside class="sidebar">
        <div class="logo">
            <i class="material-icons">school</i>
            <span>Colégio Estudiantes</span>
        </div>

        <nav>
            <a class="menu" href="home_p.jsp"><i class="material-icons">home</i>Início</a>
            <a class="menu" href="disciplinas_p.jsp"> <i class="material-icons">menu_book</i>Disciplinas</a>
            <a class="menu"><i class="material-icons">calendar_month</i>Calendário</a>
            <a class="menu active" href="turmas_p.jsp"><i class="material-icons">calendar_today</i>Turmas</a>
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
                ${dataAtualFormatada}
            </div>

            <div class="user">
                <div class="avatar">
                    <img src="${professor.fotoUrl}" alt="Avatar">
                    <span>${professor.nome}</span>
                </div>
            </div>
        </header>

        <div class="main-content">
            <div class="turmas-topo">
                <div class="turmas-titulo">
                    Turmas
                </div>

                <form action="BuscarTurmaServlet" method="GET" class="barra-pesquisa">
                    <i class="material-icons">search</i>
                    <input type="text" name="busca" placeholder="Pesquise a turma">
                </form>
            </div>

            <div class="turmas">
                <%-- O loop abaixo substitui a repetição manual de cards --%>
                <c:forEach var="turma" items="${listaTurmas}">
                    <div class="turmas-card">
                        <h3>${turma.nome}</h3>
                        <%-- Passamos o ID da turma via parâmetro na URL para a página de alunos --%>
                        <a href="AlunosServlet?idTurma=${turma.id}">
                            <i class="material-icons">arrow_forward</i>
                        </a>
                    </div>
                </c:forEach>

                <%-- Caso a lista esteja vazia --%>
                <c:if test="${empty listaTurmas}">
                    <p>Nenhuma turma encontrada para este professor.</p>
                </c:if>
            </div>
        </div>
    </main>
</body>
</html>