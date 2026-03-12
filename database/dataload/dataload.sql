insert into Disciplinas (Id, Nome) values
    (1, 'Matemática'),
    (2, 'Português'),
    (3, 'História'),
    (4, 'Geografia'),
    (5, 'Ciências'),
    (6, 'Informática');

insert into Usuarios (Nome, Sobrenome, Email, Senha, Photo) values
    ('Ana'             , 'Souza'       , 'ana.mat'                         , '123456'  , null),
    ('Carlos'          , 'Lima'        , 'carlos.port'                     , '123456'  , null),
    ('Juliana'         , 'Rocha'       , 'juliana.hist'                    , '123456'  , null),
    ('Diego'           , 'Pereira'     , 'diego.geo'                       , '123456'  , null),
    ('Marcos'          , 'Pereira'     , 'marcos.cie'                      , '123456'  , null),
    ('Diogo'           , 'Pereira'     , 'diogo.info'                      , '123456'  , null),
    ('Gustavo'         , 'Sousa'       , 'gustavosousa@escola.com'         , 'senha'   , null),
    ('Rafael'          , 'Passos'      , 'rafaelpassos@escola.com'         , 'senha'   , null),
    ('Davi'            , 'Lacerda'     , 'davilacerda@escola.com'          , 'senha'   , null),
    ('Carlos Eduardo'  , 'Bellomo'     , 'carloseduardobellomo@escola.com' , 'senha'   , null),
    ('João'            , 'Vitor'       , 'joaovitor@escola.com'            , 'senha'   , null),
    ('Neymar'          , 'Júnior'      , 'neymarjunior@escola.com'         , 'senha'   , null),
    ('Gustavo'         , 'Lima'        , 'gustavolima@escola.com'          , 'senha'   , null),
    ('Steven'          , 'Morrissey'   , 'stevenmorrissey@escola.com'      , 'senha'   , null),
    ('Freddie'         , 'Mercury'     , 'freddiemercury@escola.com'       , 'senha'   , null),
    ('Gilberto'        , 'Gil'         , 'gilbertogil@escola.com'          , 'senha'   , null),
    ('Renato'          , 'Russo'       , 'renatorusso@escola.com'          , 'senha'   , null),
    ('Michael'         , 'Jackson'     , 'michaeljackson@escola.com'       , 'senha'   , null),
    ('Charlie'         , 'Brown'       , 'charliebrown@escola.com'         , 'senha'   , null),
    ('Rocky'           , 'Balboa'      , 'rockbalboa@escola.com'           , 'senha'   , null),
    ('Edward'          , 'Cullen'      , 'edwardcullen@escola.com'         , 'senha'   , null),
    ('Elizabeth'       , 'Woolridge'   , 'elizabethwoolridge@escola.com'   , 'senha'   , null),
    ('Taylor'          , 'Swift'       , 'taylorswift@escola.com'          , 'senha'   , null),
    ('Summer'          , 'Finn'        , 'summerfinn@escola.com'           , 'senha'   , null),
    ('Peter'           , 'Parker'      , 'peterparker@escola.com'          , 'senha'   , null),
    ('Sung'            , 'Jin-woo'     , 'sungjinwoo@escola.com'           , 'senha'   , null);

insert into Professores (Nome, UsuarioId, DisciplinaId) values
    ('Ana Martins',     1, 1), -- matemática
    ('Bruno Lima',      2, 2), -- português
    ('Carla Souza',     3, 3), -- história
    ('Diego Pereira',   4, 4), -- geografia
    ('Érika Santos',    5, 5), -- ciências
    ('Fábio Almeida',   6, 6); -- inglês

insert into Turmas (Ano, Serie, Letra) values
    (2026, '2º', 'D'),
    (2026, '2º', 'E');

insert into Alunos (Matricula, Cpf, DataNascimento, UsuarioId, TelefonePai, TurmaId) values
    (1,  '11111111111', '2009-06-11', 7     , '(11)90000-0001', 1),
    (2,  '22222222222', '2010-01-27', 8     , '(11)90000-0002', 1),
    (3,  '33333333333', '2010-04-11', 9     , '(11)90000-0003', 1),
    (4,  '44444444444', '2010-05-06', 10    , '(11)90000-0004', 1),
    (5,  '55555555555', '2010-02-25', 11    , '(11)90000-0005', 1),
    (6,  '66666666666', '1992-02-05', 12    , '(11)90000-0006', 1),
    (7,  '77777777777', '1989-09-03', 13    , '(11)90000-0007', 1),
    (8,  '88888888888', '1959-05-22', 14    , '(11)90000-0008', 1),
    (9,  '99999999999', '1946-09-05', 15    , '(11)90000-0009', 1),
    (10, '10101010101', '1942-06-26', 16    , '(11)90000-0010', 1),
    (11, '12345678910', '1960-03-27', 17    , '(11)90000-0011', 2),
    (12, '23456789101', '1958-08-29', 18    , '(11)90000-0012', 2),
    (13, '34567891012', '1970-04-09', 19    , '(11)90000-0013', 2),
    (14, '45678910123', '1946-07-06', 20    , '(11)90000-0014', 2),
    (15, '56789101234', '1901-06-20', 21    , '(11)90000-0015', 2),
    (16, '56789101234', '1985-06-21', 22    , '(11)90000-0016', 2),
    (17, '67891012345', '1989-12-13', 23    , '(11)90000-0017', 2),
    (18, '78910123456', '1980-01-17', 24    , '(11)90000-0018', 2),
    (19, '89101234567', '2001-08-10', 25    , '(11)90000-0019', 2),
    (20, '91012345678', '2001-03-08', 26    , '(11)90000-0020', 2);

