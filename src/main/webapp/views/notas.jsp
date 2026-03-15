<%@ page import="java.util.List" %>
<%@ page import="com.sistema.estudiantes.model.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<%
    Aluno aluno = (Aluno) request.getSession().getAttribute("alunoSelecionado");
    System.out.println("Aluno: " + aluno.getNome());
    Professor professor = (Professor) request.getSession().getAttribute("professor");
    String[] data = (String[]) request.getSession().getAttribute("data");
    List<Disciplina> disciplinas = (List<Disciplina>) request.getSession().getAttribute("disciplinas");
    List<Nota> notas = (List<Nota>) request.getSession().getAttribute("notas");

%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%if (aluno != null) {%>
    <title>Estudiantes - Boletim de <%=aluno.getNome()%></title>
    <%} else { %>
    <title>Estudiantes - Boletim</title>
    <% } %>
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
        <a class="menu" href="${pageContext.request.contextPath}/views/calendario_a.jsp"><i class="material-icons">calendar_month</i>Calendário</a>
        <a class="menu active" href="${pageContext.request.contextPath}/views/turmas.jsp"><i class="material-icons">groups</i>Turmas</a>
        <a class="menu" href="${pageContext.request.contextPath}/views/perfil_p.jsp"><i class="material-icons">person</i>Perfil</a>
    </nav>
    <div class="config">
        <a class="menu" style="color: #ffffff" href="${pageContext.request.contextPath}/index.jsp">
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
            <h2 class="page-title">Boletim Escolar: <%= aluno.getNome()%></h2>
            <% } else { %>
            <h2 class="page-title">Boletim Escolar: Usuário não encontrado</h2>
            <% } %>
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

                            if(n1 != null && n2 != null){
                                media = (n1 + n2) / 2;
                            }

                            if (media == null) {
                                situacao = "Em andamento";
                            } else {
                                situacao = media >= 7 ? "Aprovado" : "Reprovado";
                            }

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
                               value="<%=notaEncontrada.getN1() != null ? notaEncontrada.getN1() : ""%>"></td>
                    <td><input class="nota-edit"
                               type="number"
                               step="0.01"
                               data-id="<%=notaEncontrada.getId()%>"
                               data-campo="n2"
                               min="0"
                               max="10"
                               value="<%=notaEncontrada.getN2() != null ? notaEncontrada.getN2() : ""%>"></td>
                    <td><%= media != null ? String.format("%.2f",media) : "-" %></td>
                    <td>
                        <%
                            System.out.println(media);
                        %>
                        <span class="status <%= media == null?"andamento": media >= 7 ? "approved" : "attention" %>"><%=situacao%></span>
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
                    <td><span class="status andamento">Em andamento</span>
                    </td>
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
    console.log("SCRIPT CARREGOU")

    const loading = document.getElementById("loadingOverlay");

    const btnNotas = document.getElementById("btnNotas");

    if(btnNotas) {
        btnNotas.addEventListener("click", function (e) {

            e.preventDefault(); // impede abrir imediatamente

            loading.style.display = "flex";

            setTimeout(() => {
                window.location.href = this.href;
            }, 500);

        });
    }

    function openLogoutModal() {
        document.getElementById('logoutModal').classList.add('show');
    }

    function closeLogoutModal() {
        document.getElementById('logoutModal').classList.remove('show');
    }

    document.addEventListener("DOMContentLoaded", function(){

        document.querySelectorAll(".nota-edit").forEach(input => {
            console.log("INPUT ENCONTRADO")

            document.addEventListener("input", function(e){

                if(!e.target.classList.contains("nota-edit")) return

                let valor = parseFloat(e.target.value)

                if(!isNaN(valor)){

                    if(valor < 0){
                        e.target.value = 0
                    }

                    if(valor > 10){
                        e.target.value = 10
                    }

                }

                const row = e.target.closest("tr")

                const n1Input = row.querySelector('[data-campo="n1"]')
                const n2Input = row.querySelector('[data-campo="n2"]')

                const n1 = parseFloat(n1Input.value)
                const n2 = parseFloat(n2Input.value)

                const mediaCell = row.children[3]
                const status = row.querySelector(".status")

                if(!isNaN(n1) && !isNaN(n2)){

                    const media = (n1 + n2) / 2

                    mediaCell.innerText = media.toFixed(2)

                    if(media >= 7){
                        status.innerText = "Aprovado"
                        status.className = "status approved"
                    }else{
                        status.innerText = "Reprovado"
                        status.className = "status attention"
                    }

                }else{

                    mediaCell.innerText = "-"
                    status.innerText = "Em andamento"
                    status.className = "status attention"

                }

            })


            input.addEventListener("change", function(){

                const id = this.dataset.id
                console.log(id)
                const campo = this.dataset.campo
                console.log(campo)
                const valor = this.value
                console.log(valor)

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


        // evita bug do loading ao voltar no navegador
        window.addEventListener("pageshow", function(event){

            if(event.persisted){
                const loading = document.getElementById("loadingOverlay")
                if(loading) loading.style.display = "none"
            }

        })

    })
</script>
</body>
</html>