insert into Disciplinas (Id, Nome) values
 (1, 'matematica'),
 (2, 'portugues'),
 (3, 'historia'),
 (4, 'geografia'),
 (5, 'ciencias'),
 (6, 'ingles');

insert into Usuarios (Nome, Sobrenome, Email, Senha, Photo) values
 ('Ana'             , 'Martins'     , 'ana.mat'                         , '123456'  , null),
 ('Bruno'           , 'Lima'        , 'bruno.port'                      , '123456'  , null),
 ('Carla'           , 'Souza'       , 'carla.hist'                      , '123456'  , null),
 ('Diego'           , 'Pereira'     , 'diego.geo'                       , '123456'  , null),
 ('Érika'           , 'Santos'      , 'erika.cie'                       , '123456'  , null),
 ('Fábio'           , 'Almeida'     , 'fabio.eng'                       , '123456'  , null),
 ('Gustavo'         , 'Souza'       , 'gustavosouza@escola.com'         , 'senha'   , null),
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
 ('Michael'         , 'Jackson'     , 'michaeljackson@escola.com'       , 'senha'   , null);

insert into Professores (Nome, UsuarioId, DisciplinaId) values
 ('Ana Martins',     1, 1), -- matemática
 ('Bruno Lima',      2, 2), -- português
 ('Carla Souza',     3, 3), -- história
 ('Diego Pereira',   4, 4), -- geografia
 ('Érika Santos',    5, 5), -- ciências
 ('Fábio Almeida',   6, 6); -- inglês

insert into Turmas (Ano, Serie, Letra) values
 (2026, '1º EM', 'A');

insert into Alunos (Matricula, Cpf, DataNascimento, UsuarioId, TelefonePai, TurmaId) values
 (1,  '11111111111', '2011-05-10', 7     , '(11)90000-0001', 1),
 (2,  '22222222222', '2010-08-22', 8     , '(11)90000-0002', 1),
 (3,  '33333333333', '2011-01-14', 9     , '(11)90000-0003', 1),
 (4,  '44444444444', '2010-12-03', 10    , '(11)90000-0004', 1),
 (5,  '55555555555', '2009-03-30', 11    , '(11)90000-0005', 1),
 (6,  '66666666666', '2009-07-18', 12    , '(11)90000-0006', 1),
 (7,  '77777777777', '2008-11-11', 13    , '(11)90000-0007', 1),
 (8,  '88888888888', '2008-09-27', 14    , '(11)90000-0008', 1),
 (9,  '99999999999', '2011-02-02', 15    , '(11)90000-0009', 1),
 (10, '10101010101', '2010-04-25', 16    , '(11)90000-0010', 1),
 (11, '12345678910', '2010-04-25', 17    , '(11)90000-0011', 1),
 (12, '23456789101', '2010-04-25', 18    , '(11)90000-0012', 1);

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
 ('11:40','12:30',2,1,'SEX');

insert into Admins (Usuario, Senha) values ('admin', 'admin123');

end;