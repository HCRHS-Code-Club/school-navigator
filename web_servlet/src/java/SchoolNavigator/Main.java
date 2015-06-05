package SchoolNavigator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="SchoolNavigator", urlPatterns={"/nav"})
public class Main  extends HttpServlet{
    
    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
              throws ServletException, IOException
    {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        String serverAddress = "localhost";
        Socket s = new Socket(serverAddress, 1234);
        System.out.printf("Connected to: %s\n", s.getLocalAddress());
        PrintWriter output = new PrintWriter(s.getOutputStream(), true);
        output.println("100,918");
        System.out.println("Sent: 100,918");
        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String answer = "";
        String raw = "";
        while (true) {
            raw = input.readLine();
            if(raw.equals("END"))
                break;
            answer += raw + "<br/>";
        }
        
        
        out.print(answer);
    }
}
