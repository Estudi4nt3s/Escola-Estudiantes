INSERT INTO Professor (Id, Nome) VALUES
 (1, 'Ana Paula Souza'),
 (2, 'Bruno Almeida'),
 (3, 'Carla Mendes'),
 (4, 'Diego Santos'),
 (5, 'Eduarda Lima'),
 (6, 'Fernando Rocha'),
 (7, 'Gabriela Nunes'),
 (8, 'Henrique Duarte'),
 (9, 'Isabela Carvalho'),
 (10, 'João Pedro Martins');

INSERT INTO Disciplina (Id, Nome) VALUES
 (1, 'Matemática'),
 (2, 'Português'),
 (3, 'História'),
 (4, 'Geografia'),
 (5, 'Ciências'),
 (6, 'Inglês'),
 (7, 'Educação Física'),
 (8, 'Artes'),
 (9, 'Física'),
 (10, 'Química');

INSERT INTO ProfessorDisciplina (Id, IdProfessor, IdDisciplina) VALUES
 (1, 1, 1),
 (2, 2, 2),
 (3, 3, 3),
 (4, 4, 4),
 (5, 5, 5),
 (6, 6, 6),
 (7, 7, 7),
 (8, 8, 8),
 (9, 9, 9),
 (10, 10, 10),
 (11, 1, 9),
 (12, 2, 6),
 (13, 3, 4),
 (14, 4, 3),
 (15, 5, 10);

INSERT INTO Aluno (Matricula, Nome, DataNascimento, Photo) VALUES
 (1,  'Lucas Ferreira',       '2010-03-15', NULL),
 (2,  'Mariana Oliveira',     '2009-11-02', NULL),
 (3,  'Rafael Silva',         '2011-01-28', NULL),
 (4,  'Beatriz Santos',       '2010-07-19', NULL),
 (5,  'Gustavo Costa',        '2009-05-05', NULL),
 (6,  'Camila Rodrigues',     '2011-09-13', NULL),
 (7,  'Pedro Almeida',        '2010-12-22', NULL),
 (8,  'Larissa Pereira',      '2009-08-30', NULL),
 (9,  'Thiago Barros',        '2011-04-04', NULL),
 (10, 'Fernanda Teixeira',    '2010-10-10', NULL),
 (11, 'Matheus Carvalho',     '2011-02-17', NULL),
 (12, 'Ana Clara Martins',    '2009-03-25', NULL),
 (13, 'João Vitor Araujo',    '2010-06-06', NULL),
 (14, 'Julia Moreira',        '2011-12-01', NULL),
 (15, 'Daniel Souza',         '2009-09-09', NULL),
 (16, 'Sofia Ribeiro',        '2010-01-11', NULL),
 (17, 'André Gonçalves',      '2011-05-21', NULL),
 (18, 'Bruna Figueiredo',     '2009-07-27', NULL),
 (19, 'Felipe Mendes',        '2010-02-02', NULL),
 (20, 'Carolina Duarte',      '2011-11-14', NULL);

INSERT INTO Turma (Id, Ano, Serie, Letra) VALUES
 (1, 2024, '6º Ano',  'A'),
 (2, 2024, '6º Ano',  'B'),
 (3, 2024, '7º Ano',  'A'),
 (4, 2024, '7º Ano',  'B'),
 (5, 2024, '8º Ano',  'A');

INSERT INTO TurmaAluno (Id, MatriculaAluno, IdTurma) VALUES
 (1,  1, 1),
 (2,  2, 1),
 (3,  3, 1),
 (4,  4, 1),

 (5,  5, 2),
 (6,  6, 2),
 (7,  7, 2),
 (8,  8, 2),

 (9,  9, 3),
 (10, 10, 3),
 (11, 11, 3),
 (12, 12, 3),

 (13, 13, 4),
 (14, 14, 4),
 (15, 15, 4),
 (16, 16, 4),

 (17, 17, 5),
 (18, 18, 5),
 (19, 19, 5),
 (20, 20, 5);

