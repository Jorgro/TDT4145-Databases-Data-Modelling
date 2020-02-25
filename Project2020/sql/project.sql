USE `larswwa_tdt4145`;

CREATE TABLE person
(
    PersonID INT NOT NULL,
    Name VARCHAR(32) NOT NULL,
    Birthyear INT,
    Country VARCHAR(32),
    PRIMARY KEY (PersonID)
);

CREATE TABLE title
(
    TitleID INT NOT NULL,
    Title VARCHAR(32),
    Content VARCHAR(1024),
    Duration INT,
    PublishYear INT,
    LaunchYear INT,
    PRIMARY KEY (TitleID)
);

CREATE TABLE company
(
    CompanyID INT NOT NULL,
    URL VARCHAR(64),
    Country VARCHAR(32),
    PRIMARY KEY (CompanyID)
);

CREATE TABLE category
(
    CategoryID INT NOT NULL,
    Name VARCHAR(32),
    PRIMARY KEY (CategoryID)
);

CREATE TABLE user
(
    UserID INT NOT NULL,
    PRIMARY KEY (UserID)
);

CREATE TABLE score
(
    ScoreID INT NOT NULL,
    PRIMARY KEY (ScoreID)
);

CREATE TABLE personTitle
(
    PersonTitleID INT NOT NULL,
    TitleID INT NOT NULL,
    PersonID INT NOT NULL,
    Role VARCHAR(64),
    Actor BOOLEAN DEFAULT TRUE,
    PRIMARY KEY (PersonTitleID),
    FOREIGN KEY (TitleID) REFERENCES title (TitleID),
    FOREIGN KEY (PersonID) REFERENCES person (PersonID)
);

CREATE TABLE companyTitle
(
    TitleID INT NOT NULL,
    CompanyID INT NOT NULL,
    Role VARCHAR(64),
    FOREIGN KEY (TitleID) REFERENCES title (TitleID),
    FOREIGN KEY (CompanyID) REFERENCES company (CompanyID),
    PRIMARY KEY (TitleID, CompanyID)
);

CREATE TABLE categoryInTitle
(
    TitleID INT NOT NULL,
    CategoryID INT NOT NULL,
    FOREIGN KEY (TitleID) REFERENCES title (TitleID),
    FOREIGN KEY (CategoryID) REFERENCES category (CategoryID),
    PRIMARY KEY (TitleID, CategoryID)
);

CREATE TABLE reviewOfTitle
(
    TitleID INT NOT NULL,
    UserID INT NOT NULL,
    FOREIGN KEY (TitleID) REFERENCES title (TitleID),
    FOREIGN KEY (UserID) REFERENCES user (UserID),
    PRIMARY KEY (TitleID, UserID)
);

CREATE TABLE scoreInTitle
(
    TitleID INT NOT NULL,
    ScoreID INT NOT NULL,
    FOREIGN KEY (TitleID) REFERENCES title (TitleID),
    FOREIGN KEY (ScoreID) REFERENCES score (ScoreID),
    PRIMARY KEY (TitleID, ScoreID)
);

CREATE TABLE episodeInSeries
(
    EpisodeID INT NOT NULL,
    SeriesID INT NOT NULL,
    EpisodeNumber INT,
    SeasonNumber INT,
    FOREIGN KEY (EpisodeID) REFERENCES title (TitleID),
    FOREIGN KEY (SeriesID) REFERENCES title (TitleID),
    PRIMARY KEY (EpisodeID, SeriesID)
);
