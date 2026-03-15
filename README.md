________________pt-BR________________

# 🏫 Escola Estudiantes

<p align="center">
  <img src="https://img.shields.io/badge/Status-Finalizado-green?style=for-the-badge" alt="Status Finalizado">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL">
</p>

## 📌 Sobre o Projeto
O **Escola Estudiantes** é um sistema de gestão escolar funcional desenvolvido por alunos do 2º ano do **Instituto J&F**. O projeto foi concebido durante as aulas de Desenvolvimento com fins avaliativos e diagnósticos, finalizado em 12/03.

O objetivo principal é oferecer uma plataforma versátil que atenda às necessidades de escolas do Fundamental II e Ensino Médio, permitindo a organização centralizada de notas, frequências, calendários e dados de usuários.

---

## 👥 Perfis de Acesso
O sistema é segmentado em três níveis de permissão, direcionados no momento do login:

1. **Administrador**: Possui controle total sobre o ecossistema. É responsável pela gestão global de professores, alunos, edição de calendários e manutenção de notas e perfis. O acesso é protegido por uma área restrita e senha especial.
2. **Professor**: Focado na gestão pedagógica. O professor pode visualizar o cronograma de aulas, gerenciar suas turmas e, especificamente, realizar o lançamento de notas e observações individuais para cada aluno.
3. **Aluno**: Focado na experiência do estudante. Após realizar seu cadastro, o aluno acessa sua página inicial com horários do dia, consulta o calendário de eventos, visualiza seu desempenho por disciplina e pode gerar seu boletim escolar.

---

## 🚀 Funcionalidades Principais
- **Gestão de Horários**: Visualização dinâmica das aulas do dia (Professor, Horário e Assunto).
- **Calendário Acadêmico**: Acompanhamento de feriados e eventos escolares.
- **Módulo de Disciplinas**: Espaço para consulta de notas e feedbacks (observações) pedagógicas.
- **Perfil Editável**: Autonomia para o aluno atualizar seus dados básicos.
- **Gerador de Boletim**: Emissão automática do documento de desempenho.
- **Segurança**: Recuperação de senha via e-mail e autenticação por níveis de acesso.

---

## 🛠️ Tecnologias e Ferramentas
O projeto segue os padrões da arquitetura **MVC (Model-View-Controller)** para garantir organização e escalabilidade:

- **Linguagem:** Java (Backend)
- **Servidor Web:** Apache Tomcat (Servlets)
- **Frontend:** JSP (JavaServer Pages), CSS3 e JavaScript
- **Banco de Dados:** PostgreSQL
- **Conexão:** JDBC (Java Database Connectivity)
- **Design:** [Figma] https://www.figma.com/design/V2DaO5yTUlbII11or9XyVt/Untitled?node-id=0-1&t=IlAW58wlBmOqog5d-1
- **Gestão de Projeto:** Jira

---

## ⚙️ Como Executar o Projeto

### Pré-requisitos
Para rodar este projeto localmente, você precisará de:
1. **JDK 11** ou superior.
2. **Apache Tomcat 9** ou superior.
3. **PostgreSQL** instalado e rodando.
4. O driver JDBC do PostgreSQL (`postgresql-42.x.x.jar`) na pasta `lib` do projeto ou do Tomcat.

### Instalação
1. Clone o repositório:
   ```bash
   git clone [https://github.com/Estudi4nt3s/Escola-Estudiantes.git](https://github.com/Estudi4nt3s/Escola-Estudiantes.git)
Importe o projeto na sua IDE (Recomendado: IntelliJ IDEA).

Configure o banco de dados utilizando o script SQL (se disponível na pasta /database).

Configure o arquivo de conexão JDBC com suas credenciais do PostgreSQL.

Adicione o servidor Tomcat à IDE e execute o artefato.

🎨 Design e Organização
O processo de criação passou por uma etapa de prototipagem no Figma e a organização das tarefas foi gerida via Jira, aplicando metodologias ágeis para o desenvolvimento em grupo através do Git.

Desenvolvido por Alunos do Instituto J&F - 2026

<hr>

________________en-US________________


# 🏫 Estudiantes School

## 📌 About the Project
**Estudiantes School** is a functional school management system developed by 11th-grade students from **Instituto J&F**. The project was conceived during Software Development classes for evaluative and diagnostic purposes, finalized on March 12th.

The main objective is to offer a versatile platform that meets the needs of Middle and High Schools, allowing the centralized organization of grades, attendance, academic calendars, and user data.


---

## 👥 Access Profiles
The system is segmented into three permission levels, directed at the moment of login:

1. **Administrator:** Has full control over the ecosystem. Responsible for the global management of teachers and students, editing calendars, and maintaining grades and profiles. Access is protected by a restricted area and a special password.
2. **Teacher:** Focused on pedagogical management. Teachers can view the class schedule, manage their classes, and specifically perform the entry of grades and individual observations for each student.
3. **Student:** Focused on the student experience. After registering, the student accesses a home page with the day's schedules, consults the event calendar, views performance by subject, and can generate their school report card.

---

## 🚀 Key Features
- **Academic Calendar:** Tracking of holidays and school events.
- **Subject Module:** Space for consulting grades and pedagogical feedback (observations).
- **Editable Profile:** Autonomy for students to update their basic information.
- **Report Card Generator:** Automatic issuance of the performance document.
- **Security:** Password recovery via email and authentication by access levels.

---

## 🛠️ Technologies and Tools
The project follows the **MVC (Model-View-Controller)** architecture patterns to ensure organization and scalability:

- **Language:** Java (Backend)
- **Web Server:** Apache Tomcat (Servlets)
- **Frontend:** JSP (JavaServer Pages), CSS3 e JavaScript
- **Database::** PostgreSQL
- **Connection:** JDBC (Java Database Connectivity)
- **Design:** [Figma](https://www.figma.com/design/V2DaO5yTUlbII11or9XyVt/Untitled?node-id=0-1&t=IlAW58wlBmOqog5d-1)
- **Project Management:** Jira

---

## ⚙️ How to run the project

### Prerequisites
To run this project locally, you will need:
1. **JDK 11** or higher.
2. **Apache Tomcat 9** or higher.
3. **PostgreSQL** installed and running.
4. The PostgreSQL JDBC driver (postgresql-42.x.x.jar) in the project's lib folder or Tomcat's lib folder.
 
### Installation
1. Clone the repository:
   ```bash
   git clone [https://github.com/Estudi4nt3s/Escola-Estudiantes.git](https://github.com/Estudi4nt3s/Escola-Estudiantes.git)
Import the project into your IDE (Recommended: IntelliJ IDEA).

Configure the database using the SQL script (if available in the /database folder).

Configure the JDBC connection file with your PostgreSQL credentials.

Add the Tomcat server to the IDE and run the artifact.

🎨 Design and Organization
The creation process went through a prototyping stage in Figma, and task organization was managed via Jira, applying agile methodologies for group development through Git.

Developed by Students of Instituto J&F - 2026
