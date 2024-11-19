import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @ClassName VisitCountServlet
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/13 17:47
 * @Version 1.0
 */
@WebServlet("/visit")
public class VisitCountServlet extends HttpServlet {

    private int count = 0;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        count++;
        resp.getWriter().println("You have visited this page " + count + " times.");
    }
}
