package com.epam.cwlhub.servlets.snippet;

import com.epam.cwlhub.services.impl.SnippetServiceImpl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name="SnippetUploadServlet", urlPatterns = "/upload")
@MultipartConfig(maxFileSize = 16177215)
public class SnippetUploadServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println(request.getParameter("fileName"));
        System.out.println(request.getParameter("tags"));
        if (request.getParameter("fileName").equals("")
                || request.getParameter("tags").equals("")
                || request.getPart("cwl").getSize() == 0){
            request.setAttribute("errorMessage", "Empty fields are not allowed!");
            request.getRequestDispatcher("/UploadSnippet.jsp").forward(request, response);
        } else {
            SnippetServiceImpl snippetService = SnippetServiceImpl.getInstance();
            Boolean state = snippetService.createSnippetObjectFromRequest(request);
            if (state) {
                String message = "File successfully uploaded!";
                request.setAttribute("Message", message);
                getServletContext().getRequestDispatcher("/Message.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "This file already exists in the database!");
                request.getRequestDispatcher("/UploadSnippet.jsp").forward(request, response);
            }
        }
    }
}



