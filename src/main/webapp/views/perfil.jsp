<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="importa o aluno aqui cadu" %>

<%
    Aluno aluno = (Aluno) request.getAttribute("aluno");
%>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Colégio Estudiantes - Perfil</title>

    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="perfil.css">
</head>

<body>

<aside class="sidebar">
    <div class="logo">
        <i class="material-icons">school</i>
        <span>Colégio Estudiantes</span>
    </div>

    <nav>
        <a class="menu" href="inicio.jsp"><i class="material-icons">home</i>Início</a>
        <a class="menu"><i class="material-icons">menu_book</i>Minhas Disciplinas</a>
        <a class="menu"><i class="material-icons">calendar_month</i>Calendário</a>
        <a class="menu active"><i class="material-icons">person</i>Perfil</a>
    </nav>

    <div class="config">
        <i class="material-icons">settings</i>Configurações
    </div>
</aside>

<main class="main">

    <header class="topbar">
        <div class="date">
            <i class="material-icons">calendar_today</i>
            <%= java.time.LocalDate.now() %>
        </div>

        <div class="user">
            <i class="material-icons" id="openNotification">notifications</i>
            <div class="avatar">
                <img src="<%= aluno.getFotoUrl() %>">
                <span><%= aluno.getNome() %></span>
            </div>
        </div>
    </header>

    <div class="perfil-page">

        <div class="perfil-header-card">
            <div class="perfil-foto">
                <img src="<%= aluno.getFotoUrl() %>" alt="Aluno">
            </div>

            <div class="perfil-info-principal">
                <h2><%= aluno.getNome() %></h2>
                <p><%= aluno.getSerie() %> • Ensino Médio</p>

                <div class="perfil-status ativo">
                    ● Aluno Ativo
                </div>
            </div>
        </div>

        <div class="perfil-detalhes">

            <div class="perfil-card-info">
                <h3>Informações Pessoais</h3>
                <div class="linha"><span>RA:</span> <%= aluno.getRa() %></div>
                <div class="linha"><span>Email:</span> <%= aluno.getEmail() %></div>
                <div class="linha"><span>Telefone:</span> <%= aluno.getTelefone() %></div>
            </div>

            <div class="perfil-card-info">
                <h3>Desempenho</h3>
                <div class="linha"><span>Média Geral:</span> <%= aluno.getMedia() %></div>
                <div class="linha"><span>Frequência:</span> <%= aluno.getFrequencia() %>%</div>
                <div class="linha"><span>Turma:</span> <%= aluno.getTurma() %></div>
            </div>

            <div class="perfil-card-info">
                <h3>Melhores Notas</h3>
                <div class="tags">
                    <span>Matemática - Excelente Raciocínio Lógico</span>
                    <span>Português - Boa Interpretação</span>
                    <span>História - Participativo</span>
                    <span>Alta Performance em Avaliações</span>
                </div>
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

<script src="notficacoes.js"></script>
</body>
</html>