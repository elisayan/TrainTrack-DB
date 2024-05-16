-- *********************************************
-- * SQL MySQL generation                      
-- *--------------------------------------------
-- * DB-MAIN version: 11.0.2              
-- * Generator date: Sep 14 2021              
-- * Generation date: Mon May 13 12:38:38 2024 
-- * LUN file: C:\Users\jiaax\Downloads\traintrack-relazionale.lun
-- * Schema: traintrack/1-1-1 
-- ********************************************* 


-- Database Section
-- ________________ 

create database traintrack;
use traintrack;


-- Tables Section
-- _____________ 

create table Acquisti (
     CodOrdine int not null,
     CodServizio int not null,
     constraint IDacquisto primary key (CodServizio, CodOrdine));

create table Attivazioni (
     CodNotifica int not null,
     CF char(16) not null,
     constraint IDattivazione primary key (CF, CodNotifica));

create table BuoniSconto (
     CodBuonoSconto int not null,
     Importo float(6) not null,
     DataInizioValidità date not null,
     DataScadenza date not null,
     DataUtilizzo date,
     CF char(16) not null,
     constraint IDBUONOSCONTO primary key (CodBuonoSconto));

create table Carrozze (
     CodTreno int not null,
     NumeroC smallint not null,
     constraint IDCARROZZA_ID primary key (CodTreno, NumeroC));

