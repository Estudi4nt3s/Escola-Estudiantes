<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="com.sistema.estudiantes.model.RankingDTO" %>
<%
  // Recuperando dados do Servlet
  String nome = (String) session.getAttribute("adminNome");
  List<RankingDTO> ranking = (List<RankingDTO>) request.getAttribute("rankingReal");
  Integer totalAlunos = (Integer) request.getAttribute("totalAlunos");
  Double mediaGeralObj = (Double) request.getAttribute("mediaGeral");

  // Tratando a média para evitar erro de Cast
  String mediaGeral = (mediaGeralObj != null) ? String.format("%.2f", mediaGeralObj) : "0.00";

  String status = request.getParameter("status");
  String tipo = (String) session.getAttribute("tipoUsuario");

  if (tipo == null || !tipo.equals("admin")) {
    response.sendRedirect("cadastro.jsp");
    return;
  }
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
  <title>Configurações Avançadas | ADM</title>
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
      <i class="material-icons">home</i><span>Inicio</span>
    </a>
    <a class="menu" href="${pageContext.request.contextPath}/AlunoAdminServlet">
      <i class="material-icons">groups</i><span>Alunos</span>
    </a>
    <a class="menu" href="${pageContext.request.contextPath}/ProfessorAdminServlet">
      <i class="material-icons">badge</i><span>Professores</span>
    </a>
    <a class="menu" href="${pageContext.request.contextPath}/TurmaAdmServlet">
      <i class="material-icons">school</i><span>Turmas</span>
    </a>
    <a class="menu" href="${pageContext.request.contextPath}/DisciplinaAdminServlet">
      <i class="material-icons">menu_book</i><span>Disciplinas</span>
    </a>
    <a class="menu" href="${pageContext.request.contextPath}/ChatIAServlet">
      <i class="material-icons">psychology</i><span>IA Administrativa</span>
    </a>
    <a class="menu active" href="${pageContext.request.contextPath}/servletConfiguracoes">
      <i class="material-icons">settings</i><span>Configurações</span>
    </a>
  </nav>
  <a class="config" href="${pageContext.request.contextPath}/servletLogout">
    <i class="material-icons">logout</i><span>Sair</span>
  </a>
</aside>

<main class="main">
  <header class="topbar">
    <div class="date"><i class="material-icons">admin_panel_settings</i>Área Administrativa</div>
    <div class="avatar">
      <img src="${pageContext.request.contextPath}/utils/perfil_adm.jpg">
      <span><%= (nome != null) ? nome : "Admin" %></span>
    </div>
  </header>

  <div class="content">
    <div class="card profile-header" style="display: flex; align-items: center; gap: 25px; margin-bottom: 20px;">
      <div class="profile-avatar">
        <img src="https://i.pravatar.cc/100?img=5" style="width: 80px; height: 80px; border-radius: 50%; border: 3px solid var(--red);">
      </div>
      <div class="profile-info">
        <h2 style="margin: 0; color: #333;"><%= (nome != null) ? nome : "Administrador Geral" %></h2>
        <div style="display: flex; gap: 10px; margin-top: 5px;">
          <span style="background: #e8f5e9; color: #2e7d32; padding: 4px 10px; border-radius: 20px; font-size: 11px; font-weight: bold;">SISTEMA ATIVO</span>
          <span style="background: #e3f2fd; color: #1565c0; padding: 4px 10px; border-radius: 20px; font-size: 11px; font-weight: bold;">ROOT</span>
        </div>
      </div>
    </div>

    <div class="action-grid">
      <div class="card action-btn" onclick="abrirModal('modalMetricas')">
        <i class="material-icons" style="color:#3498db">analytics</i>
        <h4>Métricas</h4>
      </div>
      <div class="card action-btn" onclick="abrirModal('modalRanking')">
        <i class="material-icons" style="color:#f1c40f">star</i>
        <h4>Ranking</h4>
      </div>
      <div class="card action-btn" onclick="abrirModal('modalRelatorios')">
        <i class="material-icons" style="color:#9b59b6">history_edu</i>
        <h4>Relatórios</h4>
      </div>
      <div class="card action-btn" onclick="abrirModal('modalComunicados')">
        <i class="material-icons" style="color:#e74c3c">campaign</i>
        <h4>Comunicados</h4>
      </div>
    </div>
    <div class="card" style="margin-top: 20px;">
      <h3><i class="material-icons">bar_chart</i> Painel de Desempenho</h3>
      <div style="display: flex; gap: 40px; justify-content: space-around; padding: 20px;">

        <div class="chart-container" style="width: 45%;">
          <canvas id="graficoNotas"></canvas>
        </div>

        <div class="chart-container" style="width: 45%;">
          <canvas id="graficoVinculacao"></canvas>
        </div>

      </div>
    </div>
  </div>
</main>

<div id="modalMetricas" class="overlay" style="display:none">
  <div class="modal">
    <h2><i class="material-icons">analytics</i> Estatísticas Gerais</h2>
    <div style="margin: 20px 0;">
      <p>Total de Alunos Matriculados: <b><%= (totalAlunos != null) ? totalAlunos : "Carregando..." %></b></p>
      <p>Média Geral da Instituição: <b><%= mediaGeral %></b></p>
    </div>
    <button onclick="fecharModal('modalMetricas')" class="btn-primary" style="width:100%">Fechar</button>
  </div>
