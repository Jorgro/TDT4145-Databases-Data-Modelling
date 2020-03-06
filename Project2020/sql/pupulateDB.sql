use larswwa_tdt4145;


INSERT INTO title (Name, Content, Duration, PublishYear, LaunchYear) VALUES
                                                                                     ('Game of Thrones', 'TV show based on George RR Martins ASOIAF', null, 2011, 2011),
                                                                                     ('Winter is coming', 'S1E1 GOT, Bran gets pushed out of a window', 1, 2011, 2011),
                                                                                     ('Star Wars episode IV: A new hope', 'Luke, Darth Vader, Yoda, Obi Wan, Pew pew death star boom', 2, 1977, 1977),
                                                                                     ('The Battle of the Bastards', 'Jon and Ramsay', 1, 2017, 2017);




INSERT INTO person (Name, Birthyear, Country) VALUES
    ('Kit Harington', '1986', 'England'),
    ('George R.R. Martin', '1948', 'USA'),
    ('Kristoffer Hivju', '1978', 'Norge'),
    ('Ramin Djawadi', '1974', 'Tyskland'),
    ('John Williams', '1932', 'USA');

INSERT INTO category (Name) VALUES
                                                     ('Drama'),
                                                     ('Komedie'),
                                                     ('Grøsser'),
                                                     ('Romantisk komedie'),
                                                     ('Dokumentar'),
                                                     ('Action');

INSERT INTO company (URL, Country, Name) VALUES
                                                           ('www.netflix.com', 'USA', 'Netflix'),
                                                           ('www.HBO.com', 'USA', 'HBO'),
                                                           ('www.nordiskfilm.se', 'Sverige', 'Nordisk film'),
                                                           ('www.apple.com', 'USA', 'Apple'),
                                                           ('www.sony.com', 'Japan', 'Sony');

INSERT INTO score (Title) VALUES ('Main title - GOT'),
                                          ('Light of the seven'),
                                          ('The Rains of Castamere'),
                                          ('Star Wars Main Theme');


INSERT INTO user (Username, Password, Email) VALUES
                                                            ('josteiht', 'passord123', 'jostein@mail.no'),
                                                            ('jorgro', 'jürgen123', 'jorgen@mail.com'),
                                                            ('larwwa', 'AnneMarte<3', 'waa@mail.se');


INSERT INTO personTitle (TitleID, PersonID, Role, Actor) VALUES
                                                                               (2, 1, 'Jon Snow', true),
                                                                               (2, 2, 'Regissør', false),
                                                                               (2, 3, 'Tormund Giantsbane', true);


INSERT INTO companyTitle (TitleID, CompanyID, Role) VALUES
                                                                           (1, 2, 'Producer'),
                                                                           (4, 2, 'Producer'),
                                                                           (3, 1, 'Jealous');

INSERT INTO categoryInTitle (TitleID, CategoryID) VALUES
                                                         (1, 1),
                                                         (2, 4),
                                                         (3, 5),
                                                         (4, 1);

INSERT INTO reviewOfTitle (TitleID, UserID, Review, Rating) VALUES
                                                                   (2, 2, 'Such show. Wow.', 5),
                                                                   (3, 3, 'Too many lazers', 2);

INSERT INTO scoreInTitle (TitleID, ScoreID) VALUES
                                                   (2, 1),
                                                   (1, 1);

INSERT INTO episodeInSeries (EpisodeID, SeriesID, EpisodeNumber, SeasonNumber) VALUES
                                                                                      (2, 1, 1, 1);


INSERT INTO roleInScore (PersonID, ScoreID, Role) VALUES
                                                                        (4, 2, 'Komponist'),
                                                                        (5, 4, 'Komponist');





