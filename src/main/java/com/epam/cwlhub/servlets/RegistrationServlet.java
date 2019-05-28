package com.epam.cwlhub.servlets;



import com.epam.cwlhub.entities.user.UserEntity;
import com.epam.cwlhub.entities.user.UserType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1;
    private UserDAO userDAO;


    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
       // String user_type = request.getParameter("user_type");

        UserEntity user = new UserEntity();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setUserType(UserType.SIMPLE_USER);

        try {
            userDAO.registerUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("userdetails.jsp");
    }
}