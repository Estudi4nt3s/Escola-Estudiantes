<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="model.Aula" %>

<%
  List<Aula> aulas = (List<Aula>) request.getAttribute("aulas");
%>

<% if(aulas == null || aulas.isEmpty()){ %>
Nenhuma aula para este dia.
<% } else {
  for(Aula aula : aulas){
%>

<div class="aula-item">
  <strong><%= aula.getNome() %></strong><br>
  Professor: <%= aula.getProfessor() %><br>
  Horário: <%= aula.getHorario() %>
</div>

<%  }
} %>