<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sistema.estudiantes.model.Aluno" %>

<%
  String tipo = (String) session.getAttribute("tipoUsuario");
  String adminNome = (String) session.getAttribute("adminNome");

  if (tipo == null || !tipo.equals("admin")) {
    response.sendRedirect("login.jsp");
    return;
  }

  List<Aluno> listaAlunos = (List<Aluno>) request.getAttribute("listaAlunos");
%>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
  <meta charset="UTF-8">
  <title>Gerenciar Alunos</title>
  <link rel="stylesheet" href="css/aluno_a.css">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>

<body>

<aside class="sidebar">
  <div class="logo">
    <i class="material-icons">admin_panel_settings</i>
    <span>Painel ADM</span>
  </div>

  <nav>
    <a class="menu" href="inicio_a.jsp">
      <i class="material-icons">home</i>Inicio
    </a>

    <a class="menu active" href="aluno_a.jsp">
      <i class="material-icons">groups</i>Alunos
    </a>

    <a class="menu" href="turmas_a.jsp">
      <i class="material-icons">school</i>Turmas
    </a>

    <a class="menu" href="disciplinas_a.jsp">
      <i class="material-icons">menu_book</i>Disciplinas
    </a>

    <a class="menu" href="configuracoes_a.jsp">
      <i class="material-icons">settings</i>Configurações
    </a>
  </nav>

  <a class="config" href="LogoutServlet">
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

    <form action="${pageContext.request.contextPath}/ServletAluno_a" method="get">
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
        <th>ID</th>
        <th>Nome</th>
        <th>Email</th>
        <th>Turma</th>
        <th>Ações</th>
      </tr>
      </thead>
      <tbody>

      <%
        if (listaAlunos != null && !listaAlunos.isEmpty()) {
          for (Aluno aluno : listaAlunos) {
      %>
      <tr>
        <td><%= aluno.getId() %></td>
        <td><%= aluno.getNome() %></td>
        <td><%= aluno.getEmail() %></td>
        <td><%= aluno.getTurma() %></td>
        <td>

          <form action="${pageContext.request.contextPath}/ServletAluno_a" method="post" style="display:inline;">
            <input type="hidden" name="id" value="<%= aluno.getId() %>">
            <button class="icon-btn edit" name="acao" value="editar">
              <i class="material-icons">edit</i>
            </button>
          </form>

          <form action="${pageContext.request.contextPath}/ServletAluno_a" method="post" style="display:inline;">
            <input type="hidden" name="id" value="<%= aluno.getId() %>">
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
        <td colspan="5" style="text-align:center;">
          Nenhum aluno encontrado.
        </td>
      </tr>
      <%
        }
      %>

      </tbody>
    </table>
  </div>

</main>

</body>
</html>