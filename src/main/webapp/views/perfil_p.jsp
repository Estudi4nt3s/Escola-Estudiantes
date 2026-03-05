<%@ page import="com.sistema.estudiantes.model.Professor" %>
<%@ page import="com.sistema.estudiantes.model.Disciplina" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">

<%
    Professor professor = (Professor) request.getSession().getAttribute("professor");
    String[] data = (String[]) request.getSession().getAttribute("data");
    Disciplina materia = (Disciplina) request.getSession().getAttribute("disciplina");
%>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Estudiantes - Perfil de <%=professor.getNome()%></title>
    <link rel="icon" href="${pageContext.request.contextPath}/utils/school.png">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/perfil_p.css">
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
            <a class="menu" href="turmas_p.jsp"><i class="material-icons">calendar_today</i>Turmas</a>
        </nav>
    </aside>

    <main class="main">

        <header class="topbar">
            <div class="date">
                <i class="material-icons">calendar_today</i>
                <%=data[2].toUpperCase().charAt(0) + data[2].toLowerCase().substring(1) + ", " + data[0] + "/" + data[1]%>
            </div>

            <div class="user">
                <div class="avatar">
                    <img src="${pageContext.request.contextPath}/utils/perfil.png" alt="Avatar">
                    <span><%=professor.getNome()%></span>
                </div>
            </div>
        </header>

        <div class="perfil-page">

            <div class="perfil-header-card">
                <div class="perfil-foto">
                    <img src="${pageContext.request.contextPath}/utils/perfil.png" alt="Professor">
                </div>
        
                <div class="perfil-info-principal">
                    <h2><%=professor.getNome()%></h2>
                    <p>Professor de <%=materia.getNome()%></p>
        
                    <div class="perfil-status ativo">
                        ● Professor Ativo | ID: #<%=professor.getId()%>
                    </div>
                </div>
                <button class="btn-edit-perfil" onclick="location.href='editar_perfil.jsp'">
                    <i class="material-icons" style="font-size: 18px; vertical-align: middle;">edit</i> Editar Perfil
                </button>
            </div>
        
            <div class="perfil-detalhes">
        
                <div class="perfil-card-info">
                    <h3>Informações Institucionais</h3>
                    <div class="linha"><span>Matrícula:</span> <%=professor.getId()%></div>
                </div>
            </div>
        </div>

    </main>
</body>
</html>