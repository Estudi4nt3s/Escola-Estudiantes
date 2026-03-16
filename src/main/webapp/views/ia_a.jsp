<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String nome = (String) session.getAttribute("adminNome");
    String tipo = (String) session.getAttribute("tipoUsuario");
//
//    if (tipo == null || !tipo.equals("admin")) {
//        response.sendRedirect("cadastro.jsp");
//        return;
//    }
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>IA Administrativa | ADM</title>
    <script src="https://cdn.jsdelivr.net/npm/marked/marked.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/configuracoes_a.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <style>
        /* Animação dos pontinhos */
        .dots { display: inline-block; font-size: 20px; font-weight: bold; }
        .dot { opacity: 0.2; animation: blink 1.4s infinite both; }
        .dot:nth-child(2) { animation-delay: 0.2s; }
        .dot:nth-child(3) { animation-delay: 0.4s; }

        @keyframes blink {
            0% { opacity: 0.2; }
            20% { opacity: 1; }
            100% { opacity: 0.2; }
        }
        /* Estilos específicos para o Chat */
        .chat-card { height: 70vh; display: flex; flex-direction: column; }
        #chat-messages { flex: 1; overflow-y: auto; padding: 20px; display: flex; flex-direction: column; gap: 15px; }
        .message { padding: 12px 18px; border-radius: 15px; max-width: 75%; font-size: 0.95rem; }
        .user-msg { background: var(--red); color: white; align-self: flex-end; }
        .ai-msg { background: #f0f0f0; color: #333; align-self: flex-start; }
    </style>
</head>
<body>

<aside class="sidebar">
    <div class="logo">
        <i class="material-icons">admin_panel_settings</i><span>Painel ADM</span>
    </div>
    <nav>
        <a class="menu" href="${pageContext.request.contextPath}/views/inicio_a.jsp"><i class="material-icons">home</i><span>Inicio</span></a>
        <a class="menu" href="${pageContext.request.contextPath}/AlunoAdminServlet"><i class="material-icons">groups</i><span>Alunos</span></a>
        <a class="menu" href="${pageContext.request.contextPath}/ProfessorAdminServlet"><i class="material-icons">badge</i><span>Professores</span></a>
        <a class="menu" href="${pageContext.request.contextPath}/TurmaAdmServlet"><i class="material-icons">school</i><span>Turmas</span></a>
        <a class="menu" href="${pageContext.request.contextPath}/DisciplinaAdminServlet"><i class="material-icons">menu_book</i><span>Disciplinas</span></a>
        <a class="menu active" href="${pageContext.request.contextPath}/ia_adm.jsp"><i class="material-icons">psychology</i><span>IA Administrativa</span></a>
        <a class="menu" href="${pageContext.request.contextPath}/servletConfiguracoes"><i class="material-icons">settings</i><span>Configurações</span></a>
    </nav>
    <a class="config" href="${pageContext.request.contextPath}/servletLogout"><i class="material-icons">logout</i><span>Sair</span></a>
</aside>

<main class="main">
    <header class="topbar">
        <div class="date"><i class="material-icons">psychology</i> Assistente IA Escolar</div>
        <div class="avatar">
            <img src="${pageContext.request.contextPath}/utils/perfil_adm.jpg">
            <span><%= (nome != null) ? nome : "Admin" %></span>
        </div>
    </header>

    <div class="content">
        <div class="card chat-card">
            <div id="chat-messages">
                <div class="message ai-msg">Olá! Estou pronta para analisar os dados da escola. Pergunte-me sobre desempenho, alunos ou turmas.</div>
            </div>

            <div style="padding: 20px; border-top: 1px solid #eee; display: flex; gap: 10px;">
                <input type="text" id="userInput" style="flex: 1; padding: 12px; border: 1px solid #ddd; border-radius: 10px;" placeholder="Ex: Analise o desempenho geral...">
                <button onclick="enviarPergunta()" class="btn-primary" style="padding: 0 25px;">Enviar</button>
            </div>
        </div>
    </div>
</main>

<script>
    document.getElementById("userInput").addEventListener("keypress", function(event) {
        if (event.key === "Enter") {
            enviarPergunta();
        }
    });

    function enviarPergunta() {
        let input = document.getElementById('userInput');
        let msg = input.value;
        if (!msg.trim()) return;

        let chat = document.getElementById('chat-messages');
        chat.innerHTML += '<div class="message user-msg">' + msg + '</div>';
        input.value = '';


        let loaderId = 'loader-' + Date.now();
        chat.innerHTML += '<div id="'+loaderId+'" class="message ai-msg">' +
            '<span class="dots"><span class="dot">.</span><span class="dot">.</span><span class="dot">.</span></span>' +
            '</div>';
        chat.scrollTop = chat.scrollHeight;

        fetch('${pageContext.request.contextPath}/ChatIAServlet', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: 'pergunta=' + encodeURIComponent(msg)
        })
            .then(response => response.text())
            // Substitua dentro do seu .then(data => { ... })
            .then(data => {
                document.getElementById(loaderId).remove();

                let htmlResposta = marked.parse(data);

                chat.innerHTML += '<div class="message ai-msg">' + htmlResposta + '</div>';
                chat.scrollTop = chat.scrollHeight;
            })
            .catch(err => {
                document.getElementById(loaderId).remove();
                chat.innerHTML += '<div class="message ai-msg">Erro ao conectar com o servidor.</div>';
            });
    }
</script>

</body>
</html>