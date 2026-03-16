<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sistema.estudiantes.model.Professor" %>
<%@ page import="com.sistema.estudiantes.model.Disciplina" %>
<%@ page import="com.sistema.estudiantes.model.DisciplinasAdm" %>

<%
    String adminNome = (String) session.getAttribute("adminNome");
    List<Professor> listaProfessores = (List<Professor>) request.getAttribute("listaProfessores");
    List<DisciplinasAdm> listaCadastro = (List<DisciplinasAdm>) request.getAttribute("listaDisciplinas");

    String acaoModal = request.getParameter("acao");
    if (acaoModal == null) { acaoModal = (String) request.getAttribute("acao"); }
    Professor profEdit = (Professor) request.getAttribute("professorEditar");
    String tipo = (String) session.getAttribute("tipoUsuario");

    if (tipo == null || !tipo.equals("admin")) {
        response.sendRedirect("cadastro.jsp");
        return;
    }
    boolean modalSalvar = "novo".equals(acaoModal) || "editar".equals(acaoModal);
    boolean modalExcluir = "pre-excluir".equals(acaoModal);
    boolean precisaAlerta = "novo".equals(acaoModal) || ("editar".equals(acaoModal) && (profEdit != null && (profEdit.getDisciplina() == null)));
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Gerenciar Professores</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/professor_a.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <style>
        @keyframes piscar { 0% { opacity: 1; } 50% { opacity: 0.3; } 100% { opacity: 1; } }
        .blink-link { animation: piscar 1.5s infinite; color: var(--red); font-weight: bold; text-decoration: underline; cursor: pointer; }
    </style>
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
        <a class="menu" href="${pageContext.request.contextPath}/ChatIAServlet">
            <i class="material-icons">psychology</i><span>IA Administrativa</span>
        </a>
        <a class="menu" href="${pageContext.request.contextPath}/servletConfiguracoes">
            <i class="material-icons">settings</i><span>Configurações</span>
        </a>
    </nav>
    <a class="config" href="${pageContext.request.contextPath}/servletLogout"><i class="material-icons">logout</i>Sair</a>
</aside>

