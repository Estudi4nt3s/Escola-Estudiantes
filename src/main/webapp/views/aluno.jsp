<%@ page import="java.util.List" %>
<%@ page import="com.sistema.estudiantes.model.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<%
    Aluno aluno = (Aluno) request.getSession().getAttribute("aluno");
    Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
    String[] data = (String[]) request.getSession().getAttribute("data");
    List<Disciplina> disciplinas = (List<Disciplina>) request.getSession().getAttribute("disciplinas");
    List<Nota> notas = (List<Nota>) request.getSession().getAttribute("notas");
    List<ObservacaoProfessor> observacaos = (List<ObservacaoProfessor>) request.getSession().getAttribute("observacoes");

%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Estudiantes - Boletim de <%=usuario.getNome()%></title>
    <link rel="icon" href="${pageContext.request.contextPath}/utils/school.png">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/aluno_p.css">
</head>

<body>

    <aside class="sidebar">
        <div class="logo">
            <i class="material-icons">school</i>
            <span>Colégio Estudiantes</span>
        </div>

        <nav>
            <a class="menu" href="${pageContext.request.contextPath}/views/home.jsp"><i class="material-icons">home</i>Início</a>
            <a class="menu" href="${pageContext.request.contextPath}/views/disciplinas.jsp"> <i class="material-icons">menu_book</i>Minhas Disciplinas</a>
            <a class="menu"> <i class="material-icons">menu_book</i>Notas</a>
            <a class="menu" href="${pageContext.request.contextPath}/views/calendario.jsp"><i class="material-icons">calendar_month</i>Calendário</a>
            <a class="menu active" href="${pageContext.request.contextPath}/views/perfil.jsp"><i class="material-icons">person</i>Perfil</a>

        </nav>
    </aside>

    <main class="main">

        <header class="topbar">
            <div class="date">
                <i class="material-icons">calendar_today</i>
                <%=data[2].toUpperCase().charAt(0) + data[2].toLowerCase().substring(1) + ", " + data[0] + "/" + data[1]%>
            </div>

            <div class="user">
                <div class="avatar">
                    <img src="${pageContext.request.contextPath}/utils/perfil.png" alt="Avatar">
                    <span><%=usuario.getNome()%></span>
                </div>
            </div>
        </header>

        <div class="main-content">
            <div class="page-header">
                <h2 class="page-title">Boletim Escolar: <%=usuario.getNome()%></h2>
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

                                    double media = (notaEncontrada.getN1() + notaEncontrada.getN2()) / 2;

                                    situacao = media >= 7 ? "Aprovado" : "Reprovado";

                        %>
                            <tr>
                                <td><strong><%=disciplina.getNome()%></strong></td>
                                <td><%=notaEncontrada.getN1()%></td>
                                <td><%=notaEncontrada.getN2()%></td>
                                <td><%=media%></td>
                                <td>
                                    <span class="status <%=media >= 7?"attention":"approved"%>"><%=situacao%></span>
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
                    %>
                            <p><strong>Prof. <%=observacaos.get(i).getNomeProfessor()%> (<%=observacaos.get(i).getNomeDisciplina()%>:</strong> "<%=observacaos.get(i).getTexto()%>"</p>
                    <%
                            if((i + 1) == observacaos.size()){
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

    <script src="${pageContext.request.contextPath}/js/notificacoes.js"></script>
</body>
</html>