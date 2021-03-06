create database MYBANK;

CREATE TABLE TB_CONTA 
(
	NUMERO INT NOT NULL PRIMARY KEY,
	CLIENTE   VARCHAR(60) NOT NULL,
	SALDO      DECIMAL(10,2) ,
	LIMITE	    DECIMAL(10,2)
);

CREATE TABLE TB_MOVIMENTACAO
(
	ID_MOVIMENTACAO  INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	TIPO_MOVIMENTACAO   ENUM ('CREDITO', 'DEBITO'),
	VALOR       DECIMAL(10,2) ,
	DATA_MOVIMENTACAO	DATE,
              NUM_CONTA INT NOT NULL ,
              FOREIGN KEY (NUM_CONTA) REFERENCES TB_CONTA (NUMERO)
);

