
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author vishw
 * signup database
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
             String fname = request.getParameter("firstname");
        String lname = request.getParameter("lastname");
        String uname = request.getParameter("Username");
        String upass = request.getParameter("Password");
  


        String sql = "insert into singup (firstname,lastname,Username,Pass)values(?,?,?,?)";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "root");
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setString(1, fname);
            ps.setString(2, lname);
          
            ps.setString(3, uname);
            ps.setString(4, upass);
   
           
          
            int rowaffected=ps.executeUpdate();
            if(rowaffected>0){
            response.sendRedirect("loginpage.html");
            }
            else{
            out.println("<center>try again</center>");}
            ps.close();
            con.close();
        } catch (Exception e) {
            
            out.println("database connection errot"+e);
        }

    }

}
