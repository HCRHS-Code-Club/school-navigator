package SchoolNavigator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="SchoolNavigator", urlPatterns={"/nav"})
public class Navigator  extends HttpServlet{
    
    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
              throws ServletException, IOException
    {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        String serverAddress = "localhost";
        String start = request.getParameter("start");
        String end = request.getParameter("end");
        try {
            Socket s = new Socket(serverAddress, 1234);
            System.out.printf("Connected to: %s\n", s.getLocalAddress());
            PrintWriter output = new PrintWriter(s.getOutputStream(), true);
            output.println(String.format("%s,%s", start, end));
            System.out.println(String.format("Sent: %s,%s", start, end));
            BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String answer = "";
            String raw;
            while (true) {
                raw = input.readLine();
                if(raw.equals("END"))
                    break;
                answer += raw + "<br/>";
            }
            out.print(answer);
            System.out.println(String.format("Recived: %s", answer));
        } catch(ConnectException e) {
            out.print("Cannot connect to map server");
        }
    }
}
