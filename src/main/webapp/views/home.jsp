<%@ page import="com.sistema.estudiantes.model.Aluno" %>
<%@ page import="com.sistema.estudiantes.model.Usuario" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.sistema.estudiantes.model.Aula" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    if (session.getAttribute("usuario") == null) {

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        response.sendRedirect("../index.jsp");
    }
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/indexStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/carregar.css">

</head>
<%
    Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
    Aluno aluno = (Aluno) request.getSession().getAttribute("aluno");
    String[] data = (String[]) request.getSession().getAttribute("data");
    String[] materia = (String[]) request.getSession().getAttribute("materia");
    List<Aula> aulas = (List<Aula>) request.getSession().getAttribute("aulas");
    int qtdmateria = (int) request.getSession().getAttribute("qtdMateria");
    Map<String,String[]> conteudo = new HashMap<>();
    conteudo.put("matemática", new String[]{"Estudo de números, operações, equações, porcentagem, geometria e resolução de problemas do dia a dia.","calculate"});
    conteudo.put("português", new String[]{"Interpretação de textos, gramática, ortografia, produção textual e desenvolvimento da comunicação escrita.","book"});
    conteudo.put("história", new String[]{"Estudo das sociedades antigas e modernas, acontecimentos históricos e formação do mundo atual.","history_edu"});
    conteudo.put("geografia", new String[]{"Estudo do espaço geográfico, meio ambiente, população, economia e organização dos territórios.","public"});
    conteudo.put("informática", new String[]{"Aprendizado sobre computadores, sistemas, internet, lógica de programação e utilização de ferramentas digitais no dia a dia.","computer"});
    conteudo.put("ciências", new String[]{"Estudo do corpo humano, meio ambiente, física básica, química e fenômenos naturais.","science"});


%>
<body>

    <aside class="sidebar">
        <div class="logo">
            <i class="material-icons">school</i>
            <span>Colégio Estudiantes</span>
        </div>

        <nav>
            <a class="menu active"><i class="material-icons">home</i>Início</a>
            <a class="menu" href="${pageContext.request.contextPath}/views/disciplinas.jsp">
                <i class="material-icons">menu_book</i>Minhas Disciplinas</a>
            <a class="menu" id="btnNotas"
               href="${pageContext.request.contextPath}/nota?sub_acao=buscar_por_id&id=<%=aluno.getMatricula()%>">
                <i class="material-icons">grading</i>Notas</a>
            <a class="menu" href="${pageContext.request.contextPath}/views/perfil.jsp"><i class="material-icons">
                person</i>Perfil</a>
        </nav>
        <div class="config">
            <a class="menu" style="color: #590101" href="sair">
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
                <i class="material-icons" id="openNotification">notifications</i>
                <div class="avatar">
                    <a href="${pageContext.request.contextPath}/views/perfil.jsp"><img src="${pageContext.request.contextPath}/utils/perfil.png" alt="avatar"></a>
                    <span><%=aluno.getNome()%></span>
                </div>
            </div>
        </header>

        <section class="dashboard">

            <div class="left">

                <div class="welcome">
                    <h2>Olá, <%=aluno.getNome()%>!</h2>
                    <p>Pronto para as aulas de hoje?</p>
                </div>

                <div class="flex">
                    <%
                        if(!data[2].equals("SÁB") && !data[2].equals("DOM")){
                            for(int i = 0;i < qtdmateria;i++){
                    %>

                    <div class="card <%=materia[i].toLowerCase()%>">
                        <h3><%=materia[i].toUpperCase().charAt(0) + materia[i].toLowerCase().substring(1,materia[i].length())%></h3>
                        <p><%=conteudo.get(materia[i].toLowerCase())[0]%></p>
                        <i class="material-icons materias"><%=conteudo.get(materia[i].toLowerCase())[1]%></i>
                    </div>
                <%
                            }
                        }
                %>
                </div>
            </div>

            <div class="content-today">
                <h2>Aulas de Hoje</h2>
                <ul>
                    <%
                        if((!data[2].equals("SÁB") && !data[2].equals("DOM")) && !Boolean.parseBoolean((materia[0]))){
                            for(int i = 0;i < qtdmateria;i++){
                             %>
                    <li><strong><%=materia[i].toUpperCase().charAt(0) + materia[i].toLowerCase().substring(1,materia[i].length())%></strong> -
                        <%=String.format("%02d",aulas.get(i).getHorarioInicio().getHour())%>:<%=String.format("%02d",aulas.get(i).getHorarioInicio().getMinute())%>
                         às
                         <%=String.format("%02d",aulas.get(i).getHorarioFim().getHour())%>:<%=String.format("%02d",aulas.get(i).getHorarioFim().getMinute())%>
                    <%
                            }
                        }
                            else{

                    %>
                        <p>Você não possui aulas hoje.</p>
                    <%
                        }
                    %>
                </ul>
            </div>

        </section>

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