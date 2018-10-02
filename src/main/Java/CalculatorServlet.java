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
import java.util.Date;
import java.util.List;


@WebServlet("/calculator")
public class CalculatorServlet extends HttpServlet {

    private String imc = "IMC: ";
    private String subponderal = " - SUBPONDERALITATE ";
    private String normal = " - MASA NORMALA ";
    private String supraponderal = " - SUPRAPONDERALITATE ";
    private String obez = " - OBEZITATE ";
    private String morbid = " - OBEZITATE MORBIDA ";
    private String mesaj = " \n Masa dumneavoastra ideala este ";
    private String kg = " kg";
    private String msg = " RMB: ";
    private String msg2 = "Pentru tipul de activitate pe care o depuneti, rata metabolismului dvs. este de ";
    private String msg3 = " kilocalorii pe zi.";
    private String n = "\n";


    private double imcVal;
    private double miVal;
    private double rmbVal;
    private double ncVal;


    private Calculator calculator = new Calculator();
    private Postgres db = new Postgres();
    private JSONObject json = new JSONObject();


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {

        String action = req.getParameter("action");

        if (action != null && action.equals("read"))
            read(req, resp);
        else if (action != null && action.equals("write"))
            write(req, resp);
    }


    private void read(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession();
        Object id = session.getAttribute("userid");

        if (imcVal < 18.49 && imcVal != 0) {

            json.put("imc", imc + imcVal + subponderal + mesaj + miVal + kg);
            json.put("inf", db.info(1));
            json.put("rmb", msg + rmbVal + n + db.info(7));
            json.put("nc", msg2 + ncVal + msg3);

            if (id != null){
                save(req, resp);
            }
            reset();
            returnJsonResponse(resp, json.toString());

        } else if (imcVal > 18.50 && imcVal < 24.99) {

            json.put("imc", imc + imcVal + normal + mesaj + miVal + kg);
            json.put("inf", db.info(2));
            json.put("rmb", msg + rmbVal + n + db.info(7));
            json.put("nc", msg2 + ncVal + msg3);

            if (id != null){
                save(req, resp);
            }
            reset();
            returnJsonResponse(resp, json.toString());

        } else if (imcVal > 25.00 && imcVal < 29.99) {

            json.put("imc", imc + imcVal + supraponderal + mesaj + miVal + kg);
            json.put("inf", db.info(3));
            json.put("rmb", msg + rmbVal + n + db.info(7));
            json.put("nc", msg2 + ncVal + msg3);

            if (id != null){
                save(req, resp);
            }
            reset();
            returnJsonResponse(resp, json.toString());

        } else if (imcVal > 30.00 && imcVal < 39.99) {

            json.put("imc", imc + imcVal + obez + mesaj + miVal + kg);
            json.put("inf", db.info(4));
            json.put("rmb", msg + rmbVal + n + db.info(7));
            json.put("nc", msg2 + ncVal + msg3);

            if (id != null){
                save(req, resp);
            }
            reset();
            returnJsonResponse(resp, json.toString());

        } else if (imcVal > 40.00) {

            json.put("imc", imc + imcVal + morbid + mesaj + miVal + kg);
            json.put("inf", db.info(4));
            json.put("rmb", msg + rmbVal + n + db.info(7));
            json.put("nc", db.info(8) + ncVal + msg3);

            if (id != null){
                save(req, resp);
            }
            reset();
            returnJsonResponse(resp, json.toString());
        }
    }

    private void write(HttpServletRequest req, HttpServletResponse resp) {

        String varsta = req.getParameter("varsta");
        String inaltime = req.getParameter("inaltime");
        String masa = req.getParameter("masa");
        String sex = req.getParameter("sex");
        String tip = req.getParameter("tip");

        double vrs = Double.parseDouble(varsta);
        double inl = Double.parseDouble(inaltime);
        double msa = Double.parseDouble(masa);


        imcVal = calculator.imcCalculator(inl, msa);
        miVal = calculator.miCalculator(inl);
        rmbVal = calculator.rmbCalculator(vrs, msa, inl, sex);
        ncVal = calculator.ncCalculator(tip, rmbVal);

    }

    private void save(HttpServletRequest req, HttpServletResponse resp) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        HttpSession session = req.getSession();
        Object id = session.getAttribute("userid");

        if (imcVal != 0 && rmbVal != 0 && ncVal != 0) {

            if (db.compareDate(id, dateFormat.format(date))) {

                db.updateValues(imcVal, rmbVal, ncVal, miVal, id);

            } else {
                db.storeValues(imcVal, rmbVal, ncVal, miVal, id);
            }
        }

        if (!id.equals(-1)) {
            List listIMC = db.getIMCfromDb(id);
            session.setAttribute("listIMC", listIMC);
        }

    }

    private void reset() {
        imcVal = 0;
        miVal = 0;
        rmbVal = 0;
        ncVal = 0;
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
