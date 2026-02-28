<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Colégio Estudiantes - Minhas Disciplinas</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="css/disciplinas_p.css">
</head>

<body>

    <aside class="sidebar">
        <div class="logo">
            <i class="material-icons">school</i>
            <span>Colégio Estudiantes</span>
        </div>

        <nav>
            <a class="menu" href="home_p.jsp"><i class="material-icons">home</i>Início</a>
            <a class="menu active" href="disciplinas_p.jsp"> <i class="material-icons">menu_book</i>Disciplinas</a>
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
                ${dataAtualFormatada}
            </div>

            <div class="user">
                <div class="avatar">
                    <img src="${professor.fotoUrl}" alt="avatar">
                    <span>${professor.nome}</span>
                </div>
            </div>
        </header>

        <section class="dashboard disciplinas">

            <h2 class="page-title">Minhas Disciplinas</h2>

            <div class="disciplinas-grid">

                <%-- Loop para renderizar cada disciplina vinda do banco de dados --%>
                <c:forEach var="disc" items="${listaDisciplinas}" varStatus="status">
                    <%-- O status.count gera 1, 2, 3... para manter as cores card1, card2... --%>
                    <div class="disciplina card${(status.index % 6) + 1}">
                        <div class="disciplina-info">
                            <h3>${disc.nome}</h3>
                            <p>Prof. ${disc.nomeProfessor}</p>
                        </div>
                        <%-- O nome da imagem pode ser dinâmico (ex: Matematica.png, Portugues.png) --%>
                        <img src="img/${disc.imagemUrl}" alt="${disc.nome}">
                    </div>
                </c:forEach>

                <c:if test="${empty listaDisciplinas}">
                    <p>Nenhuma disciplina vinculada ao seu perfil.</p>
                </c:if>

            </div>

        </section>

    </main>
</body>

</html>