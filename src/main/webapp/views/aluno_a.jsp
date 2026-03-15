<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sistema.estudiantes.model.Aluno" %>
<%@ page import="com.sistema.estudiantes.model.TurmaAdm" %>

<%
  String tipo = (String) session.getAttribute("tipoUsuario");
  String nomeAdmin = (String) session.getAttribute("adminNome");

  if (tipo == null || !tipo.equals("admin")) {
    response.sendRedirect("views/cadastro.jsp");
    return;
  }
  List<Aluno> listaAlunos = (List<Aluno>) request.getAttribute("listaAlunos");
  List<TurmaAdm> todasAsTurmas = (List<TurmaAdm>) request.getAttribute("listaTurmas");
  String acao = request.getParameter("acao");
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <title>Estudiantes - Gerenciar Alunos</title>
  <link rel="icon" href="${pageContext.request.contextPath}/utils/school.png">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/aluno_a.css">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>

<body>

<aside class="sidebar">
  <div class="logo"><i class="material-icons">admin_panel_settings</i><span>Painel ADM</span></div>
  <nav>
    <a class="menu" href="${pageContext.request.contextPath}/views/inicio_a.jsp"><i class="material-icons">home</i>Inicio</a>
    <a class="menu active" href="${pageContext.request.contextPath}/AlunoAdminServlet"><i class="material-icons">groups</i>Alunos</a>
    <a class="menu" href="${pageContext.request.contextPath}/ProfessorAdminServlet"><i class="material-icons">badge</i>Professores</a>
    <a class="menu" href="${pageContext.request.contextPath}/TurmaAdmServlet"><i class="material-icons">school</i>Turmas</a>
    <a class="menu" href="${pageContext.request.contextPath}/DisciplinaAdminServlet"><i class="material-icons">menu_book</i>Disciplinas</a>
    <a class="menu" href="${pageContext.request.contextPath}/servletConfiguracoes"><i class="material-icons">settings</i>Configurações</a>
  </nav>
  <a class="config" href="${pageContext.request.contextPath}/servletLogout"><i class="material-icons">logout</i>Sair</a>
</aside>

