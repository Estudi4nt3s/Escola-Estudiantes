<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
  String nome = (String) session.getAttribute("adminNome");
//    if (tipo == null || !tipo.equals("admin")) {
//        response.sendRedirect("cadastro.jsp");
//        return;
// }
%>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
  <meta charset="UTF-8">
  <title>Estudiantes - Painel Administrativo</title>
  <link rel="icon" href="${pageContext.request.contextPath}/utils/school.png">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/configuracoes_a.css">
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
      <i class="material-icons">home</i>Início
    </a>

    <a class="menu" href="${pageContext.request.contextPath}/AlunoAdminServlet">
      <i class="material-icons">groups</i>Alunos
    </a>

    <a class="menu" href="${pageContext.request.contextPath}/TurmaAdminServlet">
      <i class="material-icons">school</i>Turmas
    </a>

    <a class="menu" href="${pageContext.request.contextPath}/DisciplinaAdminServlet">
      <i class="material-icons">menu_book</i>Disciplinas
    </a>

    <a class="menu active" href="${pageContext.request.contextPath}/servletConfiguracoes">
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

  <div class="card">
    <h2>Configurações do Sistema</h2>

    <form action="${pageContext.request.contextPath}/servletConfiguracoes" method="post">
      <div class="form-group">
        <label>Nome da Escola</label>
        <input type="text" name="nomeEscola" placeholder="Digite o nome">
      </div>

      <div class="form-group">
        <label>Cor Principal</label>
        <input type="color" name="corPrincipal">
      </div>

      <div class="form-group">
        <label>Alterar Senha</label>
        <input type="password" name="novaSenha" placeholder="Nova senha">
      </div>

      <button type="submit" class="btn-primary">
        Salvar Alterações
      </button>

    </form>
  </div>

</main>

</body>
</html>