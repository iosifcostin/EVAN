import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/* acest servlet verifica cookies si face autologin daca gaseste cookie-ul aplicatiei

* Problema: - in web.xml acest servlet daca e pus loadOnStartUp tomcat nu
* incarca css-urile de aceea am comentat codul in web.xml ...
*
*/

@WebServlet("/startup")
public class StartUpServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        Postgres db = new Postgres();
        HttpSession session = req.getSession();
        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("evan")) {

                    int val = Integer.parseInt(cookie.getValue());

                    String numePrenume = db.numePrenume(val);
                    List listIMC = db.getIMCfromDb(val);

                    List imgPaths = db.getImgPaths(val);
                    String profilePic = db.getProfileImgPath(val);
                    List deletedPics = db.getDeletedImgPaths(val);

                    session.setAttribute("userid",val);
                    session.setAttribute("uname", numePrenume);
                    session.setAttribute("listIMC", listIMC);
                    session.setAttribute("images", imgPaths);
                    session.setAttribute("size", imgPaths.size());
                    session.setAttribute("profilePic", profilePic);
                    session.setAttribute("deletedPics", deletedPics);

                    resp.sendRedirect("userProfile.jsp");
                }else{
                    resp.sendRedirect("index.jsp");
                }
            }
        }
    }
}
