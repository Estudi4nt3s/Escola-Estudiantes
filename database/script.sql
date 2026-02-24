drop table if exists Nota
drop table if exists Observacao
drop table if exists ProfessorDisciplina
drop table if exists TurmaAluno
drop table if exists Professor
drop table if exists Aluno
drop table if exists Turma
drop table if exists Disciplina
drop table if exists Usuario
drop table if exists Aula

create table Professor ( Id     	serial          not null
                       , Nome   	varchar(60)     not null
                       , UsuarioId	int					null
                       , constraint pk_Professor primary key (Id)
                       )

create table Disciplina ( Id    serial      not null
                        , Nome  varchar(60) not null
                        , constraint pk_Disciplina primary key (Id)
                        )

create table Aula	( Id 			serial 	    not null
					, Horario		hour        not null
					, DisciplinaId	int	        not null
                    , TurmaId       int         not null
                    , diaSemana     varchar(20) not null
                    , constraint fk_Aula_Disciplina  foreign key (DisciplinaId)   references Disciplina(Id)
                    , constraint fk_Aula_Turma  foreign key (TurmaId)   references Turma(Id)
					)

create table ProfessorDisciplina ( Id           serial  not null
                                 , ProfessorId  int     not null
                                 , DisciplinaId int     not null
                                 , constraint pk_ProfessorDisciplina            primary key (Id)
                                 , constraint fk_ProfessorDisciplina_Professor  foreign key (ProfessorId)   references Professor(Id)
                                 , constraint fk_ProfessorDisciplina_Disciplina foreign key (DisciplinaId)  references Disciplina(Id)
                                 )

create table Aluno ( Matricula      serial          not null
			       , Cpf			varchar(11)     not null
                   , Nome           varchar(200)    not null
				   , UsuarioId  	int                 null
                   , constraint pk_aluno primary key (Matricula)
                   )

create table Turma ( Id     	serial      not null
                   , Ano    	int         not null
                   , Serie  	varchar(20) not null
                   , Letra  	char(1)     not null
                   , constraint pk_Turma primary key (Id)
                   )

create table TurmaAluno ( Id                serial  not null
                        , MatriculaAluno    int     not null
                        , IdTurma           int     not null
                        , constraint pk_TurmaAluno          primary key (Id)
                        , constraint fk_TurmaAluno_Aluno    foreign key (MatriculaAluno)   references Aluno(Matricula)
                        , constraint fk_TurmaAluno_Turma    foreign key (IdTurma)          references Turma(Id)
                        )

create table Observacao ( Id            serial          not null
                        , Texto         varchar(2000)   not null
                        , DataCriacao   date            not null default current_date
                        , ProfessorId   int             not null
                        , IdAluno       int             not null
                        , DisciplinaId  int             not null
                        , constraint pk_Observacao primary key (Id)
                        , constraint fk_Observacao_Professor    foreign key (ProfessorId)   references Professor(Id)
                        , constraint fk_Observacao_Aluno        foreign key (IdAluno)       references Aluno(Matricula)
                        , constraint fk_Observacao_Disciplina   foreign key (DisciplinaId)  references Disciplina(Id)
                        )

create table Nota ( Id              serial  not null
                  , DisciplinaId    int     not null
                  , IdAluno         int     not null
                  , IdTurma         int     not null
                  , Valor           numeric not null
                  , constraint pk_Nota              primary key (Id)
                  , constraint fk_Nota_Disciplina   foreign key (DisciplinaId)  references Disciplina(Id)
                  , constraint fk_Nota_Aluno        foreign key (IdAluno)       references Aluno(Matricula)
                  , constraint fk_Nota_Turma        foreign key (IdTurma)       references Turma(Id)
                  )

create table Usuario ( Id               serial          not null
                     , Email            varchar(255)    not null
                     , Senha            varchar(255)    not null
                     , isAdm            boolean         not null default false
                     , Photo            text                null
                     , DataNascimento   date                null
                     , constraint pk_Usuario        primary key (id)
                     , constraint uq_Usuario_Email  unique (Email)
                     )
