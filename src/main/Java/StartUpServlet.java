import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/startup")
public class StartUpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

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

                    session.setAttribute("userid",val);
                    session.setAttribute("uname", numePrenume);
                    session.setAttribute("listIMC", listIMC);
                    session.setAttribute("images", imgPaths);
                    session.setAttribute("size", imgPaths.size());
                    session.setAttribute("profilePic", profilePic);
                    resp.sendRedirect("userInfo.jsp");

                }else{
                    resp.sendRedirect("index.jsp");
                }
            }
        }
    }
}
