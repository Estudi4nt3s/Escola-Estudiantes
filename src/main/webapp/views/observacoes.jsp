<%@ page import="java.util.List" %>
<%@ page import="com.sistema.estudiantes.model.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<%
    Aluno aluno = (Aluno) request.getSession().getAttribute("alunoSelecionado");
    Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
    Professor professor = (Professor) request.getSession().getAttribute("professor");
    String[] data = (String[]) request.getSession().getAttribute("data");
    List<Observacao> observacoes = (List<Observacao>) request.getAttribute("observacoes");

%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <% if (aluno != null) { %>
        <title>Estudiantes - Boletim de <%=aluno.getNome()%></title>
    <% } else { %>
    <title>Estudiantes - Boletim</title>
    <% } %>
    <link rel="icon" href="${pageContext.request.contextPath}/utils/school.png">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/observacoes.css">
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
                <span><%=professor.getNome()%></span>
            </div>
        </div>
    </header>

    <div class="main-content">
        <div class="page-header">
            <% if (aluno != null && aluno.getUsuarioId() != null) { %>
            <h2 class="page-title">Observações: <%= aluno.getNome()%></h2>
            <% } else { %>
            <h2 class="page-title">Observações: Usuário não encontrado</h2>
            <% } %>
        </div>

        <button id="abrirInserir" class="submit">
            <i class="material-icons">add</i>
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
                    <td><%=o.getDataCriacao().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></td>
                    <td>
                        <button class="editar-btn" data-id="<%=o.getId()%>" data-texto="<%=o.getTexto()%>">
                            <i class="material-icons editar">edit</i>
                        </button>
                        <button class="excluir-btn" data-id="<%=o.getId()%>">
                            <i class="material-icons excluir">delete</i>
                        </button>
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
    <div id="popupInserir" class="modal">
        <div class="modal-box">
            <div class="modal-header">
                <h3>Inserir Observação</h3>
                <span class="material-icons fechar">close</span>
            </div>
            <form method="post" action="${pageContext.request.contextPath}/observacao">

                <input type="hidden" name="sub_acao" value="inserir">
                <input type="hidden" name="alunomatricula" value="<%=aluno.getMatricula()%>">

                <textarea name="texto" id="" placeholder="Digite uma observação" required></textarea>

                <button type="submit" class="confirmar">
                    Confirmar
                </button>
            </form>
        </div>
    </div>
    <div id="popupEditar" class="modal">
        <div class="modal-box">
            <div class="modal-header">
                <h3>Editar Observação</h3>
                <span class="material-icons fechar">close</span>
            </div>
            <form method="post" action="${pageContext.request.contextPath}/observacao">

                <input type="hidden" name="sub_acao" value="atualizar">
                <input type="hidden" name="id" id="editarId">

                <textarea name="texto" id="editarTexto" required></textarea>

                <button type="submit" class="confirmar">
                    Salvar
                </button>
            </form>
        </div>
    </div>
    <div id="popupExcluir" class="modal">
        <div class="modal-box">
            <div class="modal-header">
                <h3>Excluir Observação</h3>
                <span class="material-icons fechar">close</span>
            </div>
            <p class="texto-excluir">
                Tem certeza que deseja excluir essa observação?
            </p>
            <div class="acoes-excluir">
                <a id="confirmarExcluir" class="excluir-btn-modal">
                    Excluir
                </a>
                <button class="cancelar fechar">
                    Cancelar
                </button>
            </div>
        </div>
    </div>
</main>

<script src="${pageContext.request.contextPath}/js/notificacoes.js"></script>
<script src="${pageContext.request.contextPath}/js/popup.js"></script>
<script>
    const contextPath = "${pageContext.request.contextPath}";
</script>
</body>
</html>