<main class="main">
    <header class="topbar">
        <div class="date"><i class="material-icons">badge</i> Gerenciamento de Docentes</div>
        <div class="avatar"><span><%= (adminNome != null) ? adminNome : "Admin" %></span></div>
    </header>

    <div class="content">
        <div class="page-header">
            <h1>Professores Cadastrados</h1>
            <a href="${pageContext.request.contextPath}/ProfessorAdminServlet?acao=novo" class="btn-primary"><i class="material-icons">add</i> Novo Professor</a>
        </div>

        <div class="card">
            <div class="table-container">
                <table>
                    <thead>
                    <tr><th>ID</th><th>Nome</th><th>Disciplina</th><th>E-mail</th><th>Senha</th><th>Ações</th></tr>
                    </thead>
                    <tbody>
                    <% if (listaProfessores != null) {
                        for (Professor p : listaProfessores) {
                            boolean semDisciplina = (p.getDisciplina() == null || p.getDisciplina().getNome() == null);
                    %>
                    <tr>
                        <td><%= p.getId() %></td>
                        <td><%= p.getNome() %></td>
                        <td>
                            <% if (semDisciplina) { %>
                            <div style="display: flex; align-items: center; gap: 10px;">
                                <a href="ProfessorAdminServlet?acao=editar&id=<%= p.getId() %>" class="blink-link">Sem disciplina</a>
                                <a href="ProfessorAdminServlet?acao=editar&id=<%= p.getId() %>" class="btn-primary" style="padding: 2px 8px; font-size: 11px; background: var(--blue);">Vincular</a>
                            </div>
                            <% } else { %>
                            <%= p.getDisciplina().getNome() %>
                            <% } %>
                        </td>
                        <td><%= (p.getUsuario() != null && p.getUsuario().getEmail() != null) ? p.getUsuario().getEmail() : "Sem e-mail" %></td>
                        <td><code><%= (p.getUsuario() != null && p.getUsuario().getSenha() != null) ? p.getUsuario().getSenha() : "---" %></code></td>
                        <td style="display: flex; gap: 8px;">
                            <div class="actions-container">
                                <a href="ProfessorAdminServlet?acao=editar&id=<%= p.getId() %>" class="icon-btn edit"><i class="material-icons">edit</i></a>
                                <a href="ProfessorAdminServlet?acao=pre-excluir&id=<%= p.getId() %>" class="icon-btn delete"><i class="material-icons">delete</i></a>
                            </div>
                        </td>
                    </tr>
                    <% } } %>
                    </tbody>
                </table>
            </div>
        </div>

        <%-- MODAL SALVAR --%>
        <% if (modalSalvar) { %>
        <% if (precisaAlerta) { %>
        <div id="modalAviso" class="overlay" style="display: flex; z-index: 2000; background: rgba(0,0,0,0.8);">
            <div class="modal" style="text-align: center; border: 2px solid var(--red);">
                <i class="material-icons" style="font-size: 60px; color: var(--red);">warning</i>
                <h2>Atenção</h2>
                <p>É necessário vincular uma disciplina para concluir o cadastro.</p>
                <div class="modal-buttons" style="display: flex;justify-content: center; gap: 10px;">
                    <a href="${pageContext.request.contextPath}/DisciplinaAdminServlet" class="btn-primary" style="text-decoration: none; color: white;">Gerenciar Disciplinas</a>
                    <button class="btn-cancelar" onclick="fecharAviso()">Continuar</button>
                </div>
            </div>
        </div>
        <% } %>

        <div class="overlay" style="display: flex; z-index: 1000;">
            <div class="modal">
                <h2><%= "editar".equals(acaoModal) ? "Editar Professor" : "Novo Professor" %></h2>
                <form action="${pageContext.request.contextPath}/ProfessorAdminServlet" method="post">
                    <input type="hidden" name="acao" value="<%= "editar".equals(acaoModal) ? "editar" : "cadastrar" %>">
                    <% if (profEdit != null) { %><input type="hidden" name="id" value="<%= profEdit.getId() %>"><% } %>
                    <div class="form-group">
                        <label>Nome Completo</label>
                        <input type="text" id="inputNome" name="nome" value="<%= profEdit != null ? profEdit.getNome() : "" %>" required oninput="gerarEmail()">
                    </div>
                    <div class="form-group">
                        <label>Disciplina</label>
                        <select id="selectDisciplina" name="disciplinaId" required onchange="gerarEmail()">
                            <option value="">Selecione...</option>
                            <% if (listaCadastro != null) {
                                for (DisciplinasAdm d : listaCadastro) {
                                    boolean isSelected = (profEdit != null && profEdit.getDisciplina() != null && profEdit.getDisciplina().getId() == d.getId());
                            %>
                            <option value="<%= d.getId() %>" <%= isSelected ? "selected" : "" %>><%= d.getNome() %></option>
                            <% } } %>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>E-mail</label>
                        <input type="text" id="inputEmail" name="email" value="<%= (profEdit != null && profEdit.getUsuario() != null) ? profEdit.getUsuario().getEmail() : "" %>" readonly>
                    </div>
                    <div class="modal-buttons">
                        <a href="ProfessorAdminServlet" class="btn-cancelar">Cancelar</a>
                        <button type="submit" class="btn-primary">Salvar</button>
                    </div>
                </form>
            </div>
        </div>
        <% } %>

        <%-- MODAL EXCLUIR --%>
        <% if (modalExcluir && profEdit != null) { %>
        <div class="overlay" style="display: flex;">
            <div class="modal" style="text-align: center;">
                <h2>Excluir?</h2>
                <p>Deseja remover <strong><%= profEdit.getNome() %></strong>?</p>
                <form action="${pageContext.request.contextPath}/ProfessorAdminServlet" method="post">
                    <input type="hidden" name="acao" value="excluir">
                    <input type="hidden" name="id" value="<%= profEdit.getId() %>">
                    <button type="submit" class="btn-primary" style="background: var(--red);">Confirmar</button>
                    <a href="ProfessorAdminServlet" class="btn-cancelar">Cancelar</a>
                </form>
            </div>
        </div>
        <% } %>
    </div>
</main>

<script>
    function fecharAviso() { document.getElementById("modalAviso").style.display = "none"; }
    function gerarEmail() {
        const nome = document.getElementById('inputNome').value.trim().split(' ')[0].toLowerCase().normalize('NFD').replace(/[\u0300-\u036f]/g, "");
        const select = document.getElementById('selectDisciplina');
        if(nome && select.selectedIndex > 0) {
            const mat = select.options[select.selectedIndex].text.toLowerCase().normalize('NFD').replace(/[\u0300-\u036f]/g, "").replace(/\s+/g, "");
            document.getElementById('inputEmail').value = nome + "." + mat;
        }
    }
</script>
</body>
</html>