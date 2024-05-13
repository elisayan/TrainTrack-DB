-- *********************************************
-- * SQL MySQL generation                      
-- *--------------------------------------------
-- * DB-MAIN version: 11.0.2              
-- * Generator date: Sep 14 2021              
-- * Generation date: Mon May 13 12:38:38 2024 
-- * LUN file: C:\Users\jiaax\Downloads\traintrack-relazionale.lun acquisto
-- * Schema: traintrack/1-1-1 
-- ********************************************* 


-- Database Section
-- ________________ 

create database traintrack;
use traintrack;


-- Tables Section
-- _____________ 

create table acquisto (
     CodOrdine int not null,
     CodServizio int not null,
     constraint IDacquisto primary key (CodServizio, CodOrdine));

create table attivazione (
     CodNotifica int not null,
     CF char(16) not null,
     constraint IDattivazione primary key (CF, CodNotifica));

create table BUONOSCONTO (
     CodBuonoSconto int not null,
     Importo float(6) not null,
     DataInizioValidità date not null,
     DataScadenza date not null,
     DataUtilizzo date,
     CF char(16) not null,
     constraint IDBUONOSCONTO primary key (CodBuonoSconto));

create table CARROZZA (
     CodTreno int not null,
     NumeroC smallint not null,
     constraint IDCARROZZA_ID primary key (CodTreno, NumeroC));

create table CHECKIN (
     CodCheckIn int not null,
     Data  date not null,
     Ora  timestamp not null,
     CodServizio int not null,
     CF char(16) not null,
     CodPercorso int not null,
     CodTreno int,
     NumeroC smallint,
     Lettera char(1),
     NumeroP smallint,
     constraint IDCHECKIN primary key (CodCheckIn));

create table composizione (
     CodTabellone int not null,
     CodPercorso int not null,
     constraint IDcomposizione primary key (CodTabellone, CodPercorso));

create table FILA (
     CodTreno int not null,
     NumeroC smallint not null,
     Lettera char(1) not null,
     constraint IDFILA_ID primary key (CodTreno, NumeroC, Lettera));

create table mediante (
     CodStazione int not null,
     CodPercorso int not null,
     Ordine text(1000) not null,
     constraint IDmediante primary key (CodStazione, CodPercorso));

create table NOTIFICA (
     CodNotifica int not null,
     Descrizione text(500) not null,
     CodPercorso int not null,
     constraint IDNOTIFICA primary key (CodNotifica));

create table ORDINE (
     CodOrdine int not null,
     DataPagamento date not null,
     OraPagamento timestamp not null,
     CF char(16) not null,
     constraint IDORDINE_ID primary key (CodOrdine));

create table PERCORSO (
     CodPercorso int not null,
     CF char(16) not null,
     CodTreno int not null,
     TempoPercorrenza time not null,
     constraint IDPERCORSO_ID primary key (CodPercorso),
     constraint IDPERCORSO_1 unique (CodTreno, CodPercorso),
     constraint IDPERCORSO_2 unique (CF, CodPercorso));

create table PERSONA (
     CF char(16) not null,
     Nome varchar(20) not null,
     Cognome varchar(20) not null,
     Indirizzo varchar(30) not null,
     Telefono int,
     Email varchar(30) not null,
     Password varchar(20),
     SpesaTotale float(10) default 0,
     TipoPersona varchar(10) not null,
     TipoCliente varchar(10),
     constraint IDPERSONA primary key (CF));

create table POSTO (
     CodTreno int not null,
     NumeroC smallint not null,
     Lettera char(1) not null,
     NumeroP smallint not null,
     constraint IDPOSTO primary key (CodTreno, NumeroC, Lettera, NumeroP));

create table RESO (
     CodReso int not null,
     CodOrdine int not null,
     Motivazione text(500) not null,
     Data date not null,
     CF char(16) not null,
     constraint IDRESO primary key (CodReso),
     constraint FKcorrelazione_ID unique (CodOrdine));

create table sequenza (
     Suc_CodStazione int not null,
     CodStazione int not null,
     constraint IDsequenza primary key (CodStazione, Suc_CodStazione));

create table SERVIZIO (
     CodServizio int not null,
     StazionePartenza varchar(20) not null,
     StazioneArrivo varchar(20) not null,
     NomePasseggero varchar(20) not null,
     CognomePasseggero varchar(20) not null,
     TipoTreno varchar(10) not null,
     DataPartenza date not null,
     OrarioPartenza timestamp,
     TipoPasseggero varchar(10) default "adulto",
     Supplemento boolean default false,
     Prezzo float(6),
     Durata time,
     Chilometraggio smallint,
     CodPercorso int not null,
     constraint IDBIGLIETTO primary key (CodServizio));

create table STAZIONE (
     CodStazione int not null,
     Nome varchar(20) not null,
     constraint IDSTAZIONE_ID primary key (CodStazione));

create table TABELLONE (
     CodTabellone int not null,
     OrarioPartenzaPrevisto timestamp not null,
     OrarioArrivoPrevisto timestamp not null,
     OrarioArrivoReale timestamp not null,
     OrarioPartenzaReale timestamp not null,
     StatoTreno varchar(20) not null,
     Binario smallint not null,
     constraint IDTABELLONE primary key (CodTabellone));

create table TIPOABBONAMENTO (
     Durata time not null,
     Chilometraggio smallint not null,
     Prezzo float(6) not null,
     constraint IDTIPOABBONAMENTO primary key (Durata, Chilometraggio));

