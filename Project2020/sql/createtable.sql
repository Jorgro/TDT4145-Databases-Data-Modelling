USE `larswwa_tdt4145`;

CREATE TABLE person
(
    PersonID INT NOT NULL AUTO_INCREMENT,
    Name VARCHAR(32) NOT NULL,
    Birthyear INT,
    Country VARCHAR(32),
    PRIMARY KEY (PersonID)
);

CREATE TABLE title
(
    TitleID INT NOT NULL AUTO_INCREMENT,
    Name VARCHAR(32),
    Content VARCHAR(1024),
    Duration INT,
    PublishYear INT,
    LaunchYear INT,
    PRIMARY KEY (TitleID)
);

CREATE TABLE company
(
    CompanyID INT NOT NULL AUTO_INCREMENT,
    URL VARCHAR(64),
    Country VARCHAR(32),
    Name VARCHAR (32),
    PRIMARY KEY (CompanyID)
);

CREATE TABLE category
(
    CategoryID INT NOT NULL AUTO_INCREMENT,
    Name VARCHAR(32) UNIQUE,
    PRIMARY KEY (CategoryID)
);

CREATE TABLE user
(
    UserID INT NOT NULL AUTO_INCREMENT,
    Username VARCHAR(32) UNIQUE ,
    Password VARCHAR(100),
    Email VARCHAR(100),
    PRIMARY KEY (UserID)
);

CREATE TABLE score
(
    ScoreID INT NOT NULL AUTO_INCREMENT,
    Title VARCHAR(32),
    PRIMARY KEY (ScoreID)
);

CREATE TABLE personTitle
(
    PersonTitleID INT NOT NULL AUTO_INCREMENT,
    TitleID INT NOT NULL,
    PersonID INT NOT NULL,
    Role VARCHAR(64),
    Actor BOOLEAN DEFAULT TRUE,
    PRIMARY KEY (PersonTitleID),
    FOREIGN KEY (TitleID) REFERENCES title (TitleID)
                                    ON DELETE CASCADE
                                    ON UPDATE CASCADE,
    FOREIGN KEY (PersonID) REFERENCES person (PersonID)
                                    ON DELETE CASCADE
                                    ON UPDATE CASCADE
);

CREATE TABLE companyTitle
(
    CompanyTitleID INT NOT NULL AUTO_INCREMENT,
    TitleID INT NOT NULL,
    CompanyID INT NOT NULL,
    Role VARCHAR(64),
    FOREIGN KEY (TitleID) REFERENCES title (TitleID) 
                                    ON DELETE CASCADE
                                    ON UPDATE CASCADE,
    FOREIGN KEY (CompanyID) REFERENCES company (CompanyID)
                                    ON DELETE CASCADE
                                    ON UPDATE CASCADE,
    PRIMARY KEY (CompanyTitleID)
);

CREATE TABLE categoryInTitle
(
    TitleID INT NOT NULL,
    CategoryID INT NOT NULL,
    FOREIGN KEY (TitleID) REFERENCES title (TitleID)
                                    ON DELETE CASCADE
                                    ON UPDATE CASCADE,
    FOREIGN KEY (CategoryID) REFERENCES category (CategoryID)
                                    ON DELETE CASCADE
                                    ON UPDATE CASCADE,
    PRIMARY KEY (TitleID, CategoryID)
);

CREATE TABLE reviewOfTitle
(
    TitleID INT NOT NULL,
    UserID INT NOT NULL,
    Review VARCHAR(10000),
    Rating INT NOT NULL,
    FOREIGN KEY (TitleID) REFERENCES title (TitleID)
                                    ON DELETE CASCADE
                                    ON UPDATE CASCADE,
    FOREIGN KEY (UserID) REFERENCES user (UserID)
                                    ON DELETE CASCADE
                                    ON UPDATE CASCADE,
    PRIMARY KEY (TitleID, UserID)
);

CREATE TABLE scoreInTitle
(
    TitleID INT NOT NULL,
    ScoreID INT NOT NULL,
    FOREIGN KEY (TitleID) REFERENCES title (TitleID)
                                    ON DELETE CASCADE
                                    ON UPDATE CASCADE,
    FOREIGN KEY (ScoreID) REFERENCES score (ScoreID)
                                    ON DELETE CASCADE
                                    ON UPDATE CASCADE,
    PRIMARY KEY (TitleID, ScoreID)
);

CREATE TABLE episodeInSeries
(
    EpisodeID INT NOT NULL,
    SeriesID INT NOT NULL,
    EpisodeNumber INT,
    SeasonNumber INT,
    FOREIGN KEY (EpisodeID) REFERENCES title (TitleID)
                                    ON DELETE CASCADE
                                    ON UPDATE CASCADE,
    FOREIGN KEY (SeriesID) REFERENCES title (TitleID)
                                    ON DELETE CASCADE
                                    ON UPDATE CASCADE,
    PRIMARY KEY (EpisodeID, SeriesID)
);

# Trenger rolle i musikk table

CREATE TABLE roleInScore
(
    roleInScoreID INT NOT NULL AUTO_INCREMENT,
    PersonID INT NOT NULL,
    ScoreID INT NOT NULL,
    Role VARCHAR(32),
    PRIMARY KEY (roleInScoreID),
    FOREIGN KEY (PersonID) REFERENCES person (PersonID),
    FOREIGN KEY (ScoreID) REFERENCES score (ScoreID)
);

