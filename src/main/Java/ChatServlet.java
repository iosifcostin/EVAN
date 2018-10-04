import org.json.JSONObject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@WebServlet("/chat")
public class ChatServlet extends HttpServlet {

    private Postgres db = new Postgres();
    private JSONObject json = new JSONObject();


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {

        String action = req.getParameter("action");

        if (action != null && action.equals("write"))
            write(req, resp);
        if (action != null && action.equals("read"))
            read(req, resp);

    }

    private void write(HttpServletRequest req, HttpServletResponse resp) {

        String message = req.getParameter("message");

        HttpSession session = req.getSession();
        Object id = session.getAttribute("userid");

        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss  | MMMM dd, yyyy");
        Date d = new Date();
        String date = dateFormat.format(d);

        db.saveMessage(message, id, date);


    }

    private void read(HttpServletRequest req, HttpServletResponse resp) {

//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        };
//        runnable.run();

        HttpSession session = req.getSession();
        Object id = session.getAttribute("userid");

        List<String> messages = db.messages(id);


        String formattedString = messages.toString()
                .replace(",", "")  //remove the commas
                .replace("[", "")  //remove the right bracket
                .replace("]", "")  //remove the left bracket
                .trim();           //remove trailing spaces from partially initialized arrays

        json.put("chat", formattedString);
        returnJsonResponse(resp,json.toString());

        try {
            resp.sendRedirect("/chat.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
    private void returnJsonResponse(HttpServletResponse response, String jsonResponse) {
        response.setContentType("application/json");
        PrintWriter pr = null;
        try {
            pr = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert pr != null;
        pr.write(jsonResponse);
        pr.close();
    }

}
