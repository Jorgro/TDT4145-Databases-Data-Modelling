DROP DATABASE `Task1`;
CREATE DATABASE `Task1`;
USE `Task1`;

CREATE TABLE skuespiller
(	SkuespillerID INT NOT NULL,
    Navn VARCHAR(30) NOT NULL,
    Fødselsår INT NOT NULL,
    PRIMARY KEY(SkuespillerID)
);

CREATE TABLE sjanger
(
	SjangerID INT NOT NULL,
    Navn VARCHAR(30) NOT NULL,
    Beskrivelse VARCHAR(2000),
    PRIMARY KEY (SjangerID)
);

CREATE TABLE regissør
(
	RegissørID INT NOT NULL,
    Navn VARCHAR(30) NOT NULL,
    PRIMARY KEY (RegissørID)
);

CREATE TABLE film
(
	FilmID INT NOT NULL,
    Tittel VARCHAR(30) NOT NULL,
    Produksjonsår INT NOT NULL,
    RegissørID INT NOT NULL,
    FOREIGN KEY (RegissørID) REFERENCES regissør(RegissørID),
    PRIMARY KEY (FilmID)
);

CREATE TABLE sjangerforfilm
(
	SjangerID INT NOT NULL,
    FilmID INT NOT NULL,
    FOREIGN KEY (SjangerID) REFERENCES sjanger(SjangerID),
    FOREIGN KEY (FilmID) REFERENCES film(FilmID)
		ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT SjangerFF_PK PRIMARY KEY (FilmID, SjangerID)
);

CREATE TABLE skuespillerifilm
(
	FilmID INT NOT NULL,
    SkuespillerID INT NOT NULL,
    Rolle VARCHAR(30),
    FOREIGN KEY (FilmID) REFERENCES film(FilmID)
    		ON DELETE CASCADE
			ON UPDATE CASCADE,
    FOREIGN KEY (SkuespillerID) REFERENCES skuespiller(SkuespillerID),
    CONSTRAINT SkuespillerIF_PK PRIMARY KEY (FilmID, SkuespillerID)
);

