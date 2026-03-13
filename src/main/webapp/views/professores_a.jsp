<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sistema.estudiantes.model.Professor" %>
<%@ page import="com.sistema.estudiantes.model.Disciplina" %>

<%
    String adminNome = (String) session.getAttribute("adminNome");
    List<Professor> listaProfessores = (List<Professor>) request.getAttribute("listaProfessores");
    List<Disciplina> disciplinas = (List<Disciplina>) request.getAttribute("listaDisciplinas");

    String acaoModal = request.getParameter("acao");
    Professor profEdit = (Professor) request.getAttribute("professorEditar");

    boolean modalSalvar = "novo".equals(acaoModal) || "editar".equals(acaoModal);
    boolean modalExcluir = "pre-excluir".equals(acaoModal);
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

    <div class="content">
        <div class="page-header">
            <h1>Professores Cadastrados</h1>
            <div style="display: flex; gap: 10px;">
                <button class="btn-filter" onclick="toggleFilter()"><i class="material-icons">filter_list</i> Filtros</button>
                <form action="${pageContext.request.contextPath}/ProfessorAdminServlet" method="get">
                    <button class="btn-primary" name="acao" value="novo"><i class="material-icons">add</i> Novo Professor</button>
                </form>
            </div>
        </div>

        <div id="filterModal" class="overlay" style="display: none;">
            <div class="modal">
                <h2>Filtros Avançados</h2>

                <div class="form-group">
                    <label>Nome do Professor</label>
                    <input type="text" id="inputNomeBusca" onkeyup="filtrarTabela()" placeholder="Nome...">
                </div>

                <div class="form-group">
                    <label>Disciplina</label>
                    <select id="selectDisciplinaBusca" onchange="filtrarTabela()">
                        <option value="">Todas as disciplinas</option>
                        <% if (disciplinas != null) { for (Disciplina d : disciplinas) { %>
                        <option value="<%= d.getNome() %>"><%= d.getNome() %></option>
                        <% } } %>
                    </select>
                </div>

                <div class="form-group">
                    <label>E-mail</label>
                    <input type="text" id="inputEmailBusca" onkeyup="filtrarTabela()" placeholder="Parte do e-mail...">
                </div>

                <div class="modal-buttons">
                    <button class="btn-cancelar" onclick="toggleFilter()">Fechar</button>
                    <button class="btn-primary" onclick="limparFiltros()">Limpar Tudo</button>
                </div>
            </div>
        </div>

        <div class="card">
            <div class="table-container">
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Disciplina</th>
                        <th>E-mail</th>
                        <th>Ações</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% if (listaProfessores != null && !listaProfessores.isEmpty()) {
                        for (Professor p : listaProfessores) { %>
                    <tr>
                        <td><%= p.getId() %></td>
                        <td><%= p.getNome() %></td>
                        <td><%= p.getDisciplina() != null ? p.getDisciplina().getNome() : "N/A" %></td>
                        <td><%= p.getUsuario() != null ? p.getUsuario().getEmail() : "Sem e-mail" %></td>
                        <td>
                            <form action="${pageContext.request.contextPath}/ProfessorAdminServlet" method="get" style="display:inline;">
                                <input type="hidden" name="id" value="<%= p.getId() %>">
                                <button type="submit" name="acao" value="editar" class="icon-btn edit"><i class="material-icons">edit</i></button>
                            </form>
                            <form action="${pageContext.request.contextPath}/ProfessorAdminServlet" method="get" style="display:inline;">
                                <input type="hidden" name="id" value="<%= p.getId() %>">
                                <button type="submit" name="acao" value="pre-excluir" class="icon-btn delete"><i class="material-icons">delete</i></button>
                            </form>
                        </td>
                    </tr>
                    <% } } else { %>
                    <tr><td colspan="5" style="text-align:center;">Nenhum professor cadastrado.</td></tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>

        <%-- MODAL DE SALVAR --%>
        <% if (modalSalvar) { %>
        <div class="overlay">
            <div class="modal">
                <h2><%= "editar".equals(acaoModal) ? "Editar Professor" : "Novo Professor" %></h2>
                <form action="${pageContext.request.contextPath}/ProfessorAdminServlet" method="post">
                    <input type="hidden" name="acao" value="<%= "editar".equals(acaoModal) ? "editar" : "cadastrar" %>">
                    <% if ("editar".equals(acaoModal) && profEdit != null) { %>
                    <input type="hidden" name="id" value="<%= profEdit.getId() %>">
                    <% } %>
                    <div class="form-group">
                        <label>Nome Completo</label>
                        <input type="text" id="inputNome" name="nome" value="<%= profEdit != null ? profEdit.getNome() : "" %>" required>
                    </div>
                    <div class="form-group">
                        <label>Disciplina</label>
                        <select id="selectDisciplina" name="disciplinaId" required onchange="gerarEmail()">
                            <option value="">Selecione...</option>
                            <% if (disciplinas != null) { for (Disciplina d : disciplinas) {
                                boolean isSelected = (profEdit != null && profEdit.getDisciplina() != null && profEdit.getDisciplina().getId() == d.getId()); %>
                            <option value="<%= d.getId() %>" <%= isSelected ? "selected" : "" %>><%= d.getNome() %></option>
                            <% } } %>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>E-mail (Login)</label>
                        <input type="email" id="inputEmail" name="email" value="<%= (profEdit != null && profEdit.getUsuario() != null) ? profEdit.getUsuario().getEmail() : "" %>" readonly>
                    </div>
                    <div class="modal-buttons">
                        <a href="${pageContext.request.contextPath}/ProfessorAdminServlet" class="btn-cancelar">Cancelar</a>
                        <button type="submit" class="btn-primary">Salvar</button>
                    </div>
                </form>
            </div>
        </div>
        <% } %>

        <%-- MODAL EXCLUIR --%>
        <% if (modalExcluir && profEdit != null) { %>
        <div class="overlay">
            <div class="modal" style="text-align: center;">
                <i class="material-icons" style="font-size: 56px; color: var(--red);">warning</i>
                <h2 style="margin: 15px 0;">Excluir?</h2>
                <p>Deseja remover <strong><%= profEdit.getNome() %></strong>?</p>
                <form action="${pageContext.request.contextPath}/ProfessorAdminServlet" method="post">
                    <input type="hidden" name="id" value="<%= profEdit.getId() %>">
                    <div class="modal-buttons" style="justify-content: center;">
                        <button type="submit" name="acao" value="excluir" class="btn-primary" style="background: var(--red);">Confirmar</button>
                        <a href="${pageContext.request.contextPath}/ProfessorAdminServlet" class="btn-cancelar">Cancelar</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <% } %>
</main>

<script>
    function toggleFilter() {
        const modal = document.getElementById("filterModal");
        modal.style.display = (modal.style.display === "flex") ? "none" : "flex";
    }

    function filtrarTabela() {
        const nomeBusca = document.getElementById("inputNomeBusca").value.toUpperCase();
        const discBusca = document.getElementById("selectDisciplinaBusca").value.toUpperCase();
        const emailBusca = document.getElementById("inputEmailBusca").value.toLowerCase();

        const linhas = document.querySelectorAll("table tbody tr");

        linhas.forEach(linha => {
            const nome = linha.cells[1].textContent.toUpperCase();
            const disc = linha.cells[2].textContent.toUpperCase();
            const email = linha.cells[3].textContent.toLowerCase();

            const matchNome = nome.includes(nomeBusca);
            const matchDisc = (discBusca === "" || disc.includes(discBusca));
            const matchEmail = email.includes(emailBusca);

            if (matchNome && matchDisc && matchEmail) {
                linha.style.display = "";
            } else {
                linha.style.display = "none";
            }
        });
    }

    function limparFiltros() {
        document.getElementById("inputNomeBusca").value = "";
        document.getElementById("selectDisciplinaBusca").value = "";
        document.getElementById("inputEmailBusca").value = "";
        filtrarTabela();
    }

    function gerarEmail() {
        const nome = document.getElementById('inputNome').value.toLowerCase().split(' ')[0];
        const select = document.getElementById('selectDisciplina');
        const materia = select.options[select.selectedIndex].text.toLowerCase()
            .normalize('NFD').replace(/[\u0300-\u036f]/g, "").replace(/\s+/g, "");
        if(nome && materia) document.getElementById('inputEmail').value = nome + "." + materia;
    }
</script>
</body>
</html>