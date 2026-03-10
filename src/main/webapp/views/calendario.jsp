<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
  <meta charset="UTF-8">
  <title>Estudiantes - Calendário</title>
    <link rel="icon" href="${pageContext.request.contextPath}/utils/school.png">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/calendario.css">
</head>

<body>
<aside class="sidebar">
    <div class="logo">
        <i class="material-icons">school</i>
        <span>Colégio Estudiantes</span>
    </div>

    <nav>
        <a class="menu active" href="${pageContext.request.contextPath}/views/home.jsp"><i class="material-icons">home</i>Início</a>
        <a class="menu" href="${pageContext.request.contextPath}/views/disciplinas.jsp">
            <i class="material-icons">menu_book</i>Minhas Disciplinas</a>
        <a class="menu" href="${pageContext.request.contextPath}/views/aluno.jsp"> <i class="material-icons">grading</i>Notas</a>
        <a class="menu"><i class="material-icons">calendar_month</i>Calendário</a>
        <a class="menu" href="${pageContext.request.contextPath}/views/perfil.jsp"><i class="material-icons">person</i>Perfil</a>
    </nav>

    <div class="config">
        <a class="menu" style="color: #590101" href="${pageContext.request.contextPath}/index.jsp">
            <i class="material-icons">output</i>Sair
        </a>
    </div>
<div class="main">

  <div class="calendar-layout">

    <!-- CALENDÁRIO -->
    <div class="calendar-container">

      <div class="calendar-header">
        <button class="nav-btn" onclick="changeMonth(-1)">◀</button>
        <h2 id="monthYear"></h2>
        <button class="nav-btn" onclick="changeMonth(1)">▶</button>
      </div>

      <div class="calendar-grid" id="calendar"></div>

    </div>

    <!-- AULAS DO DIA -->
    <div class="aula">
      <h3>Aulas do Dia</h3>
      <div id="aulaLista">
        Selecione um dia
      </div>
    </div>

  </div>

</div>

<script>
  const monthYear = document.getElementById("monthYear");
  const calendar = document.getElementById("calendar");

  let currentDate = new Date();

  const months = [
    "Janeiro","Fevereiro","Março","Abril","Maio","Junho",
    "Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"
  ];

  function renderCalendar() {
    calendar.innerHTML = "";

    const year = currentDate.getFullYear();
    const month = currentDate.getMonth();

    monthYear.textContent = months[month] + " de " + year;

    const firstDay = new Date(year, month, 1).getDay();
    const lastDate = new Date(year, month + 1, 0).getDate();

    const dayNames = ["DOM","SEG","TER","QUA","QUI","SEX","SÁB"];
    dayNames.forEach(day=>{
      const div = document.createElement("div");
      div.classList.add("day-name");
      div.textContent = day;
      calendar.appendChild(div);
    });

    for(let i=0;i<firstDay;i++){
      const empty = document.createElement("div");
      empty.classList.add("day","empty");
      calendar.appendChild(empty);
    }

    for(let day=1; day<=lastDate; day++){

      const div = document.createElement("div");
      div.classList.add("day");
      div.textContent = day;

      const today = new Date();
      if(day === today.getDate() &&
              month === today.getMonth() &&
              year === today.getFullYear()){
        div.classList.add("today");
      }

      div.addEventListener("click", function(){

        document.querySelectorAll(".day").forEach(d=>d.classList.remove("selected"));
        div.classList.add("selected");

        const dataSelecionada = year + "-" +
                String(month+1).padStart(2,'0') + "-" +
                String(day).padStart(2,'0');

        carregarAulas(dataSelecionada);
      });

      calendar.appendChild(div);
    }
  }

  function changeMonth(direction){
    currentDate.setMonth(currentDate.getMonth() + direction);
    renderCalendar();
  }

  function carregarAulas(data){

    fetch("<%= request.getContextPath() %>/BuscarAulasServlet?data=" + data)
            .then(response => response.text())
            .then(html => {
              document.getElementById("aulaLista").innerHTML = html;
            })
            .catch(error => {
              document.getElementById("aulaLista").innerHTML = "Erro ao carregar aulas";
            });
  }

  renderCalendar();
</script>

</body>
</html>