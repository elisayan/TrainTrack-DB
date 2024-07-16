-- *********************************************
-- * SQL MySQL generation                      
-- *--------------------------------------------
-- * DB-MAIN version: 11.0.2              
-- * Generator date: Sep 14 2021              
-- * Generation date: Tue Jul 16 12:43:12 2024 
-- * LUN file: C:\Users\jiaax\Downloads\traintrackFinale.lun 
-- * Schema: relazionale-finale/1-1-1-1 
-- ********************************************* 


-- Database Section
-- ________________ 

create database traintrack;
use traintrack;


-- Tables Section
-- _____________ 

create table Attivazione (
     Email varchar(50) not null,
     CodNotifica varchar(50) not null,
     constraint IDAttivazione primary key (Email, CodNotifica));

create table Attraversato (
     CodPercorso varchar(50) not null,
     CodStazione varchar(50) not null,
     Data date not null,
     Ordine int not null,
     OrarioPartenzaPrevisto time not null,
     OrarioArrivoPrevisto time not null,
     OrarioArrivoReale time not null,
     OrarioPartenzaReale time not null,
     Binario int not null,
     StatoArrivo varchar(50) not null,
     StatoPartenza varchar(50) not null,
     constraint IDAttraversato primary key (CodStazione, CodPercorso, Data));

create table BuonoSconto (
     CodBuonoSconto varchar(50) not null,
     Importo float(10) not null,
     DataInizioValidita date not null,
     DataScadenza date not null,
     Email varchar(50) not null,
     constraint IDBUONOSCONTO primary key (CodBuonoSconto));

create table CheckIn (
     CodCheckIn varchar(50) not null,
     Data  date not null,
     Ora time not null,
     Email varchar(50) not null,
     CodServizio varchar(50) not null,
     constraint IDCHECKIN primary key (CodCheckIn));

create table Notifica (
     CodNotifica varchar(50) not null,
     Descrizione varchar(500) not null,
     CodPercorso varchar(50) not null,
     constraint IDNOTIFICA primary key (CodNotifica));

create table Percorso (
     CodPercorso varchar(50) not null,
     CodTreno varchar(50) not null,
     Email varchar(50) not null,
     TempoPercorrenza varchar(50) not null,
     Prezzo float(1) not null,
     constraint IDPERCORSO_ID primary key (CodPercorso),
     constraint IDPERCORSO_1 unique (CodTreno, CodPercorso),
     constraint IDPercorso_2 unique (Email, CodPercorso));

create table Persona (
     Email varchar(50) not null,
     Nome varchar(50) not null,
     Cognome varchar(50) not null,
     Indirizzo varchar(50) not null,
     Telefono varchar(50),
     CF varchar(50) not null,
     Password varchar(50),
     SpesaTotale float(50),
     TipoPersona varchar(50) not null,
     TipoCliente varchar(50),
     constraint IDPersona primary key (Email));

create table Sequenza (
     Suc_CodStazione varchar(50) not null,
     CodStazione varchar(50) not null,
     constraint IDSequenza primary key (CodStazione, Suc_CodStazione));

create table Servizio (
     CodServizio char(50) not null,
     StazionePartenza varchar(50) not null,
     StazioneArrivo varchar(50) not null,
     NomePasseggero varchar(50) not null,
     CognomePasseggero varchar(50) not null,
     TipoTreno varchar(50) not null,
     DataPartenza date not null,
     OrarioPartenza time,
     Prezzo float(10),
     CodPercorso varchar(50) not null,
     Email varchar(50) not null,
     Durata varchar(50),
     Chilometraggio int,
     constraint IDServizio primary key (CodServizio));

create table Stazione (
     CodStazione varchar(50) not null,
     Nome varchar(50) not null,
     constraint IDSTAZIONE_ID primary key (CodStazione));

create table TipoAbbonamento (
     Durata varchar(50) not null,
     Chilometraggio int not null,
     Prezzo float(10) not null,
     constraint IDTipoAbbonamento primary key (Durata, Chilometraggio));

create table Treno (
     CodTreno varchar(50) not null,
     PostiTotali int not null,
     Tipo varchar(50) not null,
     constraint IDTRENO primary key (CodTreno));

create table Utilizzo (
     CodBuonoSconto varchar(50) not null,
     CodServizio varchar(50) not null,
     Data date not null,
     constraint FKUti_Buo_ID primary key (CodBuonoSconto),
     constraint FKUti_Ser_ID unique (CodServizio));


-- Constraints Section
-- ___________________ 

alter table Attivazione add constraint FKAtt_Not
     foreign key (CodNotifica)
     references Notifica (CodNotifica);

alter table Attivazione add constraint FKAtt_Per
     foreign key (Email)
     references Persona (Email);

alter table Attraversato add constraint FKSituata
     foreign key (CodStazione)
     references Stazione (CodStazione);

alter table Attraversato add constraint FKInclude
     foreign key (CodPercorso)
     references Percorso (CodPercorso);

alter table BuonoSconto add constraint FKPosseduto
     foreign key (Email)
     references Persona (Email);

alter table CheckIn add constraint FKFatto
     foreign key (Email)
     references Persona (Email);

alter table CheckIn add constraint FKValidizione
     foreign key (CodServizio)
     references Servizio (CodServizio);

alter table Notifica add constraint FKRiferimento
     foreign key (CodPercorso)
     references Percorso (CodPercorso);

-- Not implemented
-- alter table Percorso add constraint IDPERCORSO_CHK
--     check(exists(select * from Attraversato
--                  where Attraversato.CodPercorso = CodPercorso)); 

-- Not implemented
-- alter table Percorso add constraint IDPERCORSO_CHK
--     check(exists(select * from Notifica
--                  where Notifica.CodPercorso = CodPercorso)); 

alter table Percorso add constraint FKSegue
     foreign key (CodTreno)
     references Treno (CodTreno);

alter table Percorso add constraint FKCondotto
     foreign key (Email)
     references Persona (Email);

alter table Sequenza add constraint FKPrecedente
     foreign key (CodStazione)
     references Stazione (CodStazione);

alter table Sequenza add constraint FKSuccessivo
     foreign key (Suc_CodStazione)
     references Stazione (CodStazione);

alter table Servizio add constraint FKRiguarda
     foreign key (CodPercorso)
     references Percorso (CodPercorso);

alter table Servizio add constraint FKAcquisto
     foreign key (Email)
     references Persona (Email);

alter table Servizio add constraint FKTipologia_FK
     foreign key (Durata, Chilometraggio)
     references TipoAbbonamento (Durata, Chilometraggio);

alter table Servizio add constraint FKTipologia_CHK
     check((Durata is not null and Chilometraggio is not null)
           or (Durata is null and Chilometraggio is null)); 

-- Not implemented
-- alter table Stazione add constraint IDSTAZIONE_CHK
--     check(exists(select * from Attraversato
--                  where Attraversato.CodStazione = CodStazione)); 

alter table Utilizzo add constraint FKUti_Buo_FK
     foreign key (CodBuonoSconto)
     references BuonoSconto (CodBuonoSconto);

alter table Utilizzo add constraint FKUti_Ser_FK
     foreign key (CodServizio)
     references Servizio (CodServizio);


-- Index Section
-- _____________ 

