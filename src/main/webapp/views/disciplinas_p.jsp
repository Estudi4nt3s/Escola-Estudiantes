<%@ page import="com.sistema.estudiantes.model.Professor" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Estudiantes - Minhas Disciplinas</title>
    <link rel="icon" href="${pageContext.request.contextPath}/utils/school.png">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/disciplinas_p.css">
</head>
<%
    String[] data = (String[]) request.getSession().getAttribute("data");
    Professor professor = (Professor) request.getSession().getAttribute("professor");

%>
<body>

    <aside class="sidebar">
        <div class="logo">
            <i class="material-icons">school</i>
            <span>Colégio Estudiantes</span>
        </div>

        <nav>
            <a class="menu" href="home_p.jsp"><i class="material-icons">home</i>Início</a>
            <a class="menu active"> <i class="material-icons">menu_book</i>Disciplinas</a>
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
                <%=data[2].toUpperCase().charAt(0) + data[2].toLowerCase().substring(1) + ", " + data[0] + "/" + data[1]%>
            </div>

            <div class="user">
                <div class="avatar">
                    <img src="perfil.jsp" alt="avatar">
                    <span><%=professor.getNome()%></span>
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