<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
JA FIZ OS SERVLETS ( EU ACHO VERIFICAR )
ASS GUSTAVO
--%>
<%
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
  <title>Painel Administrativo</title>
  <link rel="stylesheet" href="../css/configuracoes.css">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>

<body>

<aside class="sidebar">
  <div class="logo">
    <i class="material-icons">admin_panel_settings</i>
    <span>Painel ADM</span>
  </div>

  <nav>
    <a class="menu active" href="admin.jsp">
      <i class="material-icons">home</i>Início
    </a>

    <a class="menu" href="gerenciarAlunos.jsp">
      <i class="material-icons">groups</i>Alunos
    </a>

    <a class="menu" href="gerenciarTurmas.jsp">
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

  <div class="card">
    <h2>Configurações do Sistema</h2>

    <form action="../servletConfiguracoes" method="post">

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