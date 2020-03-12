use larswwa_tdt4145;

# Finne navnet på alle rollene en gitt skuespiller har.
SELECT Role, Name FROM (personTitle NATURAL JOIN person) WHERE Name = 'Kit Harington';


# Finne hvilke filmer som en gitt skuespiller opptrer i.
SELECT DISTINCT t.Name FROM personTitle INNER JOIN title t on personTitle.TitleID = t.TitleID  # with ID
WHERE Actor = TRUE AND PersonID = 3;

SELECT DISTINCT t.Name, p.Name FROM title t  JOIN personTitle pT ON t.TitleID = pT.TitleID JOIN person p ON pT.PersonID = p.PersonID
WHERE Actor = TRUE AND p.Name = 'Kristoffer Hivju'; # With name

# Finne hvilke filmselskap som lager flest filmer innen hver sjanger
SELECT cat.CategoryID, cat.name AS CategoryName, c.CompanyID, c.name AS CompanyName, count(*) AS count FROM company AS c JOIN companyTitle ct ON c.CompanyID = ct.CompanyID JOIN title t ON ct.TitleID = t.TitleID JOIN categoryInTitle cit ON cit.TitleID = t.TitleID
JOIN category cat ON cit.CategoryID = cat.CategoryID
WHERE t.TitleID NOT IN (SELECT SeriesID FROM episodeInSeries)
GROUP BY cat.CategoryID, c.CompanyID
HAVING count >= ALL (
SELECT count(*) as count FROM company c2 JOIN companyTitle ct2 ON c2.CompanyID = ct2.CompanyID JOIN title t2 ON ct2.TitleID = t2.TitleID JOIN categoryInTitle cit2 ON cit2.TitleID = t2.TitleID
    JOIN category cat2 ON cit2.CategoryID = cat2.CategoryID
WHERE cat2.CategoryID = c.CompanyID AND t2.TitleID NOT IN (
SELECT SeriesID FROM episodeInSeries )
GROUP BY cat2.CategoryID, c2.CompanyID);


SELECT * FROM company;
SELECT * FROM `categoryInTitle`;

INSERT INTO companyTitle (TitleID, CompanyID, Role) VALUES (6, 1, 'Producer');
INSERT INTO companyTitle (TitleID, CompanyID, Role) VALUES (2, 1, 'Producer');
INSERT INTO companyTitle (TitleID, CompanyID, Role) VALUES (5, 1, 'Producer');
INSERT INTO title (Name, Content, Duration, PublishYear, LaunchYear) VALUES ('Drama', 'Drame time', 1, 2011, 2011);
INSERT INTO categoryInTitle (TitleID, CategoryID) VALUES (16, 1);
INSERT INTO companyTitle (TitleID, CompanyID, Role) VALUES (16, 3, 'Producer');

INSERT INTO title (Name, Content, Duration, PublishYear, LaunchYear) VALUES ('Drama2', 'Drame time2', 1, 2011, 2011);
INSERT INTO categoryInTitle (TitleID, CategoryID) VALUES (11, 1);
INSERT INTO companyTitle (TitleID, CompanyID, Role) VALUES (11, 1, 'Producer');

INSERT INTO title (Name, Content, Duration, PublishYear, LaunchYear) VALUES ('Drama3', 'Drame time3', 1, 2011, 2011);
INSERT INTO categoryInTitle (TitleID, CategoryID) VALUES (12, 1);
INSERT INTO companyTitle (TitleID, CompanyID, Role) VALUES (12, 1, 'Producer');


# Finne antall i én sjanger for et selskap
# Crazy function
# WHERE (cT.CompanyID IN
#        (SELECT c.CompanyID FROM company c INNER JOIN companyTitle T on c.CompanyID = T.CompanyID
#         WHERE c.Name = 'HBO') ) AND CategoryID  IN
#                                     (SELECT cat.CategoryID FROM category cat NATURAL JOIN categoryInTitle cIT
#                                      WHERE cat.Name = 'Drama');
