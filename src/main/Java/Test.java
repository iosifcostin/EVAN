
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;


public class Test {

    static final String URL = "jdbc:postgresql://localhost:5432/iosif_costin";
    static final String USERNAME = "postgres";
    static final String PASSWORD = "asdfasdf";


    public static void main(String[] args) {
//        Email email = new Email();
//        String newPassword = RandomStringUtils.random(8, true, true);
//        String subiect = "Noua parola";
//        String mesaj = "Noua dumneavoastra parla EVAN este : " + newPassword;
//
//        email.send("iosif.costin@outlook.com",subiect,mesaj);

//        insertPic("C:\\home\\pic.jpg");
//        getPic("pic.jpg");

    }

    static void insertPic(String picPath) {


        try {
            Class.forName("org.postgresql.Driver");


            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String query = "INSERT INTO images (name,picture) VALUES (?,?)";

            File file = new File(picPath);

            FileInputStream fis = new FileInputStream(file);

            PreparedStatement pst = conn.prepareStatement(query);

            pst.setString(1, file.getName());
            pst.setBinaryStream(2, fis, (int) file.length());

            int rowsInserted = pst.executeUpdate();

            pst.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    static void getPic(String name) {


        try {
            Class.forName("org.postgresql.Driver");

            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String query = "SELECT picture FROM images WHERE name = ?";

            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, name);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                byte[] imgBytes = rs.getBytes(1);

                ByteArrayInputStream bis = new ByteArrayInputStream(imgBytes);
                BufferedImage bImage = ImageIO.read(bis);
                ImageIO.write(bImage, "jpg", new File("C:\\home\\output.jpg"));
            }
            rs.close();
            pst.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

