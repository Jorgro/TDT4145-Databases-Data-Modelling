USE `Task1`;
SET SQL_SAFE_UPDATES = 0;

UPDATE Skuespiller
SET Navn = "James Eugene Carrey"
WHERE Navn = "Jim Carrey"; -- Mange måter å gjøre dette på, beste er egentlig å bruke PK. 

DELETE FROM Regissør 
WHERE RegissørID = 2;

DELETE FROM Film
WHERE FilmID = 1;