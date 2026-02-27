<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.time.format.TextStyle" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sistema.estudiantes.model.Aula" %>
<%@ page import="com.sistema.estudiantes.dao.AulaDAO" %>
<%@ page import="com.sistema.estudiantes.dao.DisciplinaDAO" %>
<%@ page import="com.sistema.estudiantes.model.Disciplina" %>
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
    AulaDAO aulaDAO = new AulaDAO();
    DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
    LocalDate hoje = LocalDate.now();
    String dia = String.format("%02d",hoje.getDayOfMonth());
    String mes = String.format("%02d",hoje.getMonthValue());
    Locale ptBr = new Locale("pt", "BR");
    String semana = hoje.getDayOfWeek().getDisplayName(TextStyle.SHORT, ptBr).toUpperCase().substring(0, 3);
    System.out.println(semana);
    String nome = (String) request.getSession().getAttribute("nome");
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
            <a class="menu"><i class="material-icons">calendar_month</i>Calendário</a>
            <a class="menu"><i class="material-icons">person</i>Perfil</a>
            <a class="menu" href="${pageContext.request.contextPath}/turma">
                <i class="material-icons">calendar_month</i>Turmas (provisório)</a>
        </nav>

        <div class="config">
            <i class="material-icons">settings</i>Configurações
        </div>
    </aside>

    <main class="main">

        <header class="topbar">
            <div class="date">
                <i class="material-icons">calendar_today</i>
                <%=semana.toUpperCase().charAt(0) + semana.toLowerCase().substring(1) + ", " + dia + "/" + mes%>
            </div>

            <div class="user">
                <i class="material-icons" id="openNotification">notifications</i>
                <div class="avatar">
                    <img src="https://i.pravatar.cc/40?img=12" alt="avatar">
                    <span><%=nome%></span>
                </div>
            </div>
        </header>

        <section class="dashboard">

            <div class="left">

                <div class="welcome">
                    <h2>Olá, <%=nome%>!</h2>
                    <p>Pronto para as aulas de hoje?</p>
                </div>

                <div class="flex">
                    <%
                        if(!semana.equals("SAB") && !semana.equals("DOM")){
                            List<Aula> aulas = aulaDAO.listarComFiltro("diasemana",semana, "order by 2");
                            List<Disciplina> disciplina = disciplinaDAO.listar();
                            String materia = "";
                            for(int i = 0;i < 6;i++){
                                int id = aulas.get(i).getDisciplinaId().getId();
                                for(int j = 0; j < disciplina.size(); j++){
                                    if (disciplina.get(j).getId() == id){
                                        materia = disciplina.get(j).getNome();
                                        break;
                                    }
                                }
                    %>

                    <div class="card <%=materia%>">
                        <h3><%=materia%></h3>
                        <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Corrupti eum magnam eligendi hic
                            nesciunt.</p>
                        <img src="${pageContext.request.contextPath}/utils/<%=materia%>.png" alt="<%=materia%>">
                    </div>
                    <%System.out.println(materia);}}%>
                </div>

            </div>

            <div class="content-today">
                <h2>Aulas de Hoje</h2>
                <ul>
                    <%
                        String[] horario = new String[6];
                        horario[0] = "07:00 às 08:00";
                        horario[1] = "08:00 às 09:00";
                        horario[2] = "09:00 às 10:00";
                        horario[3] = "10:30 às 11:30";
                        horario[4] = "11:30 às 12:30";
                        horario[5] = "13:30 às 14:30";

                        if(!semana.equals("SAB") && !semana.equals("DOM")){
                            List<Aula> aulas = aulaDAO.listarComFiltro("diasemana",semana, "order by 2");
                            List<Disciplina> disciplina = disciplinaDAO.listar();
                            String materia = "";
                            for(int i = 0;i < 6;i++){
                                int id = aulas.get(i).getDisciplinaId().getId();
                                for(int j = 0; j < disciplina.size(); j++){
                                    if (disciplina.get(j).getId() == id){
                                        materia = disciplina.get(j).getNome();
                                        break;
                                    }
                                }%>
                    <li><strong><%=materia%></strong> - <%=horario[i]%></li>
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