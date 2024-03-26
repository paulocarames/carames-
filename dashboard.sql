CREATE DATABASE dashboard;

USE dashboard;

CREATE TABLE Suporte 
( 
 ID_Suporte INT PRIMARY KEY AUTO_INCREMENT,  
 Nome_Suporte VARCHAR(45) NOT NULL 
); 

CREATE TABLE Midia 
( 
 ID_Midia INT PRIMARY KEY AUTO_INCREMENT,  
 Nome_Midia VARCHAR(45) NOT NULL
); 

CREATE TABLE Tipo 
( 
 ID_Tipo INT PRIMARY KEY AUTO_INCREMENT,  
 Nome_Tipo VARCHAR(45) NOT NULL
); 

CREATE TABLE Origem 
( 
 ID_Origem INT PRIMARY KEY AUTO_INCREMENT,  
 Nome_Origem VARCHAR(45) NOT NULL
); 

CREATE TABLE Tema 
( 
 ID_Tema INT PRIMARY KEY AUTO_INCREMENT,  
 Nome_Tema VARCHAR(45) NOT NULL
); 

CREATE TABLE Titulo 
( 
 Data_aquis DATE,  
 Data_visto DATE,  
 Sinopse VARCHAR(10000),  
 Suporte INT NOT NULL,
 Midia INT NOT NULL,
 Ano VARCHAR(4), 
 Origem INT NOT NULL,
 Tipo INT NOT NULL,
 Tema INT NOT NULL,
 Palavras_Chave VARCHAR(2100),  
 Artista VARCHAR(90),  
 Nome_Titulo VARCHAR(250) NOT NULL,  
 Armazenamento VARCHAR(250),  
 Preco FLOAT,  
 ID_Titulo INT PRIMARY KEY AUTO_INCREMENT, 
 Listas VARCHAR(90),  
 Capa VARCHAR(250)
); 


-- povoamento de dados

SELECT * FROM midia

SELECT * FROM origem

SELECT * FROM suporte

SELECT * FROM tema

SELECT * FROM tipo

SELECT * FROM titulo

-- Ajuste questões de segurança, incluindo usuários, papéis e permissões.
CREATE ROLE 'app_consulta';
GRANT SELECT ON dashboard.* TO 'app_consulta';
FLUSH PRIVILEGES;

CREATE USER 'usuario_consulta'@'localhost' IDENTIFIED BY '1234';
SHOW GRANTS FOR 'usuario_consulta'@'localhost';

GRANT 'app_consulta' TO 'usuario_consulta'@'localhost';
FLUSH PRIVILEGES;
SHOW GRANTS FOR 'usuario_consulta'@'localhost';

SET DEFAULT ROLE 'app_consulta' TO 'usuario_consulta'@'localhost';

SELECT * FROM mysql.user;


CREATE ROLE 'app_assistente';
GRANT CREATE, INSERT, UPDATE ON dashboard.midia TO 'app_assistente';
GRANT CREATE, INSERT, UPDATE ON dashboard.origem TO 'app_assistente';
GRANT CREATE, INSERT, UPDATE ON dashboard.suporte TO 'app_assistente';
GRANT CREATE, INSERT, UPDATE ON dashboard.tema TO 'app_assistente';
GRANT CREATE, INSERT, UPDATE ON dashboard.tipo TO 'app_assistente';
FLUSH PRIVILEGES;

CREATE USER 'assistente'@'localhost' IDENTIFIED BY '1234';
SHOW GRANTS FOR 'assistente'@'localhost';

GRANT 'app_assistente' TO 'assistente'@'localhost';
FLUSH PRIVILEGES;
SHOW GRANTS FOR 'assistente'@'localhost';

SET DEFAULT ROLE 'app_assistente' TO 'assistente'@'localhost';

SELECT * FROM mysql.user;


INSERT INTO `dashboard`.`usuario` (`login`, `senha`) VALUES ('assistente', '1234');

UPDATE dashboard.usuario SET login = MD5(login);
UPDATE dashboard.usuario SET senha = MD5(senha);

SELECT * FROM dashboard.usuario;


-- Crie visões no banco de dados para consultas mais frequentes.
SHOW TABLES;

