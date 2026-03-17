<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sistema.estudiantes.model.Turma" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.time.format.TextStyle" %>
<%@ page import="com.sistema.estudiantes.model.Professor" %>
<%
    String busca = "";
    if (request.getParameter("busca") != null) {
        busca = request.getParameter("busca");
    }
    @SuppressWarnings("unchecked")
    List<Turma> turmas = (List<Turma>) request.getSession().getAttribute("turmas");
    Professor professor = (Professor) request.getSession().getAttribute("professor");
    LocalDate hoje = LocalDate.now();
    String dia = String.format("%02d",hoje.getDayOfMonth());
    String mes = String.format("%02d",hoje.getMonthValue());
    Locale ptBr = new Locale("pt", "BR");
    String semana = hoje.getDayOfWeek().getDisplayName(TextStyle.SHORT, ptBr).toUpperCase().substring(0, 3);
%>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Estudiantes - Início</title>
    <link rel="icon" href="${pageContext.request.contextPath}/utils/school.png">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/turmas.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/carregar.css">
</head>

<body>

<aside class="sidebar">
    <div class="logo">
        <i class="material-icons">school</i>
        <span>Colégio Estudiantes</span>
    </div>

    <nav>
        <a class="menu" href="home_p.jsp"><i class="material-icons">home</i>Início</a>
        <a class="menu" href="${pageContext.request.contextPath}/views/calendario_p.jsp"><i class="material-icons">calendar_month</i>Calendário</a>
        <a class="menu active">
            <i class="material-icons">groups</i>Turmas</a>
        <a class="menu" href="${pageContext.request.contextPath}/views/perfil_p.jsp"><i class="material-icons">person</i>Perfil</a>
    </nav>
    <div class="config">
        <a class="menu" style="color: #ffffff" onclick="openLogoutModal()">
            <i class="material-icons" style="color: #ffffff">output</i>Sair
        </a>
    </div>
</aside>

<main class="main">

    <header class="topbar">
        <div class="date">
            <i class="material-icons">calendar_today</i>
            <%=semana.toUpperCase().charAt(0) + semana.toLowerCase().substring(1) + ", " + dia + "/" + mes%>
        </div>

        <div class="user">
            <div class="avatar">
<%--                <a href="${pageContext.request.contextPath}/views/perfil_p.jsp"><img src="${pageContext.request.contextPath}/utils/perfil.png" alt="avatar"></a>--%>
                <span><%=professor.getNome()%></span>
            </div>
        </div>
    </header>

    <div class="main-content">
        <div class="turmas-topo">
            <div class="turmas-titulo">
                Turmas
            </div>

            <form method="get" action="${pageContext.request.contextPath}/turma" class="barra-pesquisa">
                <i class="material-icons">search</i>
                <input type="text" id="inputBusca" name="busca" placeholder="Pesquise a turma" value="<%= busca %>">
            </form>
        </div>

        <div class="turmas">
            <%
                if (turmas != null && !turmas.isEmpty()) {
                    for (Turma turma : turmas) {
            %>
            <div class="turmas-card">
                <h3><%= turma.getNome() %></h3>
                <a href="${pageContext.request.contextPath}/aluno?sub_acao=buscar_todos&id=<%= turma.getId() %>"
                   onclick="document.getElementById('loadingOverlay').style.display='flex'">
                    <i class="material-icons">arrow_forward</i>
                </a>
            </div>
            <%
                }
            } else {
            %>
            <p>Nenhuma turma encontrada.</p>
            <%
                }
            %>
        </div>
    </div>
</main>
<!-- Overlay -->
<div class="notification-overlay" id="notificationOverlay">

    <div class="notification-modal">

        <div class="notification-modal-header">
            <h2>Notificações</h2>
            <button id="closeNotificationModal">✕</button>
        </div>

        <ul class="notification-modal-list">
            <li class="denied">

                <div class="notification-content">
                    <strong>Avaliação de Matemática</strong>
                    <span>Status: Negado</span>
                </div>

                <button class="confirm-btn">Confirmar</button>
            </li>

            <li class="denied">
                <div class="notification-content">
                    <strong>Avaliação 2ª Guerra</strong>
                    <span>Status: Negado</span>
                </div>

                <button class="confirm-btn">Confirmar</button>
            </li>


            <li class="denied">
                <div class="notification-content">
                    <strong>Trabalho de Geografia</strong>
                    <span>Status: Negado</span>
                </div>

                <button class="confirm-btn">Confirmar</button>
            </li>
        </ul>

    </div>
</div>

<script src="${pageContext.request.contextPath}/js/notificacoes.js"></script>
<div id="loadingOverlay">
    <div class="loadingBox">
        <div class="spinner"></div>
        <p>Carregando...</p>
    </div>
</div>
<div id="logoutModal" class="logout-modal-overlay" onclick="closeLogoutModal()">
    <div class="logout-modal-content" onclick="event.stopPropagation()">
        <div class="logout-icon">
            <i class="material-icons">help_outline</i>
        </div>
        <h2>Confirmar Saída</h2>
        <p>Deseja encerrar sua sessão no sistema?</p>
        <div class="logout-buttons">
            <button class="btn-cancel" onclick="closeLogoutModal()">Cancelar</button>
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn-confirm">Sim, Sair</a>
        </div>
    </div>
</div>
<script>
    const loading = document.getElementById("loadingOverlay");
    const inputBusca = document.getElementById("inputBusca");
    const formBusca = document.querySelector(".barra-pesquisa");
    const btnNotas = document.getElementById("btnNotas"); // Pode ser null nesta página

    // --- FILTRO DA BARRA DE PESQUISA (A NOVIDADE) ---
    if (inputBusca) {
        // Bloqueia o recarregamento da página ao apertar ENTER
        if (formBusca) {
            formBusca.addEventListener("submit", (e) => e.preventDefault());
        }

        inputBusca.addEventListener("input", function() {
            const termo = inputBusca.value.toLowerCase().trim();
            const cards = document.querySelectorAll(".turmas-card");

            cards.forEach(card => {
                const nomeTurma = card.querySelector("h3").textContent.toLowerCase();
                // Se encontrar o termo, exibe como flex (estilo original), se não, esconde
                card.style.display = nomeTurma.includes(termo) ? "flex" : "none";
            });
        });
    }

    // --- FUNÇÕES DE LOGOUT (MANTER FUNCIONANDO) ---
    function openLogoutModal() {
        document.getElementById('logoutModal').classList.add('show');
    }

    function closeLogoutModal() {
        document.getElementById('logoutModal').classList.remove('show');
    }

    // --- COMPORTAMENTO DO LOADING (PROTEGIDO) ---
    // Só adiciona o evento se o btnNotas realmente existir na tela
    if (btnNotas) {
        btnNotas.addEventListener("click", function(e) {
            e.preventDefault();
            loading.style.display = "flex";
            setTimeout(() => { window.location.href = this.href; }, 500);
        });
    }

    // Garante que o loading suma ao voltar no navegador
    window.addEventListener("pageshow", (event) => {
        if (event.persisted && loading) loading.style.display = "none";
    });

    // Fechar modal ao clicar fora
    window.onclick = function(event) {
        const logoutModal = document.getElementById('logoutModal');
        if (event.target == logoutModal) closeLogoutModal();
    }
</script>

</body>
</html>