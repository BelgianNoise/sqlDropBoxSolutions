import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class SolutionDB {
    private Properties properties, propertiesVanSchool;
    private String url;

    public SolutionDB(Properties props, Properties propsSchool){
        properties = props;
        propertiesVanSchool = propsSchool;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("probleempie bij db");
        }
        url = (String)properties.get("url");
    }

    public ArrayList<String> getAllCats(){
        String sql = "SELECT DISTINCT category FROM " +
                "\"sqlSolutions\".solutions";
        try(Connection connection = DriverManager.getConnection
                (url,properties);
            Statement statement = connection.createStatement();)
        {
            ArrayList<String> strings = new ArrayList<>();
            ResultSet res = statement.executeQuery(sql);
            while (res.next()){
                String str = res.getString("category");
                strings.add(str);
            }
            return strings;
        }catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public Solution getByUrl(String url){
        String sql = "SELECT * FROM \"sqlSolutions\".solutions " +
                "WHERE url=?";
        try(Connection connection = DriverManager.getConnection
                (this.url, properties);
            PreparedStatement prepstate =
                    connection.prepareStatement(sql);)
        {
            prepstate.setString(1, url);
            ResultSet res = prepstate.executeQuery();
            res.next();
            String u = res.getString("url");
            String q = res.getString("question");
            String a = res.getString("answer");
            String c = res.getString("category");
            double d = Double.parseDouble(res.getString("cost"));
            Solution s = new Solution(u,q,c,a,d);
            return s;
        }catch (Exception e){
            // throw new IllegalArgumentException("Volgens mij zit diene " +
            //        "url nog ni onzen db. Sorry rakker");
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public ArrayList<Solution> getByUrlForSearch(String url){
        String sql = "SELECT * FROM \"sqlSolutions\".solutions " +
                "WHERE url=?";
        try(Connection connection = DriverManager.getConnection
                (this.url, properties);
            PreparedStatement prepstate =
                    connection.prepareStatement(sql);)
        {
            prepstate.setString(1, url);
            System.out.println(url);
            ResultSet res = prepstate.executeQuery();
            res.next();
            String u = res.getString("url");
            String q = res.getString("question");
            String a = res.getString("answer");
            String c = res.getString("category");
            double d = Double.parseDouble(res.getString("cost"));
            Solution s = new Solution(u,q,c,a,d);
            ArrayList<Solution> sols = new ArrayList<>();
            sols.add(s);
            return sols;
        }catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public ArrayList<Solution> getAll(){
        String sql = "SELECT * FROM \"sqlSolutions\".solutions";
        try(Connection connection = DriverManager.getConnection
                (url, properties);
            Statement statement = connection.createStatement();)
        {
            ResultSet res = statement.executeQuery(sql);
            return interpretResultSet(res);
        } catch (Exception e) {
            throw new IllegalArgumentException("foutje bij opvragen All");
        }
    }
    public ArrayList<Solution> getByCategory(String cat){
        String sql = "SELECT * FROM \"sqlSolutions\".solutions " +
                "WHERE category=?";
        try(Connection connection = DriverManager.getConnection
                (url, properties);
            PreparedStatement prepstate = connection.prepareStatement(sql);)
        {
            prepstate.setString(1, cat);
            System.out.println("query: " + prepstate);
            return interpretResultSet(prepstate.executeQuery());
        }catch (Exception e){
            throw new IllegalArgumentException("Foutje in de get by Cat");
        }
    }
    public void addSolution(String url, String question, String answer, String cat){
        checkIfURLExists(url);
        String sql = "INSERT INTO \"sqlSolutions\".solutions" +
                "(url, question, answer, category,cost) " +
                "VALUES(?,?,?,?,?::DOUBLE PRECISION)";
        try(Connection connection = DriverManager.getConnection
                (this.url, properties);
            PreparedStatement prepstate = connection.prepareStatement(sql);)
        {
            prepstate.setString(1, url);
            prepstate.setString(2,question);
            prepstate.setString(3, answer);
            prepstate.setString(4, cat);
            prepstate.setString(5, getCost(answer));

            prepstate.execute();
        }catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void checkIfURLExists(String url) {
        String sql = "SELECT * FROM \"sqlSolutions\".solutions WHERE url=?";
        try(Connection connection = DriverManager.getConnection
                (this.url, properties);
            PreparedStatement prepstate = connection.prepareStatement(sql);)
        {
            prepstate.setString(1, url);
            if(interpretResultSet(prepstate.executeQuery()).size() >= 1){
                throw new IllegalArgumentException("url besta al");
            }
        }catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void deleteSolution(String url){
        String sql = "DELETE FROM \"sqlSolutions\".solutions WHERE url=?";
        try(Connection connection = DriverManager.getConnection
                (this.url, properties);
            PreparedStatement prepstate = connection.prepareStatement(sql);)
        {
            prepstate.setString(1, url);
            prepstate.execute();
        }catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void editSolution(String url, String question, String answer,
                             String cat){
        String sql = "UPDATE \"sqlSolutions\".solutions " +
                "SET question = ?," +
                "answer = ?, category = ?, cost = ?::DOUBLE PRECISION " +
                "WHERE url = ?";
        try(Connection connection = DriverManager.getConnection
                (this.url, properties);
            PreparedStatement prepstate =
                    connection.prepareStatement(sql);)
        {
            prepstate.setString(1,question);
            prepstate.setString(2,answer);
            prepstate.setString(3,cat);
            prepstate.setString(5,url);
            prepstate.setString(4,getCost(answer));
            System.out.println("niks duplicate");
            prepstate.execute();
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private ArrayList<Solution> interpretResultSet(ResultSet res) throws SQLException {
        ArrayList<Solution> sols = new ArrayList<>();

        while(res.next()) {
            String url = res.getString("url");
            String ques = res.getString("question");
            String ans = res.getString("answer");
            String cat = res.getString("category");
            double d = Double.parseDouble(res.getString("cost"));
            Solution sol = new Solution(url,ques,cat,ans,d);
            sols.add(sol);
        }
        return sols;
    }

    private String getCost(String answer) {
        try (Connection connection = DriverManager.getConnection
                (propertiesVanSchool.get("url").toString(), propertiesVanSchool);
             Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery("explain " + answer.replaceAll("<br>"," "));
            rs.next();
            String cS = rs.getString(1);
            return cS.substring(cS.indexOf("cost") + 5, cS.indexOf(".."));
        } catch (SQLException e) {
            String error = e.getMessage();
            if (error.contains("relation") && error.contains("does not exist")) {
                if(propertiesVanSchool.get("currentSchema").equals("tennis")){
                    setSchema("ruimtereizen");
                }else{
                    setSchema("tennis");
                }
                return getCost(answer);
            } else {
                System.out.println(error);
            }
        }
        return "696969";
    }
    private void setSchema(String s){
        propertiesVanSchool.put("currentSchema", s);
        propertiesVanSchool.put("url", "jdbc:postgresql://databanken.ucll.be" +
                ":54321/basis_rdbms?currentSchema="+s);
    }
}
