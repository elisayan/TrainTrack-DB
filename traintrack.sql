-- *********************************************
-- * SQL MySQL generation                      
-- *--------------------------------------------
-- * DB-MAIN version: 11.0.2              
-- * Generator date: Sep 14 2021              
-- * Generation date: Fri Jun  7 00:58:45 2024 
-- * LUN file: C:\Users\jiaax\Downloads\traintrack-relazionale.lun 
-- * Schema: traintrack-relazionale/1-1-1 
-- ********************************************* 


-- Database Section
-- ________________ 

create database traintrack;
use traintrack;


-- Tables Section
-- _____________ 

create table Acquisto (
     CodServizio int not null,
     CodOrdine char(10) not null,
     constraint IDAcquisto primary key (CodOrdine, CodServizio));

create table Attivazione (
     CodNotifica char(10) not null,
     Email varchar(50) not null,
     constraint IDAttivazione primary key (Email, CodNotifica));

create table BuonoSconto (
     CodBuonoSconto varchar(20) not null,
     Importo int not null,
     DataInizioValidita date not null,
     DataScadenza date not null,
     DataUtilizzo date,
     Email varchar(50) not null,
     constraint IDBuonoSconto primary key (CodBuonoSconto));

create table Carrozza (
     CodTreno char(10) not null,
     NumeroCarrozza int not null,
     constraint IDCarrozza_ID primary key (CodTreno, NumeroCarrozza));

create table CheckIn (
     CodCheckIn char(10) not null,
     Data  date not null,
     Ora date not null,
     CodServizio int not null,
     Email varchar(50) not null,
     CodTreno char(10),
     NumeroCarrozza int,
     Lettera char(1),
     NumeroPosto int,
     constraint IDCheckIn primary key (CodCheckIn));

create table Composizione (
     CodTabellone char(10) not null,
     CodPercorso char(10) not null,
     constraint IDComposizione primary key (CodTabellone, CodPercorso));

create table Fila (
     CodTreno char(10) not null,
     NumeroCarrozza int not null,
     Lettera char(1) not null,
     constraint IDFila_ID primary key (CodTreno, NumeroCarrozza, Lettera));

create table Mediante (
     CodStazione char(10) not null,
     CodPercorso char(10) not null,
     Ordine char(1) not null,
     constraint IDMediante primary key (CodStazione, CodPercorso));

create table Notifica (
     CodNotifica char(10) not null,
     Descrizione varchar(500) not null,
     CodPercorso char(10) not null,
     constraint IDNOTIFICA primary key (CodNotifica));

create table Ordine (
     CodOrdine char(10) not null,
     DataPagamento date not null,
     OraPagamento time not null,
     Email varchar(50) not null,
     constraint IDORDINE_ID primary key (CodOrdine));

create table Percorso (
     CodPercorso char(10) not null,
     CodTreno char(10) not null,
     Email varchar(50) not null,
     TempoPercorrenza int not null,
     constraint IDPERCORSO_ID primary key (CodPercorso),
     constraint IDPERCORSO_1 unique (CodTreno, CodPercorso),
     constraint IDPercorso_2 unique (Email, CodPercorso));

create table Persona (
     CF char(10) not null,
     Nome char(10) not null,
     Cognome char(10) not null,
     Indirizzo varchar(50) not null,
     Telefono int,
     Email varchar(50) not null,
     Password char(10),
     SpesaTotale float(10),
     TipoPersona char(10) not null,
     TipoCliente char(10),
     constraint IDPERSONA primary key (Email));

create table Posto (
     CodTreno char(10) not null,
     NumeroCarrozza int not null,
     Lettera char(1) not null,
     NumeroPosto int not null,
     CodCheckIn char(10),
     constraint IDPosto primary key (CodTreno, NumeroCarrozza, Lettera, NumeroPosto));

create table Reso (
     CodReso char(10) not null,
     CodOrdine char(10) not null,
     Motivazione varchar(255) not null,
     Data date not null,
     Email varchar(50) not null,
     constraint IDRESO primary key (CodReso),
     constraint FKCorrelazione_ID unique (CodOrdine));

create table Sequenza (
     Suc_CodStazione char(10) not null,
     CodStazione char(10) not null,
     constraint IDSequenza primary key (CodStazione, Suc_CodStazione));

create table Servizio (
     CodServizio int not null,
     StazionePartenza varchar(50) not null,
     StazioneArrivo varchar(50) not null,
     NomePasseggero varchar(50) not null,
     CognomePasseggero varchar(50) not null,
     TipoTreno char(10) not null,
     DataPartenza date not null,
     OrarioPartenza time,
     TipoPasseggero varchar(50),
     Supplemento varchar(50),
     Prezzo float(10),
     Durata int,
     Chilometraggio int,
     CodPercorso char(10) not null,
     constraint IDServizio primary key (CodServizio));

create table Stazione (
     CodStazione char(10) not null,
     Nome char(10) not null,
     constraint IDSTAZIONE_ID primary key (CodStazione));

create table Tabellone (
     CodTabellone char(10) not null,
     CodStazione char(10),
     OrarioPartenzaPrevisto time not null,
     OrarioArrivoPrevisto time not null,
     OrarioArrivoReale time not null,
     OrarioPartenzaReale time not null,
     Binario int not null,
     StatoTreno varchar(50) not null,
     constraint IDTABELLONE primary key (CodTabellone),
     constraint FKAppartenenza_ID unique (CodStazione));

create table TipoAbbonamento (
     Durata int not null,
     Chilometraggio int not null,
     Prezzo float(10) not null,
     constraint IDTipoAbbonamento primary key (Durata, Chilometraggio));

