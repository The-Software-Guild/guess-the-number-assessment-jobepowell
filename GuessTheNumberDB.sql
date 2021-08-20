DROP DATABASE IF EXISTS guessthenumber;
CREATE DATABASE guessthenumber;
DROP DATABASE IF EXISTS guessthenumbertest;
CREATE DATABASE guessthenumbertest;
USE guessthenumber;

CREATE TABLE GAME(
	gameId INT AUTO_INCREMENT,
    CONSTRAINT pk_gameId
		PRIMARY KEY (gameId),
	status VARCHAR(20) NOT NULL,
    answer CHAR(4) NOT NULL
);

CREATE TABLE ROUND(
	roundID INT AUTO_INCREMENT,
    CONSTRAINT pk_roundId
		PRIMARY KEY (roundId),
	gameId INT,
    CONSTRAINT fk_gameId
		FOREIGN KEY(gameId)
        REFERENCES GAME(gameId),
	guess CHAR(4) NOT NULL,
    time TIME NOT NULL,
    result CHAR(6) NOT NULL
);

USE guessthenumbertest;

CREATE TABLE GAME(
	gameId INT AUTO_INCREMENT,
    CONSTRAINT pk_gameId
		PRIMARY KEY (gameId),
	status VARCHAR(20) NOT NULL,
    answer CHAR(4) NOT NULL
);

CREATE TABLE ROUND(
	roundID INT AUTO_INCREMENT,
    CONSTRAINT pk_roundId
		PRIMARY KEY (roundId),
	gameId INT,
    CONSTRAINT fk_gameId
		FOREIGN KEY(gameId)
        REFERENCES GAME(gameId),
	guess CHAR(4) NOT NULL,
    time TIME NOT NULL,
    result CHAR(6) NOT NULL
);
