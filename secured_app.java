import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommandExecutorServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private static final String LOCALHOST = "localhost";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String command = request.getParameter("command");
        
        if (command != null && command.contains(LOCALHOST)) {
            try {
                Process process = Runtime.getRuntime().exec(command);
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.getWriter().append(line).append("\n");
                }
            } catch (IOException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while executing the command.");
                e.printStackTrace();
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Command must contain the string 'localhost'.");
        }
    }
}