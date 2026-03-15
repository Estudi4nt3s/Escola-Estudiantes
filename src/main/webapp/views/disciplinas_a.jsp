<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sistema.estudiantes.model.DisciplinasAdm" %>
<%@ page import="com.sistema.estudiantes.model.Professor" %>

<%
  String nome = (String) session.getAttribute("adminNome");
  List<DisciplinasAdm> listaDisciplinas = (List<DisciplinasAdm>) request.getAttribute("listaDisciplinas");
  List<Professor> todosProfessores = (List<Professor>) request.getAttribute("listaProfessoresAuto");

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
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/disciplinas_a.css">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>

<aside class="sidebar">
  <div class="logo">
    <i class="material-icons">admin_panel_settings</i>
    <span>Painel ADM</span>
  </div>
  <nav>
    <a class="menu" href="${pageContext.request.contextPath}/views/inicio_a.jsp">
      <i class="material-icons">home</i><span>Inicio</span>
    </a>
    <a class="menu" href="${pageContext.request.contextPath}/AlunoAdminServlet">
      <i class="material-icons">groups</i><span>Alunos</span>
    </a>
    <a class="menu" href="${pageContext.request.contextPath}/ProfessorAdminServlet">
      <i class="material-icons">badge</i><span>Professores</span>
    </a>
    <a class="menu" href="${pageContext.request.contextPath}/TurmaAdmServlet">
      <i class="material-icons">school</i><span>Turmas</span>
    </a>
    <a class="menu active" href="${pageContext.request.contextPath}/DisciplinaAdminServlet">
      <i class="material-icons">menu_book</i><span>Disciplinas</span>
    </a>
    <a class="menu" href="${pageContext.request.contextPath}/servletConfiguracoes">
      <i class="material-icons">settings</i><span>Configurações</span>
    </a>
  </nav>
  <a class="config" href="${pageContext.request.contextPath}/servletLogout">
    <i class="material-icons">logout</i><span>Sair</span>
  </a>
</aside>

