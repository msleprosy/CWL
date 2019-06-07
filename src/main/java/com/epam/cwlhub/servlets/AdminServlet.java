package com.epam.cwlhub.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.cwlhub.constants.Endpoints.ADMIN_PAGE;
import static com.epam.cwlhub.constants.Endpoints.ADMIN_PAGE_URL;

@WebServlet(name = "AdminServlet", urlPatterns = ADMIN_PAGE_URL)
public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(ADMIN_PAGE).forward(req, resp);
    }
}
