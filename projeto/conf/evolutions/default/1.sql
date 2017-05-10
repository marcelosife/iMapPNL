# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table conceito (
  id                        integer auto_increment not null,
  pergunta_id               integer not null,
  descricao                 varchar(255),
  constraint pk_conceito primary key (id))
;

create table palavra_chave (
  id                        integer auto_increment not null,
  descricao                 varchar(255),
  constraint pk_palavra_chave primary key (id))
;

create table pergunta (
  id                        integer auto_increment not null,
  relacao                   varchar(255),
  chave                     varchar(255),
  pergunta                  varchar(255),
  tp_resposta_id            integer,
  constraint pk_pergunta primary key (id))
;

create table pergunta_pl (
  id                        integer auto_increment not null,
  temlista                  tinyint(1) default 0,
  maxqtdparametros          integer,
  pergunta                  varchar(255),
  tp_resposta_id            integer,
  qtd_param_id              integer,
  constraint pk_pergunta_pl primary key (id))
;

create table qtd_parametros (
  id                        integer auto_increment not null,
  num_relacao               integer,
  num_conceito              integer,
  constraint pk_qtd_parametros primary key (id))
;

create table tipo_resposta (
  id                        integer auto_increment not null,
  descricao                 varchar(255),
  constraint pk_tipo_resposta primary key (id))
;


create table pergunta_pergunta_pl (
  pergunta_id                    integer not null,
  pergunta_pl_id                 integer not null,
  constraint pk_pergunta_pergunta_pl primary key (pergunta_id, pergunta_pl_id))
;

create table pergunta_pl_palavra_chave (
  pergunta_pl_id                 integer not null,
  palavra_chave_id               integer not null,
  constraint pk_pergunta_pl_palavra_chave primary key (pergunta_pl_id, palavra_chave_id))
;
alter table conceito add constraint fk_conceito_pergunta_1 foreign key (pergunta_id) references pergunta (id) on delete restrict on update restrict;
create index ix_conceito_pergunta_1 on conceito (pergunta_id);
alter table pergunta add constraint fk_pergunta_tpResposta_2 foreign key (tp_resposta_id) references tipo_resposta (id) on delete restrict on update restrict;
create index ix_pergunta_tpResposta_2 on pergunta (tp_resposta_id);
alter table pergunta_pl add constraint fk_pergunta_pl_tpResposta_3 foreign key (tp_resposta_id) references tipo_resposta (id) on delete restrict on update restrict;
create index ix_pergunta_pl_tpResposta_3 on pergunta_pl (tp_resposta_id);
alter table pergunta_pl add constraint fk_pergunta_pl_qtdParam_4 foreign key (qtd_param_id) references qtd_parametros (id) on delete restrict on update restrict;
create index ix_pergunta_pl_qtdParam_4 on pergunta_pl (qtd_param_id);



alter table pergunta_pergunta_pl add constraint fk_pergunta_pergunta_pl_pergunta_01 foreign key (pergunta_id) references pergunta (id) on delete restrict on update restrict;

alter table pergunta_pergunta_pl add constraint fk_pergunta_pergunta_pl_pergunta_pl_02 foreign key (pergunta_pl_id) references pergunta_pl (id) on delete restrict on update restrict;

alter table pergunta_pl_palavra_chave add constraint fk_pergunta_pl_palavra_chave_pergunta_pl_01 foreign key (pergunta_pl_id) references pergunta_pl (id) on delete restrict on update restrict;

alter table pergunta_pl_palavra_chave add constraint fk_pergunta_pl_palavra_chave_palavra_chave_02 foreign key (palavra_chave_id) references palavra_chave (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table conceito;

drop table palavra_chave;

drop table pergunta;

drop table pergunta_pergunta_pl;

drop table pergunta_pl;

drop table pergunta_pl_palavra_chave;

drop table qtd_parametros;

drop table tipo_resposta;

SET FOREIGN_KEY_CHECKS=1;

