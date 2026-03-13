<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sistema.estudiantes.model.DisciplinasAdm" %>
<%@ page import="com.sistema.estudiantes.model.TurmaAdm" %>

<%
  String nome = (String) session.getAttribute("adminNome");
  List<DisciplinasAdm> listaDisciplinas = (List<DisciplinasAdm>) request.getAttribute("listaDisciplinas");

  String acao = request.getParameter("acao");
  DisciplinasAdm dEdit = (DisciplinasAdm) request.getAttribute("disciplinaEditar");

  boolean abrirModalForm = "novo".equals(acao) || "editar".equals(acao);
  boolean abrirModalExcluir = "pre-excluir".equals(acao);
    List<com.sistema.estudiantes.model.Professor> todosProfessores = (List<com.sistema.estudiantes.model.Professor>) request.getAttribute("listaProfessoresAuto");
    List<TurmaAdm> todasTurmas = (List<TurmaAdm>) request.getAttribute("listaTurmasAuto");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <title>Estudiantes - Gerenciar Disciplinas</title>
  <link rel="icon" href="${pageContext.request.contextPath}/utils/school.png">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/disciplinas_a.css">
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
    <a class="menu active" href="${pageContext.request.contextPath}/DisciplinaAdminServlet"><i class="material-icons">menu_book</i>Disciplinas</a>
    <a class="menu" href="${pageContext.request.contextPath}/servletConfiguracoes"><i class="material-icons">settings</i>Configurações</a>
  </nav>
  <a class="config" href="${pageContext.request.contextPath}/servletLogout"><i class="material-icons">logout</i>Sair</a>
</aside>