create table Treno (
     CodTreno char(10) not null,
     PostiTotali int not null,
     Tipo varchar(50) not null,
     Classe char(1),
     constraint IDTRENO primary key (CodTreno));


-- Constraints Section
-- ___________________ 

alter table Acquisto add constraint FKAcq_Ord
     foreign key (CodOrdine)
     references Ordine (CodOrdine);

alter table Acquisto add constraint FKAcq_Ser
     foreign key (CodServizio)
     references Servizio (CodServizio);

alter table Attivazione add constraint FKAtt_Per
     foreign key (Email)
     references Persona (Email);

alter table Attivazione add constraint FKAtt_Not
     foreign key (CodNotifica)
     references Notifica (CodNotifica);

alter table BuonoSconto add constraint FKPosseduto
     foreign key (Email)
     references Persona (Email);

-- Not implemented
-- alter table Carrozza add constraint IDCarrozza_CHK
--     check(exists(select * from Fila
--                  where Fila.CodTreno = CodTreno and Fila.NumeroCarrozza = NumeroCarrozza)); 

alter table Carrozza add constraint FKCostituito
     foreign key (CodTreno)
     references Treno (CodTreno);

alter table CheckIn add constraint FKValidizione
     foreign key (CodServizio)
     references Servizio (CodServizio);

alter table CheckIn add constraint FKFatto
     foreign key (Email)
     references Persona (Email);

alter table CheckIn add constraint FKScelta_FK
     foreign key (CodTreno, NumeroCarrozza, Lettera, NumeroPosto)
     references Posto (CodTreno, NumeroCarrozza, Lettera, NumeroPosto);

alter table CheckIn add constraint FKScelta_CHK
     check((CodTreno is not null and NumeroCarrozza is not null and Lettera is not null and NumeroPosto is not null)
           or (CodTreno is null and NumeroCarrozza is null and Lettera is null and NumeroPosto is null)); 

alter table Composizione add constraint FKCom_Per
     foreign key (CodPercorso)
     references Percorso (CodPercorso);

alter table Composizione add constraint FKCom_Tab
     foreign key (CodTabellone)
     references Tabellone (CodTabellone);

-- Not implemented
-- alter table Fila add constraint IDFila_CHK
--     check(exists(select * from Posto
--                  where Posto.CodTreno = CodTreno and Posto.NumeroCarrozza = NumeroCarrozza and Posto.Lettera = Lettera)); 

alter table Fila add constraint FKOrganizzazione
     foreign key (CodTreno, NumeroCarrozza)
     references Carrozza (CodTreno, NumeroCarrozza);

alter table Mediante add constraint FKMed_Per
     foreign key (CodPercorso)
     references Percorso (CodPercorso);

alter table Mediante add constraint FKMed_Sta
     foreign key (CodStazione)
     references Stazione (CodStazione);

alter table Notifica add constraint FKRiferimento
     foreign key (CodPercorso)
     references Percorso (CodPercorso);

-- Not implemented
-- alter table Ordine add constraint IDORDINE_CHK
--     check(exists(select * from Acquisto
--                  where Acquisto.CodOrdine = CodOrdine)); 

alter table Ordine add constraint FKEffettua
     foreign key (Email)
     references Persona (Email);

-- Not implemented
-- alter table Percorso add constraint IDPERCORSO_CHK
--     check(exists(select * from Notifica
--                  where Notifica.CodPercorso = CodPercorso)); 

-- Not implemented
-- alter table Percorso add constraint IDPERCORSO_CHK
--     check(exists(select * from Composizione
--                  where Composizione.CodPercorso = CodPercorso)); 

-- Not implemented
-- alter table Percorso add constraint IDPERCORSO_CHK
--     check(exists(select * from Mediante
--                  where Mediante.CodPercorso = CodPercorso)); 

alter table Percorso add constraint FKSegue
     foreign key (CodTreno)
     references Treno (CodTreno);

alter table Percorso add constraint FKCondotto
     foreign key (Email)
     references Persona (Email);

alter table Posto add constraint FKSuddivisione
     foreign key (CodTreno, NumeroCarrozza, Lettera)
     references Fila (CodTreno, NumeroCarrozza, Lettera);

alter table Reso add constraint FKCorrelazione_FK
     foreign key (CodOrdine)
     references Ordine (CodOrdine);

alter table Reso add constraint FKAnnullamento
     foreign key (Email)
     references Persona (Email);

alter table Sequenza add constraint FKPrecedente
     foreign key (CodStazione)
     references Stazione (CodStazione);

alter table Sequenza add constraint FKSuccessivo
     foreign key (Suc_CodStazione)
     references Stazione (CodStazione);

alter table Servizio add constraint FKTipologia_FK
     foreign key (Durata, Chilometraggio)
     references TipoAbbonamento (Durata, Chilometraggio);

alter table Servizio add constraint FKTipologia_CHK
     check((Durata is not null and Chilometraggio is not null)
           or (Durata is null and Chilometraggio is null)); 

alter table Servizio add constraint FKRiguarda
     foreign key (CodPercorso)
     references Percorso (CodPercorso);

-- Not implemented
-- alter table Stazione add constraint IDSTAZIONE_CHK
--     check(exists(select * from Mediante
--                  where Mediante.CodStazione = CodStazione)); 

alter table Tabellone add constraint FKAppartenenza_FK
     foreign key (CodStazione)
     references Stazione (CodStazione);


-- Index Section
-- _____________ 

