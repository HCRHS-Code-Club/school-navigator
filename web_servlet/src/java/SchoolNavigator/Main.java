package SchoolNavigator;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Main  extends HttpServlet{

    @Override
    public void init() throws ServletException
    {
    }
    
    
    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
              throws ServletException, IOException
    {
        // Set response content type
        response.setContentType("text/html");

        // Actual logic goes here.
        RequestDispatcher view = request.getRequestDispatcher("/pages/main.html");
        view.forward(request, response);
    }

    @Override
    public void destroy()
    {
        // do nothing.
    }
}