<main class="main">
  <header class="topbar">
    <div class="date"><i class="material-icons">admin_panel_settings</i>Área Administrativa</div>
    <div class="avatar">
      <img src="https://i.pravatar.cc/45?img=5">
      <span><%= (nomeAdmin != null) ? nomeAdmin : "Admin" %></span>
    </div>
  </header>

  <div class="content">
    <div class="page-header">
      <h1>Gerenciar Alunos</h1>
      <div style="display: flex; gap: 10px; align-items: center;">
        <button class="btn-filter" onclick="toggleFilter()"><i class="material-icons">filter_list</i> Filtros</button>
        <form action="${pageContext.request.contextPath}/AlunoAdminServlet" method="get">
          <button class="btn-primary" name="acao" value="novo"><i class="material-icons">add</i> Novo Aluno</button>
        </form>
      </div>
    </div>

    <%-- MODAL DE FILTROS --%>
    <div id="filterModal" class="overlay" style="display: none; justify-content: center; align-items: center;">
      <div class="modal">
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
          <h2>Filtros Avançados</h2>
          <button onclick="toggleFilter()" style="border:none; background:#eee; padding:5px; border-radius:50%; cursor:pointer;">
            <i class="material-icons">close</i>
          </button>
        </div>
        <div class="form-group">
          <label>Nome</label>
          <input type="text" id="inputNome" onkeyup="filtrarTabela()" placeholder="Ex: Gustavo...">
        </div>
        <div class="form-group">
          <label>Matrícula</label>
          <input type="number" id="inputMatricula" onkeyup="filtrarTabela()" placeholder="ID...">
        </div>
        <div class="form-group">
          <label>Turma</label>
          <select id="filterTurma" onchange="filtrarTabela()">
            <option value="">Todas as Turmas</option>
            <% if (todasAsTurmas != null) { for (TurmaAdm t : todasAsTurmas) { %>
            <option value="<%= t.getNome() %>"><%= t.getNome() %></option>
            <% } } %>
          </select>
        </div>
        <div class="form-group">
          <label>Status</label>
          <select id="filterStatus" onchange="filtrarTabela()">
            <option value="">Todos</option>
            <option value="vinculado">Vinculado</option>
            <option value="pendente">Pendente</option>
          </select>
        </div>
        <div class="modal-buttons">
          <button class="btn-clear" onclick="limparFiltros()">Limpar</button>
          <button class="btn-primary" onclick="toggleFilter()">Fechar</button>
        </div>
      </div>
    </div>

    <%-- TABELA DE ALUNOS --%>
    <div class="card">
      <div class="table-responsive">
        <table>
          <thead>
          <tr>
            <th>Matrícula</th>
            <th>Nome</th>
            <th>CPF</th>
            <th>Telefone Pai</th>
            <th>E-mail Responsável</th>
            <th>Turma</th>
            <th style="text-align: center;">Status</th>
            <th>Ações</th>
          </tr>
          </thead>
          <tbody>
          <% if (listaAlunos != null && !listaAlunos.isEmpty()) { for (Aluno aluno : listaAlunos) { %>
          <tr>
            <td><strong>#<%= aluno.getMatricula() %></strong></td>
            <td class="text-truncate"><%= aluno.getNome() %></td>
            <td><%= aluno.getCpf() %></td>
            <td><%= aluno.getTelefonePai() %></td>
            <td class="text-truncate"><%= (aluno.getEmailResponsavel() != null) ? aluno.getEmailResponsavel() : "---" %></td>
            <td>
              <% String nomeTurmaExibir = "N/A";
                if (aluno.getTurmaId() > 0 && todasAsTurmas != null) {
                  for (TurmaAdm t : todasAsTurmas) { if (t.getId() == aluno.getTurmaId()) { nomeTurmaExibir = t.getNome(); break; } }
                } %> <%= nomeTurmaExibir %>
            </td>
            <td style="text-align: center;">
              <%
                boolean temUsuario = (aluno.getUsuarioId() != null && aluno.getUsuarioId().getId() > 0);
              %>
              <span class="status-badge <%= temUsuario ? "status-success" : "status-warning" %>">
              <i class="fas <%= temUsuario ? "fa-check-circle" : "fa-exclamation-circle" %>"></i>
              <%= temUsuario ? "Vinculado" : "Pendente" %>
              </span>
            </td>
            <td>
              <div style="display: flex; gap: 5px;">
                <form action="${pageContext.request.contextPath}/AlunoAdminServlet" method="get">
                  <input type="hidden" name="matricula" value="<%= aluno.getMatricula() %>">
                  <button type="submit" class="icon-btn edit" name="acao" value="editar"><i class="material-icons">edit</i></button>
                </form>
                <form action="${pageContext.request.contextPath}/AlunoAdminServlet" method="get">
                  <input type="hidden" name="matricula" value="<%= aluno.getMatricula() %>">
                  <button type="submit" class="icon-btn delete" name="acao" value="pre-excluir"><i class="material-icons">delete</i></button>
                </form>
              </div>
            </td>
          </tr>
          <% } } else { %>
          <tr><td colspan="8" style="text-align:center;">Nenhum aluno cadastrado.</td></tr>
          <% } %>
          </tbody>
        </table>
      </div>
    </div>

    <%
      String acaoModal = request.getParameter("acao");
      Aluno alunoEdit = (Aluno) request.getAttribute("alunoEditar");
      boolean modalSalvar = "novo".equals(acaoModal) || "editar".equals(acaoModal);
      boolean modalExcluir = "pre-excluir".equals(acaoModal);
    %>

    <%-- MODAL DE SALVAR (NOVO/EDITAR) --%>
    <% if (modalSalvar) { %>
    <div class="overlay">
      <div class="modal scroll-modal">
        <h2><%= "editar".equals(acaoModal) ? "Editar Aluno" : "Novo Aluno" %></h2>
        <form action="${pageContext.request.contextPath}/AlunoAdminServlet" method="post">
          <% if ("editar".equals(acaoModal) && alunoEdit != null) { %>
          <input type="hidden" name="matricula" value="<%= alunoEdit.getMatricula() %>">
          <% } %>

          <div class="form-row">
            <div class="form-group flex-2">
              <label>Nome Completo</label>
              <input type="text" name="nome" value="<%= alunoEdit != null ? alunoEdit.getNome() : "" %>" placeholder="Nome do aluno" required>
            </div>
            <div class="form-group flex-1">
              <label>CPF</label>
              <input type="text" name="cpf" value="<%= alunoEdit != null ? alunoEdit.getCpf() : "" %>" placeholder="000.000.000-00" required>
            </div>
          </div>

          <div class="form-row">
            <div class="form-group flex-1">
              <label>Data de Nascimento</label>
              <input type="date" name="dataNascimento" value="<%= alunoEdit != null ? alunoEdit.getDataNascimento() : "" %>" required>
            </div>
            <div class="form-group flex-1">
              <label>Turma</label>
              <select name="turmaId" required>
                <option value="">Selecione...</option>
                <% if (todasAsTurmas != null) { for (TurmaAdm t : todasAsTurmas) {
                  String selected = (alunoEdit != null && alunoEdit.getTurmaId() == t.getId()) ? "selected" : "";
                %>
                <option value="<%= t.getId() %>" <%= selected %>><%= t.getNome() %></option>
                <% } } %>
              </select>
            </div>
          </div>

          <div class="form-row">
            <div class="form-group flex-1">
              <label>Telefone Pai</label>
              <input type="text" name="telefonePai" value="<%= alunoEdit != null ? alunoEdit.getTelefonePai() : "" %>" placeholder="(11) 99999-9999" required>
            </div>
            <div class="form-group flex-1">
              <label>E-mail Responsável (Automático)</label>
              <input type="email" name="emailResponsavel" value="<%= alunoEdit != null ? alunoEdit.getEmailResponsavel() : "" %>" placeholder="email@exemplo.com" required>
            </div>
          </div>

          <div class="modal-buttons">
            <button type="submit" name="acao" value="<%= acaoModal %>" class="btn-primary">Confirmar Dados</button>
            <a href="${pageContext.request.contextPath}/AlunoAdminServlet" class="btn-cancelar">Voltar</a>
          </div>
        </form>
      </div>
    </div>
    <% } %>

    <%-- MODAL DE EXCLUSÃO --%>
    <% if (modalExcluir && alunoEdit != null) { %>
    <div class="overlay">
      <div class="modal" style="text-align: center;">
        <i class="material-icons" style="font-size: 56px; color: #e74c3c;">warning</i>
        <h2 style="margin: 15px 0;">Remover Registro</h2>
        <p>Você está prestes a excluir o aluno <strong><%= alunoEdit.getNome() %></strong>. Esta ação não pode ser desfeita.</p>
        <form action="${pageContext.request.contextPath}/AlunoAdminServlet" method="post">
          <input type="hidden" name="matricula" value="<%= alunoEdit.getMatricula() %>">
          <div class="modal-buttons" style="justify-content: center; gap: 15px; margin-top: 25px;">
            <button type="submit" name="acao" value="excluir" class="btn-danger">Confirmar Exclusão</button>
            <a href="${pageContext.request.contextPath}/AlunoAdminServlet" class="btn-cancelar">Cancelar</a>
          </div>
        </form>
      </div>
    </div>
    <% } %>

    <%-- ALERTAS --%>
    <%
      String msgErro = (String) session.getAttribute("mensagemErro");
      String msgSucesso = (String) session.getAttribute("mensagemSucesso");
      if (msgErro != null) {
    %>
    <div class="alert alert-danger">
      <span><%= msgErro %></span>
      <button onclick="this.parentElement.style.display='none'">&times;</button>
    </div>
    <% session.removeAttribute("mensagemErro"); } %>
    <% if (msgSucesso != null) { %>
    <div class="alert alert-success">
      <span><%= msgSucesso %></span>
      <button onclick="this.parentElement.style.display='none'">&times;</button>
    </div>
    <% session.removeAttribute("mensagemSucesso"); } %>
  </div>
