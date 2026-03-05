<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%
    // Proteção de acesso (verifica se está logado como admin)
    String tipo = (String) session.getAttribute("tipoUsuario");
    String adminNome = (String) session.getAttribute("adminNome");

//    if (tipo == null || !tipo.equals("admin")) {
//        response.sendRedirect("cadastro.jsp");
//        return;
// }

%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Estudiantes - Painel Administrativo</title>
    <link rel="icon" href="${pageContext.request.contextPath}/utils/school.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/inicio_a.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>

<aside class="sidebar">
    <div class="logo">
        <i class="material-icons">admin_panel_settings</i>
        <span>Painel ADM</span>
    </div>

    <nav>
        <a class="menu active" href="">
            <i class="material-icons">home</i>Inicio
        </a>

        <a class="menu" href="${pageContext.request.contextPath}/AlunoAdminServlet">
            <i class="material-icons">groups</i>Alunos
        </a>

        <a class="menu" href="${pageContext.request.contextPath}/TurmaAdminServlet">
            <i class="material-icons">school</i>Turmas
        </a>

        <a class="menu" href="${pageContext.request.contextPath}/DisciplinaAdminServlet">
            <i class="material-icons">menu_book</i>Disciplinas
        </a>

        <a class="menu" href="${pageContext.request.contextPath}/servletConfiguracoes">
            <i class="material-icons">settings</i>Configurações
        </a>
    </nav>

    <a class="config" href="${pageContext.request.contextPath}/servletLogout">
        <i class="material-icons">logout</i>Sair
    </a>
</aside>

<main class="main">

    <header class="topbar">
        <div class="date">
            <i class="material-icons">admin_panel_settings</i>
            Área Administrativa
        </div>

        <div class="avatar">
            <img src="https://i.pravatar.cc/45?img=5">
            <span><%= adminNome %></span>
        </div>
    </header>

    <div class="content">

        <div class="welcome">
            <h2>Bem-vindo, <%= adminNome %> 👑</h2>
            <p>Gerencie todo o sistema do Colégio Estudiantes.</p>
        </div>

        <div class="admin-cards">

            <div class="admin-card">
                <div class="card-icon">
                    <i class="material-icons">groups</i>
                </div>
                <h3>Gerenciar Alunos</h3>
                <p>Adicionar, editar ou remover alunos.</p>
                <a href="${pageContext.request.contextPath}/AlunoAdminServlet">Acessar</a>
            </div>

            <div class="admin-card">
                <div class="card-icon">
                    <i class="material-icons">school</i>
                </div>
                <h3>Gerenciar Turmas</h3>
                <p>Organizar turmas e vincular alunos.</p>
                <a href="turmas_a.jsp">Acessar</a>
            </div>

            <div class="admin-card">
                <div class="card-icon">
                    <i class="material-icons">menu_book</i>
                </div>
                <h3>Gerenciar Disciplinas</h3>
                <p>Criar, remover ou editar disciplinas.</p>
                <a href="disciplinas_a.jsp">Acessar</a>
            </div>

            <div class="admin-card danger">
                <div class="card-icon">
                    <i class="material-icons">warning</i>
                </div>
                <h3>Configurações</h3>
                <p>Alterações avançadas do sistema.</p>
                <a href="configuracoes_a.jsp">Configurar</a>
            </div>

        </div>

    </div>

</main>

</body>
</html>