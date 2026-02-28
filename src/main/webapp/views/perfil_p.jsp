<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Colégio Estudiantes - Perfil de ${professor.nome}</title>

    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="css/perfil_p.css">
</head>

<body>

    <aside class="sidebar">
        <div class="logo">
            <i class="material-icons">school</i>
            <span>Colégio Estudiantes</span>
        </div>

        <nav>
            <a class="menu" href="home_p.jsp"><i class="material-icons">home</i>Início</a>
            <a class="menu" href="disciplinas_p.jsp"> <i class="material-icons">menu_book</i>Disciplinas</a>
            <a class="menu"><i class="material-icons">calendar_month</i>Calendário</a>
            <a class="menu" href="turmas_p.jsp"><i class="material-icons">calendar_today</i>Turmas</a>
        </nav>

        <div class="config">
            <a class="menu active" style="margin-left: -25px;" href="perfil_p.jsp">
                <i class="material-icons">person</i>Perfil
            </a>
        </div>
    </aside>

    <main class="main">

        <header class="topbar">
            <div class="date">
                <i class="material-icons">calendar_today</i>
                ${dataAtual} 
            </div>

            <div class="user">
                <div class="avatar">
                    <img src="${professor.fotoUrl}" alt="Avatar">
                    <span>${professor.nome}</span>
                </div>
            </div>
        </header>

        <div class="perfil-page">

            <div class="perfil-header-card">
                <div class="perfil-foto">
                    <img src="${professor.fotoUrlLarge}" alt="Professor">
                </div>
        
                <div class="perfil-info-principal">
                    <h2>${professor.nome}</h2>
                    <p>${professor.cargo} - ${professor.departamento}</p>
        
                    <div class="perfil-status ${professor.ativo ? 'ativo' : 'inativo'}">
                        ● Professor ${professor.ativo ? 'Ativo' : 'Inativo'} | ID: #${professor.id}
                    </div>
                </div>
                <button class="btn-edit-perfil" onclick="location.href='editar_perfil.jsp'">
                    <i class="material-icons" style="font-size: 18px; vertical-align: middle;">edit</i> Editar Perfil
                </button>
            </div>
        
            <div class="perfil-detalhes">
        
                <div class="perfil-card-info">
                    <h3>Informações Institucionais</h3>
                    <div class="linha"><span>Matrícula:</span> ${professor.matricula}</div>
                    <div class="linha"><span>Email:</span> ${professor.email}</div>
                    <div class="linha"><span>Telefone:</span> ${professor.telefone}</div>
                    <div class="linha"><span>Formação:</span> ${professor.formacao}</div>
                </div>
        
                <div class="perfil-card-info">
                    <h3>Gestão Acadêmica</h3>
                    <div class="linha"><span>Carga Horária:</span> ${professor.cargaHoraria}h / Semanal</div>
                    <div class="linha"><span>Turmas sob Gestão:</span> ${professor.listaTurmas}</div>
                    <div class="linha"><span>Tempo de Casa:</span> ${professor.tempoServico} Anos</div>
                    <div class="linha"><span>Último Plano de Aula:</span> ${professor.dataUltimoPlano}</div>
                </div>
        
                <div class="perfil-card-info">
                    <h3>Especialidades e Projetos</h3>
                    <div class="tags">
                        <c:forEach var="tag" items="${professor.especialidades}">
                            <span>${tag}</span>
                        </c:forEach>
                    </div>
                </div>
        
            </div>
        </div>

    </main>

    <script src="js/notificacoes.js"></script>
</body>
</html>