<%@ page import="java.util.List" %>
<%@ page import="com.sistema.estudiantes.model.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<%
    Aluno aluno = (Aluno) request.getSession().getAttribute("alunoSelecionado");
    Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
    String[] data = (String[]) request.getSession().getAttribute("data");
    List<Disciplina> disciplinas = (List<Disciplina>) request.getAttribute("disciplinas");
    List<Nota> notas = (List<Nota>) request.getAttribute("notas");
    List<Observacao> observacoes = (List<Observacao>) request.getAttribute("observacoes");

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
        <a class="menu" href="${pageContext.request.contextPath}/views/home_p.jsp"><i class="material-icons">home</i>Início</a>
        <a class="menu" href="${pageContext.request.contextPath}/views/calendario.jsp"><i class="material-icons">calendar_month</i>Calendário</a>
        <a class="menu active" href="${pageContext.request.contextPath}/views/turmas.jsp"><i class="material-icons">groups</i>Turmas</a>
        <a class="menu" href="${pageContext.request.contextPath}/views/perfil_p.jsp"><i class="material-icons">person</i>Perfil</a>
    </nav>
    <div class="config">
        <a class="menu" style="margin-left: -25px; color: #590101" href="${pageContext.request.contextPath}/index.jsp">
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
                <img src="${pageContext.request.contextPath}/utils/perfil.png" alt="Avatar">
                <span><%=usuario.getNome()%></span>
            </div>
        </div>
    </header>

    <div class="main-content">
        <div class="page-header">
            <% if (aluno != null && aluno.getUsuarioId() != null) { %>
            <h2 class="page-title">Observações: <%= aluno.getUsuarioId().getNome()%></h2>
            <% } else { %>
            <h2 class="page-title">Observações: Usuário não encontrado</h2>
            <% } %>
        </div>

        <button type="submit" class="submit">
            <i class="material-icons crud">add</i>
            Adicionar
        </button>
        <div class="table-container">
            <table class="grades-table">
                <thead>
                <tr>
                    <th>Observação</th>
                    <th>Data</th>
                    <th>Ações</th>
                </tr>
                </thead>
                <tbody>
                <%
                    if(observacoes != null && !observacoes.isEmpty()){

                        for(Observacao o : observacoes){
                %>
                <tr>
                    <td><%=o.getTexto()%></td>
                    <td><%=o.getDataCriacao()%></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/observacao?sub_acao=atualizar&id=<%=o.getId()%>">
                            <i class="material-icons crud">edit</i>
                        </a>
                        <a href="${pageContext.request.contextPath}/observacao?sub_acao=excluir&id=<%=o.getId()%>">
                            <i class="material-icons crud">delete</i>
                        </a>
                    </td>
                </tr>
                <%
                    }
                }
                else{
                %>
                <tr>
                    <td colspan="3">Nenhuma observação encontrada</td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
    </div>

</main>


<script src="${pageContext.request.contextPath}/js/notificacoes.js"></script>
</body>
</html>