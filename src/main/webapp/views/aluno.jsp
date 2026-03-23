<%@ page import="java.util.List" %>
<%@ page import="com.sistema.estudiantes.model.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<%
    Aluno aluno = (Aluno) request.getSession().getAttribute("aluno");
    Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
    String[] data = (String[]) request.getSession().getAttribute("data");
    List<Disciplina> disciplinas = (List<Disciplina>) request.getSession().getAttribute("disciplinas");
    List<Nota> notas = (List<Nota>) request.getSession().getAttribute("notas");
    List<Observacao> observacaos = (List<Observacao>) request.getSession().getAttribute("observacoes");

%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Estudiantes - Boletim de <%=aluno.getNome()%></title>
    <link rel="icon" href="${pageContext.request.contextPath}/utils/school.png">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/aluno.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/carregar.css">
</head>

<body>

    <aside class="sidebar">
        <div class="logo">
            <i class="material-icons">school</i>
            <span>Colégio Estudiantes</span>
        </div>

        <nav>
            <a class="menu" href="${pageContext.request.contextPath}/views/home.jsp"><i class="material-icons">home</i>Início</a>
            <a class="menu" href="${pageContext.request.contextPath}/views/calendario_a.jsp"><i class="material-icons">calendar_month</i>Calendário</a>
            <a class="menu" href="${pageContext.request.contextPath}/views/disciplinas.jsp"> <i class="material-icons">menu_book</i>Minhas Disciplinas</a>
            <a class="menu active"> <i class="material-icons">grading</i>Notas</a>
            <a class="menu" href="${pageContext.request.contextPath}/views/perfil.jsp"><i class="material-icons">person</i>Perfil</a>

        </nav>
        <div class="config">
            <a class="menu" style="color: #ffffff" onclick="openLogoutModal()">
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
                <div class="avatar">
<%--                    <a href="${pageContext.request.contextPath}/views/perfil.jsp">--%>
<%--                        <img src="${pageContext.request.contextPath}/utils/perfil.png" alt="avatar">--%>
                    </a>
                    <span><%=aluno.getNome()%></span>
                </div>
            </div>
        </header>

        <div class="main-content">
            <div class="page-header">
                <h2 class="page-title">Boletim Escolar: <%=aluno.getNome()%></h2>

                <a class="btn-boletim" href="${pageContext.request.contextPath}/gerarBoletim">
                    <i class="material-icons">picture_as_pdf</i>
                    Gerar Boletim
                </a>
            </div>

            <div class="table-container">
                <table class="grades-table">
                    <thead>
                        <tr>
                            <th>Disciplina</th>
                            <th>N1</th>
                            <th>N2</th>
                            <th>Média</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            String situacao;
                            for (Disciplina disciplina : disciplinas) {
                                Nota notaEncontrada = null;

                                for (Nota nota : notas) {
                                    if (nota.getIdDisciplina().getId() == disciplina.getId()) {
                                        notaEncontrada = nota;
                                        break;
                                    }
                                }

                                if (notaEncontrada != null) {

                                    Double n1 = notaEncontrada.getN1();
                                    Double n2 = notaEncontrada.getN2();

                                    Double media = null;

                                    if (n1 != null && n2 != null) {
                                        media = (notaEncontrada.getN1() + notaEncontrada.getN2()) / 2;
                                    }

                                    situacao = (n1 == null || n2 == null)?"Em andamento":media >= 7 ? "Aprovado" : "Reprovado";

                        %>
                            <tr>
                                <td><strong><%=disciplina.getNome().toUpperCase().charAt(0) + disciplina.getNome().toLowerCase().substring(1,disciplina.getNome().length())%></strong></td>
                                <td><%=(n1 == null ? "-" : String.format("%.2f", n1))%></td>
                                <td><%=(n2 == null ? "-" : String.format("%.2f", n2))%></td>
                                <td><%=(n1 == null || n2 == null) ? "-" : String.format("%.2f", media)%></td>
                                <td>
                                    <span class="status <%=(n1 == null || n2 == null)?"andamento":media >= 7?"approved":"attention"%>"><%=situacao%></span>
                                </td>
                            </tr>
                        <%
                                }
                                else{
                        %>
                        <tr>
                            <td><strong><%=disciplina.getNome()%></strong></td>
                            <td>-</td>
                            <td>-</td>
                            <td>-</td>
                            <td>-</td>
                        </tr>
                    <%
                            }
                        }
                    %>
                    </tbody>
                </table>
            </div>

            <div class="observations-section">
                <div class="obs-header">
                    <i class="material-icons">assignment</i>
                    <h3>Observações do Professor</h3>
                </div>
                <div class="obs-content">

                    <%
                        for(int i = 0;i < observacaos.size();i++){
                    Professor prof = observacaos.get(i).getIdProfessor();
                    String disciplina = "Sem disciplina";

                    if(prof.getDisciplina() != null){
                        disciplina = prof.getDisciplina().getNome();
                    }
                    %>
                            <p><strong>Prof. <%=prof.getNome()%> (<%=disciplina%>:</strong> "<%=observacaos.get(i).getTexto()%>")</p>
                    <%
                            if((i + 1) != observacaos.size()){
                    %>
                                <hr>
                    <%
                            }
                        }
                        if(observacaos.isEmpty()){%>
                        <p>Nenhuma observação registrada para este aluno.</p>
                    <%
                        }
                    %>
                </div>
            </div>
        </div>
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


    <script src="${pageContext.request.contextPath}/js/notificacoes.js"></script>
    <script>
        function openLogoutModal() {
            document.getElementById('logoutModal').classList.add('show');
        }

        function closeLogoutModal() {
            document.getElementById('logoutModal').classList.remove('show');
        }
    </script>
</body>
</html>