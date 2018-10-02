
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.util.List;


@WebServlet("/manipulate")
public class ImagesServlet extends HttpServlet {

    private Postgres db = new Postgres();


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {

        String action = req.getParameter("action");

        if (action != null && action.equals("delete"))
            delete(req, resp);
        if (action != null && action.equals("set"))
            setProfilePicture(req, resp);
        if (action != null && action.equals("empty"))
            emptyTrashAndPermanentlyDelete(req, resp);
        if (action != null && action.equals("restore"))
            restore(req, resp);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession();
        Object id = session.getAttribute("userid");

        String path = req.getParameter("path");

        db.deleteImgPath(path, id);

        List imgPaths = db.getImgPaths(id);

        session.setAttribute("images", imgPaths);
        session.setAttribute("size", imgPaths.size());

        ImagesServlet.trash(req, resp);

    }

    private void setProfilePicture(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();

        Object id = session.getAttribute("userid");

        String path = req.getParameter("path");

        db.setProfileImgPath(path, id);
        db.updateProfileImgPath(id, path);

        String profilePic = db.getProfileImgPath(id);
        session.setAttribute("profilePic", profilePic);

    }

    public static void trash(HttpServletRequest req, HttpServletResponse resp) {

        Postgres db = new Postgres();
        HttpSession session = req.getSession();
        Object id = session.getAttribute("userid");

        List deletedPics = db.getDeletedImgPaths(id);
        session.setAttribute("deletedPics", deletedPics);
    }
    private void emptyTrashAndPermanentlyDelete(HttpServletRequest req, HttpServletResponse resp) {
        String confirmation = req.getParameter("confirmation");

        HttpSession session = req.getSession();
        Object id = session.getAttribute("userid");

        List deletedImg = db.getPathsFromDeletedWithoutTags(id);

        Runnable runnable = () -> deletedImg.forEach((path) ->{
            String OSPath = path.toString()
                    .replace("../uploads/",
                    "C:\\Users\\ysf\\IdeaProjects\\EVAN\\target\\EVAN\\uploads\\");
            File file = new File(OSPath);
            file.delete();
        });
        runnable.run();




        if (confirmation.equals("ok")) {
            db.emptyTrash(id);
        }

        List deletedPics = db.getDeletedImgPaths(id);
        session.setAttribute("deletedPics", deletedPics);

    }
    private void restore (HttpServletRequest req, HttpServletResponse resp){

        String ok = req.getParameter("ok");

        HttpSession session = req.getSession();
        Object id = session.getAttribute("userid");

        List deletedImg = db.getPathsFromDeletedWithoutTags(id);

        if (ok.equals("ok")){

            deletedImg.forEach((path) ->{

                db.saveImgPath(id,path.toString());
            });
            db.emptyTrash(id);
        }

        List deletedPics = db.getDeletedImgPaths(id);
        session.setAttribute("deletedPics", deletedPics);

        List imgPaths = db.getImgPaths(id);
        session.setAttribute("images", imgPaths);
        session.setAttribute("size", imgPaths.size());

    }
}