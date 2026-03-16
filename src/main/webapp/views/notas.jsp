<%@ page import="java.util.List" %>
<%@ page import="com.sistema.estudiantes.model.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<%
    Aluno aluno = (Aluno) request.getSession().getAttribute("alunoSelecionado");
    Professor professor = (Professor) request.getSession().getAttribute("professor");
    String[] data = (String[]) request.getSession().getAttribute("data");
    List<Disciplina> disciplinas = (List<Disciplina>) request.getSession().getAttribute("disciplinas");
    List<Nota> notas = (List<Nota>) request.getSession().getAttribute("notas");
    Disciplina disc = (Disciplina) request.getSession().getAttribute("disciplina");
    List<Observacao> observacoes = (List<Observacao>) request.getSession().getAttribute("observacoes");
    Turma turma = (Turma) request.getSession().getAttribute("turmaSelecionada");
%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%if (aluno != null) {%>
    <title>Estudiantes - Boletim de <%=aluno.getNome()%></title>
    <%} else { %>
    <title>Estudiantes - Boletim</title>
    <% }
        assert aluno != null;%>
    <link rel="icon" href="${pageContext.request.contextPath}/utils/school.png">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/aluno_p.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/carregar.css">
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
                <img src="${pageContext.request.contextPath}/utils/perfil.png" alt="Avatar">
                <span><%=professor.getNome()%></span>
            </div>
        </div>
    </header>

    <div class="main-content">
        <div class="page-header" style="margin-bottom: 15px;">
            <h2>Aluno: <%= aluno.getNome()%></h2>
        </div>
        <h2 class="page-title boletim">Boletim:</h2>
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
                        if(disciplina.getNome().equals(disc.getNome())){
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
                               data-id="<%=notaEncontrada.getId()%>"
                               data-campo="n1"
                               min="0"
                               max="10"
                               value="<%=notaEncontrada.getN1() != null ? notaEncontrada.getN1() : ""%>"></td>
                    <td><input class="nota-edit"
                               type="number"
                               data-id="<%=notaEncontrada.getId()%>"
                               data-campo="n2"
                               min="0"
                               max="10"
                               value="<%=notaEncontrada.getN2() != null ? notaEncontrada.getN2() : ""%>"></td>
                    <td><%= media != null ? String.format("%.2f",media) : "-" %></td>
                    <td>
                        <%
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
                    }
                %>
                </tbody>
            </table>
        </div>
    </div>
    <div class="main-content">
        <div class="page-header">
            <h2 class="page-title boletim">Observações:</h2>
            <button id="abrirInserir" class="submit">
                <i class="material-icons">add</i>
                Adicionar
            </button>
        </div>


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

<div id="loadingOverlay">
    <div class="loadingBox">
        <div class="spinner"></div>
        <p>Carregando...</p>
    </div>
</div>
<button id="btnNotas"
        class="btn-sair-fixo"
        data-url="${pageContext.request.contextPath}/aluno?sub_acao=buscar_todos&id=<%= turma.getId() %>">
    Voltar
</button>
<script>
    const contextPath = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/js/notificacoes.js"></script>
<script src="${pageContext.request.contextPath}/js/popup.js"></script>

<script>
    console.log("SCRIPT CARREGOU")

    const btnNotas = document.getElementById("btnNotas")
    const loading = document.getElementById("loadingOverlay")

    if(btnNotas){

        btnNotas.addEventListener("click", function(){

            loading.style.display = "flex"

            setTimeout(()=>{
                window.location.href = this.dataset.url
            },400)

        })

    }

    document.querySelectorAll("form").forEach(form => {

        form.addEventListener("submit", function(){

            const loading = document.getElementById("loadingOverlay")
            if(loading){
                loading.style.display = "flex"
            }

        })

    })
    const btnConfirmarExcluir = document.getElementById("confirmarExcluir")

    if(btnConfirmarExcluir){

        btnConfirmarExcluir.addEventListener("click", function(e){

            console.log(this.href)

            e.preventDefault()

            const loading = document.getElementById("loadingOverlay")

            if(loading){
                loading.style.display = "flex"
            }

            setTimeout(()=>{
                window.location.href = this.href
            },400)

        })

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
                    status.className = "status andamento"

                }

            })


            input.addEventListener("change", function(){

                const id = this.dataset.id
                console.log(id)
                const campo = this.dataset.campo
                console.log(campo)
                let valor = this.value
                console.log(valor)

                if(valor === ""){
                    valor = "null"
                }

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