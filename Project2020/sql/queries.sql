use larswwa_tdt4145;

# Finne navnet på alle rollene en gitt skuespiller har.
SELECT Role, Name FROM (personTitle NATURAL JOIN person) WHERE Name = 'Kit Harington';


# Finne hvilke filmer som en gitt skuespiller opptrer i.
SELECT DISTINCT t.Name FROM personTitle INNER JOIN title t on personTitle.TitleID = t.TitleID  # with ID
                    WHERE Actor = TRUE AND PersonID = 3;

SELECT DISTINCT t.Name, p.Name FROM title t  JOIN personTitle pT ON t.TitleID = pT.TitleID JOIN person p ON pT.PersonID = p.PersonID
                WHERE Actor = TRUE AND p.Name = 'Kristoffer Hivju'; # With name

# Finne hvilke filmselskap som lager flest filmer innen hver sjanger

# Finne antall i én sjanger for et selskap
#Not finished
SELECT * FROM categoryInTitle JOIN companyTitle cT on categoryInTitle.TitleID = cT.TitleID
                    WHERE (cT.CompanyID IN (SELECT c.CompanyID FROM company c INNER JOIN companyTitle T on c.CompanyID = T.CompanyID
                                           WHERE c.Name = 'Netflix') )  ;