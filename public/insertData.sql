-- Insert into Router table
INSERT INTO `router` (`name`, `direction`)
VALUES ('EXPRESSO', 'START'),
       ('B1', 'START'),
       ('B2', 'START'),
       ('B3', 'START'),
       ('B4', 'START'),
       ('B5', 'START'),
       ('EXPRESSO', 'BACK'),
       ('B1', 'BACK'),
       ('B2', 'BACK'),
       ('B3', 'BACK'),
       ('B4', 'BACK'),
       ('B5', 'BACK');


INSERT INTO `coordinates` (`router`, `isPoint`, `latitude`, `longitude`)
VALUES (1, 1, -13.001864608982304, -38.506998491409014),
       (1, 1, -12.999220719026987, -38.50588674930342),
       (1, 1, -12.997203521205394, -38.508949461265324),
       (1, 1, -12.998788009569981, -38.51164341589637),
       (1, 1, -12.994768196219411, -38.51746537434156),
       (1, 1, -12.992133969795873, -38.52031115836471)
;

-- Insert into Point table
INSERT INTO `point` (`name`, `locale`)
VALUES ('Pt. Estacionamento PAF I / Matemática', 1),
       ('Av. Garibaldi Pt. R5', 2),
       ('Arquitetura', 3),
       ('Politécnica', 4),
       ('Creche', 5),
       ('Reitoria', 6);


-- Insert into Bus table
INSERT INTO `bus` (`name`, `router`, `point`)
VALUES ('Bus1', 1, 1);


-- Insert into Coordinates table
