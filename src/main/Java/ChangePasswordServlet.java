
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;



@WebServlet("/newpassword")
public class ChangePasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String oldPwd = request.getParameter("oldpwd");
        String newPwd = request.getParameter("newpwd");

        HttpSession s = request.getSession();
        Object id = s.getAttribute("userid");

        Postgres db = new Postgres();
        String hashedPwd = db.getHashedPwd(id);

        if (BCrypt.checkpw(oldPwd,hashedPwd)){

            db.setNewPassword(newPwd,id);
            response.sendRedirect("userProfile.jsp");


        }

    }
}
