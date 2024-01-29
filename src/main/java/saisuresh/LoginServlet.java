package saisuresh;

//import java.io.IOException;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * Servlet implementation class LoginServlet
// */
//@WebServlet("/LoginServlet")
//public class LoginServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//       
//    /**
//     * @see HttpServlet#HttpServlet()
//     */
//    public LoginServlet() {
//        super();
//        // TODO Auto-generated constructor stub
//    }
//
//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//	}
//
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doGet(request, response);
//	}
//
//}
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean isValid = validateCredentials(username, password);

        if (isValid) {
            response.sendRedirect("welcome.html"); // Redirect to welcome page upon successful login
        } else {
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Invalid credentials. Please try again.'); window.location='loginpage.html'</script>");
        }
    }

    private boolean validateCredentials(String username, String password) {
        // Database connection parameters
        String url = "jdbc:mysql://localhost:3306/cred_db";
        String dbUsername = "root";
        String dbPassword = "Admin@123";

        try {
            // Load JDBC Driver
            Class.forName("com.mysql.jdbc.Driver");

            // Establish Connection
            Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);
            if (con !=null)
            {
            	System.out.println("Connection successfull");
            }else {
            	System.out.println("failed to Connect the mysql");
               
            }

            // Prepare and execute SQL query
            String query = "SELECT * FROM login WHERE username=? AND passwd=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            // Check if any rows are returned
            if (rs.next()) {
                con.close();
                return true; // Valid credentials
            } else {
                con.close();
                return false; // Invalid credentials
            }
        } catch (Exception e) {
            System.out.println(e);
            return false; // Error occurred, consider invalid credentials
        }
    }
}
