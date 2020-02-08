USE tdt4145_filmbase;

-- A
SELECT SkuespillerID, Navn
FROM skuespiller AS s
WHERE NOT EXISTS (SELECT *
                FROM skuespiller NATURAL JOIN skuespillerifilm NATURAL JOIN film
                WHERE Produksjonsår > 2014
                    AND skuespillerifilm.SkuespillerID = s.SkuespillerID);

-- B
SELECT SkuespillerID, Navn, Fødselsår
FROM skuespiller AS s
WHERE s.Fødselsår > 2000
    OR s.SkuespillerID IN (SELECT SkuespillerID FROM skuespillerifilm NATURAL JOIN skuespiller
                            WHERE skuespiller.Fødselsår > 1990 AND skuespillerifilm.Rolle = 'Ungdom');

-- C
SELECT s.SkuespillerID, s.Navn, COUNT(f.FilmId) AS 'Antall Filmer'
FROM skuespillerifilm NATURAL JOIN skuespiller AS s NATURAL JOIN film as f
GROUP BY s.SkuespillerID;
