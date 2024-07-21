-- Inserimento Macchinisti
INSERT INTO Persona (Email, Nome, Cognome, Indirizzo, Telefono, CF, Password, SpesaTotale, TipoPersona, TipoCliente, UltimaSpesaCoupon) VALUES
                                                                                                                                            ('macchinista1@example.com', 'Giovanni', 'Ferrari', 'Via Roma 1, Roma', '1111', 'CFMACCHINISTA1', 'macchinista1', NULL, 'macchinista', NULL, NULL),
                                                                                                                                            ('macchinista2@example.com', 'Luca', 'Bianchi', 'Via Milano 2, Milano', '2222', 'CFMACCHINISTA2', 'macchinista2', NULL, 'macchinista', NULL, NULL);

-- Inserimento Clienti
INSERT INTO Persona (Email, Nome, Cognome, Indirizzo, Telefono, CF, Password, SpesaTotale, TipoPersona, TipoCliente, UltimaSpesaCoupon) VALUES
                                                                                                                                            ('ospite1@example.com', 'Anna', 'Rossi', 'Via Napoli 3, Napoli', NULL, 'CFOSPITE1', NULL, NULL, 'cliente', 'ospite', NULL),
                                                                                                                                            ('utente1@example.com', 'Mario', 'Verdi', 'Via Firenze 4, Firenze', '1111', 'CFUTENTE1', 'utente1', 0.0, 'cliente', 'utente', NULL),
                                                                                                                                            ('utente2@example.com', 'Sara', 'Neri', 'Via Torino 5, Torino', '2222', 'CFUTENTE2', 'utente2', 90.0, 'cliente', 'utente', NULL),
                                                                                                                                            ('utente3@example.com', 'Paolo', 'Blu', 'Via Genova 6, Genova', '3333', 'CFUTENTE3', 'utente3', 95.0, 'cliente', 'utente', NULL),
                                                                                                                                            ('utente4@example.com', 'Elena', 'Gialli', 'Via Bologna 7, Bologna', '4444', 'CFUTENTE4', 'utente4', 80.0, 'cliente', 'utente', NULL);


INSERT INTO Treno (CodTreno, PostiTotali, Tipo) VALUES
                                                    ('T001', 200, 'Frecciarossa'),
                                                    ('T002', 150, 'Intercity'),
                                                    ('T003', 100, 'Regionale');


INSERT INTO Stazione (CodStazione, Nome) VALUES
                                             ('S001', 'Roma'),
                                             ('S002', 'Milano'),
                                             ('S003', 'Napoli'),
                                             ('S004', 'Firenze'),
                                             ('S005', 'Torino'),
                                             ('S006', 'Genova'),
                                             ('S007', 'Bologna'),
                                             ('S008', 'Venezia'),
                                             ('S009', 'Verona'),
                                             ('S010', 'Palermo'),
                                             ('S011', 'Pisa'),
                                             ('S012', 'Trieste'),
                                             ('S013', 'Bari'),
                                             ('S014', 'Reggio Calabria'),
                                             ('S015', 'Ancona'),
                                             ('S016', 'Perugia'),
                                             ('S017', 'Catania'),
                                             ('S018', 'Trento');


INSERT INTO Percorso (CodPercorso, CodTreno, Email, TempoPercorrenza, Prezzo, PostiDisponibili) VALUES
                                                                                                    ('P001', 'T001', 'macchinista1@example.com', 180, 50.0, 200),  -- 3 ore
                                                                                                    ('P002', 'T002', 'macchinista2@example.com', 240, 40.0, 150),  -- 4 ore
                                                                                                    ('P003', 'T003', 'macchinista1@example.com', 120, 30.0, 100),  -- 2 ore
                                                                                                    ('P004', 'T001', 'macchinista2@example.com', 600, 80.0, 200);  -- 10 ore

INSERT INTO Attraversato (CodPercorso, CodStazione, Data, Ordine, OrarioPartenzaPrevisto, OrarioArrivoPrevisto, OrarioArrivoReale, OrarioPartenzaReale, Binario, StatoArrivo, StatoPartenza) VALUES
-- Percorso P001
('P001', 'S001', '2024-07-23', 1, '08:00:00', '09:30:00', '09:35:00', '08:00:00', 1, 'ritardo', 'orario'),
('P001', 'S005', '2024-07-23', 2, '09:45:00', '11:00:00', '11:00:00', '09:50:00', 2, 'orario', 'orario'),
('P001', 'S002', '2024-07-23', 3, '11:30:00', '13:00:00', '12:55:00', '11:35:00', 3, 'anticipo', 'orario'),
('P001', 'S004', '2024-07-23', 4, '13:30:00', '15:00:00', '15:05:00', '13:35:00', 4, 'ritardo', 'orario'),
('P001', 'S009', '2024-07-23', 5, '15:30:00', '17:00:00', '17:00:00', '15:35:00', 5, 'orario', 'orario'),
('P001', 'S008', '2024-07-23', 6, '17:30:00', '19:00:00', '19:05:00', '17:35:00', 6, 'ritardo', 'orario'),

-- Percorso P002
('P002', 'S003', '2024-07-24', 1, '09:00:00', '10:30:00', '10:35:00', '09:00:00', 1, 'ritardo', 'orario'),
('P002', 'S006', '2024-07-24', 2, '11:00:00', '12:30:00', '12:30:00', '11:05:00', 2, 'orario', 'orario'),
('P002', 'S007', '2024-07-24', 3, '13:00:00', '14:30:00', '14:25:00', '13:05:00', 3, 'anticipo', 'orario'),
('P002', 'S004', '2024-07-24', 4, '15:00:00', '16:30:00', '16:35:00', '15:05:00', 4, 'ritardo', 'orario'),
('P002', 'S010', '2024-07-24', 5, '17:00:00', '18:30:00', '18:30:00', '17:05:00', 5, 'orario', 'orario'),
('P002', 'S001', '2024-07-24', 6, '19:00:00', '20:30:00', '20:35:00', '19:05:00', 6, 'ritardo', 'orario'),

