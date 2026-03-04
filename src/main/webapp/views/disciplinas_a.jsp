<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sistema.estudiantes.dao.DisciplinaDAO" %>
<%@ page import="com.sistema.estudiantes.model.DisciplinasAdm" %>

<%
  String tipo = (String) session.getAttribute("tipoUsuario");
  String nome = (String) session.getAttribute("adminNome");
//
//  if (tipo == null || !tipo.equals("admin")) {
//    response.sendRedirect("../login.jsp");
//    return;
//  }

  List<DisciplinasAdm> listaDisciplinas =
          (List<DisciplinasAdm>) request.getAttribute("listaDisciplinas");
%>


<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <title>Gerenciar Disciplinas</title>
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
      <i class="material-icons">home</i>Inicio
    </a>

    <a class="menu" href="${pageContext.request.contextPath}/AlunoAdminServlet">
      <i class="material-icons">groups</i>Alunos
    </a>

    <a class="menu" href="${pageContext.request.contextPath}/TurmaAdminServlet">
      <i class="material-icons">school</i>Turmas
    </a>

    <a class="menu active" href="${pageContext.request.contextPath}/DisciplinaAdminServlet">
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
      <i class="material-icons">admin_panel_settings</i>
      Área Administrativa
    </div>

    <div class="avatar">
      <img src="https://i.pravatar.cc/45?img=5">
      <span><%= nome %></span>
    </div>
  </header>

  <div class="page-header">
    <h1>Gerenciar Disciplinas</h1>
    <a href="${pageContext.request.contextPath}/DisciplinaAdminServlet?acao=novo"
       class="btn-primary">
      <i class="material-icons">add</i>
      Nova Disciplina
    </a>
  </div>

  <div class="card">
    <table>
      <thead>
      <tr>
        <th>Nome</th>
        <th>Professor</th>
        <th>Carga Horária</th>
        <th>Turma</th>
        <th>Ações</th>
      </tr>
      </thead>
      <tbody>

      <%
        if (listaDisciplinas != null && !listaDisciplinas.isEmpty()) {
          for (DisciplinasAdm d : listaDisciplinas) {
      %>
      <tr>
        <td><%= d.getNome() %></td>
        <td><%= d.getProfessorNome() %></td>
        <td><%= d.getCargaHoraria() %>h</td>
        <td><%= d.getTurmaNome() %></td>
        <td>
          <a href="${pageContext.request.contextPath}/DisciplinaAdminServlet?acao=editar&id=<%= d.getId() %>"             class="icon-btn edit">
            <i class="material-icons">edit</i>
          </a>

          <a href="${pageContext.request.contextPath}/DisciplinaAdminServlet?acao=excluir&id=<%= d.getId() %>"             class="icon-btn delete"
             onclick="return confirm('Deseja realmente excluir esta disciplina?')">
            <i class="material-icons">delete</i>
          </a>
        </td>
      </tr>
      <%
        }
      } else {
      %>
      <tr>
        <td colspan="5" style="text-align:center;">
          Nenhuma disciplina cadastrada.
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