</main>

<script>
  function toggleFilter() {
    const modal = document.getElementById("filterModal");
    modal.style.display = (modal.style.display === "flex") ? "none" : "flex";
  }

  function filtrarTabela() {
    const nomeBusca = document.getElementById("inputNome").value.toUpperCase();
    const matriculaBusca = document.getElementById("inputMatricula").value;
    const turmaBusca = document.getElementById("filterTurma").value.toUpperCase();
    const statusBusca = document.getElementById("filterStatus").value;

    document.querySelectorAll("table tbody tr").forEach(linha => {
      if (linha.cells.length < 8) return;

      // Ajuste de índices por conta da nova coluna (Email agora é a 4)
      const tdMatricula = linha.cells[0].textContent.replace('#', '').trim();
      const tdNome = linha.cells[1].textContent.toUpperCase();
      const tdTurma = linha.cells[5].textContent.trim().toUpperCase();
      const statusText = linha.querySelector('.status-badge').textContent.toLowerCase();

      const bate = tdNome.includes(nomeBusca) &&
              (matriculaBusca === "" || tdMatricula === matriculaBusca) &&
              (turmaBusca === "" || tdTurma.includes(turmaBusca)) &&
              (statusBusca === "" || statusText.includes(statusBusca));

      linha.style.display = bate ? "" : "none";
    });
  }

  function limparFiltros() {
    document.getElementById("inputNome").value = "";
    document.getElementById("inputMatricula").value = "";
    document.getElementById("filterTurma").value = "";
    document.getElementById("filterStatus").value = "";
    filtrarTabela();
  }
</script>
</body>
</html>