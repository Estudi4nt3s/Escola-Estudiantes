drop view if exists vwQntdAlunosTurma;
drop view if exists Boletim;
drop view if exists DisciplinasAdm;
drop table if exists TurmaDisciplinas;
drop table if exists Notas;
drop table if exists Observacoes;
drop table if exists Aulas;
drop table if exists Professores;
drop table if exists Alunos;
drop table if exists Turmas;
drop table if exists Disciplinas;
drop table if exists Usuarios;
drop table if exists Admins;
drop table if exists TurmaAdm;

create table Usuarios ( Id                serial          not null
                      , Email             varchar(255)    not null
                      , Senha             varchar(255)    not null
                      , Photo             text                null
                      , TokenRecuperacao  varchar(64)         null
                      , TokenExpira       timestamp           null
                      , constraint pk_Usuarios        primary key (id)
                      , constraint uq_Usuarios_Email  unique (Email)
                      );

create table Disciplinas ( Id    serial      not null
                         , Nome  varchar(60) not null
                         , constraint pk_Disciplinas        primary key (Id)
                         , constraint uq_Disciplinas_Nome   unique (Nome)
                         );

create table Turmas ( Id     	    serial      not null
                    , Nome  	    varchar(50) not null
                    , Ano    	    int         not null
                    , QuantidadeMax int             null
                    , constraint pk_Turmas          primary key (Id)
                    , constraint uq_Turmas_Nome     unique (Nome)
                    );

create table Professores ( Id     	    serial          not null
                         , Nome         varchar(255)    not null
                         , UsuarioId	  int				          null
                         , DisciplinaId int             not null
                         , constraint pk_Professores                primary key (Id)
                         , constraint fk_Professores_Disciplinas    foreign key (DisciplinaId)  references Disciplinas(Id)
                         , constraint fk_Professores_Usuarios       foreign key (UsuarioId)     references Usuarios(Id)
                         );

create table Alunos ( Matricula         serial          not null
                    , Nome              varchar(255)    not null
			              , Cpf			          varchar(11)     not null
                    , DataNascimento    date                null
				            , UsuarioId  	      int                 null
                    , TelefonePai       varchar(15)     not null
                    , TurmaId           int                 null
                    , constraint pk_Alunos              primary key (Matricula)
                    , constraint fk_Alunos_Turmas       foreign key (TurmaId)       references Turmas(Id)
                    , constraint fk_Alunos_Usuarios     foreign key (UsuarioId)     references Usuarios(Id)
                    , constraint uq_Alunos_Cpf          unique (Cpf)
                    );

create table Observacoes ( Id               serial          not null
                         , Texto            varchar(2000)   not null
                         , DataCriacao      date            not null default current_date
                         , AlunoMatricula   int             not null
                         , ProfessorId      int             not null
                         , constraint pk_Observacoes              primary key (Id)
                         , constraint fk_Observacoes_Alunos       foreign key (AlunoMatricula)      references Alunos(Matricula)
                         , constraint fk_Observacoes_Professores  foreign key (ProfessorId)         references Professores(Id)
                         );

create table Notas ( Id             serial  not null
                   , DisciplinaId   int     not null
                   , AlunoMatricula int     not null
                   , N1             numeric     null
                   , N2             numeric     null
                   , constraint pk_Notas                primary key (Id)
                   , constraint fk_Notas_Disciplinas    foreign key (DisciplinaId)  references Disciplinas(Id)
                   , constraint fk_Notas_Alunos         foreign key (AlunoMatricula)       references Alunos(Matricula)
                   );
				  
create table Aulas	( Id            serial 	    not null
                    , HorarioInicio time        not null
                    , HorarioFim    time        not null
                    , ProfessorId	  int	        not null
                    , TurmaId       int         not null
                    , diaSemana     char(3)     not null
                    , constraint pk_Aulas               primary key (Id)
                    , constraint fk_Aulas_Professores   foreign key (ProfessorId)   references Professores(Id)
                    , constraint fk_Aulas_Turmas        foreign key (TurmaId)       references Turmas(Id)
					);

create table Admins ( Id      serial 	      not null
                    , Usuario varchar(255)  not null
                    , Senha   varchar(255)  not null
                    , constraint pk_Admins           primary key (Id)
                    , constraint uq_Admins_Usuario   unique (Usuario)
                    );

create view vwQntdAlunosTurma
as
select
      t.Nome
    , count(a.Matricula) as qntdAlunos
from Turmas t
left join Alunos a on a.TurmaId = t.Id
group by t.Nome;

create view DisciplinasAdm
as
select
      d.Nome
    , p.Nome as Professor
from Disciplinas d
right join Professores p on p.Id = d.Id;

create view Boletim
as
select
      a.Nome
    , d.Nome as Disciplina
    , n.n1
    , n.n2
    , case
        when n.n1 isnull or n.n2 isnull
            then null
        else round((n.n1 + n.n2)/2, 1)
      end as MediaFinal
    , case
        when n.N1 isnull or n.N2 isnull
            then null
        when round((n.N1 + n.N2) / 2, 1) < 7
            then 'Reprovado'
        else 'Aprovado'
      end as Status
from Notas n
right join Alunos a on a.Matricula = n.AlunoMatricula
left join Disciplinas d on d.Id = n.DisciplinaId;

create or replace function trg_alunos_notas()
returns trigger
language plpgsql
as $$
begin
      insert into Notas (DisciplinaId, AlunoMatricula)
      select d.Id
            , new.Matricula
      from Disciplinas d
      where not exists (
          select 1
          from Notas n
          where n.AlunoMatricula = new.Matricula
            and n.DisciplinaId   = d.Id
      );
    return new;
end;
$$;

drop trigger if exists trg_Alunos_Notas on Alunos;

create trigger trg_Alunos_Notas
after insert on Alunos
for each row
execute function trg_alunos_notas();

create or replace function trg_disciplinas_del_notas()
returns trigger
language plpgsql
as $$
begin
    delete from Notas n
    using Alunos a
    where n.AlunoMatricula = a.Matricula
      and n.DisciplinaId   = old.Id;
    return old;
end;
$$;

drop trigger if exists trg_Disciplinas_Delete_Notas on Disciplinas;

create trigger trg_Disciplinas_Delete_Notas
before delete on Disciplinas
for each row
execute function trg_disciplinas_del_notas();

create or replace function trg_disciplinas_ins_notas()
returns trigger
language plpgsql
as $$
begin
    insert into Notas (DisciplinaId, AlunoMatricula)
    select new.Id
         , a.Matricula
    from Alunos a
    where not exists (
            select 1
            from Notas n
            where n.AlunoMatricula = a.Matricula
              and n.DisciplinaId   = new.Id
      );
    return new;
end;
$$;

drop trigger if exists trg_Disciplinas_Insert_Notas on Disciplinas;

create trigger trg_Disciplinas_Insert_Notas
after insert on Disciplinas
for each row
execute function trg_disciplinas_ins_notas();