create table CheckIn (
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

create table Composizioni (
     CodTabellone int not null,
     CodPercorso int not null,
     constraint IDcomposizione primary key (CodTabellone, CodPercorso));

create table File (
     CodTreno int not null,
     NumeroC smallint not null,
     Lettera char(1) not null,
     constraint IDFILA_ID primary key (CodTreno, NumeroC, Lettera));

create table Medianti (
     CodStazione int not null,
     CodPercorso int not null,
     Ordine text(1000) not null,
     constraint IDmediante primary key (CodStazione, CodPercorso));

create table Notifiche (
     CodNotifica int not null,
     Descrizione text(500) not null,
     CodPercorso int not null,
     constraint IDNOTIFICA primary key (CodNotifica));

create table Ordini (
     CodOrdine int not null,
     DataPagamento date not null,
     OraPagamento timestamp not null,
     CF char(16) not null,
     constraint IDORDINE_ID primary key (CodOrdine));

create table Percorsi (
     CodPercorso int not null,
     CF char(16) not null,
     CodTreno int not null,
     TempoPercorrenza time not null,
     constraint IDPERCORSO_ID primary key (CodPercorso),
     constraint IDPERCORSO_1 unique (CodTreno, CodPercorso),
     constraint IDPERCORSO_2 unique (CF, CodPercorso));

create table Persone (
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

create table Posti (
     CodTreno int not null,
     NumeroC smallint not null,
     Lettera char(1) not null,
     NumeroP smallint not null,
     constraint IDPOSTO primary key (CodTreno, NumeroC, Lettera, NumeroP));

create table Resi (
     CodReso int not null,
     CodOrdine int not null,
     Motivazione text(500) not null,
     Data date not null,
     CF char(16) not null,
     constraint IDRESO primary key (CodReso),
     constraint FKcorrelazione_ID unique (CodOrdine));

create table Sequenze (
     Suc_CodStazione int not null,
     CodStazione int not null,
     constraint IDsequenza primary key (CodStazione, Suc_CodStazione));

create table Servizi (
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

create table Stazioni (
     CodStazione int not null,
     Nome varchar(20) not null,
     constraint IDSTAZIONE_ID primary key (CodStazione));

create table Tabelloni (
     CodTabellone int not null,
     OrarioPartenzaPrevisto timestamp not null,
     OrarioArrivoPrevisto timestamp not null,
     OrarioArrivoReale timestamp not null,
     OrarioPartenzaReale timestamp not null,
     StatoTreno varchar(20) not null,
     Binario smallint not null,
     constraint IDTABELLONE primary key (CodTabellone));

create table TipiAbbonamento (
     Durata time not null,
     Chilometraggio smallint not null,
     Prezzo float(6) not null,
     constraint IDTIPOABBONAMENTO primary key (Durata, Chilometraggio));

create table Treni (
     CodTreno int not null,
     PostiTotali smallint not null,
     Tipo varchar(10) not null,
     Classe varchar(20) default "2 classe",
     constraint IDTRENO primary key (CodTreno));


-- Constraints Section
-- ___________________ 

alter table Acquisti add constraint FKacq_SER
     foreign key (CodServizio)
     references Servizi (CodServizio);

alter table acquisti add constraint FKacq_ORD
     foreign key (CodOrdine)
     references Ordini (CodOrdine);

alter table attivazioni add constraint FKatt_PER
     foreign key (CF)
     references Persone (CF);

alter table attivazioni add constraint FKatt_NOT
     foreign key (CodNotifica)
     references Notifiche (CodNotifica);

alter table BuoniSconto add constraint FKposseduto
     foreign key (CF)
     references Persone (CF);

-- Not implemented
-- alter table Carrozze add constraint IDCARROZZA_CHK
--     check(exists(select * from File
--                  where File.CodTreno = CodTreno and File.NumeroC = NumeroC)); 

alter table Carrozze add constraint FKcomposizioneT
     foreign key (CodTreno)
     references Treni (CodTreno);

alter table CheckIn add constraint FKvalidizione
     foreign key (CodServizio)
     references Servizi (CodServizio);

alter table CheckIn add constraint FKfatto
     foreign key (CF)
     references Persone (CF);

alter table CheckIn add constraint FKconvalidazione
     foreign key (CodPercorso)
     references Percorsi (CodPercorso);

alter table CheckIn add constraint FKscelta_FK
     foreign key (CodTreno, NumeroC, Lettera, NumeroP)
     references Posti (CodTreno, NumeroC, Lettera, NumeroP);

alter table CheckIn add constraint FKscelta_CHK
     check((CodTreno is not null and NumeroC is not null and Lettera is not null and NumeroP is not null)
           or (CodTreno is null and NumeroC is null and Lettera is null and NumeroP is null)); 

alter table Composizioni add constraint FKcom_PER
     foreign key (CodPercorso)
     references Percorsi (CodPercorso);

alter table Composizioni add constraint FKcom_TAB
     foreign key (CodTabellone)
     references Tabelloni (CodTabellone);

-- Not implemented
-- alter table File add constraint IDFILA_CHK
--     check(exists(select * from Posti
--                  where Posti.CodTreno = CodTreno and Posti.NumeroC = NumeroC and Posti.Lettera = Lettera)); 

alter table File add constraint FKorganizzazione
     foreign key (CodTreno, NumeroC)
     references Carrozze (CodTreno, NumeroC);

alter table Medianti add constraint FKmed_PER
     foreign key (CodPercorso)
     references Percorsi (CodPercorso);

alter table Medianti add constraint FKmed_STA
     foreign key (CodStazione)
     references Stazioni (CodStazione);

alter table Notifiche add constraint FKriferimento
     foreign key (CodPercorso)
     references Percorsi (CodPercorso);

-- Not implemented
-- alter table Ordini add constraint IDORDINE_CHK
--     check(exists(select * from Acquisti
--                  where Acquisti.CodOrdine = CodOrdine)); 

alter table Ordini add constraint FKeffettua
     foreign key (CF)
     references Persone (CF);

-- Not implemented
-- alter table Percorsi add constraint IDPERCORSO_CHK
--     check(exists(select * from Notifiche
--                  where Notifiche.CodPercorso = CodPercorso)); 

-- Not implemented
-- alter table Percorsi add constraint IDPERCORSO_CHK
--     check(exists(select * from Composizioni
--                  where Composizioni.CodPercorso = CodPercorso)); 

-- Not implemented
-- alter table Percorsi add constraint IDPERCORSO_CHK
--     check(exists(select * from Medianti
--                  where Medianti.CodPercorso = CodPercorso)); 

alter table Percorsi add constraint FKcondotto
     foreign key (CF)
     references Persone (CF);

alter table Percorsi add constraint FKsegue
     foreign key (CodTreno)
     references Treni (CodTreno);

alter table Posti add constraint FKsuddivisione
     foreign key (CodTreno, NumeroC, Lettera)
     references File (CodTreno, NumeroC, Lettera);

alter table Resi add constraint FKcorrelazione_FK
     foreign key (CodOrdine)
     references Ordini (CodOrdine);

alter table Resi add constraint FKannullamento
     foreign key (CF)
     references Persone (CF);

alter table Sequenze add constraint FKprecedente
     foreign key (CodStazione)
     references Stazioni (CodStazione);

alter table Sequenze add constraint FKsuccessivo
     foreign key (Suc_CodStazione)
     references Stazioni (CodStazione);

alter table Servizi add constraint FKtipologia_FK
     foreign key (Durata, Chilometraggio)
     references TipiAbbonamento (Durata, Chilometraggio);

alter table Servizi add constraint FKtipologia_CHK
     check((Durata is not null and Chilometraggio is not null)
           or (Durata is null and Chilometraggio is null)); 

alter table Servizi add constraint FKriguarda
     foreign key (CodPercorso)
     references Percorsi (CodPercorso);

-- Not implemented
-- alter table Stazioni add constraint IDSTAZIONE_CHK
--     check(exists(select * from Medianti
--                  where Medianti.CodStazione = CodStazione)); 


-- Index Section
-- _____________ 

