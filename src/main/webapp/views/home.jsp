<%@ page import="com.sistema.estudiantes.model.Aluno" %>
<%@ page import="com.sistema.estudiantes.model.Usuario" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Colégio Estudiantes - Início</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/indexStyle.css">

</head>
<%
    Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
    Aluno aluno = (Aluno) request.getSession().getAttribute("aluno");
    String[] data = (String[]) request.getSession().getAttribute("data");
    String[] materia = (String[]) request.getSession().getAttribute("materia");
    int qtdmateria = (int) request.getSession().getAttribute("qtdMateria");
    Map<String,String> conteudo = new HashMap<>();
    conteudo.put("Matematica", "Estudo de números, operações, equações, porcentagem, geometria e resolução de problemas do dia a dia.");
    conteudo.put("Português","Interpretação de textos, gramática, ortografia, produção textual e desenvolvimento da comunicação escrita.");
    conteudo.put("História","Estudo das sociedades antigas e modernas, acontecimentos históricos e formação do mundo atual.");
    conteudo.put("Geografia","Estudo do espaço geográfico, meio ambiente, população, economia e organização dos territórios.");
    conteudo.put("Inglês","Aprendizado de vocabulário, gramática básica, leitura, escrita e conversação na língua inglesa.");
    conteudo.put("Ciências","Estudo do corpo humano, meio ambiente, física básica, química e fenômenos naturais.");


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
            <a class="menu" href="${pageContext.request.contextPath}/views/alunos.jsp"> <i class="material-icons">menu_book</i>Notas</a>
            <a class="menu" href="${pageContext.request.contextPath}/views/calendario.jsp"><i class="material-icons">calendar_month</i>Calendário</a>
            <a class="menu" href="${pageContext.request.contextPath}/views/perfil.jsp"><i class="material-icons">person</i>Perfil</a>
        </nav>
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
                    <a href="${pageContext.request.contextPath}/views/perfil.jsp"><img src="https://i.pravatar.cc/40?img=12" alt="avatar"></a>
                    <span><%=usuario.getNome()%></span>
                </div>
            </div>
        </header>

        <section class="dashboard">

            <div class="left">

                <div class="welcome">
                    <h2>Olá, <%=usuario.getNome()%>!</h2>
                    <p>Pronto para as aulas de hoje?</p>
                </div>

                <div class="flex">
                    <%
                        if(!data[2].equals("SÁB") && !data[2].equals("DOM")){
                            for(int i = 0;i < qtdmateria;i++){
                    %>

                    <div class="card <%=materia[i].toLowerCase()%>">
                        <h3><%=materia[i].toUpperCase().charAt(0) + materia[i].toLowerCase().substring(1,materia[i].length())%></h3>
                        <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Corrupti eum magnam eligendi hic
                            nesciunt.</p>
                        <img src="${pageContext.request.contextPath}/utils/<%=materia[i].toLowerCase(java.util.Locale.ROOT)%>.png" alt="<%=materia[i]%>">
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
                        String[] horario = {"07:00 às 08:00","08:00 às 09:00","09:00 às 10:00",
                                "10:30 às 11:30","11:30 às 12:30","13:30 às 14:30"};

                        if(!data[2].equals("SÁB") && !data[2].equals("DOM")){
                            for(int i = 0;i < qtdmateria;i++){
                             %>
                    <li><strong><%=materia[i].toUpperCase().charAt(0) + materia[i].toLowerCase().substring(1,materia[i].length())%></strong> - <%=horario[i]%></li>
                    <%
                            }
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
</body>
</html>