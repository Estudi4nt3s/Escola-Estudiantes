<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sistema.estudiantes.model.Aluno" %>

<%
  String tipo = (String) session.getAttribute("tipoUsuario");
  String adminNome = (String) session.getAttribute("adminNome");

  if (tipo == null || !tipo.equals("admin")) {
    response.sendRedirect("views/cadastro.jsp");
    return;
  }

  List<Aluno> listaAlunos = (List<Aluno>) request.getAttribute("listaAlunos");
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <title>Gerenciar Alunos</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/aluno_a.css">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
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
    <div class="date">
      <i class="material-icons">groups</i>
      Área Administrativa
    </div>

    <div class="avatar">
      <img src="https://i.pravatar.cc/45?img=5">
      <span><%= adminNome %></span>
    </div>
  </header>

  <div class="page-header">
    <h1>Gerenciar Alunos</h1>

    <form action="${pageContext.request.contextPath}/AlunoAdminServlet" method="get">
      <button class="btn-primary" name="acao" value="novo">
        <i class="material-icons">add</i>
        Novo Aluno
      </button>
    </form>
  </div>

  <div class="card">
    <table>
      <thead>
      <tr>
        <th>Matrícula</th>
        <th>Nome</th>
        <th>CPF</th>
        <th>Data Nasc.</th>
        <th>Telefone Pai</th>
        <th>Ações</th>
      </tr>
      </thead>

      <tbody>
      <%
        if (listaAlunos != null && !listaAlunos.isEmpty()) {
          for (Aluno aluno : listaAlunos) {
      %>
      <tr>
        <td><%= aluno.getMatricula() %></td>
        <td><%= aluno.getNome() %></td>
        <td><%= aluno.getCpf() %></td>
        <td><%= aluno.getDataNascimento() %></td>
        <td><%= aluno.getTelefonePai() %></td>
        <td>

          <!-- EDITAR -->
          <form action="${pageContext.request.contextPath}/AlunoAdminServlet" method="get" style="display:inline;">
            <input type="hidden" name="matricula" value="<%= aluno.getMatricula() %>">
            <button type="submit" class="icon-btn edit" name="acao" value="editar">
              <i class="material-icons">edit</i>
            </button>
          </form>

          <!-- EXCLUIR -->
          <form action="${pageContext.request.contextPath}/AlunoAdminServlet" method="get" style="display:inline;">
            <input type="hidden" name="matricula" value="<%= aluno.getMatricula() %>">
            <button type="submit" class="icon-btn delete" name="acao" value="pre-excluir">
              <i class="material-icons">delete</i>
            </button>
          </form>
        </td>
      </tr>
      <%
        }
      } else {
      %>
      <tr>
        <td colspan="6" style="text-align:center;">
          Nenhum aluno encontrado.
        </td>
      </tr>
      <%
        }
      %>
      </tbody>

    </table>
  </div>
  </div> <%
  // PEGA OS DADOS NOVAMENTE PARA OS MODAIS
  String acaoModal = request.getParameter("acao");
  Aluno alunoEdit = (Aluno) request.getAttribute("alunoEditar");

  // Lógica para saber qual modal abrir
  boolean modalSalvar = "novo".equals(acaoModal) || "editar".equals(acaoModal);
  boolean modalExcluir = "pre-excluir".equals(acaoModal);
%>

  <%-- MODAL DE SALVAR (NOVO/EDITAR) --%>
  <% if (modalSalvar) { %>
  <div class="overlay">
    <div class="modal">
      <h2><%= "editar".equals(acaoModal) ? "Editar Aluno" : "Novo Aluno" %></h2>
      <form action="${pageContext.request.contextPath}/AlunoAdminServlet" method="post">

        <% if ("editar".equals(acaoModal) && alunoEdit != null) { %>
        <input type="hidden" name="matricula" value="<%= alunoEdit.getMatricula() %>">
        <% } %>

        <div class="form-group">
          <label>Nome Completo</label>
          <input type="text" name="nome" value="<%= alunoEdit != null ? alunoEdit.getNome() : "" %>" required>
        </div>

        <div class="form-group">
          <label>CPF</label>
          <input type="text" name="cpf" value="<%= alunoEdit != null ? alunoEdit.getCpf() : "" %>" required>
        </div>

        <div class="form-group">
          <label>Data de Nascimento</label>
          <input type="date" name="dataNascimento" value="<%= alunoEdit != null ? alunoEdit.getDataNascimento() : "" %>" required>
        </div>

        <div class="form-group">
          <label>Telefone Pai</label>
          <input type="text" name="telefonePai" value="<%= alunoEdit != null ? alunoEdit.getTelefonePai() : "" %>" required>
        </div>

        <div class="modal-buttons">
          <button type="submit" name="acao" value="<%= acaoModal %>" class="btn-primary">Salvar</button>
          <a href="${pageContext.request.contextPath}/AlunoAdminServlet" class="btn-cancelar">Cancelar</a>
        </div>
      </form>
    </div>
  </div>
  <% } %>

  <%-- MODAL DE CONFIRMAÇÃO DE EXCLUSÃO --%>
  <% if (modalExcluir && alunoEdit != null) { %>
  <div class="overlay">
    <div class="modal" style="text-align: center;">
      <i class="material-icons" style="font-size: 56px; color: #e74c3c;">warning</i>
      <h2 style="margin: 15px 0;">Você tem certeza?</h2>
      <p>Deseja realmente excluir o aluno <strong><%= alunoEdit.getNome() %></strong>?</p>
      <p style="font-size: 0.85em; color: #666; margin-top: 10px;">Isso apagará permanentemente as notas e registros dele.</p>

      <form action="${pageContext.request.contextPath}/AlunoAdminServlet" method="post">
        <input type="hidden" name="matricula" value="<%= alunoEdit.getMatricula() %>">
        <div class="modal-buttons" style="justify-content: center; margin-top: 25px;">
          <button type="submit" name="acao" value="excluir" class="btn-primary" style="background: #e74c3c;">Sim, Excluir</button>
          <a href="${pageContext.request.contextPath}/AlunoAdminServlet" class="btn-cancelar">Cancelar</a>
        </div>
      </form>
    </div>
  </div>
  <% } %>
  <%-- BLOCO DE MENSAGENS FEEDBACK --%>
  <%
    String msgErro = (String) session.getAttribute("mensagemErro");
    String msgSucesso = (String) session.getAttribute("mensagemSucesso");
    if (msgErro != null) {
  %>
  <div class="alert alert-danger">
    <i class="material-icons">error</i>
    <span><%= msgErro %></span>
    <button type="button" onclick="this.parentElement.style.display='none'">&times;</button>
  </div>
  <%
      session.removeAttribute("mensagemErro"); // Remove para não repetir ao atualizar
    }
    if (msgSucesso != null) {
  %>
  <div class="alert alert-success">
    <i class="material-icons">check_circle</i>
    <span><%= msgSucesso %></span>
    <button type="button" onclick="this.parentElement.style.display='none'">&times;</button>
  </div>
  <%
      session.removeAttribute("mensagemSucesso");
    }
  %>
</main>

</body>
</html>