drop table if exists Nota;
drop table if exists Observacao;
drop table if exists ProfessorDisciplina;
drop table if exists TurmaAluno;
drop table if exists Aluno;
drop table if exists Aula;
drop table if exists Turma;
drop table if exists Disciplina;
drop table if exists Professor;
drop table if exists Usuario;
drop table if exists Admin;

create table Usuario ( Id               serial          not null
                     , Nome             varchar(255)    not null
                     , Sobrenome        varchar(255)    not null
                     , Email            varchar(255)    not null
                     , Senha            varchar(255)    not null
                     , Photo            text                null
                     , constraint pk_Usuario        primary key (id)
                     , constraint uq_Usuario_Email  unique (Email)
                     );

create table Professor ( Id     	    serial          not null
                       , Nome   	    varchar(60)     not null
                       , UsuarioId	    int					null
                       , DisciplinaId   int             not null
                       , constraint pk_Professor primary key (Id)
                       );

create table Disciplina ( Id    serial      not null
                        , Nome  varchar(60) not null
                        , constraint pk_Disciplina primary key (Id)
                        );

create table Aluno ( Matricula      serial          not null
			       , Cpf			varchar(11)     not null
                   , DataNascimento date                null
				   , UsuarioId  	int                 null
                   , TelefonePai    varchar(15)     not null
                   , TurmaId        int                 null
                   , constraint pk_aluno primary key (Matricula)
                   );

create table Turma ( Id     	serial      not null
                   , Ano    	int         not null
                   , Serie  	varchar(20) not null
                   , Letra  	char(1)     not null
                   , constraint pk_Turma primary key (Id)
                   );

create table Observacao ( Id            serial          not null
                        , Texto         varchar(2000)   not null
                        , DataCriacao   date            not null default current_date
                        , IdAluno       int             not null
                        , DisciplinaId  int             not null
                        , constraint pk_Observacao              primary key (Id)
                        , constraint fk_Observacao_Aluno        foreign key (IdAluno)       references Aluno(Matricula)
                        , constraint fk_Observacao_Disciplina   foreign key (DisciplinaId)  references Disciplina(Id)
                        );

create table Nota ( Id              serial  not null
                  , DisciplinaId    int     not null
                  , IdAluno         int     not null
                  , N1              numeric not null
                  , N2              numeric not null
                  , constraint pk_Nota              primary key (Id)
                  , constraint fk_Nota_Disciplina   foreign key (DisciplinaId)  references Disciplina(Id)
                  , constraint fk_Nota_Aluno        foreign key (IdAluno)       references Aluno(Matricula)
                  );
				  
create table Aula	( Id            serial 	    not null
                    , HorarioInicio time        not null
                    , HorarioFim    time        not null
                    , ProfessorId	int	        not null
                    , TurmaId       int         not null
                    , diaSemana     char(3)     not null
                    , constraint pk_Aula            primary key (Id)
                    , constraint fk_Aula_Professor  foreign key (ProfessorId)   references Professor(Id)
                    , constraint fk_Aula_Turma      foreign key (TurmaId)       references Turma(Id)
					);

create table Admin  ( Id    serial 	        not null
                    , Usuario varchar(255)  not null
                    , Senha varchar(255)    not null
                    , constraint pk_Admin           primary key (Id)
                    , constraint uq_Admin_Usuario   unique (Usuario)
                    );