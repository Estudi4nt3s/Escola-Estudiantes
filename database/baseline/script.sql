drop table if exists Notas;
drop table if exists Observacoes;
drop table if exists Aulas;
drop table if exists Professores;
drop table if exists Alunos;
drop table if exists Turmas;
drop table if exists Disciplinas;
drop table if exists Usuarios;
drop table if exists Admins;
drop table if exists DisciplinaAdm;
drop table if exists TurmaAdm;

create table Usuarios ( Id               serial          not null
                      , Nome             varchar(255)    not null
                      , Sobrenome        varchar(255)    not null
                      , Email            varchar(255)    not null
                      , Senha            varchar(255)    not null
                      , Photo            text                null
                      , constraint pk_Usuarios        primary key (id)
                      , constraint uq_Usuarios_Email  unique (Email)
                      );

create table Disciplinas ( Id    serial      not null
                         , Nome  varchar(60) not null
                         , constraint pk_Disciplinas primary key (Id)
                         );

create table Turmas ( Id     	serial      not null
                    , Ano    	int         not null
                    , Serie  	varchar(20) not null
                    , Letra  	char(1)     not null
                    , constraint pk_Turmas primary key (Id)
                    );

create table Professores ( Id     	    serial          not null
                         , Nome   	    varchar(60)     not null
                         , UsuarioId	    int					null
                         , DisciplinaId   int             not null
                         , constraint pk_Professores                primary key (Id)
                         , constraint fk_Professores_Disciplinas    foreign key (DisciplinaId)  references Disciplinas(Id)
                         , constraint fk_Professores_Usuarios       foreign key (UsuarioId)     references Usuarios(Id)
                         );

create table Alunos ( Matricula         serial          not null
			        , Cpf			    varchar(11)     not null
                    , DataNascimento    date                null
				    , UsuarioId  	    int                 null
                    , TelefonePai       varchar(15)     not null
                    , TurmaId           int                 null
                    , constraint pk_Alunos              primary key (Matricula)
                    , constraint fk_Alunos_Turmas       foreign key (TurmaId)       references Turmas(Id)
                    , constraint fk_Alunos_Usuarios     foreign key (UsuarioId)     references Usuarios(Id)
                    );

create table Observacoes ( Id               serial          not null
                         , Texto            varchar(2000)   not null
                         , DataCriacao      date            not null default current_date
                         , AlunoMatricula   int             not null
                         , ProfessorId      int             not null
                         , DisciplinaId     int             not null
                         , constraint pk_Observacoes              primary key (Id)
                         , constraint fk_Observacoes_Alunos       foreign key (AlunoMatricula)      references Alunos(Matricula)
                         , constraint fk_Observacoes_Professores  foreign key (ProfessorId)         references Professores(Id)
                         , constraint fk_Observacoes_Disciplinas  foreign key (DisciplinaId)        references Disciplinas(Id)
                         );

create table Notas ( Id              serial  not null
                   , DisciplinaId    int     not null
                   , AlunoId         int     not null
                   , N1              numeric not null
                   , N2              numeric not null
                   , constraint pk_Notas                primary key (Id)
                   , constraint fk_Notas_Disciplinas    foreign key (DisciplinaId)  references Disciplinas(Id)
                   , constraint fk_Notas_Alunos         foreign key (AlunoId)       references Alunos(Matricula)
                   );
				  
create table Aulas	( Id            serial 	    not null
                    , HorarioInicio time        not null
                    , HorarioFim    time        not null
                    , ProfessorId	int	        not null
                    , TurmaId       int         not null
                    , diaSemana     char(3)     not null
                    , constraint pk_Aulas               primary key (Id)
                    , constraint fk_Aulas_Professores   foreign key (ProfessorId)   references Professores(Id)
                    , constraint fk_Aulas_Turmas        foreign key (TurmaId)       references Turmas(Id)
					);

create table Admins ( Id    serial 	        not null
                    , Usuario varchar(255)  not null
                    , Senha varchar(255)    not null
                    , constraint pk_Admins           primary key (Id)
                    , constraint uq_Admins_Usuarios   unique (Usuario)
                    );

create table DisciplinaAdm ( Id               serial 	        not null
                           , Nome             varchar(255)      not null
                           , CargaHoraria     int               not null
                           , ProfessorNome    varchar(255)          null
                           , TurmaNome        varchar(255)          null
                           , constraint pk_DisciplinaAdm primary key (Id)
                           );

create table TurmaAdm ( Id               serial 	     not null
                      , Nome             varchar(255)    not null
                      , Ano              int             not null
                      , QuantidadeAlunos int                 null
                      , constraint pk_TurmaAdm primary key (Id)
                      );