<main class="main">
  <header class="topbar">
    <div class="date"><i class="material-icons">admin_panel_settings</i>Área Administrativa</div>
    <div class="avatar">
      <img src="https://i.pravatar.cc/45?img=5">
      <span><%= (nome != null) ? nome : "Admin" %></span>
    </div>
  </header>
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
      <tr><td colspan="5" style="text-align:center;">Nenhuma disciplina cadastrada.</td></tr>
      <% } %>
      </tbody>
    </table>
  </div>
  <%-- MODAL DE FORMULÁRIO (CADASTRO/EDIÇÃO) --%>
  <% if (abrirModalForm) { %>
  <div class="overlay" style="position: fixed; top: 0; left: 0; width: 100vw; height: 100vh; background: rgba(0,0,0,0.7); backdrop-filter: blur(5px); display: flex; align-items: center; justify-content: center; z-index: 10000;">
    <div class="modal" style="background: white; width: 90%; max-width: 450px; padding: 35px; border-radius: 25px; box-shadow: 0 20px 50px rgba(0,0,0,0.3); position: relative;">
      <h2 style="margin-bottom: 25px; text-align: center; color: #222;"><%= "editar".equals(acao) ? "Editar Disciplina" : "Nova Disciplina" %></h2>

      <form action="DisciplinaAdminServlet" method="post">
        <% if (dEdit != null) { %><input type="hidden" name="id" value="<%= dEdit.getId() %>"><% } %>

        <div class="form-group" style="margin-bottom: 15px; display: flex; flex-direction: column;">
          <label style="font-weight: 600; margin-bottom: 8px;">Nome da Disciplina</label>
          <input type="text" name="nome" id="inputNomeDisc" placeholder="Ex: Matemática"
                 style="padding: 12px; border: 1px solid #ddd; border-radius: 10px;"
                 value="<%= dEdit != null ? dEdit.getNome() : "" %>" required>
          <small id="feedbackDisc" style="color: #666; display:block; margin-top:5px;">Verificando..</small>
        </div>

          <div class="form-group" style="margin-bottom: 15px; display: flex; flex-direction: column;">
              <label style="font-weight: 600; margin-bottom: 8px;">Professor</label>
              <input type="text" name="professorNome" list="listaProfs"
                     style="padding: 12px; border: 1px solid #ddd; border-radius: 10px;"
                     value="<%= dEdit != null ? dEdit.getProfessorNome() : "" %>" required>
              <datalist id="listaProfs">
                  <% if (todosProfessores != null) {
                      for (com.sistema.estudiantes.model.Professor p : todosProfessores) { %>
                  <option value="<%= p.getNome() %>" />
                  <%  }
                  } %>
              </datalist>
          </div>
        <div class="modal-buttons" style="display: flex; justify-content: flex-end; gap: 10px; margin-top: 25px;">
          <button type="submit" name="acao" value="<%= acao %>" id="btnSalvar" class="btn-primary">Salvar</button>
          <a href="DisciplinaAdminServlet" class="btn-cancelar" style="padding: 12px 20px; background: #eee; border-radius: 10px; text-decoration: none; color: #666; font-weight: 600;">Cancelar</a>
        </div>
      </form>
    </div>
  </div>
  <% } %>

  <% if (abrirModalExcluir && dEdit != null) { %>
  <div class="overlay" style="position: fixed; top: 0; left: 0; width: 100vw; height: 100vh; background: rgba(0,0,0,0.7); backdrop-filter: blur(5px); display: flex; align-items: center; justify-content: center; z-index: 10000;">
    <div class="modal" style="background: white; width: 90%; max-width: 400px; padding: 35px; border-radius: 25px; box-shadow: 0 20px 50px rgba(0,0,0,0.3); text-align: center;">
      <i class="material-icons" style="font-size: 56px; color: #e74c3c; margin-bottom: 15px;">warning</i>
      <h2 style="margin-bottom: 10px;">Confirmar Exclusão</h2>
      <p style="color: #666; margin-bottom: 25px;">Remover a disciplina <strong><%= dEdit.getNome() %></strong>?</p>

      <form action="DisciplinaAdminServlet" method="post">
        <input type="hidden" name="id" value="<%= dEdit.getId() %>">
        <div class="modal-buttons" style="display: flex; justify-content: center; gap: 10px;">
          <button type="submit" name="acao" value="excluir" class="btn-primary" style="background: #e74c3c;">Sim, Remover</button>
          <a href="DisciplinaAdminServlet" class="btn-cancelar" style="padding: 12px 20px; background: #eee; border-radius: 10px; text-decoration: none; color: #666; font-weight: 600;">Cancelar</a>
        </div>
      </form>
    </div>
  </div>
  <% } %>
  <%-- MODAL DE CADASTRO DE PROFESSOR (SÓ ABRE SE NÃO EXISTIR) --%>
  <div id="modalProfessor" class="overlay" style="position: fixed; top: 0; left: 0; width: 100vw; height: 100vh; background: rgba(0,0,0,0.8); backdrop-filter: blur(5px); display: none; align-items: center; justify-content: center; z-index: 11000;">
    <div class="modal" style="background: white; width: 90%; max-width: 400px; padding: 35px; border-radius: 25px; box-shadow: 0 20px 50px rgba(0,0,0,0.3);">
      <h2 style="text-align: center; color: #d11d22;">Professor não encontrado!</h2>
      <p style="text-align: center; color: #666; margin-bottom: 20px;">Deseja cadastrar <strong id="nomeProfNaoEncontrado"></strong> agora?</p>

      <form id="formNovoProfessor">
        <div class="form-group" style="margin-bottom: 15px; display: flex; flex-direction: column;">
          <label style="font-weight: 600;">E-mail do Professor</label>
          <input type="email" id="emailNovoProf" style="padding: 12px; border: 1px solid #ddd; border-radius: 10px;" required>
        </div>
        <div class="form-group" style="margin-bottom: 15px; display: flex; flex-direction: column;">
          <label style="font-weight: 600;">Sobrenome</label>
          <input type="text" id="sobrenomeNovoProf" style="padding: 12px; border: 1px solid #ddd; border-radius: 10px;" required>
        </div>
        <div class="modal-buttons" style="display: flex; justify-content: center; gap: 10px;">
          <button type="button" onclick="cadastrarProfessorAjax()" class="btn-primary">Criar Professor</button>
          <button type="button" onclick="document.getElementById('modalProfessor').style.display='none'" class="btn-cancelar" style="padding: 12px 20px; background: #eee; border-radius: 10px; border:none; cursor:pointer;">Voltar</button>
        </div>
      </form>
    </div>
  </div>

  <script>
    // Monitorar o campo de Professor no modal de Disciplina
    const inputProfessor = document.querySelector('input[name="professorNome"]');
    const btnSalvarDisciplina = document.getElementById('btnSalvar');

    if(inputProfessor) {
      inputProfessor.addEventListener('blur', function() {
        const nome = this.value.trim();
        if(nome.length < 3) return;

        // Chamada para verificar se professor existe
        fetch('DisciplinaAdminServlet?acao=verificarProfessor&nome=' + encodeURIComponent(nome))
                .then(res => res.json())
                .then(data => {
                  if(!data.existe) {
                    document.getElementById('nomeProfNaoEncontrado').innerText = nome;
                    document.getElementById('modalProfessor').style.display = 'flex';
                    btnSalvarDisciplina.disabled = true;
                  } else {
                    btnSalvarDisciplina.disabled = false;
                  }
                });
      });
    }

    function cadastrarProfessorAjax() {
      const nome = document.getElementById('nomeProfNaoEncontrado').innerText;
      const email = document.getElementById('emailNovoProf').value;
      const sobrenome = document.getElementById('sobrenomeNovoProf').value;

      if(!email || !sobrenome) { alert("Preencha os dados do professor!"); return; }

      const params = new URLSearchParams();
      params.append('acao', 'criarProfessorRapido');
      params.append('nome', nome);
      params.append('sobrenome', sobrenome);
      params.append('email', email);

      fetch('DisciplinaAdminServlet', {
        method: 'POST',
        body: params
      }).then(res => {
        if(res.ok) {
          alert("Professor criado e vinculado!");
          document.getElementById('modalProfessor').style.display = 'none';
          btnSalvarDisciplina.disabled = false;
        }
      });
    }
  </script>
</main>

<script>
  document.addEventListener('DOMContentLoaded', function() {
    const inputNome = document.getElementById('inputNomeDisc');
    const feedback = document.getElementById('feedbackDisc');
    const btnSalvar = document.getElementById('btnSalvar');

    if (!inputNome) return;

    const regexDisc = /^[A-ZÀ-Ú][a-zà-ú0-9\s]{2,30}$/;

    function validar() {
      const valor = inputNome.value.trim();
      if (valor.length === 0) {
        feedback.innerText = "O nome não pode estar vazio.";
        feedback.style.color = "#e74c3c";
        btnSalvar.disabled = true;
      } else if (regexDisc.test(valor)) {
        feedback.innerText = "Nome válido!";
        feedback.style.color = "#27ae60";
        btnSalvar.disabled = false;
      } else {
        feedback.innerText = "Inicie com maiúscula e use entre 3 a 30 caracteres.";
        feedback.style.color = "#e74c3c";
        btnSalvar.disabled = true;
      }
    }
    inputNome.addEventListener('input', validar);
    if (inputNome.value.length > 0) validar();
  });
</script>
</body>
</html>