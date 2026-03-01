<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sistema.estudiantes.model.Disciplina" %>
<%--
DisciplinaDAO dao = new DisciplinaDAO();
List<Disciplina> lista = dao.listarTodasComRelacionamentos();

request.setAttribute("listaDisciplinas", lista);
request.getRequestDispatcher("views/gerenciarDisciplinas.jsp")
       .forward(request, response);
       TEM QUE FAZER O SERVLET
ASS GUSTAVO

--%>

<%
  List<Disciplina> listaDisciplinas = (List<Disciplina>) request.getAttribute("listaDisciplinas");
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
  <title>Gerenciar Disciplinas</title>
  <link rel="stylesheet" href="../css/gerenciarDisciplinas.css">
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

    <a class="menu" href="gerenciarTurmas.jsp">
      <i class="material-icons">school</i>Turmas
    </a>

    <a class="menu active" href="gerenciarDisciplinas.jsp">
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
    <h1>Gerenciar Disciplinas</h1>
    <a href="../servletDisciplina?acao=novo" class="btn-primary">
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
          for (Disciplina d : listaDisciplinas) {
      %>
      <tr>
        <td><%= d.getNome() %></td>
        <td><%= d.getProfessorNome() %></td>
        <td><%= d.getCargaHoraria() %>h</td>
        <td><%= d.getTurmaNome() %></td>
        <td>
          <a href="../servletDisciplina?acao=editar&id=<%= d.getId() %>"
             class="icon-btn edit">
            <i class="material-icons">edit</i>
          </a>

          <a href="../servletDisciplina?acao=excluir&id=<%= d.getId() %>"
             class="icon-btn delete"
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