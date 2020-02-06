USE `TASK1`;

DROP TABLE Skuespiller;
DROP TABLE Sjanger;
DROP TABLE Film;
DROP TABLE SkuespillerIFilm;
DROP TABLE SjangerForFilm;

CREATE TABLE Skuespiller 
(	SkuespillerID INT NOT NULL,
    Navn VARCHAR(30) NOT NULL,
    Fødselsår INT NOT NULL,
    PRIMARY KEY(SkuespillerID)
);

CREATE TABLE Sjanger 
(
	SjangerID INT NOT NULL,
    Navn VARCHAR(15) NOT NULL,
    Beskrivelse VARCHAR(2000),
    PRIMARY KEY (SjangerID)
);

CREATE TABLE Film 
(
	FilmID INT NOT NULL,
    Tittel VARCHAR(15) NOT NULL,
    Produksjonsår INT NOT NULL,
    PRIMARY KEY (FilmID)
);

CREATE TABLE SjangerForFilm
(
	FilmID INT NOT NULL,
    SjangerID INT NOT NULL,
    FOREIGN KEY (SjangerID) REFERENCES Sjanger(SjangerID),
    FOREIGN KEY (FilmID) REFERENCES Film(FilmID)
		ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT SjangerFF_PK PRIMARY KEY (FilmID, SjangerID)
);

CREATE TABLE Regissør
(
	RegissørID INT NOT NULL,
    Navn VARCHAR(15) NOT NULL,
    PRIMARY KEY (RegissørID)
);

CREATE TABLE SkuespillerIFilm
(
	FilmID INT NOT NULL,
    SkuespillerID INT NOT NULL,
    Rolle VARCHAR(9),
    FOREIGN KEY (FilmID) REFERENCES Film(FilmID)
    		ON DELETE CASCADE
			ON UPDATE CASCADE,
    FOREIGN KEY (SkuespillerID) REFERENCES Skuespiller(SkuespillerID),
    Constraint SkuespillerIF_PK primary key (FilmID, SkuespillerID)
);

