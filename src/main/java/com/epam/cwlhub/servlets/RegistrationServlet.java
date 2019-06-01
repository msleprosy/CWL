package com.epam.cwlhub.servlets;


import com.epam.cwlhub.constants.Endpoints;
import com.epam.cwlhub.dao.AuthentificationService;
import com.epam.cwlhub.dao.AuthentificationServiceImpl;
import com.epam.cwlhub.entities.user.UserEntity;
import com.epam.cwlhub.entities.user.UserType;
import com.epam.cwlhub.storage.dbconnection.DBConnection;
import com.epam.cwlhub.storage.dbconnection.DBConnector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Endpoint;
import java.io.IOException;
import java.sql.Connection;

public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1;
    private final DBConnection dbConnection = DBConnector.getInstance();
    private AuthentificationService authentificationService = new AuthentificationServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        UserEntity user = new UserEntity();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setUserType(UserType.SIMPLE_USER);

        try {
            Connection conn = dbConnection.getDBConnection();
            authentificationService.registerUser(conn, user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect(Endpoints.USERDETAILS);
    }
}