create table TRENO (
     CodTreno int not null,
     PostiTotali smallint not null,
     Tipo varchar(10) not null,
     Classe varchar(20) default "2 classe",
     constraint IDTRENO primary key (CodTreno));


-- Constraints Section
-- ___________________ 

alter table acquisto add constraint FKacq_SER
     foreign key (CodServizio)
     references SERVIZIO (CodServizio);

alter table acquisto add constraint FKacq_ORD
     foreign key (CodOrdine)
     references ORDINE (CodOrdine);

alter table attivazione add constraint FKatt_PER
     foreign key (CF)
     references PERSONA (CF);

alter table attivazione add constraint FKatt_NOT
     foreign key (CodNotifica)
     references NOTIFICA (CodNotifica);

alter table BUONOSCONTO add constraint FKposseduto
     foreign key (CF)
     references PERSONA (CF);

-- Not implemented
-- alter table CARROZZA add constraint IDCARROZZA_CHK
--     check(exists(select * from FILA
--                  where FILA.CodTreno = CodTreno and FILA.NumeroC = NumeroC)); 

alter table CARROZZA add constraint FKcomposizioneT
     foreign key (CodTreno)
     references TRENO (CodTreno);

alter table CHECKIN add constraint FKvalidizione
     foreign key (CodServizio)
     references SERVIZIO (CodServizio);

alter table CHECKIN add constraint FKfatto
     foreign key (CF)
     references PERSONA (CF);

alter table CHECKIN add constraint FKconvalidazione
     foreign key (CodPercorso)
     references PERCORSO (CodPercorso);

alter table CHECKIN add constraint FKscelta_FK
     foreign key (CodTreno, NumeroC, Lettera, NumeroP)
     references POSTO (CodTreno, NumeroC, Lettera, NumeroP);

alter table CHECKIN add constraint FKscelta_CHK
     check((CodTreno is not null and NumeroC is not null and Lettera is not null and NumeroP is not null)
           or (CodTreno is null and NumeroC is null and Lettera is null and NumeroP is null)); 

alter table composizione add constraint FKcom_PER
     foreign key (CodPercorso)
     references PERCORSO (CodPercorso);

alter table composizione add constraint FKcom_TAB
     foreign key (CodTabellone)
     references TABELLONE (CodTabellone);

-- Not implemented
-- alter table FILA add constraint IDFILA_CHK
--     check(exists(select * from POSTO
--                  where POSTO.CodTreno = CodTreno and POSTO.NumeroC = NumeroC and POSTO.Lettera = Lettera)); 

alter table FILA add constraint FKorganizzazione
     foreign key (CodTreno, NumeroC)
     references CARROZZA (CodTreno, NumeroC);

alter table mediante add constraint FKmed_PER
     foreign key (CodPercorso)
     references PERCORSO (CodPercorso);

alter table mediante add constraint FKmed_STA
     foreign key (CodStazione)
     references STAZIONE (CodStazione);

alter table NOTIFICA add constraint FKriferimento
     foreign key (CodPercorso)
     references PERCORSO (CodPercorso);

-- Not implemented
-- alter table ORDINE add constraint IDORDINE_CHK
--     check(exists(select * from acquisto
--                  where acquisto.CodOrdine = CodOrdine)); 

alter table ORDINE add constraint FKeffettua
     foreign key (CF)
     references PERSONA (CF);

-- Not implemented
-- alter table PERCORSO add constraint IDPERCORSO_CHK
--     check(exists(select * from NOTIFICA
--                  where NOTIFICA.CodPercorso = CodPercorso)); 

-- Not implemented
-- alter table PERCORSO add constraint IDPERCORSO_CHK
--     check(exists(select * from composizione
--                  where composizione.CodPercorso = CodPercorso)); 

-- Not implemented
-- alter table PERCORSO add constraint IDPERCORSO_CHK
--     check(exists(select * from mediante
--                  where mediante.CodPercorso = CodPercorso)); 

alter table PERCORSO add constraint FKcondotto
     foreign key (CF)
     references PERSONA (CF);

alter table PERCORSO add constraint FKsegue
     foreign key (CodTreno)
     references TRENO (CodTreno);

alter table POSTO add constraint FKsuddivisione
     foreign key (CodTreno, NumeroC, Lettera)
     references FILA (CodTreno, NumeroC, Lettera);

alter table RESO add constraint FKcorrelazione_FK
     foreign key (CodOrdine)
     references ORDINE (CodOrdine);

alter table RESO add constraint FKannullamento
     foreign key (CF)
     references PERSONA (CF);

alter table sequenza add constraint FKprecedente
     foreign key (CodStazione)
     references STAZIONE (CodStazione);

alter table sequenza add constraint FKsuccessivo
     foreign key (Suc_CodStazione)
     references STAZIONE (CodStazione);

alter table SERVIZIO add constraint FKtipologia_FK
     foreign key (Durata, Chilometraggio)
     references TIPOABBONAMENTO (Durata, Chilometraggio);

alter table SERVIZIO add constraint FKtipologia_CHK
     check((Durata is not null and Chilometraggio is not null)
           or (Durata is null and Chilometraggio is null)); 

alter table SERVIZIO add constraint FKriguarda
     foreign key (CodPercorso)
     references PERCORSO (CodPercorso);

-- Not implemented
-- alter table STAZIONE add constraint IDSTAZIONE_CHK
--     check(exists(select * from mediante
--                  where mediante.CodStazione = CodStazione)); 


-- Index Section
-- _____________ 

