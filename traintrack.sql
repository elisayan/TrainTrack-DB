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
     CodOrdine char(1) not null,
     CodServizio char(1) not null,
     constraint IDacquisto primary key (CodServizio, CodOrdine));

create table attivazione (
     CodNotifica char(1) not null,
     CF char(1) not null,
     constraint IDattivazione primary key (CF, CodNotifica));

create table BUONOSCONTO (
     CodBuonoSconto char(1) not null,
     Importo char(1) not null,
     DataInizioValidità char(1) not null,
     DataScadenza char(1) not null,
     DataUtilizzo char(1),
     CF char(1) not null,
     constraint IDBUONOSCONTO primary key (CodBuonoSconto));

create table CARROZZA (
     CodTreno char(1) not null,
     NumeroC char(1) not null,
     constraint IDCARROZZA_ID primary key (CodTreno, NumeroC));

create table CHECKIN (
     CodCheckIn char(1) not null,
     Data  char(1) not null,
     Ora char(1) not null,
     CodServizio char(1) not null,
     CF char(1) not null,
     CodPercorso char(1) not null,
     CodTreno char(1),
     NumeroC char(1),
     Lettera char(1),
     NumeroP char(1),
     constraint IDCHECKIN primary key (CodCheckIn));

create table composizione (
     CodTabellone char(1) not null,
     CodPercorso char(1) not null,
     constraint IDcomposizione primary key (CodTabellone, CodPercorso));

create table FILA (
     CodTreno char(1) not null,
     NumeroC char(1) not null,
     Lettera char(1) not null,
     constraint IDFILA_ID primary key (CodTreno, NumeroC, Lettera));

create table mediante (
     CodStazione char(1) not null,
     CodPercorso char(1) not null,
     Ordine char(1) not null,
     constraint IDmediante primary key (CodStazione, CodPercorso));

create table NOTIFICA (
     CodNotifica char(1) not null,
     Descrizione char(1) not null,
     CodPercorso char(1) not null,
     constraint IDNOTIFICA primary key (CodNotifica));

create table ORDINE (
     CodOrdine char(1) not null,
     DataPagamento char(1) not null,
     OraPagamento char(1) not null,
     CF char(1) not null,
     constraint IDORDINE_ID primary key (CodOrdine));

create table PERCORSO (
     CodPercorso char(1) not null,
     CF char(1) not null,
     CodTreno char(1) not null,
     TempoPercorrenza char(1) not null,
     constraint IDPERCORSO_ID primary key (CodPercorso),
     constraint IDPERCORSO_1 unique (CodTreno, CodPercorso),
     constraint IDPERCORSO_2 unique (CF, CodPercorso));

create table PERSONA (
     CF char(1) not null,
     Nome char(1) not null,
     Cognome char(1) not null,
     Indirizzo char(1) not null,
     Telefono char(1),
     Email char(1) not null,
     Password char(1),
     SpesaTotale char(1),
     TipoPersona char(1) not null,
     TipoCliente char(1),
     constraint IDPERSONA primary key (CF));

create table POSTO (
     CodTreno char(1) not null,
     NumeroC char(1) not null,
     Lettera char(1) not null,
     NumeroP char(1) not null,
     constraint IDPOSTO primary key (CodTreno, NumeroC, Lettera, NumeroP));

create table RESO (
     CodReso char(1) not null,
     CodOrdine char(1) not null,
     Motivazione char(1) not null,
     Data char(1) not null,
     CF char(1) not null,
     constraint IDRESO primary key (CodReso),
     constraint FKcorrelazione_ID unique (CodOrdine));

create table sequenza (
     Suc_CodStazione char(1) not null,
     CodStazione char(1) not null,
     constraint IDsequenza primary key (CodStazione, Suc_CodStazione));

create table SERVIZIO (
     CodServizio char(1) not null,
     StazionePartenza char(1) not null,
     StazioneArrivo char(1) not null,
     NomePasseggero char(1) not null,
     CognomePasseggero char(1) not null,
     TipoTreno char(1) not null,
     DataPartenza char(1) not null,
     OrarioPartenza char(1),
     TipoPasseggero char(1),
     Supplemento char(1),
     Prezzo char(1),
     Durata char(1),
     Chilometraggio char(1),
     CodPercorso char(1) not null,
     constraint IDBIGLIETTO primary key (CodServizio));

create table STAZIONE (
     CodStazione char(1) not null,
     Nome char(1) not null,
     constraint IDSTAZIONE_ID primary key (CodStazione));

create table TABELLONE (
     CodTabellone char(1) not null,
     OrarioPartenzaPrevisto char(1) not null,
     OrarioArrivoPrevisto char(1) not null,
     OrarioArrivoReale char(1) not null,
     OrarioPartenzaReale char(1) not null,
     StatoTreno char(1) not null,
     Binario char(1) not null,
     constraint IDTABELLONE primary key (CodTabellone));

create table TIPOABBONAMENTO (
     Durata char(1) not null,
     Chilometraggio char(1) not null,
     Prezzo char(1) not null,
     constraint IDTIPOABBONAMENTO primary key (Durata, Chilometraggio));

create table TRENO (
     CodTreno char(1) not null,
     PostiTotali char(1) not null,
     Tipo char(1) not null,
     Classe char(1),
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

