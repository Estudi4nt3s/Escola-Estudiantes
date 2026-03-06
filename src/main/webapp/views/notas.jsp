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
        <a class="menu active"> <i class="material-icons">menu_book</i>Notas</a>
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
                    <td><strong><%=disciplina.getNome().toUpperCase().charAt(0) + disciplina.getNome().toLowerCase().substring(1,disciplina.getNome().length())%></strong></td>
                    <td>
                        <input class="nota-edit"
                               type="number"
                               step="0.01"
                               data-id="<%=notaEncontrada.getId()%>"
                               data-campo="n1"
                               min="0"
                               max="10"
                               value="<%=notaEncontrada.getN1()%>"></td>
                    <td><input class="nota-edit"
                               type="number"
                               step="0.01"
                               data-id="<%=notaEncontrada.getId()%>"
                               data-campo="n2"
                               min="0"
                               max="10"
                               value="<%=notaEncontrada.getN2()%>"></td>
                    <td><%=String.format("%.2f",media)%></td>
                    <td>
                        <span class="status <%=media >= 7?"approved":"attention"%>"><%=situacao%></span>
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
    </div>
</main>

<a href="${pageContext.request.contextPath}/gerarBoletim">
    Gerar Boletim
</a>

<script src="${pageContext.request.contextPath}/js/notificacoes.js"></script>
<script>
    document.querySelectorAll(".nota-edit").forEach(input => {

        input.addEventListener("input", function(){

            const row = this.closest("tr")

            const n1 = parseFloat(row.children[1].querySelector("input").value) || 0
            const n2 = parseFloat(row.children[2].querySelector("input").value) || 0

            const media = (n1 + n2) / 2

            row.children[3].innerText = media.toFixed(2)

            const status = row.children[4].querySelector(".status")

            if(media >= 7){
                status.innerText = "Aprovado"
                status.className = "status approved"
            }else{
                status.innerText = "Reprovado"
                status.className = "status attention"
            }

        })

        input.addEventListener("change", function(){

            const id = this.dataset.id
            const campo = this.dataset.campo
            const valor = this.value

            fetch("${pageContext.request.contextPath}/nota", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body:
                    "sub_acao=atualizar" +
                    "&id=" + id +
                    "&campo=" + campo +
                    "&valor=" + valor
            })

        })

    })
</script>
</body>
</html>