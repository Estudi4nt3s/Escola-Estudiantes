<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sistema.estudiantes.model.TurmaAdm" %>
<%
  String nome = (String) session.getAttribute("adminNome");
  List<TurmaAdm> listaTurmas = (List<TurmaAdm>) request.getAttribute("listaTurmas");
  String tipo = (String) session.getAttribute("tipoUsuario");

  if (tipo == null || !tipo.equals("admin")) {
    response.sendRedirect("cadastro.jsp");
    return;
  }
  String acao = request.getParameter("acao");
  TurmaAdm tEdit = (TurmaAdm) request.getAttribute("turmaEditar");

  boolean abrirModalForm = "novo".equals(acao) || "editar".equals(acao);
  boolean abrirModalExcluir = "pre-excluir".equals(acao);
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <title>Estudiantes - Gerenciar Turmas</title>
  <link rel="icon" href="${pageContext.request.contextPath}/utils/school.png">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/turmas_a.css">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>

<aside class="sidebar">
  <div class="logo"><i class="material-icons">admin_panel_settings</i><span>Painel ADM</span></div>
  <nav>
    <a class="menu" href="${pageContext.request.contextPath}/views/inicio_a.jsp"><i class="material-icons">home</i>Inicio</a>
    <a class="menu" href="${pageContext.request.contextPath}/AlunoAdminServlet"><i class="material-icons">groups</i>Alunos</a>
    <a class="menu" href="${pageContext.request.contextPath}/ProfessorAdminServlet"><i class="material-icons">badge</i>Professores</a>
    <a class="menu active" href="${pageContext.request.contextPath}/TurmaAdmServlet"><i class="material-icons">school</i>Turmas</a>
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
      <span><%= (nome != null) ? nome : "Admin" %></span>
    </div>
  </header>

  <div class="content">
    <div class="page-header">
      <h1>Gerenciar Turmas</h1>
      <a href="TurmaAdmServlet?acao=novo" class="btn-primary">
        <i class="material-icons">add_circle</i> Cadastrar Turma
      </a>
    </div>

    <div class="card">
      <div class="table-container">
        <table>
          <thead><tr><th>Nome</th><th>Ano</th><th>Qtd Alunos</th><th>Ações</th></tr></thead>

         <tbody>
          <% if (listaTurmas != null && !listaTurmas.isEmpty()) {
            for (TurmaAdm turma : listaTurmas) { %>
          <tr>
            <td><%= turma.getNome() %></td>
            <td><%= turma.getAno() %></td>
            <td><%= turma.getQuantidadeAlunos() %></td>
            <td>
              <a href="TurmaAdmServlet?acao=editar&id=<%= turma.getId() %>" class="icon-btn edit"><i class="material-icons">edit</i></a>
              <a href="TurmaAdmServlet?acao=pre-excluir&id=<%= turma.getId() %>" class="icon-btn delete"><i class="material-icons">delete</i></a>
            </td>
          </tr>
          <% } } else { %>
          <tr><td colspan="4" style="text-align:center;">Nenhuma turma cadastrada.</td></tr>
          <% } %>
          </tbody>
        </table>
      </div>
    </div>

    <%-- MODAL DE FORMULÁRIO --%>
    <% if (abrirModalForm) { %>
    <div class="overlay">
      <div class="modal">
        <h2><%= "editar".equals(acao) ? "Editar Turma" : "Nova Turma" %></h2>
        <form action="TurmaAdmServlet" method="post">
          <% if (tEdit != null) { %><input type="hidden" name="id" value="<%= tEdit.getId() %>"><% } %>

          <div class="form-group">
            <label>Nome da Turma</label>
            <input type="text"
                   name="nome"
                   id="inputNomeTurma"
                   list="sugestoes-turmas"
                   placeholder="Ex: 2º Ano EM - D"
                   value="<%= tEdit != null ? tEdit.getNome() : "" %>"
                   required>
            <datalist id="sugestoes-turmas">
              <option value="1º Ano EM - A">
              <option value="2º Ano EM - A">
              <option value="3º Ano EM - A">
            </datalist>
            <small id="feedbackNome" style="color: #666; margin-top: 5px; display: block; font-weight: 500;">
              Aguardando digitação...
            </small>
          </div>

          <div class="form-group">
            <label>Ano Letivo</label>
            <input type="number" name="ano" value="<%= tEdit != null ? tEdit.getAno() : "2026" %>" required>
          </div>

          <div class="modal-buttons">
            <button type="submit" name="acao" value="<%= acao %>" class="btn-primary">Salvar</button>
            <a href="TurmaAdmServlet" class="btn-cancelar">Cancelar</a>
          </div>
        </form>
      </div>
    </div>
    <% } %>

    <%-- MODAL DE EXCLUSÃO (VOCÊ TEM CERTEZA?) --%>
    <% if (abrirModalExcluir && tEdit != null) { %>
    <div class="overlay">
      <div class="modal" style="text-align: center;">
        <i class="material-icons" style="font-size: 48px; color: #e74c3c;">warning</i>
        <h2>Você tem certeza?</h2>
        <p>Deseja excluir a turma <strong><%= tEdit.getNome() %></strong>?</p>
        <form action="TurmaAdmServlet" method="post">
          <input type="hidden" name="id" value="<%= tEdit.getId() %>">
          <div class="modal-buttons" style="justify-content: center; margin-top: 20px;">
            <button type="submit" name="acao" value="excluir" class="btn-primary" style="background: #e74c3c;">Sim, Excluir</button>
            <a href="TurmaAdmServlet" class="btn-cancelar">Cancelar</a>
          </div>
        </form>
      </div>
    </div>
    <% } %>
  </div>
</main>
<script>
  document.addEventListener('DOMContentLoaded', function() {
    const inputNome = document.getElementById('inputNomeTurma');
    const feedback = document.getElementById('feedbackNome');
    const btnSalvar = document.querySelector('button[value="novo"], button[value="editar"]');

    if (!inputNome) return;
    const regexTurma = /^[1-9]º Ano EM - [A-Z]$/;

    function validar() {
      const valor = inputNome.value;

      if (valor.length === 0) {
        feedback.innerText = "Falta o nome da turma.";
        feedback.style.color = "#666";
        btnSalvar.disabled = true;
        return;
      }

      if (regexTurma.test(valor)) {
        feedback.innerText = "Formato perfeito!";
        feedback.style.color = "#27ae60";
        inputNome.style.borderColor = "#27ae60";
        btnSalvar.disabled = false;
      } else {
        // Lógica de "Falta alguma coisa"
        if (!/^[1-9]º/.test(valor)) {
          feedback.innerText = "Comece com o ano (ex: 1º, 2º...).";
        } else if (!/Ano EM/.test(valor)) {
          feedback.innerText = "Falta o texto 'Ano EM'.";
        } else if (!/ - [A-Z]$/.test(valor)) {
          feedback.innerText = "Falta o hífen e uma letra maiúscula (ex: - A).";
        }

        feedback.style.color = "#e74c3c";
        inputNome.style.borderColor = "#e74c3c";
        btnSalvar.disabled = true;
      }
    }

    // Executa ao digitar
    inputNome.addEventListener('input', validar);

    // Executa ao carregar (para casos de edição)
    if(inputNome.value.length > 0) validar();
  });
</script>
</body>
</html>