INSERT INTO Observacao (Id, Texto, DataCriacao, IdProfessor, IdAluno, IdDisciplina) VALUES
 (1,  'Bom desempenho nas atividades de sala.',        '2024-03-10', 1,  1,  1),
 (2,  'Precisa revisar frações.',                      '2024-03-12', 1,  2,  1),
 (3,  'Leitura fluente e boa interpretação.',          '2024-04-05', 2,  3,  2),
 (4,  'Demonstra interesse por História.',             '2024-04-08', 3,  4,  3),
 (5,  'Participação ativa em Geografia.',              '2024-04-09', 4,  5,  4),
 (6,  'Excelente curiosidade científica.',             '2024-04-15', 5,  6,  5),
 (7,  'Pronúncia em inglês em evolução.',              '2024-04-20', 6,  7,  6),
 (8,  'Ótimo trabalho em equipe na Educação Física.',  '2024-04-22', 7,  8,  7),
 (9,  'Boa compreensão de conceitos de força.',        '2024-05-01', 9,  9,  9),
 (10, 'Apresentou dúvidas sobre ligações químicas.',   '2024-05-03', 10, 10, 10),
 (11, 'Aprimorar cálculos de potência.',               '2024-05-05', 1,  11, 9),
 (12, 'Vocabulário em inglês ampliando.',              '2024-05-08', 2,  12, 6),
 (13, 'Análise crítica de mapas muito boa.',           '2024-05-10', 4,  13, 4),
 (14, 'Contextualiza fatos históricos com atualidade.', '2024-05-12', 3, 14, 3),
 (15, 'Entendimento adequado de reações químicas.',    '2024-05-15', 5,  15, 10);

INSERT INTO Nota (Id, IdDisciplina, IdAluno, IdTurma, Valor) VALUES
 (1,  1, 1, 1, 8.5),
 (2,  2, 1, 1, 7.8),
 (3,  1, 2, 1, 6.9),
 (4,  2, 2, 1, 8.2),
 (5,  1, 3, 1, 9.1),
 (6,  2, 3, 1, 8.9),
 (7,  1, 4, 1, 7.2),
 (8,  2, 4, 1, 7.5),

 (9,  5, 5, 2, 8.0),
 (10, 6, 5, 2, 7.4),
 (11, 5, 6, 2, 6.8),
 (12, 6, 6, 2, 8.6),
 (13, 5, 7, 2, 9.0),
 (14, 6, 7, 2, 9.2),
 (15, 5, 8, 2, 7.1),
 (16, 6, 8, 2, 7.9),

 (17, 3,  9,  3, 8.3),
 (18, 4,  9,  3, 7.7),
 (19, 3, 10,  3, 9.4),
 (20, 4, 10,  3, 8.8),
 (21, 3, 11,  3, 7.0),
 (22, 4, 11,  3, 7.3),
 (23, 3, 12,  3, 8.1),
 (24, 4, 12,  3, 7.6),

 (25, 9,  13, 4, 6.5),
 (26, 10, 13, 4, 7.2),
 (27, 9,  14, 4, 8.7),
 (28, 10, 14, 4, 8.0),
 (29, 9,  15, 4, 7.9),
 (30, 10, 15, 4, 7.4),
 (31, 9,  16, 4, 9.3),
 (32, 10, 16, 4, 8.9),

 (33, 8,  17, 5, 9.1),
 (34, 7,  17, 5, 9.5),
 (35, 8,  18, 5, 7.8),
 (36, 7,  18, 5, 8.2),
 (37, 8,  19, 5, 8.4),
 (38, 7,  19, 5, 8.7),
 (39, 8,  20, 5, 9.0),
 (40, 7,  20, 5, 9.3);

INSERT INTO Usuario (Id, Email, Senha) VALUES
 (1,  'ana.paula@escola.edu.br',     'senha_ana'),
 (2,  'bruno.almeida@escola.edu.br', 'senha_bruno'),
 (3,  'carla.mendes@escola.edu.br',  'senha_carla'),
 (4,  'diego.santos@escola.edu.br',  'senha_diego'),
 (5,  'eduarda.lima@escola.edu.br',  'senha_eduarda'),
 (6,  'fernando.rocha@escola.edu.br','senha_fernando'),
 (7,  'gabriela.nunes@escola.edu.br','senha_gabriela'),
 (8,  'henrique.duarte@escola.edu.br','senha_henrique'),
 (9,  'isabela.carvalho@escola.edu.br','senha_isabela'),
 (10, 'joao.martins@escola.edu.br',  'senha_joao');

COMMIT;