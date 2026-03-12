<%@ page import="com.sistema.estudiantes.model.Aluno" %>
<%@ page import="com.sistema.estudiantes.model.Usuario" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Estudiantes - Minhas Disciplinas</title>
    <link rel="icon" href="${pageContext.request.contextPath}/utils/school.png">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/disciplinas.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/carregar.css">
</head>
<%
    String[] data = (String[]) request.getSession().getAttribute("data");
    Aluno aluno = (Aluno) request.getSession().getAttribute("aluno");
    Map<String,String> conteudo = new HashMap<>();
    conteudo.put("matemática","calculate");
    conteudo.put("português","book");
    conteudo.put("história","history_edu");
    conteudo.put("geografia","public");
    conteudo.put("informática","computer");
    conteudo.put("ciências","science");
%>

<body>

    <aside class="sidebar">
        <div class="logo">
            <i class="material-icons">school</i>
            <span>Colégio Estudiantes</span>
        </div>

    <nav>
        <a class="menu" href="${pageContext.request.contextPath}/views/home.jsp">
        <i class="material-icons">home</i>Início</a>
        <a class="menu active"><i class="material-icons">menu_book</i>Minhas Disciplinas</a>
        <a class="menu" href="${pageContext.request.contextPath}/nota?sub_acao=buscar_por_id&id=<%=aluno.getMatricula()%>"> <i class="material-icons">menu_book</i>Notas</a>
        <a class="menu" href="${pageContext.request.contextPath}/views/perfil.jsp"><i class="material-icons">person</i>Perfil</a>
    </nav>
        <div class="config">
            <a class="menu" style="color: #590101" href="${pageContext.request.contextPath}/index.jsp">
                <i class="material-icons">output</i>Sair
            </a>
        </div>
    </aside>

    <main class="main">

    <header class="topbar">
        <div class="date">
            <i class="material-icons">calendar_today</i>
            <%=data[2].toUpperCase().charAt(0) + data[2].toLowerCase().substring(1) + ", " + data[0] + "/" + data[1]%>
        </div>

            <div class="user">
                <i class="material-icons" id="openNotification" style="cursor: pointer;">notifications</i>

                    <div class="avatar">
                        <a href="${pageContext.request.contextPath}/views/perfil.jsp">
                            <img src="${pageContext.request.contextPath}/utils/perfil.png" alt="avatar">
                        </a>
                            <span><%=aluno.getNome()%></span>
                    </div>

            </div>
        </header>

        <section class="dashboard disciplinas">

            <h2 class="page-title">Minhas Disciplinas</h2>

            <div class="disciplinas-grid">

            <div class="disciplina matemática">
                <div class="disciplina-info">
                    <h3>Matemática</h3>
                    <p>Prof. Valdislei</p>
                </div>
                <i class="material-icons materias"><%=conteudo.get("matemática")%></i>
            </div>

            <div class="disciplina português">
                <div class="disciplina-info">
                    <h3>Português</h3>
                    <p>Prof. Cláudia</p>
                </div>
                <i class="material-icons materias"><%=conteudo.get("português")%></i>
            </div>

            <div class="disciplina geografia">
                <div class="disciplina-info">
                    <h3>Geografia</h3>
                    <p>Prof. Flávio</p>
                </div>
                <i class="material-icons materias"><%=conteudo.get("geografia")%></i>
            </div>

            <div class="disciplina história">
                <div class="disciplina-info">
                    <h3>História</h3>
                    <p>Prof. Rosangela</p>
                </div>
                <i class="material-icons materias"><%=conteudo.get("história")%></i>
            </div>

            <div class="disciplina informática">
                <div class="disciplina-info">
                    <h3>Informática</h3>
                    <p>Prof. Diego</p>
                </div>
                <i class="material-icons materias"><%=conteudo.get("informática")%></i>
            </div>

            <div class="disciplina ciências">
                <div class="disciplina-info">
                    <h3>Ciências</h3>
                    <p>Prof. Robson</p>
                </div>
                <i class="material-icons materias"><%=conteudo.get("ciências")%></i>
            </div>

            </div>

        </section>

    </main>

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
    <script>

        const btnNotas = document.getElementById("btnNotas");
        const loading = document.getElementById("loadingOverlay");

        btnNotas.addEventListener("click", function(e){

            e.preventDefault(); // impede abrir imediatamente

            loading.style.display = "flex";

            setTimeout(()=>{
                window.location.href = this.href;
            },500);

        });

    </script>
</body>

</html>