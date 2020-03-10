package org.openjfx;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompanyCtrl extends DBConnector {

    static public class CompanyCount

    {
        public String company;
        public int count;

        public CompanyCount(String company, int count) {
            this.company = company;
            this.count = count;
        }

        public String toString() {
            return this.company + ": " + this.count;
        }
    };



    public Map<String, List<CompanyCount>> getCompaniesWithMostMoviesInCategory() throws SQLException {
        Map<String, List<CompanyCount>> result = new HashMap<>();
        PreparedStatement prep = conn.prepareStatement(
    "SELECT cat.CategoryID, cat.name AS CategoryName, c.CompanyID, c.name AS CompanyName, count(*) AS count FROM company AS c JOIN companyTitle ct ON c.CompanyID = ct.CompanyID JOIN title t ON ct.TitleID = t.TitleID JOIN categoryInTitle cit ON cit.TitleID = t.TitleID " +
            "JOIN category cat ON cit.CategoryID = cat.CategoryID "+
            "GROUP BY cat.CategoryID, c.CompanyID " +
            "HAVING count >= ALL (" +
            "SELECT count(*) as count FROM company c2 JOIN companyTitle ct2 ON c2.CompanyID = ct2.CompanyID JOIN title t2 ON ct2.TitleID = t2.TitleID JOIN categoryInTitle cit2 ON cit2.TitleID = t2.TitleID " +
            "JOIN category cat2 ON cit2.CategoryID = cat2.CategoryID " +
            "WHERE c2.CompanyID != c.CompanyID AND cat2.CategoryID = cat.CategoryID AND t2.TitleID NOT IN (" +
            "SELECT SeriesID FROM episodeInSeries ) " +
            "GROUP BY cat2.CategoryID, c2.CompanyID "+
            ");");
        ResultSet rs = prep.executeQuery();
        while (rs.next()) {
            String category = rs.getString("CategoryName");
            String company = rs.getString("CompanyName");
            int count = rs.getInt("count");
            if (!result.containsKey(category)) {
                result.put(category, new ArrayList<>());
            }
            result.get(category).add(new CompanyCount(company, count));
        }
        System.out.println(rs);
        return result;
    }

    public static void main(String[] args) {
        CompanyCtrl c = new CompanyCtrl();
        c.connect();
        try {
            System.out.println(c.getCompaniesWithMostMoviesInCategory());}
        catch (Exception e) {
            System.out.println(e); }
    }
};
