-- *********************************************
-- * SQL MySQL generation                      
-- *--------------------------------------------
-- * DB-MAIN version: 11.0.2              
-- * Generator date: Sep 14 2021              
-- * Generation date: Fri Jun 14 22:52:51 2024 
-- * LUN file: C:\Users\jiaax\Downloads\traintrack-raffinato.lun 
-- * Schema: traintrackRelazionale/1-1-2 
-- ********************************************* 


-- Database Section
-- ________________ 

create database traintrack;
use traintrack;


-- Tables Section
-- _____________ 

create table Acquisto (
     CodOrdine varchar(50) not null,
     CodServizio varchar(50) not null,
     constraint IDAcquisto primary key (CodServizio, CodOrdine));

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
     constraint IDAttraversato primary key (CodPercorso, CodStazione, Data));

create table BuonoSconto (
     CodBuonoSconto varchar(50) not null,
     Importo float(10) not null,
     DataInizioValidita date not null,
     DataScadenza date not null,
     DataUtilizzo date,
     Email varchar(50) not null,
     constraint IDBUONOSCONTO primary key (CodBuonoSconto));

create table Carrozza (
     CodTreno varchar(50) not null,
     NumeroCarrozza char(10) not null,
     constraint IDCarrozza_ID primary key (CodTreno, NumeroCarrozza));

create table CheckIn (
     CodCheckIn varchar(50) not null,
     Data  date not null,
     Ora time not null,
     CodServizio varchar(50) not null,
     Email varchar(50) not null,
     CodTreno varchar(50),
     NumeroCarrozza char(10),
     Lettera char(1),
     NumeroPosto char(10),
     constraint IDCHECKIN primary key (CodCheckIn));

create table Fila (
     CodTreno varchar(50) not null,
     NumeroCarrozza char(10) not null,
     Lettera char(1) not null,
     constraint IDFila_ID primary key (CodTreno, NumeroCarrozza, Lettera));

create table Notifica (
     CodNotifica varchar(50) not null,
     Descrizione varchar(500) not null,
     CodPercorso varchar(50) not null,
     constraint IDNOTIFICA primary key (CodNotifica));

create table Ordine (
     CodOrdine varchar(50) not null,
     DataPagamento date not null,
     OraPagamento time not null,
     Email varchar(50) not null,
     constraint IDORDINE_ID primary key (CodOrdine));

create table Percorso (
     CodPercorso varchar(50) not null,
     Email varchar(50) not null,
     CodTreno char(10) not null,
     TempoPercorrenza varchar(50) not null,
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
     constraint IDPERSONA primary key (Email));

create table Posto (
     CodTreno varchar(50) not null,
     NumeroCarrozza char(10) not null,
     Lettera char(1) not null,
     NumeroPosto char(10) not null,
     constraint IDPosto primary key (CodTreno, NumeroCarrozza, Lettera, NumeroPosto));

create table Reso (
     CodReso varchar(50) not null,
     CodOrdine varchar(50) not null,
     Motivazione varchar(100) not null,
     Data date not null,
     Email varchar(50) not null,
     constraint IDRESO primary key (CodReso),
     constraint FKCorrelazione_ID unique (CodOrdine));

create table Sequenza (
     Pre_CodStazione varchar(50) not null,
     CodStazione varchar(50) not null,
     constraint IDSequenza primary key (Pre_CodStazione, CodStazione));

create table Servizio (
     CodServizio varchar(50) not null,
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
     Durata varchar(50),
     Chilometraggio int,
     CodPercorso varchar(50) not null,
     constraint IDServizio primary key (CodServizio));

create table Stazione (
     CodStazione varchar(50) not null,
     Nome varchar(50) not null,
     constraint IDSTAZIONE_ID primary key (CodStazione));

create table TipoAbbonamento (
     Durata varchar(50) not null,
     Chilometraggio int not null,
     Prezzo float(10) not null,
     constraint IDTIPOABBONAMENTO primary key (Durata, Chilometraggio));

create table Treno (
     CodTreno varchar(50) not null,
     PostiTotali int not null,
     Tipo char(10) not null,
     Classe char(1),
     constraint IDTRENO primary key (CodTreno));


-- Constraints Section
-- ___________________ 

alter table Acquisto add constraint FKAcq_Ser
     foreign key (CodServizio)
     references Servizio (CodServizio);

alter table Acquisto add constraint FKAcq_Ord
     foreign key (CodOrdine)
     references Ordine (CodOrdine);

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

-- Not implemented
-- alter table Fila add constraint IDFila_CHK
--     check(exists(select * from Posto
--                  where Posto.CodTreno = CodTreno and Posto.NumeroCarrozza = NumeroCarrozza and Posto.Lettera = Lettera)); 

alter table Fila add constraint FKOrganizzazione
     foreign key (CodTreno, NumeroCarrozza)
     references Carrozza (CodTreno, NumeroCarrozza);

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
--     check(exists(select * from Attraversato
--                  where Attraversato.CodPercorso = CodPercorso)); 

-- Not implemented
-- alter table Percorso add constraint IDPERCORSO_CHK
--     check(exists(select * from Notifica
--                  where Notifica.CodPercorso = CodPercorso)); 

alter table Percorso add constraint FKCondotto
     foreign key (Email)
     references Persona (Email);

alter table Percorso add constraint FKSegue
     foreign key (CodTreno)
     references Treno (CodTreno);

alter table Posto add constraint FKSuddivisione
     foreign key (CodTreno, NumeroCarrozza, Lettera)
     references Fila (CodTreno, NumeroCarrozza, Lettera);

alter table Reso add constraint FKAnnullamento
     foreign key (Email)
     references Persona (Email);

alter table Reso add constraint FKCorrelazione_FK
     foreign key (CodOrdine)
     references Ordine (CodOrdine);

alter table Sequenza add constraint FKSuccessivo
     foreign key (CodStazione)
     references Stazione (CodStazione);

alter table Sequenza add constraint FKPrecedente
     foreign key (Pre_CodStazione)
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
--     check(exists(select * from Attraversato
--                  where Attraversato.CodStazione = CodStazione)); 


-- Index Section
-- _____________ 

