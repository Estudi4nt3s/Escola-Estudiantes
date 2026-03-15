<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.sistema.estudiantes.model.Professor" %>
<%@ page import="com.sistema.estudiantes.model.Usuario" %>
<%@ page import="com.sistema.estudiantes.model.Disciplina" %>


<%
    Professor professor = (Professor) request.getSession().getAttribute("professor");
    Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
    String[] data = (String[]) request.getSession().getAttribute("data");
    Disciplina materia = (Disciplina) request.getSession().getAttribute("disciplina");
%>


<!DOCTYPE html>
<html lang="pt-BR">


<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Estudiantes - Perfil de Professor</title>


    <link rel="icon" href="${pageContext.request.contextPath}/utils/school.png">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/foto_edit.css">
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
        <a class="menu" href="${pageContext.request.contextPath}/views/home_p.jsp"><i class="material-icons">home</i>Início</a>
        <a class="menu" href="${pageContext.request.contextPath}/views/calendario_p.jsp"><i class="material-icons">calendar_month</i>Calendário</a>
        <a class="menu" href="${pageContext.request.contextPath}/views/turmas.jsp"><i class="material-icons">groups</i>Turmas</a>
        <a class="menu active"><i class="material-icons">person</i>Perfil</a>
    </nav>


    <div class="config">
        <a class="menu" style="color: #ffffff" onclick="openLogoutModal()">
            <i class="material-icons" style="color: #ffffff">output</i>Sair
        </a>
    </div>
</aside>


<main class="main">


    <header class="topbar">
        <div class="date">
            <i class="material-icons">calendar_today</i>
            <%= data[2].toUpperCase().charAt(0) + data[2].toLowerCase().substring(1) + ", " + data[0] + "/" + data[1] %>
        </div>


        <div class="user">
            <div class="avatar">
                <img id="top-avatar-img" src="${pageContext.request.contextPath}/utils/perfil.png" alt="Avatar">
                <span><%= professor.getNome() %></span>
            </div>
        </div>
    </header>


    <div class="perfil-page">
        <div class="perfil-header-card">
            <div class="perfil-foto" onclick="openPhotoModal()" style="cursor: pointer;">
                <img id="main-profile-img" src="${pageContext.request.contextPath}/utils/perfil.png" alt="Professor">
            </div>


            <div class="perfil-info-principal">
                <h2><%= professor.getNome() %></h2>
                <p>Professor de <%= professor.getDisciplina().getNome()%></p>


                <div class="perfil-status ativo">
                    ● Professor Ativo | ID: #<%= professor.getId() %>
                </div>
            </div>


            <button class="btn-edit-perfil">
                <i class="material-icons" style="font-size: 18px; vertical-align: middle;">edit</i> Editar Perfil
            </button>
        </div>


        <div class="perfil-detalhes">
            <div class="perfil-card-info">
                <h3>Informações Institucionais</h3>
                <div class="linha"><span>Matrícula:</span> <%= professor.getId() %></div>
                <div class="linha"><span>Usário/E-mail:</span> <%= usuario.getEmail() %></div>
                <div class="linha"><span>Matéria:</span> <%= materia.getNome().toUpperCase().charAt(0) + materia.getNome().toLowerCase().substring(1,materia.getNome().length()) %></div>
            </div>
        </div>
    </div>
</main>

<div id="photoModal" class="photo-modal-overlay" onclick="closePhotoModal(event)">
    <div class="photo-modal-content" onclick="event.stopPropagation()">
        <span class="close-btn" onclick="closePhotoModal(event)">&times;</span>


        <div class="photo-wrapper">
            <img id="modal-preview-img" src="${pageContext.request.contextPath}/utils/perfil.png" alt="Foto em destaque">


            <label for="file-upload" class="edit-pencil-btn">
                <img src="${pageContext.request.contextPath}/utils/edit.png" alt="Editar" onerror="this.src='https://cdn-icons-png.flaticon.com/512/1159/1159633.png'">
            </label>
            <input type="file" id="file-upload" accept="image/*" onchange="previewImage(event)" style="display: none;">
        </div>


        <p style="color: white; margin-top: 15px; font-weight: 500;">Clique no ícone para alterar a foto</p>
    </div>
</div>


<div id="logoutModal" class="logout-modal-overlay" onclick="closeLogoutModal()">
    <div class="logout-modal-content" onclick="event.stopPropagation()">
        <div class="logout-icon">
            <i class="material-icons">help_outline</i>
        </div>
        <h2>Confirmar Saída</h2>
        <p>Você tem certeza que deseja sair do sistema?</p>


        <div class="logout-buttons">
            <button class="btn-cancel" onclick="closeLogoutModal()">Cancelar</button>
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn-confirm">Sim, Sair</a>
        </div>
    </div>
</div>


<script>
    function openPhotoModal() { document.getElementById('photoModal').classList.add('show'); }
    function closePhotoModal(event) { document.getElementById('photoModal').classList.remove('show'); }


    function previewImage(event) {
        const reader = new FileReader();
        reader.onload = function() {
            document.getElementById('modal-preview-img').src = reader.result;
            document.getElementById('main-profile-img').src = reader.result;
            document.getElementById('top-avatar-img').src = reader.result;
        };
        if(event.target.files[0]) reader.readAsDataURL(event.target.files[0]);
    }


    function openLogoutModal() { document.getElementById('logoutModal').classList.add('show'); }
    function closeLogoutModal() { document.getElementById('logoutModal').classList.remove('show'); }

    window.addEventListener("pageshow", function(event) {

        if (event.persisted) {
            const loading = document.getElementById("loadingOverlay");
            if(loading) loading.style.display = "none";
        }

    });
</script>
</body>
</html>
