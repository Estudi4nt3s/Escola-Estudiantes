<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Colégio Estudiantes - Início</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/indexStyle.css">

</head>

<body>

    <aside class="sidebar">
        <div class="logo">
            <i class="material-icons">school</i>
            <span>Colégio Estudiantes</span>
        </div>

        <nav>
            <a class="menu active"><i class="material-icons">home</i>Início</a>
            <a class="menu" href="${pageContext.request.contextPath}/views/disciplinas.jsp">
                <i class="material-icons">menu_book</i>Minhas Disciplinas</a>
            <a class="menu"><i class="material-icons">calendar_month</i>Calendário</a>
            <a class="menu"><i class="material-icons">person</i>Perfil</a>
            <a class="menu" href="${pageContext.request.contextPath}/views/turmas.jsp">
                <i class="material-icons">calendar_month</i>Turmas (provisório)</a>
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
                    <img src="https://i.pravatar.cc/40?img=12" alt="avatar">
                    <span>Mateus Carlos</span>
                </div>
            </div>
        </header>

        <section class="dashboard">

            <div class="left">

                <div class="welcome">
                    <h2>Olá, Mateus!</h2>
                    <p>Pronto para as aulas de hoje?</p>
                </div>

                <div class="flex">
                    <div class="card card1">
                        <h3>Matemática</h3>
                        <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Corrupti eum magnam eligendi hic
                            nesciunt.</p>
                        <img src="Matematica.png" alt="Matemática">
                    </div>

                    <div class="card card2">
                        <h3>Português</h3>
                        <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Corrupti eum magnam eligendi hic
                            nesciunt.</p>
                        <img src="Matematica.png" alt="Matemática">
                    </div>
                    <div class="card card3">
                        <h3>Historia</h3>
                        <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Corrupti eum magnam eligendi hic
                            nesciunt.</p>
                        <img src="Matematica.png" alt="Matemática">
                    </div>
                    <div class="card card4">
                        <h3>Geografia</h3>
                        <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Corrupti eum magnam eligendi hic
                            nesciunt.</p>
                        <img src="Matematica.png" alt="Matemática">
                    </div>
                    <div class="card card5">
                        <h3>Ingles</h3>
                        <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Corrupti eum magnam eligendi hic
                            nesciunt.</p>
                        <img src="Matematica.png" alt="Matemática">
                    </div>
                    <div class="card card6">
                        <h3>Ciencias</h3>
                        <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Corrupti eum magnam eligendi hic
                            nesciunt.</p>
                        <img src="Matematica.png" alt="Matemática">
                    </div>
                </div>

            </div>

            <div class="content-today">
                <h2>Aulas de Hoje</h2>
                <ul>
                    <li><strong>Matemática</strong> - 07:00 às 08:00</li>
                    <li><strong>Português</strong> - 08:00 às 9:00</li>
                    <li><strong>História</strong> - 9:00 às 10:00</li>
                    <li><strong>Geografia</strong> - 10:30 às 11:30</li>
                    <li><strong>Inglês</strong> - 12:00 às 13:00</li>
                    <li><strong>Ciências</strong> - 13:30 às 14:30</li>
                </ul>
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

    <script src="${pageContext.request.contextPath}/js/notificacoes.js"></script>
</body>

</html>