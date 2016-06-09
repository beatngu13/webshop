TRUNCATE TABLE customer;
SET FOREIGN_KEY_CHECKS = 0; 
TRUNCATE TABLE category; 
SET FOREIGN_KEY_CHECKS = 1;
TRUNCATE TABLE product;

INSERT INTO category(id, name) values(1, 'Nahrungsmittel');

INSERT INTO customer(id, name, lastname, username, password, role) VALUES (1, 'admin', 'admin', 'admin', 'admin', (SELECT id from role WHERE type ='admin'));
INSERT INTO customer(id, name, lastname, username, password, role) VALUES (2, 'Max', 'Mustermann', 'mmustermann', '1234', (SELECT id from role WHERE type ='user'));
