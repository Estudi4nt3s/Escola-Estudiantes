<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Colégio Estudiantes - Boletim de ${aluno.nome}</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="css/aluno_p.css">
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
            <div class="page-header">
                <h2 class="page-title">Boletim Escolar: ${aluno.nome}</h2>
            </div>

            <div class="table-container">
                <table class="grades-table">
                    <thead>
                        <tr>
                            <th>Disciplina</th>
                            <th>N1</th>
                            <th>N2</th>
                            <th>Média</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="nota" items="${aluno.notas}">
                            <tr>
                                <td><strong>${nota.disciplinaNome}</strong></td>
                                <td>${nota.n1}</td>
                                <td>${nota.n2}</td>
                                <td>${nota.media}</td>
                                <td>
                                    <%-- Lógica de cores baseada no status --%>
                                    <c:choose>
                                        <c:when test="${nota.media >= 6.0}">
                                            <span class="status approved">Aprovado</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="status attention">Recuperação</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="observations-section">
                <div class="obs-header">
                    <i class="material-icons">assignment</i>
                    <h3>Observações do Professor</h3>
                </div>
                <div class="obs-content">
                    <c:forEach var="obs" items="${aluno.observacoes}" varStatus="status">
                        <p><strong>Prof. ${obs.professorNome} (${obs.materia}):</strong> "${obs.texto}"</p>
                        <c:if test="${!status.last}">
                            <hr>
                        </c:if>
                    </c:forEach>
                    
                    <c:if test="${empty aluno.observacoes}">
                        <p>Nenhuma observação registrada para este aluno.</p>
                    </c:if>
                </div>
            </div>
        </div>
    </main>

    <script src="js/notificacoes.js"></script>
</body>
</html>