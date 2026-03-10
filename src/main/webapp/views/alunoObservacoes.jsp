<%--
  Created by IntelliJ IDEA.
  User: davioliveira-ieg
  Date: 04/03/2026
  Time: 21:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sistema.estudiantes.model.Professor" %>
<%@ page import="com.sistema.estudiantes.model.Usuario" %>
<%@ page import="com.sistema.estudiantes.model.ProfessorDisciplina" %>
<%@ page import="com.sistema.estudiantes.model.Disciplina" %>
<%@ page import="com.sistema.estudiantes.model.Observacao" %>
<%@ page import="com.sistema.estudiantes.model.Aluno" %>


<html>
 <head>
     <link rel="stylesheet" href="${pageContext.request.contextPath}/css/observacoesAluno.css">

 </head>
    <div class="sidebar">

        <div class="sidebar-top">
            <div class="logo">Colégio Estudiantes</div>

            <div class="menu">
                <div class="menu-item">
                    <span class="material-symbols-outlined">home</span>
                    Início
                </div>

                <div class="menu-item">
                    <span class="material-symbols-outlined">menu_book</span>
                    Minhas Disciplinas
                </div>

                <div class="menu-item">
                    <span class="material-symbols-outlined">calendar_month</span>
                    Calendário
                </div>

                <div class="menu-item">
                    <span class="material-symbols-outlined">person</span>
                    Perfil
                </div>
            </div>
        </div>

        <div class="sidebar-bottom">
            <div class="menu-item">
                <span class="material-symbols-outlined">settings</span>
                Configurações
            </div>
        </div>

    </div>

    <!-- MAIN -->
    <div class="main">

        <!-- TOPBAR -->
        <div class="topbar">
            <div class="top-left">
                <span class="material-symbols-outlined">calendar_today</span>
                <%= dataAtual %>
            </div>

            <div class="top-right">
                <span class="material-symbols-outlined">notifications</span>
                <div class="user">
                    <img src="https://i.pravatar.cc/40" alt="">
                    <%= nomeUsuario %>
                </div>
            </div>
        </div>

        <!-- CONTENT -->
        <div class="content">
            <div class="center-box">

                <div class="title">Observações</div>

                <div class="select-box">
                    <%= disciplina %> ▼
                </div>

                <div class="card">
                    <div class="card-header">
                        <span><%= professor %></span>
                        <span><%= dataObs %></span>
                    </div>

                    <div class="card-body">
                        O aluno foi muito Lorem ipsum dolor sit amet consectetur adipisicing elit.
                        Esse aliquid consectetur dolorum a reprehenderit labore impedit amet placeat eos iure
                        dolore praesentium repudiandae, distinctio vitae ullam modi voluptatem repellendus molestias!
                        na aula hoje, gostaria que ele continuasse assim durante as aulas
                    </div>
                </div>

            </div>
        </div>

    </div>

    </body>
</html>
</head>
<body>

</body>
</html>
