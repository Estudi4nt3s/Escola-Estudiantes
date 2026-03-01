<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sistema.estudiantes.model.Turma" %>
<%--
TurmaDAO dao = new TurmaDAO();
List<Turma> lista = dao.listarTodas();
request.setAttribute("listaTurmas", lista);
request.getRequestDispatcher("views/gerenciarTurmas.jsp").forward(request, response);
TEM QUE FAZER O SERVLET
ASS GUSTAVO
--%>

<%

  List<Turma> listaTurmas = (List<Turma>) request.getAttribute("listaTurmas");
  String nome = (String) session.getAttribute("nome");

  if (nome == null) {
    response.sendRedirect("../login.jsp");
    return;
  }
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <title>Gerenciar Turmas</title>
  <link rel="stylesheet" href="/css/turmas_a.css">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>

<body>

<aside class="sidebar">
  <div class="logo">
    <i class="material-icons">admin_panel_settings</i>
    <span>Painel ADM</span>
  </div>

  <nav>
    <a class="menu" href="admin.jsp">
      <i class="material-icons">home</i>Inicio
    </a>

    <a class="menu" href="gerenciarAlunos.jsp">
      <i class="material-icons">groups</i>Alunos
    </a>

    <a class="menu active" href="gerenciarTurmas.jsp">
      <i class="material-icons">school</i>Turmas
    </a>

    <a class="menu" href="gerenciarDisciplinas.jsp">
      <i class="material-icons">menu_book</i>Disciplinas
    </a>

    <a class="menu" href="configuracoes.jsp">
      <i class="material-icons">settings</i>Configurações
    </a>
  </nav>

  <a class="config" href="../servletLogout">
    <i class="material-icons">logout</i>Sair
  </a>
</aside>

<main class="main">
  <header class="topbar">
    <div class="date">
      <i class="material-icons">admin_panel_settings</i>
      Área Administrativa
    </div>

    <div class="avatar">
      <img src="https://i.pravatar.cc/45?img=5">
      <span><%= nome %></span>
    </div>
  </header>

  <div class="page-header">
    <h1>Gerenciar Turmas</h1>
    <a href="../servletTurma?acao=novo" class="btn-primary">
      <i class="material-icons">add</i> Nova Turma
    </a>
  </div>

  <div class="card">
    <table>
      <thead>
      <tr>
        <th>Nome</th>
        <th>Ano</th>
        <th>Qtd Alunos</th>
        <th>Ações</th>
      </tr>
      </thead>
      <tbody>

      <%
        if (listaTurmas != null && !listaTurmas.isEmpty()) {
          for (Turma turma : listaTurmas) {
      %>
      <tr>
        <td><%= turma.getNome() %></td>
        <td><%= turma.getAno() %></td>
        <td><%= turma.getQuantidadeAlunos() %></td>
        <td>
          <a href="../servletTurma?acao=editar&id=<%= turma.getId() %>" class="icon-btn edit">
            edit
          </a>
          <a href="../servletTurma?acao=excluir&id=<%= turma.getId() %>"
             class="icon-btn delete"
             onclick="return confirm('Deseja realmente excluir esta turma?')">
            delete
          </a>
        </td>
      </tr>
      <%
        }
      } else {
      %>
      <tr>
        <td colspan="4" style="text-align:center;">Nenhuma turma cadastrada.</td>
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