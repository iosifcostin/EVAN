import org.apache.commons.lang.RandomStringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String back = "index.jsp";
        HttpSession session = request.getSession();

        session.invalidate();

        Cookie cookie = new Cookie("evan", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.sendRedirect(back);
    }

}
