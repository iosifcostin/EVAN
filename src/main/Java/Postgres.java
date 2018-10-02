
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpSession;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;


public class Postgres {

    private final String URL = "jdbc:postgresql://localhost:5432/evan_db";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "asdfasdf";

    public void register(String nume, String prenume, String email, String parola, String dataNasterii, String sex) {

        String pwd = BCrypt.hashpw(parola, BCrypt.gensalt());

        try {
            Class.forName("org.postgresql.Driver");


            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String query = "INSERT INTO utilizatori (nume,prenume,email,parola,datanasterii,sex)  VALUES (?,?,?,?,?,?)";

            PreparedStatement pSt = conn.prepareStatement(query);
            pSt.setString(1, nume);
            pSt.setString(2, prenume);
            pSt.setString(3, email);
            pSt.setString(4, pwd);
            pSt.setString(5, dataNasterii);
            pSt.setString(6, sex);

            int rowsInserted = pSt.executeUpdate();

            pSt.close();

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public int login(String email, String parola) {

        String encrypted = getHashedPwd(email);

        if (BCrypt.checkpw(parola, encrypted)) {
            parola = encrypted;
        }

        int id = -1;

        try {
            Class.forName("org.postgresql.Driver");

            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            PreparedStatement pst = conn.prepareStatement("SELECT id FROM utilizatori WHERE email=? AND parola=? ");

            pst.setString(1, email);
            pst.setString(2, parola);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                id = rs.getInt("id");
            }

            rs.close();
            pst.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }


    public String getHashedPwd(String email) {

        String encrypted = null;

        try {
            Class.forName("org.postgresql.Driver");

            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String query = "SELECT parola FROM utilizatori WHERE email = ?";

            PreparedStatement pst = conn.prepareStatement(query);

            pst.setString(1, email);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                encrypted = rs.getString("parola");
            }

            rs.close();
            pst.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return encrypted;
    }

    public String getHashedPwd(Object id) {

        String encrypted = null;

        try {
            Class.forName("org.postgresql.Driver");

            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String query = "SELECT parola FROM utilizatori WHERE id = ?";

            PreparedStatement pst = conn.prepareStatement(query);

            pst.setObject(1, id);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                encrypted = rs.getString("parola");
            }

            rs.close();
            pst.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return encrypted;
    }

    public String info(int id) {

        String mesaj = null;

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String query = "SELECT mesaj FROM mesaje WHERE id = ?";

            PreparedStatement pSt = conn.prepareStatement(query);
            pSt.setLong(1, id);

            ResultSet rs = pSt.executeQuery();

            while (rs.next()) {

                mesaj = rs.getString("mesaj");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mesaj;
    }


    public String numePrenume(int id) {

        String nume = null;
        String prenume = null;

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String query = "SELECT nume, prenume FROM utilizatori WHERE id = ?";

            PreparedStatement pSt = conn.prepareStatement(query);
            pSt.setLong(1, id);

            ResultSet rs = pSt.executeQuery();

            while (rs.next()) {

                nume = rs.getString("nume");
                prenume = rs.getString("prenume");

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nume.trim() + " " + prenume.trim();

    }


    public void storeValues(double imc, double rmb, double nc, double mi, Object iduser) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String query = "INSERT INTO istoric (imc,rmb,nc,mi,iduser,datap) VALUES (?,?,?,?,?,?)";

            PreparedStatement pSt = conn.prepareStatement(query);

            pSt.setDouble(1, imc);
            pSt.setDouble(2, rmb);
            pSt.setDouble(3, nc);
            pSt.setDouble(4, mi);
            pSt.setObject(5, iduser);
            pSt.setString(6, dateFormat.format(date));

            int rowInserted = pSt.executeUpdate();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List getIMCfromDb(Object iduser) {

        List listIMC = new ArrayList();


        double imc;

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String query = "SELECT imc FROM istoric WHERE iduser = ? ORDER BY datap ASC ";

            PreparedStatement pSt = conn.prepareStatement(query);
            pSt.setObject(1, iduser);

            ResultSet rs = pSt.executeQuery();

            while (rs.next()) {

                imc = rs.getDouble("imc");

                listIMC.add(imc);

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listIMC;

    }


    public void updateValues(double imc, double rmb, double nc, double mi, Object iduser) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();


        try {
            Class.forName("org.postgresql.Driver");

            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String query = "UPDATE istoric SET imc = ?,rmb = ?,nc = ?,mi = ? WHERE iduser = ? AND datap = ?";

            PreparedStatement pSt = conn.prepareStatement(query);

            pSt.setDouble(1, imc);
            pSt.setDouble(2, rmb);
            pSt.setDouble(3, nc);
            pSt.setDouble(4, mi);
            pSt.setObject(5, iduser);
            pSt.setString(6, dateFormat.format(date));

            int rowsUpdated = pSt.executeUpdate();

            pSt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean compareDate(Object id, String date) {

        List listOfDates = new ArrayList();

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String query = "SELECT datap FROM istoric WHERE iduser = ?";

            PreparedStatement pSt = conn.prepareStatement(query);
            pSt.setObject(1, id);

            ResultSet rs = pSt.executeQuery();


            while (rs.next()) {

                String dbDate = rs.getString("datap");
                listOfDates.add(dbDate);
            }
            pSt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfDates.contains(date);
    }

    public void updatePassword(String email, String parola) {

        String pwd = BCrypt.hashpw(parola, BCrypt.gensalt());

        try {
            Class.forName("org.postgresql.Driver");

            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String query = "UPDATE utilizatori SET parola = ? WHERE email = ?";

            PreparedStatement pSt = conn.prepareStatement(query);

            pSt.setString(1, pwd);
            pSt.setString(2, email);

            int rowsUpdated = pSt.executeUpdate();

            pSt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void setNewPassword(String parola, Object id) {

        String pwd = BCrypt.hashpw(parola, BCrypt.gensalt());

        try {
            Class.forName("org.postgresql.Driver");

            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String query = "UPDATE utilizatori SET parola = ? WHERE id=?";

            PreparedStatement pSt = conn.prepareStatement(query);

            pSt.setString(1, pwd);
            pSt.setObject(2, id);

            int rowsUpdated = pSt.executeUpdate();

            pSt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveImgPath(Object id, String path) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        try {
            Class.forName("org.postgresql.Driver");

            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String query = "INSERT INTO poze (imgpath, userid, datap) VALUES (?,?,?)";

            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, path);
            pst.setObject(2, id);
            pst.setString(3, dateFormat.format(date));

            int rowsInserted = pst.executeUpdate();

            pst.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List getImgPaths(Object id) {

        List<String> imgPaths = new ArrayList<>();

        int width = 180;
        int height = 180;


        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String query = "SELECT imgpath FROM poze WHERE userid = ?";

            PreparedStatement pSt = conn.prepareStatement(query);
            pSt.setObject(1, id);

            ResultSet rs = pSt.executeQuery();

            int contor = -1;

            while (rs.next()) {

                contor++;
                String path = rs.getString("imgpath");

                String imgId = "id=\"myImg" + contor + "\"";

                String imgTag = "<img " + imgId + " src=" + path + " width=" + width + " height=" + height + ">";

                imgPaths.add(imgTag);
            }
            pSt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return imgPaths;

    }

    public List getDeletedImgPaths(Object id) {

        List<String> imgPaths = new ArrayList<>();

        int width = 180;
        int height = 180;


        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String query = "SELECT imgpath FROM pozesterse WHERE userid = ?";

            PreparedStatement pSt = conn.prepareStatement(query);
            pSt.setObject(1, id);

            ResultSet rs = pSt.executeQuery();

            int contor = -1;

            while (rs.next()) {

                contor++;
                String path = rs.getString("imgpath");

                String imgId = "id=\"myImg" + contor + "\"";

                String imgTag = "<img " + imgId + " src=" + path + " width=" + width + " height=" + height + ">";

                imgPaths.add(imgTag);
            }
            pSt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return imgPaths;

    }

    public List getPathsFromDeletedWithoutTags(Object id) {

        List<String> imgPaths = new ArrayList<>();

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String query = "SELECT imgpath FROM pozesterse WHERE userid = ?";

            PreparedStatement pSt = conn.prepareStatement(query);
            pSt.setObject(1, id);

            ResultSet rs = pSt.executeQuery();

            while (rs.next()) {

                String path = rs.getString("imgpath");
                imgPaths.add(path);
            }
            pSt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return imgPaths;

    }

    public void deleteImgPath(String path, Object id) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String query1 = "INSERT INTO pozesterse (imgpath, userid, datap) VALUES (?,?,?)";
            String query2 = "DELETE FROM poze WHERE imgpath = ?";

            PreparedStatement pSt1 = conn.prepareStatement(query1);
            pSt1.setString(1, path);
            pSt1.setObject(2, id);
            pSt1.setString(3, dateFormat.format(date));

            PreparedStatement pSt2 = conn.prepareStatement(query2);
            pSt2.setString(1, path);

            int rowInserted = pSt1.executeUpdate();
            int rowsDeleted = pSt2.executeUpdate();

            pSt1.close();
            pSt2.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void emptyTrash(Object id) {

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String query = "DELETE FROM pozesterse WHERE userid = ?";

            PreparedStatement pSt = conn.prepareStatement(query);
            pSt.setObject(1, id);

            int rowsDeleted = pSt.executeUpdate();

            pSt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setProfileImgPath(String path, Object id) {

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String query = "INSERT INTO pozaprofil (imgpath, userid) VALUES (?,?)";

            PreparedStatement pSt = conn.prepareStatement(query);
            pSt.setString(1, path);
            pSt.setObject(2, id);


            int rowInserted = pSt.executeUpdate();

            pSt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String getProfileImgPath(Object id) {

        String path = null;

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String query = "SELECT imgpath FROM pozaprofil WHERE userid = ?";

            PreparedStatement pSt = conn.prepareStatement(query);
            pSt.setObject(1, id);

            ResultSet rs = pSt.executeQuery();

            while (rs.next()) {

                path = rs.getString("imgpath");
            }
            pSt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return path;

    }

    public void updateProfileImgPath(Object id, String path) {

        try {
            Class.forName("org.postgresql.Driver");

            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String query = "UPDATE pozaprofil SET imgpath = ? WHERE userid = ?";

            PreparedStatement pSt = conn.prepareStatement(query);
            pSt.setString(1, path);
            pSt.setObject(2, id);


            int rowUpdated = pSt.executeUpdate();

            pSt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveMessage(String mesaj, Object id, String date) {


        try {
            Class.forName("org.postgresql.Driver");

            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String query = "INSERT INTO conversatie (mesaj,iduser,data)  VALUES (?,?,?)";

            PreparedStatement pSt = conn.prepareStatement(query);
            pSt.setString(1, mesaj);
            pSt.setObject(2, id);
            pSt.setString(3, date);

            int rowsInserted = pSt.executeUpdate();

            pSt.close();

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<String> messages(Object id) {

        List<String> messages = new ArrayList<>();

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String query = "SELECT * FROM conversatie ORDER BY id DESC LIMIT 10";

            PreparedStatement pSt = conn.prepareStatement(query);

            ResultSet rs = pSt.executeQuery();

            while (rs.next()) {

                String message = rs.getString("mesaj");
                int iduser = rs.getInt("iduser");
                String data = rs.getString("data");

                if ((int)id == iduser) {
                    String current = "<div class=\"outgoing_msg\">\n" +
                            "                        <div class=\"sent_msg\">\n" +
                            "                            <p>" + message + "</p>\n" +
                            "                            <span class=\"time_date\">" + data + "</span> </div>\n" +
                            "                    </div>";

                    messages.add(current);
                }
                else{

                    String name = numePrenume(iduser);
                    String userPic = getProfileImgPath(iduser);

                    String others = "<div class=\"incoming_msg\">\n" +
                            "                        <div class=\"incoming_msg_img\"> <img style=\"size: 20px\" src="+userPic+" alt=\"sunil\"> </div>\n" +
                            "                        <div class=\"received_msg\">\n" +
                            "                            <div class=\"received_withd_msg\">\n" +
                            "                                <p style=\"color: red\" >"+name+"</p>\n" +
                            "                                <p>"+message+"</p>\n" +
                            "                                <span class=\"time_date\">"+data+"</span></div>\n" +
                            "                        </div>\n" +
                            "                    </div>";
                    messages.add(others);
                }
            }
            pSt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }


    public static void main(String[] args) {

        Postgres db = new Postgres();


    }

}


