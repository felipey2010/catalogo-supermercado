create schema if not exists public;

create table if not exists public.acesso (
                               id        serial      not null constraint acesso_pkey primary key,
                               descricao varchar(40) not null,
                               nome      varchar(40) not null,
                               dt_inclusao timestamp    not null,
                               dt_remocao  timestamp
);
create table if not exists public.perfil (
                               id serial not null constraint perfil_pkey primary key,
                               dt_inclusao timestamp    not null,
                               dt_remocao  timestamp,
                               nome        varchar(255) not null,
                               iseditable boolean not null
);
create table if not exists public.perfil_acesso (
                               perfil_id integer not null constraint perfil_id references public.perfil on delete cascade,
                               acesso_id integer not null constraint acesso_id references public.acesso on delete cascade
);
create table if not exists public.pais (
                             codigo      integer      not null constraint pais_pkey primary key,
                             nome        varchar(255) not null,
                             isobinario  varchar(2),
                             isoternario varchar(3)
);
create table if not exists public.pessoa (
                           id                  serial not null constraint pessoa_pkey primary key,
                           dt_inclusao         timestamp,
                           dt_remocao          timestamp,
                           codigo              varchar(255) constraint un_codigo unique,
                           email               varchar(255),
                           nome                varchar(120) not null,
                           genero              varchar(12) not null,
                           naturalidade        varchar(30),
                           nacionalidade_id    integer not null constraint fk_nacionalidade_id references public.pais
);

create table if not exists public.telefone (
                         pessoa_id integer      not null constraint fk_pessoa_id references public.pessoa on delete cascade,
                         numero    varchar(255) not null
);
create table if not exists public.usuario (
                            id          serial       not null constraint usuario_pkey primary key,
                            login            varchar(20)  not null,
                            ativo            boolean,
                            dt_inclusao      timestamp    not null,
                            dt_remocao       timestamp,
                            dt_ultimo_login  timestamp,
                            senha            varchar(255) not null,
                            pessoa_id integer not null constraint fk_pessoa_id references public.pessoa on delete cascade,
);
create table if not exists public.usuario_perfil (
                            usuario_id int not null constraint fk_usuario_id references public.usuario on delete cascade,
                            perfil_id  integer     not null constraint fk_perfil_id references public.perfil on delete cascade,
                            constraint usuario_perfil_pkey primary key (usuario_id, perfil_id)
);
create table if not exists public.mensagem_global (
            id             serial  not null constraint mensagem_pkey primary key,
            arquivo        bytea,
            datainclusao   timestamp,
            dataremocao    timestamp,
            mensagem       text    not null,
            usuario_id     integer constraint fk_usuario_id references public.usuario,
            global         boolean not null,
            titulo varchar(255)
);