package com.epam.cwlhub.servlets;

import com.epam.cwlhub.entities.user.UserEntity;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

//@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }
    public UserEntity checkLogin(String email, String password) throws SQLException
    {
        String jdbcURL = "jdbc:postgresql://ECSC00A04EEC.epam.com:5432/postgres";
        String dbUser = "postgres";
        String dbPassword = "CWLHubHardPassword228";


        //Class.forName("org.postgresql.Driver");
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
        String sql = "SELECT * FROM users WHERE email = ? and password = ?";
        System.out.println(sql);
        //  String sql = "SELECT * FROM persons";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, email);
        statement.setString(2, password);

        ResultSet result = statement.executeQuery();

        UserEntity user = null;



        if (result.next()) {
            user = new UserEntity();
            user.setLastName(result.getString("lastName"));
            user.setEmail(email);
        }

        connection.close();

        return user;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            UserEntity user = checkLogin(email, password);
            String destPage = "login.jsp";

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                destPage = "home.jsp";
            } else {
                String message = "Invalid email/password";
                request.setAttribute("message: ", message);
                response.getWriter().println("message: " + message);
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
            dispatcher.forward(request, response);

        }
        catch (SQLException ex ) {
            throw new ServletException(ex);
        }
    }

}
