<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.sistema.estudiantes.model.Aula" %>

<%
  List<Aula> aulas = (List<Aula>) request.getAttribute("aulas");
%>

<% if(aulas == null || aulas.isEmpty()){ %>
Nenhuma aula para este dia.
<% } else {
  for(Aula aula : aulas){
%>

<div class="aula-item">
  Professor: <%= aula.getProfessorId().getNome() %><br>
  Horário: <%= aula.getHorarioInicio() + "-" + aula.getHorarioFim() %>
</div>

<%  }
} %>