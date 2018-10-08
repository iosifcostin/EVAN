import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String nume = req.getParameter("nume");
        String prenume = req.getParameter("prenume");
        String email = req.getParameter("email");
        String parola = req.getParameter("parola");
        String dataN = req.getParameter("dataNasterii");
        String sex = req.getParameter("sex");

        Postgres db = new Postgres();

        db.register(nume, prenume, email, parola, dataN, sex);

        int value = db.login(email, parola);
        String numePrenume = db.numePrenume(value);
        List imgPaths = db.getImgPaths(value);

        if (value != -1) {

            HttpSession session = req.getSession();
            session.setAttribute("userid", value);
            session.setAttribute("uname", numePrenume);
            session.setAttribute("images", imgPaths);
            session.setAttribute("size", imgPaths.size());


            resp.sendRedirect("userProfile.jsp");
        } else {
            System.out.println("LoginServlet:registration not done correctly ");
            String back = "/index.jsp";
            HttpSession session = req.getSession();
            session.removeAttribute("userid");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(back);
            dispatcher.forward(req, resp);
        }
    }

}
