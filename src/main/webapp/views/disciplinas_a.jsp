<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sistema.estudiantes.model.DisciplinasAdm" %>
<%@ page import="com.sistema.estudiantes.model.Professor" %>

<%
  String nome = (String) session.getAttribute("adminNome");
  List<DisciplinasAdm> listaDisciplinas = (List<DisciplinasAdm>) request.getAttribute("listaDisciplinas");

  String acao = request.getParameter("acao");
  DisciplinasAdm dEdit = (DisciplinasAdm) request.getAttribute("disciplinaEditar");

  String tipo = (String) session.getAttribute("tipoUsuario");

  if (tipo == null || !tipo.equals("admin")) {
    response.sendRedirect("cadastro.jsp");
    return;
  }
  boolean abrirModalForm = "novo".equals(acao) || "editar".equals(acao);
  boolean abrirModalExcluir = "pre-excluir".equals(acao);
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <title>Estudiantes - Gerenciar Disciplinas</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/disciplinas_a.css?v=<%= System.currentTimeMillis() %>">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>

<aside class="sidebar">
  <div class="logo"><i class="material-icons">admin_panel_settings</i><span>Painel ADM</span></div>
  <nav>
    <a class="menu" href="${pageContext.request.contextPath}/views/inicio_a.jsp"><i class="material-icons">home</i><span>Inicio</span></a>
    <a class="menu" href="${pageContext.request.contextPath}/AlunoAdminServlet"><i class="material-icons">groups</i><span>Alunos</span></a>
    <a class="menu" href="${pageContext.request.contextPath}/ProfessorAdminServlet"><i class="material-icons">badge</i><span>Professores</span></a>
    <a class="menu" href="${pageContext.request.contextPath}/TurmaAdmServlet"><i class="material-icons">school</i><span>Turmas</span></a>
    <a class="menu active" href="${pageContext.request.contextPath}/DisciplinaAdminServlet"><i class="material-icons">menu_book</i><span>Disciplinas</span></a>
    <a class="menu" href="${pageContext.request.contextPath}/ChatIAServlet">
      <i class="material-icons">psychology</i><span>IA Administrativa</span>
    </a>
    <a class="menu" href="${pageContext.request.contextPath}/servletConfiguracoes"><i class="material-icons">settings</i><span>Configurações</span></a>
  </nav>
  <a class="config" href="${pageContext.request.contextPath}/servletLogout"><i class="material-icons">logout</i><span>Sair</span></a>
</aside>

