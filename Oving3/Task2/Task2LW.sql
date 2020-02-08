USE tdt4145_filmbase;

-- A
SELECT *
FROM film;

-- B
SELECT Navn
FROM skuespiller
WHERE Fødselsår > 1960;

-- C
SELECT Navn
FROM skuespiller
WHERE Fødselsår >= 1980 AND Fødselsår < 1990
ORDER BY Navn;

-- D
SELECT Tittel, Rolle
FROM film INNER JOIN skuespillerifilm s on film.FilmId = s.FilmID
WHERE SkuespillerID = (SELECT SkuespillerID FROM skuespiller WHERE Navn = 'Morgan Freeman');

-- E
SELECT DISTINCT Tittel
FROM ((film INNER JOIN regissør r on film.RegissørID = r.RegissørID)
        INNER JOIN skuespillerifilm sif on film.FilmId = sif.FilmID)
        INNER JOIN skuespiller s on sif.SkuespillerID = s.SkuespillerID
WHERE s.Navn = r.Navn;

-- F
SELECT COUNT(Navn)
FROM skuespiller
WHERE Navn like 'C%';

-- G
SELECT sjanger.Navn as Sjanger, COUNT(*) AS Antall
FROM sjanger INNER JOIN sjangerforfilm s on sjanger.SjangerID = s.SjangerID
GROUP BY sjanger.Navn;

-- H
SELECT s.Navn
FROM skuespiller as s
WHERE NOT EXISTS (SELECT * FROM skuespillerifilm AS sf NATURAL JOIN film AS f
        WHERE f.Tittel = 'Ace Ventura: When Nature Calls' AND sf.SkuespillerID = s.SkuespillerID)
        AND EXISTS (SELECT * FROM skuespillerifilm AS sf NATURAL JOIN film AS f WHERE f.tittel = 'Ace Ventura: Pet Detective' AND sf.SkuespillerID = s.SkuespillerID);

-- I
SELECT f.Tittel AS Film, f.filmId, AVG(s.Fødselsår) AS Snitt
FROM (skuespillerifilm INNER JOIN skuespiller s on skuespillerifilm.SkuespillerID = s.SkuespillerID) INNER JOIN film f on skuespillerifilm.FilmID = f.FilmId
GROUP BY Film
HAVING Snitt > ALL (SELECT AVG(Fødselsår) FROM skuespiller);