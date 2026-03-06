<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String nome = (String) session.getAttribute("adminNome");
  String anoLetivo = (String) session.getAttribute("anoLetivo");
  String limite = (String) session.getAttribute("limiteAlunos");
  Boolean matriculas = (Boolean) session.getAttribute("matriculasAbertas");
  String status = request.getParameter("status");
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <title>Configurações do Sistema</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/configuracoes_a.css">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>

<aside class="sidebar">
  <div class="logo"><i class="material-icons">admin_panel_settings</i><span>Painel ADM</span></div>
  <nav>
    <a class="menu" href="${pageContext.request.contextPath}/views/inicio_a.jsp"><i class="material-icons">home</i>Inicio</a>
    <a class="menu" href="${pageContext.request.contextPath}/AlunoAdminServlet"><i class="material-icons">groups</i>Alunos</a>
    <a class="menu" href="${pageContext.request.contextPath}/ProfessorAdminServlet"><i class="material-icons">badge</i>Professores</a>
    <a class="menu" href="${pageContext.request.contextPath}/TurmaAdmServlet"><i class="material-icons">school</i>Turmas</a>
    <a class="menu" href="${pageContext.request.contextPath}/DisciplinaAdminServlet"><i class="material-icons">menu_book</i>Disciplinas</a>
    <a class="menu active" href="${pageContext.request.contextPath}/servletConfiguracoes"><i class="material-icons">settings</i>Configurações</a>
  </nav>
  <a class="config" href="${pageContext.request.contextPath}/servletLogout"><i class="material-icons">logout</i>Sair</a>
</aside>

<main class="main">
  <header class="topbar">
    <div class="date"><i class="material-icons">settings</i> Configurações Avançadas</div>
    <div class="avatar">
      <img src="https://i.pravatar.cc/45?img=5">
      <span><%= (nome != null) ? nome : "Admin" %></span>
    </div>
  </header>

  <div class="card" style="height: auto; margin-top: 20px; padding-bottom: 20px;">
    <h2>Parâmetros do Sistema</h2>

    <% if ("success".equals(status)) { %>
    <p style="color: green; margin-bottom: 15px; font-weight: 600;">✓ Alterações salvas com sucesso!</p>
    <% } %>

    <form action="${pageContext.request.contextPath}/servletConfiguracoes" method="post">

      <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">
        <div class="form-group">
          <label>Ano Letivo Atual</label>
          <input type="text" name="anoLetivo" placeholder="Ex: 2026" value="<%= anoLetivo != null ? anoLetivo : "2026" %>">
        </div>

        <div class="form-group">
          <label>Máximo de Alunos/Turma</label>
          <input type="number" name="limiteAlunos" value="<%= limite != null ? limite : "40" %>">
        </div>
      </div>

      <div class="form-group" style="flex-direction: row; align-items: center; gap: 10px; margin: 10px 0;">
        <input type="checkbox" name="matriculasAbertas" style="width: 20px; height: 20px;" <%= (matriculas != null && matriculas) ? "checked" : "" %>>
        <label style="margin-bottom: 0;">Permitir Novas Matrículas no Sistema</label>
      </div>

      <hr style="margin: 20px 0; border: 0; border-top: 1px solid #eee;">

      <div class="form-group">
        <label>Redefinir Senha do Administrador</label>
        <input type="password" name="novaSenha" placeholder="Digite apenas se desejar alterar">
      </div>

      <button type="submit" class="btn-primary" style="width: 100%; justify-content: center;">
        <i class="material-icons">save</i> Aplicar Configurações
      </button>
    </form>
  </div>
</main>

</body>
</html>