<main class="main">
  <header class="topbar">
    <div class="date"><i class="material-icons">admin_panel_settings</i>Área Administrativa</div>
    <div class="avatar">
      <img src="${pageContext.request.contextPath}/utils/perfil_adm.jpg">
      <span><%= (nome != null) ? nome : "Admin" %></span>
    </div>
  </header>

  <div class="content">
    <div class="page-header">
      <h1>Gerenciar Disciplinas</h1>
      <a href="DisciplinaAdminServlet?acao=novo" class="btn-primary"><i class="material-icons">add</i>Nova Disciplina</a>
    </div>

    <div class="card">
      <table>
        <thead>
        <tr><th>Nome</th><th>Professor</th><th>Ações</th></tr>
        </thead>
        <tbody>
        <% if (listaDisciplinas != null && !listaDisciplinas.isEmpty()) {
          for (DisciplinasAdm d : listaDisciplinas) {
            boolean semProf = (d.getProfessorNome() == null ||
                    d.getProfessorNome().trim().equalsIgnoreCase("Sem Professor") ||
                    d.getProfessorNome().trim().isEmpty());
        %>
        <tr>
          <td><%= d.getNome() %></td>
          <td>
            <% if (semProf) { %>
            <div style="display: flex; align-items: center; gap: 8px;">
              <span class="blink-alerta" onclick="abrirAvisoProf()">⚠ Sem Professor</span>
            </div>
            <% } else { %>
            <span style="color: #2c3e50;"><%= d.getProfessorNome() %></span>
            <% } %>
          </td>
          <td>
            <div class="actions-container">
              <a href="DisciplinaAdminServlet?acao=editar&id=<%= d.getId() %>" class="icon-btn edit"><i class="material-icons">edit</i></a>
              <a href="DisciplinaAdminServlet?acao=pre-excluir&id=<%= d.getId() %>" class="icon-btn delete"><i class="material-icons">delete</i></a>
            </div>
          </td>
        </tr>
        <% } } else { %>
        <tr><td colspan="3" style="text-align:center;">Nenhuma disciplina cadastrada.</td></tr>
        <% } %>
        </tbody>
      </table>
    </div>

    <%-- MODAL DE CADASTRO/EDIÇÃO --%>
    <% if (abrirModalForm) { %>
    <div class="overlay" style="display: flex;">
      <div class="modal">
        <h2><%= "editar".equals(acao) ? "Editar Disciplina" : "Nova Disciplina" %></h2>
        <form action="DisciplinaAdminServlet" method="post">
          <% if (dEdit != null) { %><input type="hidden" name="id" value="<%= dEdit.getId() %>"><% } %>
          <div class="form-group">
            <label>Nome da Disciplina</label>
            <input type="text" name="nome" id="inputNomeDisc" value="<%= dEdit != null ? dEdit.getNome() : "" %>" required>
            <small id="feedbackDisc">Verificando...</small>
          </div>
          <div class="modal-buttons">
            <button type="submit" name="acao" value="<%= acao %>" id="btnSalvar" class="btn-primary">Salvar</button>
            <a href="DisciplinaAdminServlet" class="btn-cancelar">Cancelar</a>
          </div>
        </form>
      </div>
    </div>
    <% } %>

    <%-- MODAL DE AVISO: VINCULAR PROFESSOR --%>
    <div id="modalAvisoProf" class="overlay" style="display: none; z-index: 2000; background: rgba(0,0,0,0.7);">
      <div class="modal" style="text-align: center;">
        <i class="material-icons" style="font-size: 60px; color: #f39c12;">person_add</i>
        <h2>Professor Necessário</h2>
        <p>Esta disciplina não possui um docente vinculado. Adicione um professor para prosseguir.</p>
        <div class="modal-buttons" style="justify-content: center; gap: 10px;">
          <a href="${pageContext.request.contextPath}/ProfessorAdminServlet?acao=novo" class="btn-primary">Ir para Professores</a>
          <button type="button" class="btn-cancelar" onclick="fecharAvisoProf()">Cancelar</button>
        </div>
      </div>
    </div>

    <%-- MODAL EXCLUIR --%>
    <% if (abrirModalExcluir && dEdit != null) { %>
    <div class="overlay" style="display: flex;">
      <div class="modal" style="text-align: center;">
        <i class="material-icons" style="font-size: 56px; color: #e74c3c;">warning</i>
        <h2>Confirmar Exclusão</h2>
        <p>Remover a disciplina <strong><%= dEdit.getNome() %></strong>?</p>
        <form action="DisciplinaAdminServlet" method="post">
          <input type="hidden" name="id" value="<%= dEdit.getId() %>">
          <div class="modal-buttons" style="justify-content: center;">
            <button type="submit" name="acao" value="excluir" class="btn-primary" style="background: #e74c3c;">Sim, Remover</button>
            <a href="DisciplinaAdminServlet" class="btn-cancelar">Cancelar</a>
          </div>
        </form>
      </div>
    </div>
    <% } %>
  </div>
</main>

<script>
  function abrirAvisoProf(){
    const modal = document.getElementById("modalAvisoProf");
    modal.style.display = "flex";
  }

  function fecharAvisoProf(){
    const modal = document.getElementById("modalAvisoProf");
    modal.style.display = "none";
  }

  const inputNome = document.getElementById('inputNomeDisc');
  const feedback = document.getElementById('feedbackDisc');
  const btnSalvar = document.getElementById('btnSalvar');

  if (inputNome) {
    inputNome.addEventListener('input', () => {
      const regex = /^[A-ZÀ-Ú][a-zà-ú0-9\s]{2,30}$/;
      if (regex.test(inputNome.value.trim())) {
        feedback.innerText = "Nome válido!"; feedback.style.color = "#27ae60"; btnSalvar.disabled = false;
      } else {
        feedback.innerText = "Inicie com maiúscula (3-30 caracteres)."; feedback.style.color = "#e74c3c"; btnSalvar.disabled = true;
      }
    });
  }
</script>
</body>
</html>