</div>

<div id="modalRanking" class="overlay" style="display:none">
  <div class="modal">
    <h2><i class="material-icons">star</i> Top 3 Alunos</h2>
    <table style="margin: 20px 0; width: 100%; border-collapse: collapse;">
      <thead>
      <tr style="text-align: left; border-bottom: 2px solid #eee;">
        <th>Aluno</th>
        <th>Média</th>
      </tr>
      </thead>
      <tbody>
      <%
        if(ranking != null && !ranking.isEmpty()) {
          for(RankingDTO r : ranking) { // Corrigido para RankingDTO
      %>
      <tr style="border-bottom: 1px solid #eee;">
        <td style="padding: 10px 0;"><%= r.getNome() %></td>
        <td style="color: var(--red); font-weight: bold;"><%= String.format("%.2f", r.getMedia()) %></td>
      </tr>
      <%
        }
      } else {
      %>
      <tr><td colspan="2" style="padding: 10px 0; text-align: center;">Nenhum dado disponível.</td></tr>
      <% } %>
      </tbody>
    </table>
    <button onclick="fecharModal('modalRanking')" class="btn-primary" style="width:100%">Fechar</button>
  </div>
</div>

<div id="modalRelatorios" class="overlay" style="display:none">
  <div class="modal" style="max-width: 600px; padding: 30px;">

    <div style="text-align: center; border-bottom: 2px solid #f0f0f0; padding-bottom: 20px; margin-bottom: 20px;">
      <i class="material-icons" style="font-size: 50px; color: #9b59b6;">picture_as_pdf</i>
      <h2 style="margin: 10px 0 5px;">Boletim Institucional</h2>
      <p style="color: #666; font-size: 14px; margin: 0;">Relatório de Desempenho Acadêmico - 2026</p>
    </div>

    <div style="margin-bottom: 25px;">
      <h4 style="color: #333; margin-bottom: 10px;">Conteúdo do Documento:</h4>
      <ul style="color: #555; font-size: 14px; line-height: 1.6; padding-left: 20px;">
        <li><b>Desempenho por Turma:</b> Médias agrupadas por sala.</li>
        <li><b>Visão da Disciplina:</b> Notas detalhadas por matéria.</li>
        <li><b>Corpo Docente:</b> Professores responsáveis por cada disciplina.</li>
        <li><b>Indicadores:</b> Número de alunos avaliados por turma.</li>
      </ul>
    </div>

    <a href="gerarRelatorio" class="btn-primary"
       style="text-decoration:none; display:flex; align-items:center; justify-content:center; gap: 10px; height: 50px; background-color: #9b59b6; border-radius: 8px;">
      <i class="material-icons">file_download</i> Gerar Boletim em PDF
    </a>

    <button onclick="fecharModal('modalRelatorios')"
            style="width: 100%; margin-top: 15px; background: none; border: none; cursor: pointer; color: #888; font-size: 14px;">
      Cancelar e Voltar
    </button>
  </div>
</div>

<div id="modalComunicados" class="overlay" style="display:none">
  <div class="modal">
    <h2><i class="material-icons">campaign</i> Enviar Comunicado</h2>
    <div class="form-group" style="margin-top: 15px;">
      <label>Assunto do Aviso</label>
      <input type="text" placeholder="Ex: Reunião de Pais">
      <label style="margin-top:10px;">Mensagem</label>
      <textarea style="width: 100%; border-radius: 10px; padding: 10px; border: 1px solid #ddd; height: 80px;"></textarea>
    </div>
    <button class="btn-primary" style="width:100%; margin-top:10px;">Disparar para Todos</button>
    <button onclick="fecharModal('modalComunicados')" class="btn-cancelar" style="width:100%; border:none; background:none; margin-top:10px; cursor:pointer; color:#888;">Cancelar</button>
  </div>
</div>

<script>
  window.onload = function() {
    // Gráfico de Vinculação
    new Chart(document.getElementById('graficoVinculacao'), {
      type: 'doughnut',
      data: {
        labels: ['Vinculados', 'Pendentes'],
        datasets: [{
          data: [<%= request.getAttribute("totalVinculados") %>, <%= request.getAttribute("totalPendentes") %>],
          backgroundColor: ['#27ae60', '#f39c12']
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false, // Importante para respeitar o height da div
        plugins: { legend: { position: 'bottom' } }
      }
    });

    // Gráfico de Barras
    new Chart(document.getElementById('graficoNotas'), {
      type: 'bar',
      data: {
        labels: ['Média Geral'],
        datasets: [{
          label: 'Nota Média',
          data: [<%= mediaGeralObj %>],
          backgroundColor: '#d11d22'
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: { y: { beginAtZero: true, max: 10 } }
      }
    });
  };
  function abrirModal(id) { document.getElementById(id).style.display = 'flex'; }
  function fecharModal(id) { document.getElementById(id).style.display = 'none'; }
  window.onclick = function(event) { if (event.target.className === 'overlay') event.target.style.display = "none"; }
</script>

</body>
</html>