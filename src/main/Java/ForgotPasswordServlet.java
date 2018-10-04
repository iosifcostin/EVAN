import org.apache.commons.lang.RandomStringUtils;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/password")
public class ForgotPasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String usrEmail = request.getParameter("email");
        String newPassword = RandomStringUtils.random(8, true, true);

        Postgres database = new Postgres();
        Email email = new Email();

        database.updatePassword(usrEmail, newPassword);

        String subiect = "Noua parola";
        String mesaj = "Noua dumneavoastra parla EVAN este : " + newPassword;

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                email.send(usrEmail, subiect, mesaj);
            }
        };
        runnable.run();


        if (BCrypt.checkpw(newPassword, database.getHashedPwd(usrEmail))) {

            String msg = "Vei primi un email cu noua parola";

            HttpSession session = request.getSession();
            session.setAttribute("message", msg);

            response.sendRedirect("firstPage.jsp");
        }
    }
}
