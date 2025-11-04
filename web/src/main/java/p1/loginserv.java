/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package p1;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.*;

/**
 *
 * @author vishw
 * login database
 */
@WebServlet(name = "loginserv", urlPatterns = {"/loginserv"})
public class loginserv extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String uname = request.getParameter("Username");
        String upass = request.getParameter("Password");
        
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "root");
                 PreparedStatement ps = con.prepareStatement("SELECT * FROM singup WHERE Username = ? AND Pass = ?")) {
                
                // Set the parameters for the SQL query
                ps.setString(1, uname);
                ps.setString(2, upass);
                
                // Execute the query
                try (ResultSet rs = ps.executeQuery()) {
                    // If a record is found, user is authenticated, forward to the dashboard
                    if (rs.next()) {
//                        HttpSession session = request.getSession(true);
//                        session.setAttribute("username", uname);
                        RequestDispatcher rd = request.getRequestDispatcher("dashboard.html");
                        rd.forward(request, response);
                    } 
                    // If no record is found, display an alert and include the login form again
                    else {
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('Invalid username or password! Please try again.');");
                        out.println("location='loginpage.html';");  // Assuming 'login.html' is your login page
                        out.println("</script>");
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            out.println("Error: " + e.getMessage());
        }
    }
}
