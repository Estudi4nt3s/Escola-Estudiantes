<%@ page import="com.sistema.estudiantes.model.Professor" %>
<%@ page import="com.sistema.estudiantes.model.Disciplina" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sistema.estudiantes.model.Turma" %>
<%@ page import="com.sistema.estudiantes.model.Aula" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Colégio Estudiantes - Início</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home_p.css">
</head>
<%
    String[] data = (String[]) request.getSession().getAttribute("data");
    Professor professor = (Professor) request.getSession().getAttribute("professor");
    Disciplina materia = (Disciplina) request.getSession().getAttribute("disciplina");
    @SuppressWarnings("unchecked")
    List<Turma> turmas = (List<Turma>) request.getSession().getAttribute("turmas");
    @SuppressWarnings("unchecked")
    List<Aula> aulas = (List<Aula>) request.getSession().getAttribute("aulas");


    int qtdmateria = 0;
    String[] turma = new String[6];
    for(int i = 0; i < Math.min(aulas.size(), 6); i++){
        for (Turma value : turmas) {
            if (value.getId() == aulas.get(i).getTurmaId().getId()) {
                turma[i] = value.getSerie();
                qtdmateria++;
                break;
            }
        }
    }
%>
<body>

    <aside class="sidebar">
        <div class="logo">
            <i class="material-icons">school</i>
            <span>Colégio Estudiantes</span>
        </div>

        <nav>
            <a class="menu active"><i class="material-icons">home</i>Início</a>
            <a class="menu" href="${pageContext.request.contextPath}/views/disciplinas_p.jsp"> <i class="material-icons">menu_book</i>Disciplinas</a>
            <a class="menu" href="${pageContext.request.contextPath}/views/calendario.jsp"><i class="material-icons">calendar_month</i>Calendário</a>
            <a class="menu" href="${pageContext.request.contextPath}/views/turmas.jsp"><i class="material-icons">calendar_today</i>Turmas</a>
        </nav>

        <div class="config">
            <a class="menu" style="margin-left: -25px;" href="perfil_p.jsp">
                <i class="material-icons">person</i>Perfil
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
                <div class="avatar">
                    <img src="${pageContext.request.contextPath}/utils/perfil.png" alt="avatar">
                    <span><%=professor.getNome()%></span>
                </div>
            </div>
        </header>

        <section class="dashboard">

            <div class="left">

                <div class="welcome">
                    <%-- Saudação personalizada com o primeiro nome --%>
                    <h2>Olá, <%=professor.getNome()%>!</h2>
                    <p>Pronto para as aulas de hoje?</p>
                </div>

                <div class="flex">
                    <%-- Loop para gerar os cards de conteúdo das aulas --%>
                    <%
                        if((!data[2].equals("SÁB") && !data[2].equals("DOM")) || qtdmateria != 0){
                            for(int i = 0;i < Math.min(aulas.size(),6);i++){
                    %>
                        <div class="card card<%=materia.getNome().toLowerCase()%>">
                            <h3><%=turma[i]%></h3>
                            <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Corrupti eum magnam eligendi hic
                                nesciunt.</p>
                            <img src="${pageContext.request.contextPath}/utils/<%=materia.getNome().toLowerCase()%>.png" alt="<%=materia.getNome()%>">
                        </div>
                    <%
                            }
                        }
                        else{
                    %>
                        <p>Você não possui aulas agendadas para hoje.</p>
                    <%
                        }
                    %>
                </div>

            </div>

            <div class="content-today">
                <h2>Aulas de Hoje</h2>
                <ul>
                    <%-- Loop para a lista lateral de horários --%>
                    <%
                        String[] horario = {"07:00 às 08:00","08:00 às 09:00","09:00 às 10:00",
                                "10:30 às 11:30","11:30 às 12:30","13:30 às 14:30"};
                    if((!data[2].equals("SÁB") && !data[2].equals("DOM")) || qtdmateria != 0){
                        for(int i = 0;i < qtdmateria;i++){
                            %>
                        <li>
                            <strong><%=materia.getNome()%></strong> - <%=horario[i]%>
                        </li>
                    <%
                        }
                    }
                    %>
                </ul>
            </div>

        </section>

    </main>

    <script src="<%=request.getContextPath()%>/js/notificacoes.js"></script>
</body>
</html>