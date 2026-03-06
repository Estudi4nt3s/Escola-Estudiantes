<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sistema.estudiantes.model.Aluno" %>

<%
  String tipo = (String) session.getAttribute("tipoUsuario");
  String adminNome = (String) session.getAttribute("adminNome");
//
//  if (tipo == null || !tipo.equals("admin")) {
//    response.sendRedirect("views/cadastro.jsp");
//    return;
//  }

  List<Aluno> listaAlunos = (List<Aluno>) request.getAttribute("listaAlunos");
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <title>Estudiantes - Gerenciar Alunos</title>
  <link rel="icon" href="${pageContext.request.contextPath}/utils/school.png">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/aluno_a.css">
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
      <i class="material-icons">home</i>Inicio
    </a>

    <a class="menu active" href="${pageContext.request.contextPath}/AlunoAdminServlet">
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
          <form action="${pageContext.request.contextPath}/AlunoAdminServlet"
                method="post" style="display:inline;">
            <input type="hidden" name="matricula"
                   value="<%= aluno.getMatricula() %>">
            <button class="icon-btn edit" name="acao" value="editar">
              <i class="material-icons">edit</i>
            </button>
          </form>

          <!-- EXCLUIR -->
          <form action="${pageContext.request.contextPath}/AlunoAdminServlet"
                method="post" style="display:inline;">
            <input type="hidden" name="matricula"
                   value="<%= aluno.getMatricula() %>">
            <button class="icon-btn delete" name="acao" value="excluir">
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
  <%
    String acao = request.getParameter("acao");
    Aluno alunoEditar = (Aluno) request.getAttribute("alunoEditar");
    boolean abrirModal = "novo".equals(acao) || "editar".equals(acao);
  %>

  <% if (abrirModal) { %>
  <div class="overlay">
    <div class="modal">

      <h2>
        <%= "editar".equals(acao) ? "Editar Aluno" : "Novo Aluno" %>
      </h2>

      <form action="${pageContext.request.contextPath}/AlunoAdminServlet" method="post">

        <% if ("editar".equals(acao)) { %>
        <input type="hidden" name="matricula"
               value="<%= alunoEditar.getMatricula() %>">
        <% } %>

        <div class="form-group">
          <label>Nome</label>
          <input type="text" name="nome"
                 value="<%= alunoEditar != null ? alunoEditar.getNome() : "" %>"
                 required>
        </div>

        <div class="form-group">
          <label>CPF</label>
          <input type="text" name="cpf"
                 value="<%= alunoEditar != null ? alunoEditar.getCpf() : "" %>"
                 required>
        </div>

        <div class="form-group">
          <label>Data de Nascimento</label>
          <input type="date" name="dataNascimento"
                 value="<%= alunoEditar != null ? alunoEditar.getDataNascimento() : "" %>"
                 required>
        </div>

        <div class="form-group">
          <label>Telefone Pai</label>
          <input type="text" name="telefonePai"
                 value="<%= alunoEditar != null ? alunoEditar.getTelefonePai() : "" %>"
                 required>
        </div>

        <div class="modal-buttons">
          <button type="submit" name="acao"
                  value="<%= acao %>"
                  class="btn-primary">
            Salvar
          </button>

          <a href="AlunoAdminServlet" class="btn-cancelar">
            Cancelar
          </a>
        </div>

      </form>
    </div>
  </div>
  <% } %>
</main>

</body>
</html>