<%--
  Created by IntelliJ IDEA.
  User: rafaelqueiroz-ieg
  Date: 12/03/2026
  Time: 20:51
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Estudiantes - Ops!</title>

    <link rel="icon" href="${pageContext.request.contextPath}/utils/school.png">

    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

    <style>
        :root {
            --red: #d11d22;
            --red-dark: #a5161a;
            --bg: #bd0000bc; /* Fundo avermelhado semi-transparente solicitado */
            --card: #ffffff;
            --shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
            --text-main: #1a1a1a;
            --text-sec: #777;
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Roboto', sans-serif;
        }

        body {
            background: var(--bg);
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
            /* Se quiser um efeito de desfoque no fundo para destacar o card */
            backdrop-filter: blur(4px);
        }

        .error-container {
            background: var(--card);
            max-width: 500px;
            width: 100%;
            padding: 50px 40px;
            border-radius: 28px;
            box-shadow: var(--shadow);
            text-align: center;
            animation: fadeUp 0.6s ease forwards;
            position: relative;
            overflow: hidden;
        }

        /* Linha decorativa no topo */
        .error-container::before {
            content: "";
            position: absolute;
            top: 0;
            left: 40px;
            right: 40px;
            height: 5px;
            background: linear-gradient(90deg, var(--red), transparent);
            border-radius: 0 0 10px 10px;
        }

        .error-icon {
            font-size: 100px;
            color: var(--red);
            margin-bottom: 20px;
            opacity: 0.9;
        }

        h1 {
            font-size: 4.5rem;
            color: var(--text-main);
            margin-bottom: 5px;
            font-weight: 700;
            letter-spacing: -2px;
        }

        h2 {
            font-size: 1.5rem;
            color: var(--text-main);
            margin-bottom: 15px;
        }

        p {
            color: var(--text-sec);
            line-height: 1.6;
            margin-bottom: 35px;
            font-size: 1rem;
        }

        .btn-home {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
            background: var(--red);
            color: white;
            text-decoration: none;
            padding: 16px 32px;
            border-radius: 16px;
            font-weight: 600;
            transition: all 0.3s ease;
            box-shadow: 0 4px 15px rgba(209, 29, 34, 0.3);
        }

        .btn-home:hover {
            background: var(--red-dark);
            transform: translateY(-3px);
            box-shadow: 0 8px 25px rgba(209, 29, 34, 0.4);
        }

        .logo-minimal {
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
            margin-top: 45px;
            color: #444;
            font-weight: 500;
            font-size: 0.95rem;
        }

        @keyframes fadeUp {
            from {
                transform: translateY(40px);
                opacity: 0;
            }
            to {
                transform: translateY(0);
                opacity: 1;
            }
        }
    </style>
</head>
<body>

<div class="error-container">
    <i class="material-icons error-icon">error_outline</i>
    <h1>404</h1>
    <h2>Ops! Página não encontrada</h2>
    <p>Parece que o caminho para o conhecimento foi interrompido. Verifique se o endereço está correto ou retorne ao portal.</p>

    <a href="${pageContext.request.contextPath}/index.jsp" class="btn-home">
        <i class="material-icons">home</i>
        Ir para o Início
    </a>

    <div class="logo-minimal">
        <i class="material-icons" style="font-size: 20px; color: var(--red);">school</i>
        <span>Colégio Estudiantes</span>
    </div>
</div>

</body>
</html>