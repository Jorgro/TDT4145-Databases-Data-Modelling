USE `Task1`;

-- a)
SELECT SkuespillerID, Navn
FROM skuespiller as S
WHERE NOT EXISTS (
    SELECT *
    FROM skuespiller NATURAL JOIN skuespillerifilm
    NATURAL JOIN film
    WHERE Produksjonsår > 2010 AND S.SkuespillerID = skuespillerifilm.SkuespillerID
    );

-- b)
SELECT *
FROM skuespiller
WHERE Fødselsår > 2000
UNION
SELECT *
FROM skuespiller
WHERE Fødselsår > 1990 AND SkuespillerID IN
(
    SELECT SkuespillerID
    FROM skuespiller NATURAL JOIN skuespillerifilm
    WHERE Rolle = 'Ungdom'
    );

-- c)
SELECT SkuespillerID, Navn, COUNT(FilmID) as AntallFilmer
FROM skuespiller NATURAL JOIN skuespillerifilm
GROUP BY skuespillerID;

