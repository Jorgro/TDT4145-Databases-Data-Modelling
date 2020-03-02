use larswwa_tdt4145;


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
                                                           (4, 'www.apple.com', 'USA'),
                                                           (5, 'www.sony.com', 'Japan');

INSERT INTO score (ScoreID, Title) VALUES (1, 'Main title - GOT'),
                                          (2, 'Light of the seven'),
                                          (3, 'The Rains of Castamere');