insert into Aulas (HorarioInicio, HorarioFim, ProfessorId, TurmaId, diaSemana) values
    ('07:00','07:50',1,1,'SEG'),
    ('07:50','08:40',2,1,'SEG'),
    ('08:40','09:30',3,1,'SEG'),
    ('10:00','10:50',4,1,'SEG'),
    ('10:50','11:40',5,1,'SEG'),
    ('11:40','12:30',6,1,'SEG'),
    ('07:00','07:50',6,1,'TER'),
    ('07:50','08:40',1,1,'TER'),
    ('08:40','09:30',2,1,'TER'),
    ('10:00','10:50',3,1,'TER'),
    ('10:50','11:40',4,1,'TER'),
    ('11:40','12:30',5,1,'TER'),
    ('07:00','07:50',5,1,'QUA'),
    ('07:50','08:40',6,1,'QUA'),
    ('08:40','09:30',1,1,'QUA'),
    ('10:00','10:50',2,1,'QUA'),
    ('10:50','11:40',3,1,'QUA'),
    ('11:40','12:30',4,1,'QUA'),
    ('07:00','07:50',4,1,'QUI'),
    ('07:50','08:40',5,1,'QUI'),
    ('08:40','09:30',6,1,'QUI'),
    ('10:00','10:50',1,1,'QUI'),
    ('10:50','11:40',2,1,'QUI'),
    ('11:40','12:30',3,1,'QUI'),
    ('07:00','07:50',3,1,'SEX'),
    ('07:50','08:40',4,1,'SEX'),
    ('08:40','09:30',5,1,'SEX'),
    ('10:00','10:50',6,1,'SEX'),
    ('10:50','11:40',1,1,'SEX'),
    ('11:40','12:30',2,1,'SEX'),
 -- turma 2
    ('07:00','07:50',2,2,'SEG'),
    ('07:50','08:40',3,2,'SEG'),
    ('08:40','09:30',4,2,'SEG'),
    ('10:00','10:50',5,2,'SEG'),
    ('10:50','11:40',6,2,'SEG'),
    ('11:40','12:30',1,2,'SEG'),
    ('07:00','07:50',1,2,'TER'),
    ('07:50','08:40',2,2,'TER'),
    ('08:40','09:30',3,2,'TER'),
    ('10:00','10:50',4,2,'TER'),
    ('10:50','11:40',5,2,'TER'),
    ('11:40','12:30',6,2,'TER'),
    ('07:00','07:50',6,2,'QUA'),
    ('07:50','08:40',1,2,'QUA'),
    ('08:40','09:30',2,2,'QUA'),
    ('10:00','10:50',3,2,'QUA'),
    ('10:50','11:40',4,2,'QUA'),
    ('11:40','12:30',5,2,'QUA'),
    ('07:00','07:50',5,2,'QUI'),
    ('07:50','08:40',6,2,'QUI'),
    ('08:40','09:30',1,2,'QUI'),
    ('10:00','10:50',2,2,'QUI'),
    ('10:50','11:40',3,2,'QUI'),
    ('11:40','12:30',4,2,'QUI'),
    ('07:00','07:50',4,2,'SEX'),
    ('07:50','08:40',5,2,'SEX'),
    ('08:40','09:30',6,2,'SEX'),
    ('10:00','10:50',1,2,'SEX'),
    ('10:50','11:40',2,2,'SEX'),
    ('11:40','12:30',3,2,'SEX');

insert into Admins (Usuario, Senha) values ('admin', 'admin123');

insert into Notas (DisciplinaId, AlunoId, N1, N2) values
    (1, 1, 9  , 7  ),  -- Matemática
    (2, 1, 7.0, 7.5),  -- Português
    (3, 1, 8.2, 8.0),  -- História
    (4, 1, 7.8, 8.1),  -- Geografia
    (5, 1, 8.7, 9.0),  -- Ciências
    (6, 1, 7.5, 8.0),  -- Inglês
    (1, 2, 6.5, 7.5),  -- Matemática
    (2, 2, 7.2, 7.8),  -- Português
    (3, 2, 6.9, 7.3),  -- História
    (4, 2, 7.1, 7.6),  -- Geografia
    (5, 2, 7.4, 7.9),  -- Ciências
    (6, 2, 8.0, 8.4),  -- Inglês
    (1, 3, 9.0, 8.8),  -- Matemática
    (2, 3, 8.1, 8.5),  -- Português
    (3, 3, 7.7, 8.0),  -- História
    (4, 3, 8.3, 8.6),  -- Geografia
    (5, 3, 7.9, 8.2),  -- Ciências
    (6, 3, 8.4, 8.7);  -- Inglês

insert into Observacoes (Texto, DataCriacao, AlunoMatricula, ProfessorId) values
    ('lindo, muito obrigado' , current_date, 1, 6),
    ('profissionalismo'     , current_date, 1, 6),
    ('hahahahahaha'         , current_date, 1, 5),
    ('aoba'                 , current_date, 1, 4),
    ('ulala'                , current_date, 1, 3),
    ('teste'                , current_date, 1, 3),
    ('teste 2'              , current_date, 1, 2),
    ('teste 3'              , current_date, 1, 2);

end;