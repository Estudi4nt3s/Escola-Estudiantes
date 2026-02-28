<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
  <meta charset="UTF-8">
  <title>Calendário</title>
  <link rel="stylesheet" href="calendario.css">
</head>

<body>

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