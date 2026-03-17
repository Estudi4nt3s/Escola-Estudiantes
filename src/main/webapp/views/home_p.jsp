<%@ page import="com.sistema.estudiantes.model.Professor" %>
<%@ page import="com.sistema.estudiantes.model.Disciplina" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sistema.estudiantes.model.Turma" %>
<%@ page import="com.sistema.estudiantes.model.Aula" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.LocalTime" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Estudiantes - Início</title>
    <link rel="icon" href="${pageContext.request.contextPath}/utils/school.png">
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

    LocalTime agora = LocalTime.now();

    if (professor == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }

    int qtdmateria = 0;
    String[] turma = new String[6];

    if (aulas != null && turmas != null) {
        for (int i = 0; i < aulas.size(); i++) {
            for (Turma value : turmas) {
                if (value.getId() == aulas.get(i).getTurmaId().getId()) {
                    turma[i] = value.getNome();
                    qtdmateria++;
                    break;
                }
            }
        }
    }

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
            <a class="menu" href="${pageContext.request.contextPath}/views/calendario_p.jsp"><i class="material-icons">calendar_month</i>Calendário</a>
            <a class="menu" href="${pageContext.request.contextPath}/views/turmas.jsp"><i class="material-icons">groups</i>Turmas</a>
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
                <%=data[2].toUpperCase().charAt(0) + data[2].toLowerCase().substring(1) + ", " + data[0] + "/" + data[1]%>
            </div>

            <div class="user">
                <div class="avatar">
<%--                    <a href="${pageContext.request.contextPath}/views/perfil_p.jsp"><img src="${pageContext.request.contextPath}/utils/perfil.png" alt="avatar"></a>--%>
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
                        System.out.println(materia.getNome());
                        if((!data[2].equals("SÁB") && !data[2].equals("DOM")) && !(materia == null)){
                            for(int i = 0;i < aulas.size();i++){
                    %>
                        <div class="card card <%=materia.getNome().toLowerCase()%>">
                            <h3><%=turma[i]%></h3>
                            <p><%=conteudo.get(materia.getNome().toLowerCase())[0]%></p>
                            <i class="material-icons materias"><%=conteudo.get(materia.getNome().toLowerCase())[1]%></i>
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
                    <%-- Loop para a lista lateral de horários --%>
                    <%
                    if((!data[2].equals("SÁB") && !data[2].equals("DOM")) && !(materia == null)){
                        for(int i = 0;i < qtdmateria;i++){
                            %>
                        <li class="<%=agora.isAfter(aulas.get(i).getHorarioInicio()) && agora.isBefore(aulas.get(i).getHorarioFim())?"atual":""%>">
                            <strong><%=turma[i]%></strong> -
                            <%=String.format("%02d",aulas.get(i).getHorarioInicio().getHour())%>:<%=String.format("%02d",aulas.get(i).getHorarioInicio().getMinute())%>
                             às
                            <%=String.format("%02d",aulas.get(i).getHorarioFim().getHour())%>:<%=String.format("%02d",aulas.get(i).getHorarioFim().getMinute())%>
                        </li>
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

    <script src="<%=request.getContextPath()%>/js/notificacoes.js"></script>
</body>
</html>