USE `Task1`;

-- a)
SELECT *
FROM film;

-- b)
SELECT DISTINCT Navn
FROM skuespiller as S
WHERE S.Fødselsår > 1960;

-- c)
SELECT DISTINCT Navn
FROM skuespiller as S
WHERE S.Fødselsår >= 1980 AND S.Fødselsår < 1990
ORDER BY S.Navn;

-- d)
SELECT Tittel, Rolle
FROM film
      NATURAL JOIN skuespillerifilm
      NATURAL JOIN skuespiller
WHERE skuespiller.Navn = 'Morgan Freeman';

-- e)
SELECT DISTINCT Tittel
FROM skuespillerifilm
    NATURAL JOIN film
    NATURAL JOIN regissør
    NATURAL JOIN skuespiller
WHERE skuespiller.navn = regissør.Navn;

-- f)
SELECT COUNT(*)
FROM skuespiller
WHERE skuespiller.Navn LIKE 'C%';

-- g)
SELECT sjanger.Navn, Count(FilmID) as antalllFilmer
FROM sjanger
    NATURAL JOIN sjangerforfilm
GROUP BY SjangerID;

-- h)
SELECT *
FROM skuespiller as S
WHERE S.SkuespillerID IN
      (
    SELECT skuespillerifilm.SkuespillerID
    FROM skuespiller NATURAL JOIN skuespillerifilm
    NATURAL JOIN film
    WHERE film.Tittel = 'Ace Ventura: Pet Detective'
    )
    AND
    S.SkuespillerID NOT IN
    (
    SELECT skuespillerifilm.SkuespillerID
    FROM skuespiller NATURAL JOIN skuespillerifilm
    NATURAL JOIN film
    WHERE film.Tittel = 'Ace Ventura: When Nature Calls'
);

-- i)
SELECT Tittel, FilmID, AVG(Fødselsår)
FROM film NATURAL JOIN skuespillerifilm
    NATURAL JOIN skuespiller
GROUP BY FilmID
HAVING AVG(Fødselsår) > (
    SELECT AVG(Fødselsår)
    FROM skuespiller
    );

