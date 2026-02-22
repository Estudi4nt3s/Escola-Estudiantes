<%--
  Created by IntelliJ IDEA.
  User: gustavosousa-ieg
  Date: 05/02/2026
  Time: 08:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Colégio Estudiantes - Início</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="disciplinasStyle.css">
</head>

<body>

<aside class="sidebar">
    <div class="logo">
        <i class="material-icons">school</i>
        <span>Colégio Estudiantes</span>
    </div>

    <nav>
        <a class="menu active"><i class="material-icons">home</i>Início</a>
        <a class="menu"><i class="material-icons">menu_book</i>Minhas Disciplinas</a>
        <a class="menu"><i class="material-icons">calendar_month</i>Calendário</a>
        <a class="menu"><i class="material-icons">person</i>Perfil</a>
    </nav>

    <div class="config">
        <i class="material-icons">settings</i>Configurações
    </div>
</aside>

<main class="main">

    <header class="topbar">
        <div class="date">
            <i class="material-icons">calendar_today</i>
            Seg, 09/02
        </div>

        <div class="user">
            <i class="material-icons" id="openNotification">notifications</i>
            <div class="avatar">
                <img src="https://i.pravatar.cc/40?img=12">
                <span>Mateus Carlos</span>
            </div>
        </div>
    </header>

    <section class="dashboard disciplinas">

        <h2 class="page-title">Minhas Disciplinas</h2>

        <div class="disciplinas-grid">

            <div class="disciplina card1">
                <div class="disciplina-info">
                    <h3>Matemática</h3>
                    <p>Prof. Valdislei</p>
                </div>
                <img src="Matematica.png" alt="">
            </div>

            <div class="disciplina card2">
                <div class="disciplina-info">
                    <h3>Português</h3>
                    <p>Prof. Cláudia</p>
                </div>
                <img src="Matematica.png" alt="">
            </div>

            <div class="disciplina card3">
                <div class="disciplina-info">
                    <h3>Geografia</h3>
                    <p>Prof. Flávio</p>
                </div>
                <img src="Matematica.png" alt="">
            </div>

            <div class="disciplina card4">
                <div class="disciplina-info">
                    <h3>História</h3>
                    <p>Prof. Rosangela</p>
                </div>
                <img src="Matematica.png" alt="">
            </div>

            <div class="disciplina card5">
                <div class="disciplina-info">
                    <h3>Inglês</h3>
                    <p>Prof. Erika</p>
                </div>
                <img src="Matematica.png" alt="">
            </div>

            <div class="disciplina card6">
                <div class="disciplina-info">
                    <h3>Ciências</h3>
                    <p>Prof. Robson</p>
                </div>
                <img src="Matematica.png" alt="">
            </div>

        </div>

    </section>


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

<script src="notficacoes.js"></script>
</body>

</html>