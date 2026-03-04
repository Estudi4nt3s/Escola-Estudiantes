<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Colégio Estudiantes - Gestão de Notas</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="css/aluno_p.css">
</head>

<body>

    <%-- Sidebar e Header permanecem consistentes --%>
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
            <div class="date"><i class="material-icons">calendar_today</i> ${dataAtualFormatada}</div>
            <div class="user">
                <div class="avatar">
                    <img src="${professor.fotoUrl}" alt="Avatar">
                    <span>${professor.nome}</span>
                </div>
            </div>
        </header>

        <div class="main-content">
            <%-- Início do Formulário Global para salvar todas as notas de uma vez --%>
            <form action="SalvarNotasServlet" method="POST">
                <input type="hidden" name="alunoId" value="${aluno.id}">

                <div class="page-header">
                    <div class="header-info">
                        <h2 class="page-title">Gestão de Notas: ${aluno.nome}</h2>
                        <p>Turma: ${aluno.turmaNome}</p>
                    </div>
                    <button type="submit" class="btn-save-all">
                        <i class="material-icons">save</i> Salvar Tudo
                    </button>
                </div>

                <div class="table-container">
                    <table class="grades-table teacher-view">
                        <thead>
                            <tr>
                                <th>Disciplina</th>
                                <th>N1</th>
                                <th>N2</th>
                                <th>Média</th>
                                <th>Ação</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="nota" items="${aluno.notas}">
                                <tr>
                                    <td><strong>${nota.disciplinaNome}</strong></td>
                                    <td>
                                        <input type="number" step="0.1" min="0" max="10" 
                                               name="n1_${nota.disciplinaId}" 
                                               value="${nota.n1}" class="grade-input">
                                    </td>
                                    <td>
                                        <input type="number" step="0.1" min="0" max="10" 
                                               name="n2_${nota.disciplinaId}" 
                                               value="${nota.n2}" class="grade-input">
                                    </td>
                                    <td class="final-grade">${nota.media}</td>
                                    <td>
                                        <i class="material-icons" style="color: #666; cursor: pointer;">sync</i>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </form>

            <%-- Seção de Observações Existentes --%>
            <div class="observations-section">
                <div class="obs-header">
                    <i class="material-icons">assignment</i>
                    <h3>Histórico de Observações</h3>
                </div>
                <div class="obs-content">
                    <c:forEach var="obs" items="${aluno.observacoes}" varStatus="status">
                        <p><strong>Prof. ${obs.professorNome} (${obs.materia}):</strong> "${obs.texto}"</p>
                        <c:if test="${!status.last}"><hr></c:if>
                    </c:forEach>
                </div>
            </div>

            <%-- Formulário para Nova Observação --%>
            <div class="observations-section" id="obs_sec">
                <div class="obs-header">
                    <i class="material-icons">send</i>
                    <h3>Enviar Nova Observação</h3>
                </div>
                <form action="EnviarObservacaoServlet" method="POST" class="obs-form">
                    <input type="hidden" name="alunoId" value="${aluno.id}">
                    <textarea name="comentario" placeholder="Digite aqui o feedback individual para ${aluno.nome}..." class="obs-textarea" required></textarea>
                    <div class="form-footer">
                        <span class="tip">O aluno receberá uma notificação assim que você enviar.</span>
                        <button type="submit" class="btn-send">Enviar Comentário</button>
                    </div>
                </form>
            </div>
        </div>
    </main>

    <script src="js/notificacoes.js"></script>
</body>
</html>