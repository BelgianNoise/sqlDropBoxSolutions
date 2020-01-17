import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
    private SolutionDB solutions;
    private Properties properties, propsVanSchool;

    @Override
    public void init() throws ServletException {
        super.init();
        properties = new Properties();
        properties.put("url","jdbc:postgresql://" +
                "localhost:5432" +
                "/postgres?currentSchema=sqlSolutions");
        properties.put("user", "postgres");
        properties.put("password","PoesTgres");
        properties.put("currentSchema","sqlSolutions");
        properties.put("ssl","true");
        properties.put("sslmode","prefer");
        properties.put("sslfactory",
                "org.postgresql.ssl.NonValidatingFactory");

        propsVanSchool = new Properties();
        propsVanSchool.put("url", "jdbc:postgresql://databanken.ucll.be" +
                ":54321/basis_rdbms?currentSchema=tennis");
        propsVanSchool.put("user", "r0702794");
        propsVanSchool.put("password", "Seafight1");
        propsVanSchool.put("currentSchema", "tennis");
        propsVanSchool.put("ssl","true");
        propsVanSchool.put("sslmode","prefer");
        propsVanSchool.put("sslfactory",
                "org.postgresql.ssl.NonValidatingFactory");
        solutions = new SolutionDB(properties, propsVanSchool);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response){
        String command = "";
        if(request.getParameter("command") != null){
            command = request.getParameter("command");
        }
        String destination = "index.jsp";
        try {
            switch (command) {
                case "index":
                    destination = indexHandler(request, response);
                    break;
                case "add":
                    destination = addHandler(request, response);
                    break;
                case "edit":
                    destination = editHandler(request, response);
                    break;
                case "editredirect":
                    destination = editRedirectHandler(request, response);
                    break;
                case "delete":
                    destination = deleteHandler(request, response);
                    break;
                case "login":
                    destination = loginHandler(request, response);
                    break;
                case "getbycat":
                    destination = getByCat(request, response);
                    break;
                case "contribute":
                    destination = contribute(request, response);
                    break;
                case "searchbyurl":
                    destination = search(request, response);
                    break;
                default:
                    destination = indexHandler(request, response);

            }
        }catch (Exception e){
            request.setAttribute("foutje", e.getMessage());
            destination = indexHandler(request, response);
        }
        if(destination != null){
            try {
                request.getRequestDispatcher(destination)
                        .forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String search(HttpServletRequest request,
                          HttpServletResponse response){
        String een = request.getParameter("eerste");
        String twee = request.getParameter("tweede");
        String urlll = "https://webontwerp.khleuven.be/" +
                "sqldropbox/oefeningreeks/maak" +
                "/"+een+"/oefening/"+twee;
        ArrayList<Solution> sol = solutions.getByUrlForSearch(urlll);
        request.setAttribute("solutions", sol);
        request.setAttribute("category", sol.get(0).getCategory());
        return "overzicht.jsp";
    }
    private String contribute(HttpServletRequest request,
                              HttpServletResponse response){
        return "contribute.jsp";
    }
    private String getByCat(HttpServletRequest request,
                            HttpServletResponse response){
        String cat = request.getParameter("category");
        System.out.println("Looking for cat: \"" + cat+ "\"");
        request.setAttribute("solutions",
                solutions.getByCategory(cat));
        request.setAttribute("category", cat);
        return "overzicht.jsp";
    }
    private boolean checkIfLoggedIn(HttpServletRequest request){
        return request.getSession().getAttribute("user") != null;
    }

    private String indexHandler(HttpServletRequest request,
                                HttpServletResponse response){
        request.setAttribute("categories", solutions.getAllCats());
        return "index.jsp";
    }
    private String addHandler(HttpServletRequest request,
                              HttpServletResponse response){
        if(!checkIfLoggedIn(request)){
            throw new IllegalArgumentException("Inloggen he flippo");
        }

        String url = request.getParameter("url");
        String ans = request.getParameter("answer");
        String ques = request.getParameter("question");
        String cat = request.getParameter("category");

        solutions.addSolution(url, ques, ans, cat);

        try {
            response.sendRedirect("Controller");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String editHandler(HttpServletRequest request,
                               HttpServletResponse response){
        if(!checkIfLoggedIn(request)){
            throw new IllegalArgumentException("Inloggen he flippo");
        }

        String ans = request.getParameter("answer");
        String ques = request.getParameter("question");
        String cat = request.getParameter("category");
        String url = request.getParameter("url");
        System.out.println(url);
        solutions.editSolution(url,ques,ans,cat);

        try {
            response.sendRedirect("Controller");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String deleteHandler(HttpServletRequest request,
                                 HttpServletResponse response){
        if(!checkIfLoggedIn(request)){
            throw new IllegalArgumentException("Inloggen he flippo");
        }

        String url = request.getParameter("url");

        solutions.deleteSolution(url);

        try {
            response.sendRedirect("Controller");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String loginHandler(HttpServletRequest request,
                                HttpServletResponse response){
        String user = request.getParameter("user");
        String pw = request.getParameter("password");
        if(user.equals("admin") && pw.equals("sadmin")){
            request.getSession().setAttribute("user", "admin");

            try {
                System.out.println("Logged in");
                response.sendRedirect("Controller");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }else{
            throw new IllegalArgumentException("Foute gegevens min " +
                    "makker, laat het uit");
        }
    }
    private String editRedirectHandler(HttpServletRequest request,
                                       HttpServletResponse response){
        request.setAttribute("sol",
                this.solutions.getByUrl(
                        request.getParameter("url")));
        return "edit.jsp";
    }
}
