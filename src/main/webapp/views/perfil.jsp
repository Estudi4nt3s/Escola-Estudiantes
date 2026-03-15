<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.sistema.estudiantes.model.Aluno" %>
<%@ page import="com.sistema.estudiantes.model.Usuario" %>
<%@ page import="com.sistema.estudiantes.model.Turma" %>
<%@ page import="com.sistema.estudiantes.model.Usuario" %>

<%
    // Recuperando objetos da sessão
    Aluno aluno = (Aluno) session.getAttribute("aluno");
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    Turma turma = (Turma) session.getAttribute("turma");
    String[] data = (String[]) session.getAttribute("data");


    // Redireciona caso a sessão tenha expirado
    if (usuario == null || aluno == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
%>


<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Estudiantes - Perfil de <%= aluno.getNome() %></title>

    <link rel="icon" href="${pageContext.request.contextPath}/utils/school.png">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/perfil_p.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/carregar.css">
</head>

<body>

<aside class="sidebar">
    <div class="logo">
        <i class="material-icons">school</i>
        <span>Colégio Estudiantes</span>
    </div>

    <nav>
        <a class="menu" href="${pageContext.request.contextPath}/views/home.jsp"><i class="material-icons">home</i>Início</a>
        <a class="menu" href="${pageContext.request.contextPath}/views/calendario_a.jsp"><i class="material-icons">calendar_month</i>Calendário</a>
        <a class="menu" href="${pageContext.request.contextPath}/views/disciplinas.jsp"><i class="material-icons">menu_book</i>Minhas Disciplinas</a>
        <a class="menu" id="btnNotas" href="${pageContext.request.contextPath}/nota?sub_acao=buscar_por_id&id=<%=aluno.getMatricula()%>"> <i class="material-icons">grading</i>Notas</a>
        <a class="menu active"><i class="material-icons">person</i>Perfil</a>
    </nav>
    <div class="config">
        <a class="menu" style="color: #ffffff" onclick="openLogoutModal()">
            <i class="material-icons" style="color: #fff">output</i>Sair
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
<%--            <i class="material-icons" id="openNotification" onclick="toggleNotifications()">notifications</i>--%>
            <div class="avatar">
                <img id="top-avatar-img" src="${pageContext.request.contextPath}/utils/perfil.png" alt="Avatar">
                <span><%= aluno.getNome() %></span>
            </div>
        </div>
    </header>

    <div class="perfil-page">
        <div class="perfil-header-card">
            <div class="perfil-foto" onclick="openPhotoModal()" style="cursor: pointer;">
                <img id="main-profile-img" src="${pageContext.request.contextPath}/utils/perfil.png" alt="Aluno">
            </div>

            <div class="perfil-info-principal">
                <h2><%= aluno.getNome() %></h2>
                <p><%= turma.getNome() %> - Ensino Técnico</p>


                <div class="perfil-status ativo">
                    ● Aluno Ativo
                </div>
            </div>


<%--            <button class="btn-edit-perfil" onclick="location.href='editarPerfil.jsp'">--%>
<%--                <i class="material-icons" style="font-size: 18px; vertical-align: middle;">edit</i> Editar Perfil--%>
<%--            </button>--%>
        </div>

        <div class="perfil-detalhes">
            <div class="perfil-card-info">
                <h3>Informações Pessoais</h3>
                <div class="linha"><span>RA (Matrícula):</span> <span><%= aluno.getMatricula() %></span></div>
                <div class="linha"><span>E-mail:</span> <span><%= usuario.getEmail() %></span></div>
                <div class="linha"><span>Telefone Resp.:</span> <span><%= aluno.getTelefonePai() %></span></div>
            </div>


            <div class="perfil-card-info">
                <h3>Turma e Ensino</h3>
                <div class="linha"><span>Série:</span> <span><%= turma.getAno() %></span></div>
                <div class="linha"><span>Turma:</span> <span><%= turma.getNome() %></span></div>
                <div class="linha"><span>Instituição:</span> <span>Instituto J&F</span></div>
            </div>
        </div>
    </div>
</main>

<div class="notification-overlay" id="notificationOverlay">
    <div class="notification-modal">
        <div class="notification-modal-header">
            <h2>Notificações</h2>
            <button onclick="toggleNotifications()">✕</button>
        </div>
        <ul class="notification-modal-list">
            <li class="denied">
                <div class="notification-content">
                    <strong>Avaliação de Matemática</strong>
                    <span>Status: Pendente de revisão</span>
                </div>
                <button class="confirm-btn">Ver</button>
            </li>
        </ul>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/notificacoes.js"></script>
<div id="loadingOverlay">
    <div class="loadingBox">
        <div class="spinner"></div>
        <p>Carregando...</p>
    </div>
</div>


    <div id="photoModal" class="photo-modal-overlay" onclick="closePhotoModal(event)">
    <div class="photo-modal-content" onclick="event.stopPropagation()">
        <span class="close-btn" onclick="closePhotoModal(event)">&times;</span>
        <div class="photo-wrapper">
            <img id="modal-preview-img" src="${pageContext.request.contextPath}/utils/perfil.png" alt="Preview">
            <label for="file-upload" class="edit-pencil-btn">
                <img src="${pageContext.request.contextPath}/utils/edit.png" alt="Editar" onerror="this.src='https://cdn-icons-png.flaticon.com/512/1159/1159633.png'">
            </label>
            <form id="uploadForm" action="${pageContext.request.contextPath}/uploadFoto" method="post" enctype="multipart/form-data">
                <input type="file" name="foto" id="file-upload" accept="image/*" onchange="previewImage(event)" style="display: none;">
            </form>
        </div>
        <p style="color: white; margin-top: 15px; font-weight: 500;">Clique no ícone para alterar sua foto de aluno</p>
    </div>
    </div>

<div id="logoutModal" class="logout-modal-overlay" onclick="closeLogoutModal()">
    <div class="logout-modal-content" onclick="event.stopPropagation()">
        <div class="logout-icon">
            <i class="material-icons">help_outline</i>
        </div>
        <h2>Confirmar Saída</h2>
        <p>Deseja encerrar sua sessão no sistema?</p>
        <div class="logout-buttons">
            <button class="btn-cancel" onclick="closeLogoutModal()">Cancelar</button>
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn-confirm">Sim, Sair</a>
        </div>
    </div>
</div>

<script>
    // Funções de Modal e UI
    function toggleNotifications() {
        document.getElementById('notificationOverlay').classList.toggle('show');
    }
    function openPhotoModal() {
        document.getElementById('photoModal').classList.add('show');
    }

    const btnNotas = document.getElementById("btnNotas");
    const loading = document.getElementById("loadingOverlay");

    btnNotas.addEventListener("click", function(e){

        e.preventDefault(); // impede abrir imediatamente

        loading.style.display = "flex";

        setTimeout(()=>{
            window.location.href = this.href;
        },500);

    });


    function openLogoutModal() {
        document.getElementById('logoutModal').classList.add('show');
    }

    function closeLogoutModal() {
        document.getElementById('logoutModal').classList.remove('show');
    }
    function closePhotoModal(event) {
        document.getElementById('photoModal').classList.remove('show');
    }

    function previewImage(event) {
        const reader = new FileReader();
        reader.onload = function() {
            document.getElementById('modal-preview-img').src = reader.result;
            // Opcional: já submeter o formulário ao selecionar
            // document.getElementById('uploadForm').submit();
        };
        if(event.target.files[0]) reader.readAsDataURL(event.target.files[0]);
    }


    // Fechar modais ao clicar fora
    window.onclick = function(event) {
        const notif = document.getElementById('notificationOverlay');
        if (event.target == notif) notif.classList.remove('show');
    }

    window.addEventListener("pageshow", function(event) {

        if (event.persisted) {
            const loading = document.getElementById("loadingOverlay");
            if(loading) loading.style.display = "none";
        }

    });
</script>
</body>
</html>