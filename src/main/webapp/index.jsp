<<<<<<< HEAD
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
=======
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.time.format.TextStyle" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sistema.estudiantes.model.Aula" %>
<%@ page import="com.sistema.estudiantes.dao.AulaDAO" %>
<%@ page import="com.sistema.estudiantes.dao.DisciplinaDAO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
>>>>>>> main
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Estudantes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/login.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inria+Serif:wght@300;400;700&family=Inter:wght@300;400;700&display=swap" rel="stylesheet">
</head>
<%
    AulaDAO aulaDAO = new AulaDAO();
    DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
    LocalDate hoje = LocalDate.now();
    String dia = String.format("%02d",hoje.getDayOfMonth());
    String mes = String.format("%02d",hoje.getMonthValue());
    Locale ptBr = new Locale("pt", "BR");
    String semana = hoje.getDayOfWeek().getDisplayName(TextStyle.SHORT, ptBr).substring(0, 3);
    String nome = (String) request.getSession().getAttribute("nome");
%>
<body>

    <a href="#" class="restricted">acesso restrito</a>

    <main>
        <div class="div-overlay">
            <div class="overlay"></div>
            <img src="${pageContext.request.contextPath}/3b9acabc-be87-4b60-af72-8bc163abe1d6.png" alt="Estudantes">
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
                <%=dia + "/" + mes%>
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
                        if(!semana.equals("sab") && !semana.equals("dom")){
                            List<Aula> aulas = aulaDAO.listarComFiltro("diaSemana",semana + "order by 2");
                            for(int i = 0;i < 6;i++){
                                int id = aulas.get(i).getDisciplinaId();
                                String materia = disciplinaDAO.listarComFiltro("id",id).getFirst().getNome();%>

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

                        if(!semana.equals("sab") && !semana.equals("dom")){
                            List<Aula> aulas = aulaDAO.listarComFiltro("diaSemana",semana + "order by 2");
                            for(int i = 0;i < 6;i++){
                                int id = aulas.get(i).getDisciplinaId();
                                String materia = disciplinaDAO.listarComFiltro("id",id).getFirst().getNome();%>

                    %>
                    <li><strong><%=materia%></strong> - <%=horario[i]%></li>
                    <%
                            }
                        }
                    %>
                </ul>
            </div>
        </div>
    </main>
</body>
</html>