CREATE VIEW view_assistente AS SELECT Data_aquis, Sinopse, Nome_Suporte AS Suporte, Nome_Midia AS Midia, Ano, Nome_Origem AS Origem, Nome_tipo AS Tipo, Nome_tema AS Tema, Artista, Nome_Titulo, Capa
FROM titulo
INNER JOIN midia
ON midia.id_midia  = midia
INNER JOIN origem
ON origem.id_origem = origem
INNER JOIN suporte
ON suporte.id_suporte = suporte
INNER JOIN tema
ON tema.id_tema = tema 
INNER JOIN tipo
ON tipo.id_tipo = tipo 
ORDER BY Ano DESC;
SELECT * FROM view_assistente;

CREATE VIEW view_ver
AS SELECT Data_aquis, Sinopse, Nome_Suporte AS Suporte, Nome_Midia AS Midia, Ano, Nome_Origem AS Origem, Nome_tipo AS Tipo, Nome_tema AS Tema, Artista, Nome_Titulo, Capa
FROM titulo
INNER JOIN midia
ON midia.id_midia  = midia
INNER JOIN origem
ON origem.id_origem = origem
INNER JOIN suporte
ON suporte.id_suporte = suporte
INNER JOIN tema
ON tema.id_tema = tema 
INNER JOIN tipo
ON tipo.id_tipo = tipo 
WHERE Data_aquis IS NOT NULL AND Data_visto IS NULL AND Suporte >3
ORDER BY Ano DESC;
SELECT * FROM view_ver;

CREATE VIEW view_tudo
AS SELECT Data_aquis, Data_visto, Sinopse, Nome_Suporte AS Suporte, Nome_Midia AS Midia, Ano, Nome_Origem AS Origem, Nome_tipo AS Tipo, Nome_tema AS Tema, Palavras_Chave, Artista, Nome_Titulo, Armazenamento, Preco, ID_Titulo, Listas, Capa
FROM titulo
INNER JOIN midia
ON midia.id_midia  = midia
INNER JOIN origem
ON origem.id_origem = origem
INNER JOIN suporte
ON suporte.id_suporte = suporte
INNER JOIN tema
ON tema.id_tema = tema 
INNER JOIN tipo
ON tipo.id_tipo = tipo 
ORDER BY Ano DESC;
SELECT * FROM view_tudo;

SELECT COUNT(titulo.midia)Total, Nome_Midia AS Midia
FROM titulo
INNER JOIN midia ON titulo.midia = midia.id_midia
GROUP BY midia.id_midia ORDER BY Total DESC

SELECT COUNT(titulo.origem)Total, Nome_Origem AS Origem
FROM titulo
INNER JOIN origem ON titulo.origem = origem.id_origem
GROUP BY origem.id_origem ORDER BY Total DESC

SELECT COUNT(titulo.tema)Total, Nome_Tema AS Tema
FROM titulo
INNER JOIN tema ON titulo.tema = tema.id_tema
GROUP BY tema.id_tema ORDER BY Total DESC


-- Crie ao menos uma stored function, um stored procedure ou um trigger para o banco de dados, selecionando uma funcionalidade que seja adequada para tanto.
DELIMITER //
CREATE PROCEDURE sp_OuvirDiscos()
BEGIN
    select Data_aquis, Ano, Artista, Nome_Titulo, Capa from titulo WHERE Artista NOT LIKE '-' AND Midia = 35
    ORDER BY Ano DESC;
END //
    
DELIMITER ;

CALL sp_OuvirDiscos


-- Crie ao menos um índice composto para uma das tabelas.
SELECT * FROM titulo WHERE Nome_Titulo LIKE 'Predator%';
EXPLAIN SELECT * FROM titulo WHERE Nome_Titulo = 'Predator%';
CREATE INDEX idx_titulo ON titulo(Nome_Titulo, artista);
SHOW INDEX FROM titulo;
EXPLAIN SELECT * FROM titulo WHERE Nome_Titulo = 'Predator%';

CREATE INDEX idx_suporte ON titulo(Nome_Titulo, suporte);

SELECT Data_aquis, Data_visto, Sinopse, Nome_Suporte, Ano, Nome_Titulo, Capa
FROM titulo
INNER JOIN suporte
ON suporte.id_suporte = Suporte
WHERE Nome_Titulo LIKE '%Predator%' AND Nome_Suporte LIKE '%T%';

DROP INDEX idx_suporte ON titulo;


-- Descreva textualmente uma rotina de administração de banco de dados, prevendo backups, restore e checagem de integridade periódica:
-- Utilizando a ferramenta SQLBackupAndFTP, foi estabelecida conexão com o servidor (MySQLServer (TCP/IP)) e definido backup diário automático em uma pasta no Google Drive.