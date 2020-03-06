use larswwa_tdt4145;


INSERT INTO title (TitleID, Name, Content, Duration, PublishYear, LaunchYear) VALUES
                                                                                     (1, 'Game of Thrones', 'TV show based on George RR Martins ASOIAF', null, 2011, 2011),
                                                                                     (2, 'Winter is coming', 'S1E1 GOT, Bran gets pushed out of a window', 1, 2011, 2011),
                                                                                     (3, 'Star Wars episode IV: A new hope', 'Luke, Darth Vader, Yoda, Obi Wan, Pew pew death star boom', 2, 1977, 1977),
                                                                                     (4, 'The Battle of the Bastards', 'Jon and Ramsay', 1, 2017, 2017);




INSERT INTO person (PersonID, Name, Birthyear, Country) VALUES
    (1, 'Kit Harington', '1986', 'England'),
    (2, 'George R.R. Martin', '1948', 'USA'),
    (3, 'Kristoffer Hivju', '1978', 'Norge'),
    (4, 'Ramin Djawadi', '1974', 'Tyskland'),
    (5, 'John Williams', '1932', 'USA');

INSERT INTO category (CategoryID, Name) VALUES
                                                     (1,	'Drama'),
                                                     (2,	'Komedie'),
                                                     (3,	'Grøsser'),
                                                     (4,	'Romantisk komedie'),
                                                     (5,    'Dokumentar'),
                                                     (6,    'Action');

INSERT INTO company (CompanyID, URL, Country, Name) VALUES
                                                           (1, 'www.netflix.com', 'USA', 'Netflix'),
                                                           (2, 'www.HBO.com', 'USA', 'HBO'),
                                                           (3, 'www.nordiskfilm.se', 'Sverige', 'Nordisk film'),
                                                           (4, 'www.apple.com', 'USA', 'Apple'),
                                                           (5, 'www.sony.com', 'Japan', 'Sony');

INSERT INTO score (ScoreID, Title) VALUES (1, 'Main title - GOT'),
                                          (2, 'Light of the seven'),
                                          (3, 'The Rains of Castamere'),
                                          (4, 'Star Wars Main Theme');


INSERT INTO user (UserID, Username, Password, Email) VALUES
                                                            (1, 'josteiht', 'passord123', 'jostein@mail.no'),
                                                            (2, 'jorgro', 'jürgen123', 'jorgen@mail.com'),
                                                            (3, 'larwwa', 'AnneMarte<3', 'waa@mail.se');


INSERT INTO personTitle (PersonTitleID, TitleID, PersonID, Role, Actor) VALUES
                                                                               (1, 2, 1, 'Jon Snow', true),
                                                                               (2, 2, 2, 'Regissør', false),
                                                                               (3, 2, 3, 'Tormund Giantsbane', true);


INSERT INTO companyTitle (CompanyTitleID, TitleID, CompanyID, Role) VALUES
                                                                           (1, 1, 2, 'Producer'),
                                                                           (1, 4, 2, 'Producer'),
                                                                           (2, 3, 1, 'Jealous');

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


INSERT INTO roleInScore (roleInScoreID, PersonID, ScoreID, Role) VALUES
                                                                        (1, 4, 2, 'Komponist'),
                                                                        (2, 5, 4, 'Komponist');