<main class="main">
  <header class="topbar">
    <div class="date"><i class="material-icons">admin_panel_settings</i>Área Administrativa</div>
    <div class="avatar">
      <img src="https://i.pravatar.cc/45?img=5">
      <span><%= (nome != null) ? nome : "Admin" %></span>
    </div>
  </header>

  <%-- DIV CONTENT ADICIONADA AQUI --%>
  <div class="content">
    <div class="page-header">
      <h1>Gerenciar Disciplinas</h1>
      <a href="DisciplinaAdminServlet?acao=novo" class="btn-primary">
        <i class="material-icons">add</i>Nova Disciplina
      </a>
    </div>

    <div class="card">
      <table>
        <thead>
        <tr><th>Nome</th><th>Professor</th><th>Ações</th></tr>
        </thead>
        <tbody>
        <% if (listaDisciplinas != null && !listaDisciplinas.isEmpty()) {
          for (DisciplinasAdm d : listaDisciplinas) { %>
        <tr>
          <td><%= d.getNome() %></td>
          <td><%= d.getProfessorNome() %></td>
          <td>
            <a href="DisciplinaAdminServlet?acao=editar&id=<%= d.getId() %>" class="icon-btn edit"><i class="material-icons">edit</i></a>
            <a href="DisciplinaAdminServlet?acao=pre-excluir&id=<%= d.getId() %>" class="icon-btn delete"><i class="material-icons">delete</i></a>
          </td>
        </tr>
        <% } } else { %>
        <tr><td colspan="3" style="text-align:center;">Nenhuma disciplina cadastrada.</td></tr>
        <% } %>
        </tbody>
      </table>
    </div>

    <%-- MODAIS --%>
    <% if (abrirModalForm) { %>
    <div class="overlay">
      <div class="modal">
        <h2><%= "editar".equals(acao) ? "Editar Disciplina" : "Nova Disciplina" %></h2>
        <form action="DisciplinaAdminServlet" method="post">
          <% if (dEdit != null) { %><input type="hidden" name="id" value="<%= dEdit.getId() %>"><% } %>
          <div class="form-group">
            <label>Nome da Disciplina</label>
            <input type="text" name="nome" id="inputNomeDisc" value="<%= dEdit != null ? dEdit.getNome() : "" %>" required>
            <small id="feedbackDisc">Verificando...</small>
          </div>
          <div class="form-group">
            <label>Professor</label>
            <input type="text" name="professorNome" list="listaProfs" value="<%= dEdit != null ? dEdit.getProfessorNome() : "" %>" required>
            <datalist id="listaProfs">
              <% if (todosProfessores != null) { for (Professor p : todosProfessores) { %>
              <option value="<%= p.getNome() %>" />
              <% } } %>
            </datalist>
          </div>
          <div class="modal-buttons">
            <button type="submit" name="acao" value="<%= acao %>" id="btnSalvar" class="btn-primary">Salvar</button>
            <a href="DisciplinaAdminServlet" class="btn-cancelar">Cancelar</a>
          </div>
        </form>
      </div>
    </div>
    <% } %>

    <%-- MODAL EXCLUIR --%>
    <% if (abrirModalExcluir && dEdit != null) { %>
    <div class="overlay">
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
  </div> <%-- FIM DA DIV CONTENT --%>

  <%-- MODAL PROFESSOR NÃO ENCONTRADO (FORA DO CONTENT) --%>
  <div id="modalProfessor" class="overlay" style="display: none;">
    <div class="modal">
      <h2 style="color: var(--red);">Professor não encontrado!</h2>
      <p>Deseja cadastrar <strong id="nomeProfNaoEncontrado"></strong> agora?</p>
      <form id="formNovoProfessor">
        <div class="form-group">
          <label>E-mail do Professor</label>
          <input type="email" id="emailNovoProf" required>
        </div>
        <div class="form-group">
          <label>Sobrenome</label>
          <input type="text" id="sobrenomeNovoProf" required>
        </div>
        <div class="modal-buttons">
          <button type="button" onclick="cadastrarProfessorAjax()" class="btn-primary">Criar Professor</button>
          <button type="button" onclick="document.getElementById('modalProfessor').style.display='none'" class="btn-cancelar">Voltar</button>
        </div>
      </form>
    </div>
  </div>
</main>
<script>
  // Validação de Disciplina
  const inputNome = document.getElementById('inputNomeDisc');
  const feedback = document.getElementById('feedbackDisc');
  const btnSalvar = document.getElementById('btnSalvar');

  if (inputNome) {
    inputNome.addEventListener('input', () => {
      const valor = inputNome.value.trim();
      const regex = /^[A-ZÀ-Ú][a-zà-ú0-9\s]{2,30}$/;
      if (regex.test(valor)) {
        feedback.innerText = "Nome válido!";
        feedback.style.color = "#27ae60";
        btnSalvar.disabled = false;
      } else {
        feedback.innerText = "Inicie com maiúscula (3-30 caracteres).";
        feedback.style.color = "#e74c3c";
        btnSalvar.disabled = true;
      }
    });
  }

  // Verificação de Professor AJAX
  const inputProf = document.querySelector('input[name="professorNome"]');
  if (inputProf) {
    inputProf.addEventListener('blur', function() {
      const nome = this.value.trim();
      if (nome.length < 3) return;
      fetch('DisciplinaAdminServlet?acao=verificarProfessor&nome=' + encodeURIComponent(nome))
              .then(res => res.json())
              .then(data => {
                if (!data.existe) {
                  document.getElementById('nomeProfNaoEncontrado').innerText = nome;
                  document.getElementById('modalProfessor').style.display = 'flex';
                }
              });
    });
  }

  function cadastrarProfessorAjax() {
    const nome = document.getElementById('nomeProfNaoEncontrado').innerText;
    const email = document.getElementById('emailNovoProf').value;
    const sobrenome = document.getElementById('sobrenomeNovoProf').value;

    const params = new URLSearchParams();
    params.append('acao', 'criarProfessorRapido');
    params.append('nome', nome);
    params.append('sobrenome', sobrenome);
    params.append('email', email);

    fetch('DisciplinaAdminServlet', { method: 'POST', body: params })
            .then(res => {
              if (res.ok) {
                alert("Professor criado!");
                document.getElementById('modalProfessor').style.display = 'none';
              }
            });
  }
</script>
</body>
</html>