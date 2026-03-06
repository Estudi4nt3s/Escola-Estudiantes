<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sistema.estudiantes.model.Professor" %>

<%
    String tipo = (String) session.getAttribute("tipoUsuario");
    String adminNome = (String) session.getAttribute("adminNome");

//    if (tipo == null || !tipo.equals("admin")) {
//        response.sendRedirect(request.getContextPath() + "/views/cadastro.jsp");
//        return;
//    }

    List<Professor> listaProfessores = (List<Professor>) request.getAttribute("listaProfessores");
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Gerenciar Professores</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/professor_a.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>

<body>

<aside class="sidebar">
    <div class="logo"><i class="material-icons">admin_panel_settings</i><span>Painel ADM</span></div>
    <nav>
        <a class="menu" href="${pageContext.request.contextPath}/views/inicio_a.jsp"><i class="material-icons">home</i>Inicio</a>
        <a class="menu" href="${pageContext.request.contextPath}/AlunoAdminServlet"><i class="material-icons">groups</i>Alunos</a>
        <a class="menu active" href="${pageContext.request.contextPath}/ProfessorAdminServlet"><i class="material-icons">badge</i>Professores</a>
        <a class="menu" href="${pageContext.request.contextPath}/TurmaAdmServlet"><i class="material-icons">school</i>Turmas</a>
        <a class="menu" href="${pageContext.request.contextPath}/DisciplinaAdminServlet"><i class="material-icons">menu_book</i>Disciplinas</a>
        <a class="menu" href="${pageContext.request.contextPath}/servletConfiguracoes"><i class="material-icons">settings</i>Configurações</a>
    </nav>
    <a class="config" href="${pageContext.request.contextPath}/servletLogout"><i class="material-icons">logout</i>Sair</a>
</aside>

<main class="main">

    <header class="topbar">
        <div class="date"><i class="material-icons">badge</i> Gerenciamento de Docentes</div>
        <div class="avatar">
            <img src="https://i.pravatar.cc/45?img=12">
            <span><%= adminNome %></span>
        </div>
    </header>

    <div class="page-header">
        <h1>Professores Cadastrados</h1>
        <form action="${pageContext.request.contextPath}/ProfessorAdminServlet" method="get">
            <button class="btn-primary" name="acao" value="novo">
                <i class="material-icons">add</i> Novo Professor
            </button>
        </form>
    </div>

    <div class="card">
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>E-mail (Login)</th>
                <th>Ações</th>
            </tr>
            </thead>
            <tbody>
            <% if (listaProfessores != null && !listaProfessores.isEmpty()) {
                for (Professor p : listaProfessores) { %>
            <tr>
                <td><%= p.getId() %></td>
                <td><%= p.getNome() %></td>
                <td><%= p.getUsuarioId() != null ? p.getUsuarioId().getEmail() : "Sem e-mail" %></td>
                <td style="display: flex; gap: 10px;">
                    <form action="${pageContext.request.contextPath}/ProfessorAdminServlet" method="get" style="display:inline;">
                        <input type="hidden" name="id" value="<%= p.getId() %>">
                        <button type="submit" name="acao" value="editar" class="icon-btn edit">
                            <i class="material-icons">edit</i>
                        </button>
                    </form>

                    <form action="${pageContext.request.contextPath}/ProfessorAdminServlet" method="get" style="display:inline;">
                        <input type="hidden" name="id" value="<%= p.getId() %>">
                        <button type="submit" name="acao" value="pre-excluir" class="icon-btn delete">
                            <i class="material-icons">delete</i>
                        </button>
                    </form>
                </td>
            </tr>
            <% } } else { %>
            <tr><td colspan="4" style="text-align:center;">Nenhum professor encontrado.</td></tr>
            <% } %>
            </tbody>
        </table>
    </div>

    <%
        String acaoModal = request.getParameter("acao");
        Professor profEdit = (Professor) request.getAttribute("professorEditar");
        boolean modalSalvar = "novo".equals(acaoModal) || "editar".equals(acaoModal);
        boolean modalExcluir = "pre-excluir".equals(acaoModal);
    %>

    <%-- MODAL DE SALVAR (NOVO/EDITAR) --%>
    <% if (modalSalvar) { %>
    <div class="overlay">
        <div class="modal">
            <h2><%= "editar".equals(acaoModal) ? "Editar Professor" : "Novo Professor" %></h2>
            <form action="${pageContext.request.contextPath}/ProfessorAdminServlet" method="post">
                <input type="hidden" name="acao" value="<%= acaoModal %>">

                <% if ("editar".equals(acaoModal) && profEdit != null) { %>
                <input type="hidden" name="id" value="<%= profEdit.getId() %>">
                <input type="hidden" name="usuarioId" value="<%= profEdit.getUsuarioId().getId() %>">
                <% } %>

                <div class="form-group">
                    <label>Nome</label>
                    <input type="text" name="nome" value="<%= profEdit != null ? profEdit.getNome() : "" %>" required>
                </div>

                <%-- Exibe sobrenome apenas no cadastro novo --%>
                <% if ("novo".equals(acaoModal)) { %>
                <div class="form-group">
                    <label>Sobrenome</label>
                    <input type="text" name="sobrenome" required>
                </div>
                <% } %>

                <div class="form-group">
                    <label>E-mail (Login)</label>
                    <input type="email" name="email" value="<%= (profEdit != null && profEdit.getUsuarioId() != null) ? profEdit.getUsuarioId().getEmail() : "" %>" required>
                </div>

                <div class="form-group">
                    <label><%= "editar".equals(acaoModal) ? "Nova Senha (deixe em branco para manter)" : "Senha" %></label>
                    <input type="password" name="senha" <%= "editar".equals(acaoModal) ? "" : "required" %>>
                </div>

                <div class="modal-buttons">
                    <button type="submit" class="btn-primary">Salvar</button>
                    <a href="${pageContext.request.contextPath}/ProfessorAdminServlet" class="btn-cancelar">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
    <% } %>

    <%-- MODAL DE EXCLUSÃO --%>
    <% if (modalExcluir && profEdit != null) { %>
    <div class="overlay">
        <div class="modal" style="text-align: center;">
            <i class="material-icons" style="font-size: 56px; color: #e74c3c;">warning</i>
            <h2 style="margin: 15px 0;">Você tem certeza?</h2>
            <p>Deseja realmente excluir o professor <strong><%= profEdit.getNome() %></strong>?</p>

            <form action="${pageContext.request.contextPath}/ProfessorAdminServlet" method="post">
                <input type="hidden" name="id" value="<%= profEdit.getId() %>">
                <div class="modal-buttons" style="justify-content: center; margin-top: 25px;">
                    <button type="submit" name="acao" value="excluir" class="btn-primary" style="background: #e74c3c;">Sim, Excluir</button>
                    <a href="${pageContext.request.contextPath}/ProfessorAdminServlet" class="btn-cancelar">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
    <% } %>

</main>
</body>
</html>