-- Percorso P003
('P003', 'S002', '2024-07-25', 1, '07:00:00', '08:30:00', '08:30:00', '07:00:00', 1, 'orario', 'orario'),
('P003', 'S005', '2024-07-25', 2, '09:00:00', '10:30:00', '10:25:00', '09:05:00', 2, 'anticipo', 'orario'),
('P003', 'S007', '2024-07-25', 3, '11:00:00', '12:30:00', '12:35:00', '11:05:00', 3, 'ritardo', 'orario'),
('P003', 'S003', '2024-07-25', 4, '13:00:00', '14:30:00', '14:30:00', '13:05:00', 4, 'orario', 'orario'),
('P003', 'S004', '2024-07-25', 5, '15:00:00', '16:30:00', '16:35:00', '15:05:00', 5, 'ritardo', 'orario'),
('P003', 'S008', '2024-07-25', 6, '17:00:00', '18:30:00', '18:30:00', '17:05:00', 6, 'orario', 'orario'),

-- Percorso P004
('P004', 'S001', '2024-07-26', 1, '06:00:00', '07:00:00', '07:05:00', '06:00:00', 1, 'ritardo', 'orario'),
('P004', 'S005', '2024-07-26', 2, '07:30:00', '08:30:00', '08:30:00', '07:35:00', 2, 'orario', 'orario'),
('P004', 'S008', '2024-07-26', 3, '09:00:00', '10:00:00', '10:05:00', '09:05:00', 3, 'ritardo', 'orario'),
('P004', 'S010', '2024-07-26', 4, '10:30:00', '11:30:00', '11:30:00', '10:35:00', 4, 'orario', 'orario'),
('P004', 'S012', '2024-07-26', 5, '12:00:00', '13:00:00', '13:05:00', '12:05:00', 5, 'ritardo', 'orario'),
('P004', 'S014', '2024-07-26', 6, '13:30:00', '14:30:00', '14:30:00', '13:35:00', 6, 'orario', 'orario'),
('P004', 'S016', '2024-07-26', 7, '15:00:00', '16:00:00', '16:05:00', '15:05:00', 7, 'ritardo', 'orario'),
('P004', 'S013', '2024-07-26', 8, '16:30:00', '17:30:00', '17:30:00', '16:35:00', 8, 'orario', 'orario'),
('P004', 'S015', '2024-07-26', 9, '18:00:00', '19:00:00', '19:05:00', '18:05:00', 9, 'ritardo', 'orario'),
('P004', 'S018', '2024-07-26', 10, '19:30:00', '20:30:00', '20:30:00', '19:35:00', 10, 'orario', 'orario');


INSERT INTO BuonoSconto (Importo, DataInizioValidita, DataScadenza, Email) VALUES
                                                                               (10.0, '2024-07-01', '2024-08-01', 'utente1@example.com'),
                                                                               (10.0, '2024-07-05', '2024-08-05', 'utente2@example.com'),
                                                                               (10.0, '2024-07-10', '2024-08-10', 'utente3@example.com'),
                                                                               (10.0, '2024-07-15', '2024-08-15', 'utente4@example.com');


INSERT INTO Notifica (CodNotifica, Descrizione, CodPercorso) VALUES
                                                                 ('N001', 'Percorso in orario', 'P001'),
                                                                 ('N002', 'Percorso in ritardo', 'P002'),
                                                                 ('N003', 'Percorso cancellato', 'P003');

INSERT INTO TipoAbbonamento (Durata, Chilometraggio, Prezzo) VALUES
                                                                 ('Settimanale', 100, 20.0),
                                                                 ('Mensile', 500, 80.0),
                                                                 ('Annuale', 6000, 800.0);

INSERT INTO Servizio (StazionePartenza, StazioneArrivo, NomePasseggero, CognomePasseggero, TipoTreno, DataPartenza, OrarioPartenza, Prezzo, CodPercorso, Email, Durata, Chilometraggio, CodServizio) VALUES
-- Abbonamento Settimanale
('Roma', 'Milano', 'Mario', 'Verdi', 'Frecciarossa', '2024-07-23', NULL, NULL, 'P001', 'utente1@example.com', 'Settimanale', 100, 1),
('Napoli', 'Firenze', 'Sara', 'Neri', 'Intercity', '2024-07-24', NULL, NULL, 'P002', 'utente2@example.com', 'Settimanale', 100, 2),

-- Abbonamento Mensile
('Roma Termini', 'Napoli', 'Paolo', 'Blu', 'Regionale', '2024-07-25', NULL, NULL, 'P003', 'utente3@example.com', 'Mensile', 500, 3),
('Firenze', 'Torino', 'Elena', 'Gialli', 'Frecciarossa', '2024-07-26', NULL, NULL, 'P004', 'utente4@example.com', 'Mensile', 500, 4),

-- Abbonamento Annuale
('Roma', 'Venezia', 'Anna', 'Rossi', 'Frecciarossa', '2024-07-27', NULL, NULL, 'P001', 'ospite1@example.com', 'Annuale', 6000, 5),
('Napoli', 'Milano', 'Luca', 'Bianchi', 'Intercity', '2024-07-28', NULL, NULL, 'P002', 'utente1@example.com', 'Annuale', 6000, 6);
