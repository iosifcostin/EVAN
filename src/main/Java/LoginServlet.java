import org.apache.commons.lang.RandomStringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;


@WebServlet("/connect")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Postgres db = new Postgres();

        String email = request.getParameter("mail");
        String parola = request.getParameter("psw");


        int val = db.login(email, parola);
        String numePrenume = db.numePrenume(val);
        List listIMC = db.getIMCfromDb(val);
        List imgPaths = db.getImgPaths(val);
        String profilePic = db.getProfileImgPath(val);

        Cookie cookie = new Cookie("evan", ""+val);
        cookie.setMaxAge(60*60);
        response.addCookie(cookie);



        if (val != -1) {
            HttpSession session = request.getSession();
            session.setAttribute("userid", val);
            session.setAttribute("uname", numePrenume);
            session.setAttribute("listIMC", listIMC);
            session.setAttribute("images", imgPaths);
            session.setAttribute("size", imgPaths.size());
            session.setAttribute("profilePic", profilePic);
            ImagesServlet.trash(request,response);
            response.sendRedirect("userProfile.jsp");


        } else {

            String back = "index.jsp";
            HttpSession session = request.getSession();
            session.removeAttribute("userid");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(back);
            dispatcher.forward(request, response);
        }

    }
}
