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
- **Design:** [Figma](link-do-seu-figma-aqui)
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

Desenvolvido por Alunos do Instituto J&F - 2024


---

### 💡 O que mais você pode adicionar?

Para deixar o README ainda melhor, aqui estão algumas sugestões:

1.  **Diagrama do Banco de Dados (ERD):** Como você usa PostgreSQL, colocar uma imagem do diagrama de como as tabelas (Aluno, Professor, Notas, Turmas) se relacionam é muito valioso.
2.  **Screenshots:** Coloque fotos da página de Login ou da Home do Aluno. Isso chama muito a atenção de quem visita o repositório.
3.  **Script do Banco:** Verifique se no seu repositório existe um arquivo `.sql` com os `CREATE TABLE`. Se não existir, é bom criar para que outra pessoa consiga rodar o projeto.

### Respondendo sobre as dependências:
Como você usa **JSP + Servlets + JDBC** puro (sem Maven/Gradle), é essencial que você verifique se o arquivo `.jar` do driver do PostgreSQL está dentro da pasta `WEB-INF/lib` do seu projeto. Se não estiver, quem baixar o código não conseguirá conectar ao banco.



[Image of Database Entity Relationship Diagram for School Management System]


O que achou dessa estrutura? Se precisar que eu detalhe mais alguma funcionalidade o
